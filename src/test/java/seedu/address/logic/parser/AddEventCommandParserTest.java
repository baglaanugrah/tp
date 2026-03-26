package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventName;

public class AddEventCommandParserTest {

    private final AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expected = new Event(
                new EventName("Tech Meetup"),
                new EventDate("2026-06-15"),
                Optional.of("NUS Techno Edge"),
                Optional.of("Annual networking"));

        assertParseSuccess(parser,
                " " + PREFIX_NAME + "Tech Meetup "
                        + PREFIX_DATE + "2026-06-15 "
                        + PREFIX_LOCATION + "NUS Techno Edge "
                        + PREFIX_DESCRIPTION + "Annual networking",
                new AddEventCommand(expected));
    }

    @Test
    public void parse_optionalFieldsAbsent_success() {
        Event expected = new Event(
                new EventName("Hackathon"),
                new EventDate("2026-08-20"),
                Optional.empty(),
                Optional.empty());

        assertParseSuccess(parser,
                " " + PREFIX_NAME + "Hackathon "
                        + PREFIX_DATE + "2026-08-20",
                new AddEventCommand(expected));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing name
        assertParseFailure(parser, " " + PREFIX_DATE + "2026-06-15", expectedMessage);

        // missing date
        assertParseFailure(parser, " " + PREFIX_NAME + "Tech Meetup", expectedMessage);

        // missing both
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidName_failure() {
        assertParseFailure(parser,
                " " + PREFIX_NAME + "!! Invalid @@ "
                        + PREFIX_DATE + "2026-06-15",
                EventName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidDate_failure() {
        assertParseFailure(parser,
                " " + PREFIX_NAME + "Tech Meetup "
                        + PREFIX_DATE + "not-a-date",
                EventDate.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidDateOutOfRange_failure() {
        assertParseFailure(parser,
                " " + PREFIX_NAME + "Tech Meetup "
                        + PREFIX_DATE + "2026-02-30",
                EventDate.MESSAGE_CONSTRAINTS);
    }
}
