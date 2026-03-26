package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;

/**
 * Unmodifiable view of an event book.
 */
public interface ReadOnlyEventBook {
    ObservableList<Event> getEventList();
}
