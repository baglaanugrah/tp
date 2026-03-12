package seedu.address.logic.commands;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;

/**
 * Filters and lists persons in the address book based on various criteria.
 * Supported prefixes:
 * r/ for RSVP status, d/ for dietary requirement, t/ for team, a/ for
 * attendance status.
 *
 * This class currently contains only the skeleton structure for future
 * implementation.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters the person list by a single criterion.\n"
            + "Parameters: PREFIX/VALUE\n"
            + "Supported prefixes:\n"
            + "  r/ RSVP status\n"
            + "Example: " + COMMAND_WORD + " r/yes";

    public static final String MESSAGE_SUCCESS = "Filter command added";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
