package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;


/**
 * Lists all events or participants to the user depending on the current app mode.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all events or participants.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS_EVENTS = "Listed all events";
    public static final String MESSAGE_SUCCESS_PARTICIPANTS = "Listed all participants";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (model.isInEventParticipantsMode()) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_SUCCESS_PARTICIPANTS);
        }

        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(MESSAGE_SUCCESS_EVENTS);
    }
}
