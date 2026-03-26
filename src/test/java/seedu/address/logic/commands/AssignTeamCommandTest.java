package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Team;

public class AssignTeamCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_success() {
        Team targetTeam = new Team("Alpha");
        Person personToAssign = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        AssignTeamCommand assignTeamCommand = new AssignTeamCommand(INDEX_FIRST_PERSON, targetTeam);

        Person assignedPerson = new Person(
                personToAssign.getName(),
                personToAssign.getPhone(),
                personToAssign.getEmail(),
                personToAssign.getAddress(),
                Optional.of(targetTeam),
                personToAssign.getTags(),
                personToAssign.getCheckInStatus(),
                personToAssign.getGitHub().orElse(null),
                personToAssign.getRsvpStatus());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToAssign, assignedPerson);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        String expectedMessage = String.format(AssignTeamCommand.MESSAGE_ASSIGN_TEAM_SUCCESS,
                personToAssign.getName().fullName, targetTeam.teamName);

        assertCommandSuccess(assignTeamCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AssignTeamCommand assignTeamCommand = new AssignTeamCommand(outOfBoundIndex, new Team("Alpha"));

        assertCommandFailure(assignTeamCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        AssignTeamCommand firstCommand = new AssignTeamCommand(INDEX_FIRST_PERSON, new Team("Alpha"));
        AssignTeamCommand firstCommandCopy = new AssignTeamCommand(INDEX_FIRST_PERSON, new Team("Alpha"));
        AssignTeamCommand secondCommand = new AssignTeamCommand(INDEX_SECOND_PERSON, new Team("Beta"));

        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(firstCommand.equals(firstCommandCopy));
        assertFalse(firstCommand.equals(null));
        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void toStringMethod() {
        AssignTeamCommand assignTeamCommand = new AssignTeamCommand(INDEX_FIRST_PERSON, new Team("Alpha"));
        String expected = AssignTeamCommand.class.getCanonicalName()
                + "{index=" + INDEX_FIRST_PERSON + ", team=" + new Team("Alpha") + "}";
        assertEquals(expected, assignTeamCommand.toString());
    }
}
