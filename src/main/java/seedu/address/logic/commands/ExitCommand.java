package seedu.address.logic.commands;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exits the application.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (model.isInEventParticipantsMode()) {
            throw new CommandException(Messages.MESSAGE_LEAVE_EVENT_FIRST_BEFORE_EXIT);
        }
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true, false);
    }

}
