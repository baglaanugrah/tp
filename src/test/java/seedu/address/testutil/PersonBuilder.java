package seedu.address.testutil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Email;
import seedu.address.model.person.GitHub;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RsvpStatus;
import seedu.address.model.person.Team;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_RSVP = "pending";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Optional<Team> team;
    private Set<Tag> tags;
    private Attendance status;
    private GitHub github;
    private RsvpStatus rsvpStatus;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        team = Optional.empty();
        tags = new HashSet<>();
        status = new Attendance();
        github = null;
        rsvpStatus = new RsvpStatus(DEFAULT_RSVP);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        team = personToCopy.getTeam();
        tags = new HashSet<>(personToCopy.getTags());
        status = personToCopy.getCheckInStatus();
        github = personToCopy.getGitHub().orElse(null);
        rsvpStatus = personToCopy.getRsvpStatus();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Team} of the {@code Person} that we are building.
     */
    public PersonBuilder withTeam(String team) {
        this.team = Optional.of(new Team(team));
        return this;
    }

    /**
     * Sets the {@code Team} of the {@code Person} to empty.
     */
    public PersonBuilder withoutTeam() {
        this.team = Optional.empty();
        return this;
    }

    /**
     * Sets the {@code GitHub} of the {@code Person} that we are building.
     * Pass {@code null} to leave it unset.
     */
    public PersonBuilder withGitHub(String github) {
        this.github = (github == null) ? null : new GitHub(github);
        return this;
    }

    /**
     * Sets the {@code RsvpStatus} of the {@code Person} that we are building.
     */
    public PersonBuilder withRsvpStatus(String rsvpStatus) {
        this.rsvpStatus = new RsvpStatus(rsvpStatus);
        return this;
    }

    /**
     * Sets the {@code status}  of the {@code Person} that we are building.
     */
    public PersonBuilder withStatus(Attendance status) {
        this.status = status;
        return this;
    };

    public Person build() {
        return new Person(name, phone, email, address, team, tags, status, github, rsvpStatus);
    }

}
