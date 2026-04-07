package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class PersonMatchesFilterPredicateTest {

    @Test
    public void test_caseInsensitiveRsvpTagAndTeam_returnsTrue() {
        Person person = new PersonBuilder()
                .withName("Alice")
                .withTeam("Alpha")
                .withTags("python")
                .withRsvpStatus("yes")
                .withStatus(new Attendance(true))
                .build();

        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(
                Optional.of(new RsvpStatus("YES")),
                Set.of(new Tag("Python")),
                Optional.of(new Team("alpha")),
                Optional.of(new Attendance(true)));

        assertTrue(predicate.test(person));
    }

    @Test
    public void test_tagDifferentCase_matches() {
        Person person = new PersonBuilder()
                .withName("Alice")
                .withTags("Python")
                .build();

        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(
                Optional.empty(),
                Set.of(new Tag("python")),
                Optional.empty(),
                Optional.empty());

        assertTrue(predicate.test(person));
    }

    @Test
    public void test_teamDifferentCase_matches() {
        Person person = new PersonBuilder()
                .withName("Alice")
                .withTeam("Alpha")
                .build();

        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(
                Optional.empty(),
                Set.of(),
                Optional.of(new Team("alpha")),
                Optional.empty());

        assertTrue(predicate.test(person));
    }

    @Test
    public void test_nonMatchingTag_returnsFalse() {
        Person person = new PersonBuilder()
                .withName("Alice")
                .withTags("python")
                .build();

        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(
                Optional.empty(),
                Set.of(new Tag("java")),
                Optional.empty(),
                Optional.empty());

        assertFalse(predicate.test(person));
    }

    @Test
    public void test_teamFilterButPersonHasNoTeam_returnsFalse() {
        Person person = new PersonBuilder()
                .withName("Alice")
                .withoutTeam()
                .build();

        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(
                Optional.empty(),
                Set.of(),
                Optional.of(new Team("alpha")),
                Optional.empty());

        assertFalse(predicate.test(person));
    }
}
