package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.ThemeMode;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 * Contains integration tests for {@code SwitchModeCommand}.
 */
public class SwitchModeCommandTest {

    private final Model model = new ModelManager();
    private final Model expectedModel = new ModelManager();

    @Test
    public void execute_switchToLight_success() {
        CommandResult expectedCommandResult = new CommandResult(
                String.format(SwitchModeCommand.MESSAGE_SUCCESS, "light"),
                false,
                false,
                false,
                false,
                ThemeMode.LIGHT);

        assertCommandSuccess(new SwitchModeCommand(ThemeMode.LIGHT), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        SwitchModeCommand switchToDark = new SwitchModeCommand(ThemeMode.DARK);
        SwitchModeCommand switchToLight = new SwitchModeCommand(ThemeMode.LIGHT);

        org.junit.jupiter.api.Assertions.assertEquals(switchToDark, new SwitchModeCommand(ThemeMode.DARK));
        org.junit.jupiter.api.Assertions.assertNotEquals(switchToDark, switchToLight);
    }
}
