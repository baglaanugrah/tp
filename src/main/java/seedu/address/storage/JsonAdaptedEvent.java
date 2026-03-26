package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventName;
import seedu.address.model.person.Person;

/**
 * Jackson-friendly version of {@link Event}
 */
class JsonAdaptedEvent {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";
    public static final String MESSAGE_DUPLICATE_PARTICIPANT = "Participants list contains duplicate person(s).";

    private final String name;
    private final String date;
    private final String location;
    private final String description;
    private final List<JsonAdaptedPerson> participants = new ArrayList<>();

    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("name") String name, @JsonProperty("date") String date,
                            @JsonProperty("location") String location,
                            @JsonProperty("description") String description,
                            @JsonProperty("participants") List<JsonAdaptedPerson> participants) {
        this.name = name;
        this.date = date;
        this.location = location;
        this.description = description;
        if (participants != null) {
            this.participants.addAll(participants);
        }
    }

    public JsonAdaptedEvent(Event source) {
        name = source.getName().fullName;
        date = source.getDate().toString();
        location = source.getLocation().orElse(null);
        description = source.getDescription().orElse(null);
        this.participants.addAll(source.getParticipants().getPersonList().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList()));
    }

    public Event toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, EventName.class.getSimpleName()));
        }
        if (!EventName.isValidEventName(name)) {
            throw new IllegalValueException(EventName.MESSAGE_CONSTRAINTS);
        }
        if (date == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, EventDate.class.getSimpleName()));
        }
        if (!EventDate.isValidEventDate(date)) {
            throw new IllegalValueException(EventDate.MESSAGE_CONSTRAINTS);
        }
        Event event = new Event(new EventName(name), new EventDate(date),
                Optional.ofNullable(location), Optional.ofNullable(description));

        AddressBook participantsBook = new AddressBook();
        for (JsonAdaptedPerson participant : participants) {
            Person person = participant.toModelType();
            if (participantsBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PARTICIPANT);
            }
            participantsBook.addPerson(person);
        }
        event.setParticipants(participantsBook);

        return event;
    }
}
