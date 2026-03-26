package seedu.address.logic.statistics;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Calculates summary statistics for a collection of persons.
 */
public class StatisticsCalculator {

    /**
     * Computes summary statistics for the given {@code persons}.
     */
    public StatisticsSummary calculate(Collection<Person> persons) {
        requireNonNull(persons);

        int total = persons.size();
        int checkedInCount = (int) persons.stream()
                .filter(person -> person.getCheckInStatus().getStatus())
                .count();

        Map<String, Long> rsvpCounts = persons.stream()
                .collect(Collectors.groupingBy(
                        person -> person.getRsvpStatus().toString().toLowerCase(Locale.ROOT),
                        Collectors.counting()
                ));
        int rsvpYesCount = rsvpCounts.getOrDefault("yes", 0L).intValue();
        int rsvpNoCount = rsvpCounts.getOrDefault("no", 0L).intValue();
        int rsvpPendingCount = rsvpCounts.getOrDefault("pending", 0L).intValue();

        Set<String> uniqueTeams = persons.stream()
                .map(Person::getTeam)
                .flatMap(java.util.Optional::stream)
                .map(team -> team.teamName.toLowerCase(Locale.ROOT))
                .collect(Collectors.toSet());

        Map<String, Integer> tagCounts = persons.stream()
                .flatMap(person -> person.getTags().stream())
                .collect(Collectors.toMap(
                        tag -> normaliseTagName(tag),
                        tag -> 1,
                        Integer::sum
                ));

        Map<String, Integer> sortedTagCounts = tagCounts.entrySet().stream()
                .sorted(Comparator
                        .comparing(Map.Entry<String, Integer>::getValue, Comparator.reverseOrder())
                        .thenComparing(Map.Entry::getKey))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue, (left, right) -> left,
                        LinkedHashMap::new
                ));

        return new StatisticsSummary(
                total,
                checkedInCount,
                rsvpYesCount,
                rsvpNoCount,
                rsvpPendingCount,
                uniqueTeams.size(),
                sortedTagCounts
        );
    }

    private String normaliseTagName(Tag tag) {
        return tag.tagName.toLowerCase(Locale.ROOT);
    }

}
