package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Represents an Event in the event book.
 * Each event maintains its own participant list.
 * Gurantees: name and date are present and not null; immutable identity fields.
 */
public class Event {
    private final EventName name;
    private final EventDate date;
    private final Optional<String> location;
    private final Optional<String> description;
    private final AddressBook participants;

    /**
     * Name and date are required. Location and description are optional.
     */
    public Event(EventName name, EventDate date, Optional<String> location, Optional<String> description) {
        requireAllNonNull(name, date);
        this.name = name;
        this.date = date;
        this.location = location != null ? location : Optional.empty();
        this.description = description != null ? description : Optional.empty();
        this.participants = new AddressBook();
    }

    public EventName getName() {
        return name;
    }

    public EventDate getDate() {
        return date;
    }

    public Optional<String> getLocation() {
        return location;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public ReadOnlyAddressBook getParticipants() {
        return participants;
    }

    public boolean isSameEvent(Event other) {
        return other == this || (other != null && name.equals(other.name));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Event)) {
            return false;
        }
        Event e = (Event) other;
        return name.equals(e.name) && date.equals(e.date)
                && location.equals(e.location) && description.equals(e.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, location, description);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("date", date)
                .add("location", location.orElse(null))
                .add("description", description.orElse(null))
                .add("participants", participants.getPersonList().size())
                .toString();
    }
}
