package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.enterDefaultEvent;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;


public class CheckInCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        enterDefaultEvent(model);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToCheckIn = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        CheckInCommand checkInCommand = new CheckInCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(CheckInCommand.MESSAGE_CHECK_IN_PERSON_SUCCESS,
                Messages.format(personToCheckIn));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        enterDefaultEvent(expectedModel);
        expectedModel.checkInPerson(personToCheckIn);

        assertCommandSuccess(checkInCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        CheckInCommand checkInCommand = new CheckInCommand(outOfBoundIndex);

        assertCommandFailure(checkInCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_alreadyCheckedIn_throwsCommandException() {
        Person originalPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person checkedInPerson = new PersonBuilder(originalPerson).withStatus(new Attendance(true)).build();
        model.setPerson(originalPerson, checkedInPerson);

        CheckInCommand checkInCommand = new CheckInCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(checkInCommand, model, CheckInCommand.MESSAGE_PERSON_ALREADY_CHECKED_IN);
    }

    @Test
    public void equals() {
        CheckInCommand checkInFirstCommand = new CheckInCommand(INDEX_FIRST_PERSON);
        CheckInCommand checkInSecondCommand = new CheckInCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(checkInFirstCommand.equals(checkInFirstCommand));

        // same values -> returns true
        CheckInCommand checkInFirstCommandCopy = new CheckInCommand(INDEX_FIRST_PERSON);
        assertTrue(checkInFirstCommand.equals(checkInFirstCommandCopy));

        // different types -> returns false
        assertFalse(checkInFirstCommand.equals(1));

        // null -> returns false
        assertFalse(checkInFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(checkInFirstCommand.equals(checkInSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        CheckInCommand checkInCommand = new CheckInCommand(targetIndex);
        String expected = CheckInCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, checkInCommand.toString());
    }
}
