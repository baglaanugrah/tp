package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.EventBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.event.Event;

/**
 * Jackson-friendly version of {@link EventBook}.
 */
@JsonRootName(value = "eventbook")
class JsonSerializableEventBook {
    public static final String MESSAGE_DUPLICATE_EVENT = "Event list contains duplicate event(s).";
    private final List<JsonAdaptedEvent> events = new ArrayList<>();

    @JsonCreator
    public JsonSerializableEventBook(@JsonProperty("events") List<JsonAdaptedEvent> events) {
        if (events != null) {
            this.events.addAll(events);
        }
    }

    public JsonSerializableEventBook(ReadOnlyEventBook source) {
        events.addAll(source.getEventList().stream().map(JsonAdaptedEvent::new).collect(Collectors.toList()));
    }

    public EventBook toModelType() throws IllegalValueException {
        EventBook eventBook = new EventBook();
        for (JsonAdaptedEvent event: events) {
            Event e = event.toModelType();
            if (eventBook.hasEvent(e)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EVENT);
            }
            eventBook.addEvent(e);
        }
        return eventBook;
    }
}
