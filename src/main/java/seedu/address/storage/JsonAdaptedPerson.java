package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
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

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String team;
    private final Boolean status;
    private final String github;
    private final String rsvpStatus;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("team") String team, @JsonProperty("github") String github,
            @JsonProperty("rsvpStatus") String rsvpStatus, @JsonProperty("tags") List<JsonAdaptedTag> tags,
            @JsonProperty("status") Boolean status) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.team = team;
        this.status = status;
        this.github = github;
        this.rsvpStatus = rsvpStatus;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        team = source.getTeam().map(t -> t.teamName).orElse(null);
        status = source.getCheckInStatus().getStatus();
        github = source.getGitHub().map(g -> g.value).orElse(null);
        rsvpStatus = source.getRsvpStatus().toString();
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Optional<Team> modelTeam;
        if (team != null && !team.isEmpty()) {
            if (!Team.isValidTeam(team)) {
                throw new IllegalValueException(Team.MESSAGE_CONSTRAINTS);
            }
            modelTeam = Optional.of(new Team(team));
        } else {
            modelTeam = Optional.empty();
        }

        final GitHub modelGitHub = (github != null && GitHub.isValidGitHub(github))
                ? new GitHub(github) : null;

        final RsvpStatus modelRsvpStatus = (rsvpStatus != null && RsvpStatus.isValidRsvpStatus(rsvpStatus))
                ? new RsvpStatus(rsvpStatus) : new RsvpStatus("pending");

        final Set<Tag> modelTags = new HashSet<>(personTags);
        final Attendance modelAttendance = status == null ? new Attendance() : new Attendance(status);

        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelTeam, modelTags,
            modelAttendance, modelGitHub, modelRsvpStatus);
    }

}
