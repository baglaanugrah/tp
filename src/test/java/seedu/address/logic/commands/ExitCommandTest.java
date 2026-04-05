package seedu.address.logic.commands;

import static seedu.address.logic.Messages.MESSAGE_LEAVE_EVENT_FIRST_BEFORE_EXIT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventName;
import seedu.address.testutil.Assert;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true, false);
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void executeExitInParticipantsMode_throwsCommandException() {
        Event event = new Event(
                new EventName("Event 1"),
                new EventDate("2026-01-01"),
                Optional.empty(),
                Optional.empty());
        model.enterEvent(event);

        Assert.assertThrows(seedu.address.logic.commands.exceptions.CommandException.class,
                MESSAGE_LEAVE_EVENT_FIRST_BEFORE_EXIT, () -> new ExitCommand().execute(model));
    }
}
