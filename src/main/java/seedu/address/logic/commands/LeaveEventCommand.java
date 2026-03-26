package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Switches the UI context back to the events list.
 */
public class LeaveEventCommand extends Command {

    public static final String COMMAND_WORD = "leave";

    public static final String MESSAGE_LEAVE_EVENT_SUCCESS = "Returned to event list";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.leaveEvent();
        return new CommandResult(MESSAGE_LEAVE_EVENT_SUCCESS);
    }
}

