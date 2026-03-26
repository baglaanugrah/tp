package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Person's team in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTeam(String)}
 */
public class Team {

    public static final String MESSAGE_CONSTRAINTS =
        "Team name must be alphanumeric and under 15 characters.";
    public static final String VALIDATION_REGEX = "[A-Za-z0-9]{1,15}";

    public final String teamName;

    /**
     * Constructs a {@code Team}.
     *
     * @param teamName A valid team name.
     */
    public Team(String teamName) {
        requireNonNull(teamName);
        checkArgument(isValidTeam(teamName), MESSAGE_CONSTRAINTS);
        this.teamName = teamName;
    }

    /**
     * Returns true if a given string is a valid team name.
     */
    public static boolean isValidTeam(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Team)) {
            return false;
        }

        Team otherTeam = (Team) other;
        return teamName.equals(otherTeam.teamName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamName);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return teamName;
    }

}
