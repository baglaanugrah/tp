package seedu.address.model.person;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Checks that a {@code Person} matches the given filter criteria.
 * Criteria are combined with AND: person must match RSVP (if specified) and all tags (if specified).
 */
public class PersonMatchesFilterPredicate implements Predicate<Person> {

    private final Optional<RsvpStatus> rsvpFilter;
    private final Set<Tag> tagFilter;

    /**
     * Creates a predicate that filters by optional RSVP and optional tags.
     *
     * @param rsvpFilter Optional RSVP status to match; empty means no RSVP filter.
     * @param tagFilter  Set of tags the person must have; empty means no tag filter.
     */
    public PersonMatchesFilterPredicate(Optional<RsvpStatus> rsvpFilter, Set<Tag> tagFilter) {
        this.rsvpFilter = rsvpFilter != null ? rsvpFilter : Optional.empty();
        this.tagFilter = tagFilter != null ? tagFilter : Set.of();
    }

    @Override
    public boolean test(Person person) {
        boolean matchesRsvp = rsvpFilter.isEmpty() || person.getRsvpStatus().equals(rsvpFilter.get());
        boolean matchesTags = tagFilter.isEmpty() || person.getTags().containsAll(tagFilter);
        return matchesRsvp && matchesTags;
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
                && tagFilter.equals(otherPredicate.tagFilter);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(rsvpFilter, tagFilter);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("rsvpFilter", rsvpFilter)
                .add("tagFilter", tagFilter)
                .toString();
    }
}
