package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.enterDefaultEvent;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration and unit tests for {@code ViewCommand}.
 */
public class ViewCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        enterDefaultEvent(model);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws CommandException {
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PERSON);
        Person expectedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        CommandResult result = viewCommand.execute(model);

        assertEquals(String.format(ViewCommand.MESSAGE_VIEW_PERSON_SUCCESS, INDEX_FIRST_PERSON.getOneBased()),
                result.getFeedbackToUser());
        assertFalse(result.canClearPersonToView());
        assertTrue(model.getPersonToView().isPresent());
        assertEquals(expectedPerson, model.getPersonToView().get());
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ViewCommand viewCommand = new ViewCommand(outOfBoundIndex);

        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_notInParticipantsMode_throwsCommandException() {
        Model modelNotInEventMode = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(viewCommand, modelNotInEventMode, Messages.MESSAGE_ENTER_EVENT_FIRST);
    }

    @Test
    public void equals() {
        ViewCommand viewFirstCommand = new ViewCommand(INDEX_FIRST_PERSON);
        ViewCommand viewSecondCommand = new ViewCommand(INDEX_SECOND_PERSON);

        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        ViewCommand viewFirstCommandCopy = new ViewCommand(INDEX_FIRST_PERSON);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        assertFalse(viewFirstCommand.equals(1));
        assertFalse(viewFirstCommand.equals(null));
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ViewCommand viewCommand = new ViewCommand(targetIndex);
        String expected = ViewCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, viewCommand.toString());
    }
}
