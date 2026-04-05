package seedu.address.model;

import java.nio.file.Path;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.ThemeMode;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

    /** @return true if the user has finished or skipped the first-launch onboarding tutorial */
    boolean isOnboardingCompleted();

    ThemeMode getThemeMode();

}
