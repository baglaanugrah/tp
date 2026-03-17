package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.PersonMatchesFilterPredicate;
import seedu.address.model.person.RsvpStatus;
import seedu.address.model.tag.Tag;

/**
 * Contains tests for {@link FilterCommandParser}.
 * These tests verify that the parser accepts only the allowed
 * combinations of prefixes and builds the correct {@link FilterCommand}.
 */
public class FilterCommandParserTest {

    // Parser under test
    private final FilterCommandParser parser = new FilterCommandParser();

    /**
     * Empty or whitespace-only arguments should be rejected.
     */
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    /**
     * Valid case: single RSVP criterion with value "yes".
     */
    @Test
    public void parse_validRsvpYes_returnsFilterCommand() {
        PersonMatchesFilterPredicate expectedPredicate =
                new PersonMatchesFilterPredicate(Optional.of(new RsvpStatus("yes")), Set.of());
        FilterCommand expectedCommand = new FilterCommand(expectedPredicate);
        assertParseSuccess(parser, " r/yes", expectedCommand);
    }

    /**
     * Valid case: single RSVP criterion with value "no".
     */
    @Test
    public void parse_validRsvpNo_returnsFilterCommand() {
        PersonMatchesFilterPredicate expectedPredicate =
                new PersonMatchesFilterPredicate(Optional.of(new RsvpStatus("no")), Set.of());
        FilterCommand expectedCommand = new FilterCommand(expectedPredicate);
        assertParseSuccess(parser, " r/no", expectedCommand);
    }

    /**
     * Valid case: single tag criterion.
     */
    @Test
    public void parse_validTag_returnsFilterCommand() {
        Set<Tag> tags = Set.of(new Tag("python"));
        PersonMatchesFilterPredicate expectedPredicate =
                new PersonMatchesFilterPredicate(Optional.empty(), tags);
        FilterCommand expectedCommand = new FilterCommand(expectedPredicate);
        assertParseSuccess(parser, " t/python", expectedCommand);
    }

    /**
     * Invalid case: both RSVP and tag prefixes supplied.
     */
    @Test
    public void parse_bothRsvpAndTag_throwsParseException() {
        assertParseFailure(parser, " r/yes t/python",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    /**
     * Invalid case: more than one tag prefix supplied.
     */
    @Test
    public void parse_multipleTags_throwsParseException() {
        assertParseFailure(parser, " t/python t/senior",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    /**
     * Invalid case: more than one RSVP prefix supplied.
     */
    @Test
    public void parse_multipleRsvp_throwsParseException() {
        assertParseFailure(parser, " r/yes r/no",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    /**
     * Invalid case: only unsupported prefixes used (no r/ or t/).
     */
    @Test
    public void parse_invalidPrefix_throwsParseException() {
        assertParseFailure(parser, " n/Alice",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    /**
     * Invalid case: valid tag prefix combined with an unsupported prefix.
     */
    @Test
    public void parse_validTagWithInvalidPrefix_throwsParseException() {
        assertParseFailure(parser, " t/python n/Alice",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }
}

