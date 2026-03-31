package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventName;
import seedu.address.model.person.Address;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Email;
import seedu.address.model.person.GitHub;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RsvpStatus;
import seedu.address.model.person.Team;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a delimited string of tags into a {@code Set<Tag>}.
     * Leading and trailing whitespaces around each tag will be trimmed.
     * Empty tokens are ignored.
     *
     * @param tagsString The string containing delimited tags (can be null or empty).
     * @param delimiter The delimiter separating tags (e.g., ";", ",").
     * @return A set of parsed Tag objects, or an empty set if input is null/blank.
     * @throws ParseException if any tag name is invalid.
     */
    public static Set<Tag> parseDelimitedTags(String tagsString, String delimiter) throws ParseException {
        if (tagsString == null || tagsString.isBlank()) {
            return new HashSet<>();
        }

        Collection<String> tagNames = new HashSet<>();
        String[] tokens = tagsString.split(delimiter);
        for (String token : tokens) {
            String trimmed = token.trim();
            if (!trimmed.isEmpty()) {
                tagNames.add(trimmed);
            }
        }

        return parseTags(tagNames);
    }

    /**
     * Parses a {@code String team} into a {@code Team}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code team} is invalid.
     */
    public static Team parseTeam(String team) throws ParseException {
        requireNonNull(team);
        String trimmedTeam = team.trim();
        if (!Team.isValidTeam(trimmedTeam)) {
            throw new ParseException(Team.MESSAGE_CONSTRAINTS);
        }
        return new Team(trimmedTeam);
    }

    /**
     * Parses a {@code String github} into a {@code GitHub}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code github} is invalid.
     */
    public static GitHub parseGitHub(String github) throws ParseException {
        requireNonNull(github);
        String trimmed = github.trim();
        if (!GitHub.isValidGitHub(trimmed)) {
            throw new ParseException(GitHub.MESSAGE_CONSTRAINTS);
        }
        return new GitHub(trimmed);
    }

    /**
     * Parses a {@code String rsvp} into a {@code RsvpStatus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code rsvp} is invalid.
     */
    public static RsvpStatus parseRsvpStatus(String rsvp) throws ParseException {
        requireNonNull(rsvp);
        String trimmed = rsvp.trim();
        if (!RsvpStatus.isValidRsvpStatus(trimmed)) {
            throw new ParseException(RsvpStatus.MESSAGE_CONSTRAINTS);
        }
        return new RsvpStatus(trimmed);
    }

    /**
     * Parses a {@code String name} into an {@code EventName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static EventName parseEventName(String name) throws ParseException {
        requireNonNull(name);
        String trimmed = name.trim();
        if (!EventName.isValidEventName(trimmed)) {
            throw new ParseException(EventName.MESSAGE_CONSTRAINTS);
        }
        return new EventName(trimmed);
    }

    /**
     * Parses a {@code String date} into an {@code EventDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static EventDate parseEventDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmed = date.trim();
        if (!EventDate.isValidEventDate(trimmed)) {
            throw new ParseException(EventDate.MESSAGE_CONSTRAINTS);
        }
        return new EventDate(trimmed);
    }

    /**
     * Parses a {@code String attendance} into a {@code Attendance}.
     * Leading and trailing whitespaces will be trimmed.
     * Accepts various formats: "yes", "checked-in", "true" for checked in,
     * and "no", "not checked-in", "false" for not checked in.
     *
     * @throws ParseException if the given {@code attendance} is invalid.
     */
    public static Attendance parseAttendance(String attendance) throws ParseException {
        requireNonNull(attendance);
        String trimmed = attendance.trim().toLowerCase();

        if (trimmed.equals("yes") || trimmed.equals("checked-in") || trimmed.equals("true")) {
            return new Attendance(true);
        } else if (trimmed.equals("no") || trimmed.equals("not checked-in") || trimmed.equals("false")) {
            return new Attendance(false);
        } else {
            throw new ParseException(Attendance.MESSAGE_CONSTRAINTS);
        }
    }
}
