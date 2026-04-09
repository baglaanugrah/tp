package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.EventBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventName;
import seedu.address.model.person.Person;

public class AddEventCommandTest {

    private static final Event VALID_EVENT = new Event(
            new EventName("Tech Meetup"),
            new EventDate("2026-06-15"),
            Optional.of("NUS"),
            Optional.empty());

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddEventCommand(null));
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();

        CommandResult result = new AddEventCommand(VALID_EVENT).execute(modelStub);

        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, Messages.format(VALID_EVENT)),
                result.getFeedbackToUser());
        assertEquals(1, modelStub.eventsAdded.size());
        assertEquals(VALID_EVENT, modelStub.eventsAdded.get(0));
    }

    @Test
    public void execute_successMessage_doesNotContainClassPath() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        CommandResult result = new AddEventCommand(VALID_EVENT).execute(modelStub);

        // message should contain the event name and date
        assertTrue(result.getFeedbackToUser().contains("Tech Meetup"));
        assertTrue(result.getFeedbackToUser().contains("Date:"));
        assertTrue(result.getFeedbackToUser().contains("Location:"));
        assertTrue(result.getFeedbackToUser().contains("Description:"));

        // message should NOT contain the Java class path
        assertFalse(result.getFeedbackToUser().contains("seedu.address.model.event.Event"));
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        AddEventCommand command = new AddEventCommand(VALID_EVENT);
        ModelStub modelStub = new ModelStubWithEvent(VALID_EVENT);

        assertThrows(CommandException.class,
                AddEventCommand.MESSAGE_DUPLICATE_EVENT, () -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        Event eventA = new Event(new EventName("Event A"), new EventDate("2026-01-01"),
                Optional.empty(), Optional.empty());
        Event eventB = new Event(new EventName("Event B"), new EventDate("2026-02-02"),
                Optional.empty(), Optional.empty());

        AddEventCommand addA = new AddEventCommand(eventA);
        AddEventCommand addB = new AddEventCommand(eventB);

        assertTrue(addA.equals(addA));
        assertTrue(addA.equals(new AddEventCommand(eventA)));
        assertFalse(addA.equals(addB));
        assertFalse(addA.equals(null));
        assertFalse(addA.equals(1));
    }

    @Test
    public void toStringMethod() {
        AddEventCommand command = new AddEventCommand(VALID_EVENT);
        String expected = AddEventCommand.class.getCanonicalName() + "{toAdd=" + VALID_EVENT + "}";
        assertEquals(expected, command.toString());
    }

    /** A default model stub that has all methods failing. */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void checkInPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void addEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public boolean hasEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ReadOnlyEventBook getEventBook() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ObservableList<Event> getFilteredEventList() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /** A Model stub that contains a single event. */
    private class ModelStubWithEvent extends ModelStub {
        private final Event event;

        ModelStubWithEvent(Event event) {
            requireNonNull(event);
            this.event = event;
        }

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return this.event.isSameEvent(event);
        }
    }

    /** A Model stub that always accepts the event being added. */
    private class ModelStubAcceptingEventAdded extends ModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<>();

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return eventsAdded.stream().anyMatch(event::isSameEvent);
        }

        @Override
        public void addEvent(Event event) {
            requireNonNull(event);
            eventsAdded.add(event);
        }

        @Override
        public ReadOnlyEventBook getEventBook() {
            return new EventBook();
        }
    }
}
