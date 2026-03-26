package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;

/**
 * Wraps all event data. Duplicates are disallowed by {@code Event#isSameEvent}.
 */
public class EventBook implements ReadOnlyEventBook {
    private final UniqueEventList events;

    public EventBook() {
        events = new UniqueEventList();
    }

    /**
     * Creates an EventBook using the events in {@code toBeCopied}.
     */
    public EventBook(ReadOnlyEventBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the event list with {@code events}.
     */
    public void setEvents(List<Event> events) {
        this.events.setEvents(events);
    }

    /**
     * Resets the existing data of this {@code EventBook} with {@code newData}.
     */
    public void resetData(ReadOnlyEventBook newData) {
        requireNonNull(newData);
        setEvents(newData.getEventList());
    }

    /**
     * Returns true if an event that is the same as {@code event} exists in the event book.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return events.contains(event);
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void removeEvent(Event event) {
        events.remove(event);
    }

    @Override
    public ObservableList<Event> getEventList() {
        return events.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof EventBook
                && events.equals(((EventBook) other).events));
    }

    @Override
    public int hashCode() {
        return events.hashCode();
    }
}
