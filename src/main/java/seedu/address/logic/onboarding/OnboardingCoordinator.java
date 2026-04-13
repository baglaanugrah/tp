package seedu.address.logic.onboarding;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGN_TEAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.Logic;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.AssignTeamCommand;
import seedu.address.logic.commands.EnterEventCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * Tracks first-launch onboarding steps and produces supplemental UI messages.
 * Command matching uses the first word of input (same convention as
 * {@link seedu.address.logic.parser.AddressBookParser}).
 * Messages are personalised using the user's events and participants from {@link Logic} when available.
 */
public class OnboardingCoordinator {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final int TOTAL_STEPS = 5;

    private OnboardingStep currentStep = OnboardingStep.ADD_EVENT;
    private boolean flowFinishedInSession;

    /** Filled after a successful {@code addevent} during onboarding. */
    private String tutorialEventName;
    private int tutorialEventIndex = 1;

    /** Filled after a successful {@code add} during onboarding. */
    private String tutorialParticipantName;

    /** Filled after a successful {@code assign} during onboarding. */
    private String tutorialTeamName;

    /**
     * Shown once when the main window loads and onboarding is still active.
     */
    public String getWelcomeMessage(Logic logic) {
        return "Welcome! Thank you for using TeamEventPro. This tutorial will help you get familiar with the app by "
                + "introducing you to some simple commands. If you are already familiar with the app, you can skip the "
                + "tutorial by clicking on the button in the Help menu." + "\n\n"
                + currentStepReminder(logic);
    }

    /**
     * Reminder for the current step (used after parse/execution errors).
     */
    public String getCurrentStepReminder(Logic logic) {
        return currentStepReminder(logic);
    }

    private String currentStepReminder(Logic logic) {
        return "Onboarding — Step " + currentStep.stepNumber() + "/" + TOTAL_STEPS + ":\n"
                + instructionForCurrentStep(logic);
    }

    /**
     * Sets the onboarding step from persisted preferences.
     * Out-of-range values are clamped to supported tutorial steps.
     *
     * @param currentStep persisted step number
     */
    public void setCurrentStep(int currentStep) {
        this.currentStep = OnboardingStep.fromStepNumber(currentStep);
    }

    /**
     * Restores onboarding state from persisted progress and the current model state.
     */
    public void restoreProgress(Logic logic) {
        refreshTutorialEventFromModel(logic);

        if (currentStep.stepNumber() > OnboardingStep.ADD_EVENT.stepNumber() && tutorialEventName == null) {
            currentStep = OnboardingStep.ADD_EVENT;
            logic.setOnboardingTutorialStep(currentStep.stepNumber());
            return;
        }

        if (logic.isInEventParticipantsMode()) {
            if (currentStep.stepNumber() >= OnboardingStep.ASSIGN_TEAM.stepNumber()
                    || currentStep == OnboardingStep.SEARCH) {
                refreshTutorialParticipantFromModel(logic);
            }
            if (currentStep == OnboardingStep.SEARCH) {
                refreshTutorialTeamFromModel(logic);
            }
        }
    }

    /**
     * @param commandText raw user input
     * @param commandSucceeded whether parsing and execution succeeded
     * @param logic current app logic (for personalised copy)
     * @return text to show in the onboarding panel (empty if none)
     */
    public Optional<String> onCommandExecuted(String commandText, boolean commandSucceeded, Logic logic) {
        if (flowFinishedInSession) {
            return Optional.empty();
        }
        if (!commandSucceeded) {
            return Optional.of(currentStepReminder(logic));
        }

        Optional<String> commandWordOpt = extractCommandWord(commandText);
        if (commandWordOpt.isEmpty()) {
            return Optional.of(currentStepReminder(logic));
        }
        String commandWord = commandWordOpt.get();

        if (shouldAllowEventReentry(commandWord, logic)) {
            restoreProgress(logic);
            return Optional.of(currentStepReminder(logic));
        }

        if (!currentStep.matchesCommandWord(commandWord)) {
            return Optional.of("You've completed another action, but the tutorial still expects this step:\n"
                    + currentStepReminder(logic));
        }

        switch (currentStep) {
        case ADD_EVENT:
            refreshTutorialEventFromModel(logic);
            currentStep = OnboardingStep.ENTER_EVENT;
            logic.setOnboardingTutorialStep(currentStep.stepNumber());
            return Optional.of(buildMessageAfterEventCreated(logic));
        case ENTER_EVENT:
            currentStep = OnboardingStep.ADD_PARTICIPANT;
            logic.setOnboardingTutorialStep(currentStep.stepNumber());
            return Optional.of(buildMessageAfterEnteredEvent(logic));
        case ADD_PARTICIPANT:
            refreshTutorialParticipantFromModel(logic);
            currentStep = OnboardingStep.ASSIGN_TEAM;
            logic.setOnboardingTutorialStep(currentStep.stepNumber());
            return Optional.of(buildMessageAfterParticipantAdded(logic));
        case ASSIGN_TEAM:
            refreshTutorialTeamFromModel(logic);
            currentStep = OnboardingStep.SEARCH;
            logic.setOnboardingTutorialStep(currentStep.stepNumber());
            return Optional.of(buildMessageAfterTeamAssigned(logic));
        case SEARCH:
            flowFinishedInSession = true;
            return Optional.of("Tutorial complete! You're ready to use the app.\n"
                    + "(You can dismiss this message with your next command.)");
        default:
            return Optional.empty();
        }

    }

