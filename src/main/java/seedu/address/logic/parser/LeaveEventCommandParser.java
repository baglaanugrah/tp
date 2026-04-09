package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.LeaveEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates an {@link LeaveEventCommand}.
 */
public class LeaveEventCommandParser implements Parser<LeaveEventCommand> {

    /**
     * Parses the given argument string and creates a {@link LeaveEventCommand}.
     * Expected format: {@code event}.
     *
     * @param args raw argument string
     * @return parsed {@code LeaveEventCommand}
     * @throws ParseException if the input does not match the expected format
     */
    @Override
    public LeaveEventCommand parse(String args) throws ParseException {
        if (args == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LeaveEventCommand.MESSAGE_USAGE));
        }
        String trimmed = args.trim();
        String[] tokens = trimmed.split("\\s+");
        if (tokens.length != 1 || !tokens[0].equals("event")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LeaveEventCommand.MESSAGE_USAGE));
        }

        return new LeaveEventCommand();
    }
}
