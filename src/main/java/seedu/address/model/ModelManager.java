package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private FilteredList<Person> filteredPersons;
    private final EventBook eventBook;
    private Event activeEvent;
    private Person personToView;
    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyEventBook eventBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.eventBook = new EventBook(eventBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
    }

    /** Creates a ModelManager with empty data. */
    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    /** Creates a ModelManager without an event book (uses empty EventBook). */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        this(addressBook, new EventBook(), userPrefs);
    }


    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        requireNonNull(addressBook);
        if (activeEvent != null) {
            activeEvent.setParticipants(addressBook);
        } else {
            this.addressBook.resetData(addressBook);
        }
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return activeEvent != null ? activeEvent.getParticipants() : addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return activeEvent != null ? activeEvent.hasPerson(person) : addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        if (activeEvent != null) {
            activeEvent.deleteParticipant(target);
        } else {
            addressBook.removePerson(target);
        }
    }

    @Override
    public void addPerson(Person person) {
        if (activeEvent != null) {
            activeEvent.addParticipant(person);
        } else {
            addressBook.addPerson(person);
        }
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void checkInPerson(Person person) {
        requireNonNull(person);
        if (activeEvent != null) {
            activeEvent.checkInParticipant(person);
        } else {
            addressBook.checkInPerson(person);
        }
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }
    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        if (activeEvent != null) {
            activeEvent.setParticipant(target, editedPerson);
        } else {
            addressBook.setPerson(target, editedPerson);
        }
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean isInEventParticipantsMode() {
        return activeEvent != null;
    }

    @Override
    public void enterEvent(Event event) {
        requireNonNull(event);
        activeEvent = event;
        filteredPersons = new FilteredList<>(activeEvent.getParticipants().getPersonList());
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void leaveEvent() {
        activeEvent = null;
        filteredPersons = new FilteredList<>(addressBook.getPersonList());
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

    //=========== EventBook ====================================================================================

    @Override
    public void addEvent(Event event) {
        eventBook.addEvent(event);
    }

    @Override
    public void setEvent(Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);
        eventBook.setEvent(target, editedEvent);
        if (activeEvent == target) {
            activeEvent = editedEvent;
            filteredPersons = new FilteredList<>(activeEvent.getParticipants().getPersonList());
            updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }
    }

    @Override
    public void deleteEvent(Event target) {
        requireNonNull(target);
        eventBook.removeEvent(target);
        if (activeEvent == target) {
            leaveEvent();
        }
    }

    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return eventBook.hasEvent(event);
    }

    @Override
    public ReadOnlyEventBook getEventBook() {
        return eventBook;
    }

    @Override
    public ObservableList<Event> getFilteredEventList() {
        return eventBook.getEventList();
    }

    //=========== View Person ==================================================================================

    @Override
    public Optional<Person> getPersonToView() {
        return Optional.ofNullable(personToView);
    }

    @Override
    public void setPersonToView(Optional<Person> person) {
        requireNonNull(person);
        this.personToView = person.orElse(null);
    }
}