    /**
     * Returns whether the tutorial flow has reached its final step in this app session.
     */
    public boolean isFlowFinishedInSession() {
        return flowFinishedInSession;
    }

    /**
     * Returns whether a successful {@code enter event ...} should be treated as a prerequisite action
     * while resuming onboarding from a participant-related step.
     */
    private boolean shouldAllowEventReentry(String commandWord, Logic logic) {
        return currentStep.stepNumber() >= OnboardingStep.ADD_PARTICIPANT.stepNumber()
                && EnterEventCommand.COMMAND_WORD.equals(commandWord)
                && logic.isInEventParticipantsMode();
    }

    /**
     * Stores the most recently shown event and its displayed index for personalized instructions.
     */
    private void refreshTutorialEventFromModel(Logic logic) {
        var events = logic.getFilteredEventList();
        if (events.isEmpty()) {
            return;
        }
        Event last = events.get(events.size() - 1);
        tutorialEventName = last.getName().fullName;
        tutorialEventIndex = events.indexOf(last) + 1;
    }

    /**
     * Stores the most recently shown participant name for personalized instructions.
     */
    private void refreshTutorialParticipantFromModel(Logic logic) {
        var persons = logic.getFilteredPersonList();
        if (persons.isEmpty()) {
            return;
        }
        Person last = persons.get(persons.size() - 1);
        tutorialParticipantName = last.getName().fullName;
    }

    /**
     * Stores any currently assigned team name for personalized instructions.
     */
    private void refreshTutorialTeamFromModel(Logic logic) {
        for (Person p : logic.getFilteredPersonList()) {
            if (p.getTeam().isPresent()) {
                tutorialTeamName = p.getTeam().get().teamName;
                return;
            }
        }
    }

    private String eventNameOrPlaceholder() {
        return tutorialEventName != null && !tutorialEventName.isBlank() ? tutorialEventName : "your event";
    }

    private String participantNameOrPlaceholder() {
        return tutorialParticipantName != null && !tutorialParticipantName.isBlank()
                ? tutorialParticipantName : "your participant";
    }

    private String buildMessageAfterEventCreated(Logic logic) {
        return "Event created: \"" + eventNameOrPlaceholder() + "\".\n\n"
                + "Next — Step " + currentStep.stepNumber() + "/" + TOTAL_STEPS + ":\n"
                + instructionForCurrentStep(logic);
    }

    private String buildMessageAfterEnteredEvent(Logic logic) {
        return "You're now viewing participants for \"" + eventNameOrPlaceholder() + "\".\n\n"
                + "Next — Step " + currentStep.stepNumber() + "/" + TOTAL_STEPS + ":\n"
                + instructionForCurrentStep(logic);
    }

    private String buildMessageAfterParticipantAdded(Logic logic) {
        return "Participant added: \"" + participantNameOrPlaceholder() + "\".\n\n"
                + "Next — Step " + currentStep.stepNumber() + "/" + TOTAL_STEPS + ":\n"
                + instructionForCurrentStep(logic);
    }

