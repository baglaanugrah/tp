package seedu.address.model.event.exceptions;

/**
 * Signals that an operation would result in duplicate Events (Events are considered duplicates
 * if they have the same identity).
 */
public class DuplicateEventException extends RuntimeException {
    public DuplicateEventException() {
        super("Operation would result in duplicate events.");
    }
}
