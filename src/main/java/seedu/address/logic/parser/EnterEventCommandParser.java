package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EnterEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates an {@link EnterEventCommand}.
 */
public class EnterEventCommandParser implements Parser<EnterEventCommand> {

    @Override
    public EnterEventCommand parse(String args) throws ParseException {
        if (args == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EnterEventCommand.MESSAGE_USAGE));
        }
        String trimmed = args.trim();
        String[] tokens = trimmed.split("\\s+");
        if (tokens.length != 2 || !tokens[0].equals("event")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EnterEventCommand.MESSAGE_USAGE));
        }

        Index index;
        index = ParserUtil.parseIndex(tokens[1]);

        return new EnterEventCommand(index);
    }
}

