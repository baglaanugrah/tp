package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExportCommand;

public class ExportCommandParserTest {

    private final ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_emptyArgs_usesDefaultPath() {
        assertParseSuccess(parser, "   ", new ExportCommand(""));
    }

    @Test
    public void parse_withPath_success() {
        assertParseSuccess(parser, " data/exports/out.csv ", new ExportCommand("data/exports/out.csv"));
    }
}