    private String buildMessageAfterTeamAssigned(Logic logic) {
        String team = tutorialTeamName != null && !tutorialTeamName.isBlank() ? tutorialTeamName : "your team";
        return "Team assigned: \"" + team + "\" for \"" + participantNameOrPlaceholder() + "\".\n\n"
                + "Last step — Step " + currentStep.stepNumber() + "/" + TOTAL_STEPS + ":\n"
                + instructionForCurrentStep(logic) + "\n"
                + "If you wish to leave the event mode after executing this command, enter `leave event` ";
    }

    /**
     * Builds the command instruction block for the current onboarding step.
     */
    private String instructionForCurrentStep(Logic logic) {
        if (requiresEventReentry(logic)) {
            return "Resume by reopening \"" + eventNameOrPlaceholder() + "\":\n"
                    + EnterEventCommand.COMMAND_WORD + " event " + tutorialEventIndex + "\n"
                    + "(Then continue with this onboarding step.)";
        }

        switch (currentStep) {
        case ADD_EVENT:
            return "Create an event:\n"
                    + AddEventCommand.COMMAND_WORD + " "
                    + PREFIX_NAME + "Orientation Day "
                    + PREFIX_DATE + "2026-08-20 "
                    + PREFIX_LOCATION + "COM1 "
                    + PREFIX_DESCRIPTION + "Welcome session";
        case ENTER_EVENT:
            return "Open \"" + eventNameOrPlaceholder() + "\" to manage participants:\n"
                    + EnterEventCommand.COMMAND_WORD + " event " + tutorialEventIndex + "\n"
                    + "(Index matches the event list; yours is shown above.)";
        case ADD_PARTICIPANT:
            return "Add someone to \"" + eventNameOrPlaceholder() + "\":\n"
                    + AddCommand.COMMAND_WORD + " "
                    + PREFIX_NAME + "Jane Doe "
                    + "p/91234567 e/jane@example.com a/Blk 123";
        case ASSIGN_TEAM:
            return "Put \"" + participantNameOrPlaceholder() + "\" on a team (use their list index):\n"
                    + AssignTeamCommand.COMMAND_WORD + " 1 " + PREFIX_ASSIGN_TEAM + "Alpha";
        case SEARCH:
            return "Try searching by keyword (matches name, phone, email, team, etc.):\n"
                    + SearchCommand.COMMAND_WORD + " " + searchKeywordSuggestion();
        default:
            return "";
        }
    }

    private String searchKeywordSuggestion() {
        if (tutorialParticipantName != null && !tutorialParticipantName.isBlank()) {
            return tutorialParticipantName.trim().split("\\s+")[0];
        }
        if (tutorialTeamName != null && !tutorialTeamName.isBlank()) {
            return tutorialTeamName;
        }
        return "Jane";
    }

    /**
     * Returns whether the current step needs event context to continue.
     */
    private boolean requiresEventReentry(Logic logic) {
        return currentStep.stepNumber() >= OnboardingStep.ADD_PARTICIPANT.stepNumber()
                && !logic.isInEventParticipantsMode();
    }

    /**
     * Extracts the first word of trimmed input, if any.
     */
    public static Optional<String> extractCommandWord(String userInput) {
        String trimmed = userInput.trim();
        if (trimmed.isEmpty()) {
            return Optional.empty();
        }
        Matcher matcher = BASIC_COMMAND_FORMAT.matcher(trimmed);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        return Optional.of(matcher.group("commandWord"));
    }

    private enum OnboardingStep {
        ADD_EVENT(AddEventCommand.COMMAND_WORD),
        ENTER_EVENT(EnterEventCommand.COMMAND_WORD),
        ADD_PARTICIPANT(AddCommand.COMMAND_WORD),
        ASSIGN_TEAM(AssignTeamCommand.COMMAND_WORD),
        SEARCH(SearchCommand.COMMAND_WORD);

        private final String expectedCommandWord;

        OnboardingStep(String expectedCommandWord) {
            this.expectedCommandWord = expectedCommandWord;
        }

        int stepNumber() {
            return ordinal() + 1;
        }

        boolean matchesCommandWord(String commandWord) {
            return expectedCommandWord.equals(commandWord);
        }

        /**
         * Maps a persisted step number to the nearest valid onboarding step.
         */
        static OnboardingStep fromStepNumber(int stepNumber) {
            int clampedStep = Math.min(Math.max(stepNumber, 1), values().length);
            return values()[clampedStep - 1];
        }
    }
}
