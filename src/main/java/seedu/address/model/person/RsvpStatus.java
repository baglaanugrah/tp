package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's RSVP status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRsvpStatus(String)}
 */
public class RsvpStatus {

    public static final String MESSAGE_CONSTRAINTS =
            "RSVP status must be 'yes', 'no', or 'pending' (case-insensitive).";

    public final String value;

    /**
     * Constructs a {@code RsvpStatus}.
     * The value is normalised to title case (e.g. "yes" becomes "Yes").
     *
     * @param rsvp A valid RSVP status string.
     */
    public RsvpStatus(String rsvp) {
        requireNonNull(rsvp);
        checkArgument(isValidRsvpStatus(rsvp), MESSAGE_CONSTRAINTS);
        String normalised = rsvp.trim().toLowerCase();
        this.value = normalised.substring(0, 1).toUpperCase() + normalised.substring(1);
    }

    /**
     * Returns true if a given string is a valid RSVP status.
     */
    public static boolean isValidRsvpStatus(String test) {
        String normalised = test.trim().toLowerCase();
        return normalised.equals("yes") || normalised.equals("no") || normalised.equals("pending");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RsvpStatus)) {
            return false;
        }

        RsvpStatus otherRsvp = (RsvpStatus) other;
        return value.equals(otherRsvp.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
