package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.ThemeMode;
import seedu.address.model.Model;

/**
 * Switches the application theme mode.
 */
public class SwitchModeCommand extends Command {

    public static final String COMMAND_WORD = "switchmode";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switches the application theme.\n"
            + "Parameters: THEME (dark|light)\n"
            + "Example: " + COMMAND_WORD + " dark";

    public static final String MESSAGE_SUCCESS = "Switched to %s mode.";
    public static final String MESSAGE_ALREADY_IN_MODE = "You are already in %s mode.";

    private final ThemeMode targetThemeMode;

    public SwitchModeCommand(ThemeMode targetThemeMode) {
        this.targetThemeMode = requireNonNull(targetThemeMode);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (model.getThemeMode() == targetThemeMode) {
            return new CommandResult(
                    String.format(MESSAGE_ALREADY_IN_MODE, targetThemeMode.getDisplayName().toLowerCase()),
                    false,
                    false,
                    false,
                    false);
        }

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, targetThemeMode.getDisplayName().toLowerCase()),
                false,
                false,
                false,
                false,
                targetThemeMode);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SwitchModeCommand)) {
            return false;
        }

        SwitchModeCommand otherCommand = (SwitchModeCommand) other;
        return targetThemeMode == otherCommand.targetThemeMode;
    }
}
