package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Non-visible-field text does not match
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("nomatch", "zzz"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void test_otherVisibleFieldsContainKeywords_returnsTrue() {
        PersonBuilder builder = new PersonBuilder()
                .withName("Alice Bob")
                .withPhone("99998888")
                .withAddress("Main Street")
                .withTeam("alpha")
                .withTags("friend")
                .withRsvpStatus("yes")
                .withStatus(new Attendance(true));

        assertTrue(new NameContainsKeywordsPredicate(Collections.singletonList("9999")).test(builder.build()));
        assertTrue(new NameContainsKeywordsPredicate(Collections.singletonList("street")).test(builder.build()));
        assertTrue(new NameContainsKeywordsPredicate(Collections.singletonList("alpha")).test(builder.build()));
        assertTrue(new NameContainsKeywordsPredicate(Collections.singletonList("checked-in")).test(builder.build()));
        assertFalse(new NameContainsKeywordsPredicate(Collections.singletonList("friend")).test(builder.build()));
        assertFalse(new NameContainsKeywordsPredicate(Collections.singletonList("yes")).test(builder.build()));
    }

    @Test
    public void test_emailAndGitHubContainKeywords_returnsTrue() {
        PersonBuilder builder = new PersonBuilder().withName("Alice Bob")
                .withEmail("alice@example.com")
                .withGitHub("alice-hub");

        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(
                Collections.singletonList("ALICE@EXAMPLE.COM"));
        assertTrue(predicate.test(builder.build()));

        predicate = new NameContainsKeywordsPredicate(Collections.singletonList("HUB"));
        assertTrue(predicate.test(builder.build()));
    }

    @Test
    public void test_missingGitHubDoesNotMatchGitHubKeyword_returnsFalse() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(
                Collections.singletonList("alicehub"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withGitHub(null).build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(keywords);

        String expected = NameContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
