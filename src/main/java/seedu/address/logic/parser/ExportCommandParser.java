package seedu.address.logic.parser;

import seedu.address.logic.commands.ExportCommand;

/**
 * Parses input arguments and creates an {@link ExportCommand} object.
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    @Override
    public ExportCommand parse(String args) {
        if (args == null) {
            return new ExportCommand("");
        }
        return new ExportCommand(args.trim());
    }
}
