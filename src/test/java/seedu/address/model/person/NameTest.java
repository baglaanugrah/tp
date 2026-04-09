package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid — blank/empty
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only

        // invalid — disallowed special characters
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // asterisk not allowed
        assertFalse(Name.isValidName("peter@tan")); // @ not allowed
        assertFalse(Name.isValidName("peter#1")); // # not allowed

        // invalid — exceeds max length (101 characters)
        assertFalse(Name.isValidName("a".repeat(101)));

        // valid — standard names
        assertTrue(Name.isValidName("peter jack")); // alphabets with space
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric
        assertTrue(Name.isValidName("Capital Tan")); // capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long name

        // valid — exactly at max length (100 characters)
        assertTrue(Name.isValidName("a".repeat(100)));

        // valid — apostrophes (e.g. Irish/English names)
        assertTrue(Name.isValidName("O'Brian"));
        assertTrue(Name.isValidName("O'Brien"));

        // valid — slashes (e.g. Singaporean naming convention)
        assertTrue(Name.isValidName("s/o Kumar"));
        assertTrue(Name.isValidName("d/o Priya"));

        // valid — accented characters
        assertTrue(Name.isValidName("José"));
        assertTrue(Name.isValidName("João"));
        assertTrue(Name.isValidName("Tomáš"));
        assertTrue(Name.isValidName("Renée"));

        // valid — hyphens (e.g. hyphenated surnames)
        assertTrue(Name.isValidName("Mary-Jane Watson"));
        assertTrue(Name.isValidName("Jean-Luc Picard"));
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));
    }
}
