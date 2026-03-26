package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AssignTeamCommand;
import seedu.address.model.person.Team;

public class AssignTeamCommandParserTest {

    private final AssignTeamCommandParser parser = new AssignTeamCommandParser();

    @Test
    public void parse_validArgs_returnsAssignTeamCommand() {
        assertParseSuccess(parser, "1 team/Alpha",
                new AssignTeamCommand(INDEX_FIRST_PERSON, new Team("Alpha")));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertParseFailure(parser, "1 Alpha",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTeamCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTeamName_throwsParseException() {
        assertParseFailure(parser, "1 team/Alpha-Team", Team.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        assertParseFailure(parser, "team/Alpha",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTeamCommand.MESSAGE_USAGE));
    }
}
