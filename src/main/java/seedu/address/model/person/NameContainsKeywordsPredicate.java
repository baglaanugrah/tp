package seedu.address.model.person;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s visible fields match any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .filter(keyword -> !keyword.trim().isEmpty())
                .anyMatch(keyword -> matchesAnyVisibleField(person, keyword));
    }

    private boolean matchesAnyVisibleField(Person person, String keyword) {
        return searchableFields(person).anyMatch(field -> containsIgnoreCase(field, keyword));
    }

    private Stream<String> searchableFields(Person person) {
        Stream<String> optionalTeam = person.getTeam().stream().map(team -> team.teamName);
        Stream<String> optionalGithub = person.getGitHub().stream().map(github -> github.value);

        return Stream.concat(Stream.of(
                        person.getName().fullName,
                        person.getPhone().value,
                        person.getAddress().value,
                        person.getEmail().value,
                        person.getCheckInStatus().toString()),
                Stream.concat(optionalTeam, optionalGithub));
    }

    private boolean containsIgnoreCase(String value, String keyword) {
        String normalizedKeyword = keyword.trim().toLowerCase(Locale.ROOT);
        return value.toLowerCase(Locale.ROOT).contains(normalizedKeyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (NameContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
