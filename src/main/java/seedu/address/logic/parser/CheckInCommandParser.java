package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CheckInCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CheckInCommand object
 */
public class CheckInCommandParser implements Parser<CheckInCommand> {

    /**
     * Parses the given argument string and creates a {@link CheckInCommand}.
     *
     * @param args raw argument string containing an index
     * @return parsed {@code CheckInCommand}
     * @throws ParseException if the user input does not conform to the expected format
     */
    public CheckInCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CheckInCommand.MESSAGE_USAGE), ive);
        }

        return new CheckInCommand(index);

    }
}
