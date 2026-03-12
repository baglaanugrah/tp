package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Team} matches the team name given (case-insensitive).
 * Also supports filtering by no-team status.
 */
public class TeamMatchesPredicate implements Predicate<Person> {
    private final String teamName;
    private final boolean filterNoTeam;

    /**
     * Constructs a predicate to filter by team name.
     * @param teamName The team name to filter by (case-insensitive), or null to filter by no-team.
     */
    public TeamMatchesPredicate(String teamName) {
        if (teamName == null || teamName.trim().isEmpty()) {
            this.teamName = null;
            this.filterNoTeam = true;
        } else {
            this.teamName = teamName.trim();
            this.filterNoTeam = false;
        }
    }

    @Override
    public boolean test(Person person) {
        if (filterNoTeam) {
            return person.getTeam().isEmpty();
        }
        return person.getTeam()
                .map(team -> team.teamName.equalsIgnoreCase(teamName))
                .orElse(false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TeamMatchesPredicate)) {
            return false;
        }

        TeamMatchesPredicate otherPredicate = (TeamMatchesPredicate) other;
        return filterNoTeam == otherPredicate.filterNoTeam
                && (teamName == null ? otherPredicate.teamName == null : teamName.equals(otherPredicate.teamName));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("teamName", teamName)
                .add("filterNoTeam", filterNoTeam)
                .toString();
    }
}
