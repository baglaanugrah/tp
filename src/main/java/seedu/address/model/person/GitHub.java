package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's GitHub username in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGitHub(String)}
 */
public class GitHub {

    public static final String MESSAGE_CONSTRAINTS =
            "GitHub username must only contain alphanumeric characters or hyphens, "
            + "and cannot start or end with a hyphen.";

    public static final String VALIDATION_REGEX = "[a-zA-Z0-9]([a-zA-Z0-9]|-(?=[a-zA-Z0-9])){0,38}";

    public final String value;

    /**
     * Constructs a {@code GitHub}.
     *
     * @param github A valid GitHub username.
     */
    public GitHub(String github) {
        requireNonNull(github);
        checkArgument(isValidGitHub(github), MESSAGE_CONSTRAINTS);
        this.value = github;
    }

    /**
     * Returns true if a given string is a valid GitHub username.
     */
    public static boolean isValidGitHub(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GitHub)) {
            return false;
        }

        GitHub otherGitHub = (GitHub) other;
        return value.equals(otherGitHub.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
