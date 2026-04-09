package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Switches the UI context to the participants (address book) of the selected event.
 */
public class EnterEventCommand extends Command {

    public static final String COMMAND_WORD = "enter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " event INDEX\n"
            + "Example: " + COMMAND_WORD + " event 1";

    public static final String MESSAGE_ENTER_EVENT_SUCCESS = "Entered event: %1$s";

    private final Index index;

    /**
     * @param index of the event to enter
     */
    public EnterEventCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    /**
     * Enters the selected event and switches the app into participant-list mode for that event.
     *
     * @param model {@link Model} containing the currently displayed event list
     * @return command result message indicating the entered event
     * @throws CommandException if already inside an event or if the event index is invalid
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Event> lastShownEventList = model.getFilteredEventList();

        if (model.isInEventParticipantsMode()) {
            throw new CommandException(Messages.MESSAGE_ALREADY_IN_EVENT);
        }

        if (index.getZeroBased() >= lastShownEventList.size()) {
            throw new CommandException("The event index provided is invalid");
        }

        Event eventToEnter = lastShownEventList.get(index.getZeroBased());
        model.enterEvent(eventToEnter);
        return new CommandResult(String.format(MESSAGE_ENTER_EVENT_SUCCESS, eventToEnter.getName().fullName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EnterEventCommand)) {
            return false;
        }

        EnterEventCommand e = (EnterEventCommand) other;
        return index.equals(e.index);
    }
}
