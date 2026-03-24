package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Represents an Event's date in the event book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEventDate(String)}
 */
public class EventDate {
    public static final String MESSAGE_CONSTRAINTS =
            "Event dates should be in the format YYYY-MM-DD and must be a valid calendar date.";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd")
            .withResolverStyle(ResolverStyle.STRICT);
    public final LocalDate date;

    /**
     * Constructs an {@code EventDate}
     *
     * @param date A valid date string in YYYY-MM-DD format.
     */
    public EventDate(String date) {
        requireNonNull(date);
        checkArgument(isValidEventDate(date), MESSAGE_CONSTRAINTS);
        this.date = LocalDate.parse(date.trim(), FORMATTER);
    }

    /**
     * Returns true if a given string is a valid event date.
     * A valid event date must follow the format {@code YYYY-MM-DD} and represent
     * a real calendar date (e.g. {@code 2026-13-01} is invalid as month 13 does not exist).
     *
     * @param test The string to validate.
     * @return {@code true} if {@code test} is a valid date in {@code YYYY-MM-DD} format,
     *         {@code false} otherwise.
     */
    public static boolean isValidEventDate(String test) {
        try {
            LocalDate.parse(test.trim(), FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return date.format(FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof EventDate
                && date.equals(((EventDate) other).date));
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
