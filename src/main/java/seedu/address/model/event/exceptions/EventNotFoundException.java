package seedu.address.model.event.exceptions;

/**
 * Signals that the operation is unable to find the specified Event
 */
public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException() {
        super("Event not found in the list.");
    }
}
