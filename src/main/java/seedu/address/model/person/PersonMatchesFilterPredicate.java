package seedu.address.model.person;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Checks that a {@code Person} matches the given filter criteria.
 */
public class PersonMatchesFilterPredicate implements Predicate<Person> {

    private final Optional<RsvpStatus> rsvpFilter;
    private final Set<Tag> tagFilter;
    private final Optional<Team> teamFilter;
    private final Optional<Attendance> checkinFilter;
    /**
     * Creates a predicate that filters by optional RSVP and optional tags.
     *
     * @param rsvpFilter Optional RSVP status to match; empty means no RSVP filter.
     * @param tagFilter  Set of tags the person must have; empty means no tag filter.
     * @param teamFilter Optional team to match; empty means no team filter.
     * @param checkinFilter Optional check-in status to match; empty means no check-in filter.
     */
    public PersonMatchesFilterPredicate(Optional<RsvpStatus> rsvpFilter,
                                        Set<Tag> tagFilter,
                                        Optional<Team> teamFilter,
                                        Optional<Attendance> checkinFilter) {
        this.rsvpFilter = rsvpFilter != null ? rsvpFilter : Optional.empty();
        this.tagFilter = tagFilter != null ? tagFilter : Set.of();
        this.teamFilter = teamFilter != null ? teamFilter : Optional.empty();
        this.checkinFilter = checkinFilter != null ? checkinFilter : Optional.empty();
    }

    @Override
    public boolean test(Person person) {
        boolean matchesRsvp = rsvpFilter.isEmpty() || person.getRsvpStatus().equals(rsvpFilter.get());
        boolean matchesTags = tagFilter.isEmpty() || person.getTags().containsAll(tagFilter);
        boolean matchesTeam = teamFilter.isEmpty() || person.getTeam().equals(teamFilter);
        boolean matchesCheckin = checkinFilter.isEmpty() || person.getCheckInStatus().equals(checkinFilter.get());
        return matchesRsvp && matchesTags && matchesTeam && matchesCheckin;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PersonMatchesFilterPredicate)) {
            return false;
        }
        PersonMatchesFilterPredicate otherPredicate = (PersonMatchesFilterPredicate) other;
        return rsvpFilter.equals(otherPredicate.rsvpFilter)
                && tagFilter.equals(otherPredicate.tagFilter)
                && teamFilter.equals(otherPredicate.teamFilter)
                && checkinFilter.equals(otherPredicate.checkinFilter);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(rsvpFilter, tagFilter, teamFilter, checkinFilter);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("rsvpFilter", rsvpFilter)
                .add("tagFilter", tagFilter)
                .add("teamFilter", teamFilter)
                .add("checkinFilter", checkinFilter)
                .toString();
    }
}
