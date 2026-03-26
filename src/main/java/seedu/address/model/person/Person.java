package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Optional<Team> team;
    private final Attendance status;
    private final Set<Tag> tags = new HashSet<>();
    private final GitHub github;
    private final RsvpStatus rsvpStatus;

    /**
     * Constructor with default values for optional fields.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        this(name, phone, email, address, Optional.empty(), tags, null, new RsvpStatus("pending"));
    }

    /**
     * Constructor with team field.
     */
    public Person(Name name, Phone phone, Email email, Address address, Optional<Team> team, Set<Tag> tags) {
        this(name, phone, email, address, team, tags, new Attendance(), null, new RsvpStatus("pending"));
    }

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Optional<Team> team, Set<Tag> tags,
                  GitHub github, RsvpStatus rsvpStatus) {
        this(name, phone, email, address, team, tags, new Attendance(), github, rsvpStatus);
    }

    /**
     * Constructor for compatibility when only check-in status is provided.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Attendance status,
                  GitHub github, RsvpStatus rsvpStatus) {
        this(name, phone, email, address, Optional.empty(), tags, status, github, rsvpStatus);
    }

    /**
     * Full constructor with all fields.
     */
    public Person(Name name, Phone phone, Email email, Address address, Optional<Team> team,
                  Set<Tag> tags, Attendance status, GitHub github, RsvpStatus rsvpStatus) {
        requireAllNonNull(name, phone, email, address, tags, status, rsvpStatus);
        requireAllNonNull(team);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.team = team;
        this.tags.addAll(tags);
        this.status = status;
        this.github = github;
        this.rsvpStatus = rsvpStatus;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Attendance getCheckInStatus() {
        return status;
    }

    /**
     * Returns the team of this person, if present.
     */
    public Optional<Team> getTeam() {
        return team;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Optional<GitHub> getGitHub() {
        return Optional.ofNullable(github);
    }

    public RsvpStatus getRsvpStatus() {
        return rsvpStatus;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && team.equals(otherPerson.team)
                && tags.equals(otherPerson.tags)
                && status.equals(otherPerson.status)
                && Objects.equals(github, otherPerson.github)
                && rsvpStatus.equals(otherPerson.rsvpStatus);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, team, tags, status, github, rsvpStatus);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("team", team.orElse(null))
                .add("tags", tags)
                .add("status", status)
                .add("github", github)
                .add("rsvpStatus", rsvpStatus)
                .toString();
    }

}
