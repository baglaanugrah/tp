package seedu.address.model.person;

import java.util.Locale;
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
        boolean matchesRsvp = rsvpFilter.isEmpty() || matchesRsvp(person, rsvpFilter.get());
        boolean matchesTags = tagFilter.isEmpty() || matchesTags(person, tagFilter);
        boolean matchesTeam = teamFilter.isEmpty() || matchesTeam(person, teamFilter.get());
        boolean matchesCheckin = checkinFilter.isEmpty() || matchesCheckin(person, checkinFilter.get());
        return matchesRsvp && matchesTags && matchesTeam && matchesCheckin;
    }

    private boolean matchesRsvp(Person person, RsvpStatus rsvpStatus) {
        return person.getRsvpStatus().toString().toLowerCase(Locale.ROOT)
                .equalsIgnoreCase(rsvpStatus.toString().toLowerCase(Locale.ROOT));
    }

    private boolean matchesTags(Person person, Set<Tag> tags) {
        if (tags.isEmpty()) {
            return true;
        }
        String requested = tags.iterator().next().tagName.toLowerCase(Locale.ROOT);
        return person.getTags().stream()
                .anyMatch(tag -> tag.tagName.toLowerCase(Locale.ROOT).equals(requested));
    }

    private boolean matchesTeam(Person person, Team team) {
        return person.getTeam()
        .map(personTeam -> personTeam.teamName.equalsIgnoreCase(team.teamName))
        .orElse(false);
    }

    private boolean matchesCheckin(Person person, Attendance checkinStatus) {
        return person.getCheckInStatus().equals(checkinStatus);
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
