package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import seedu.address.commons.util.CsvUtil;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Exports participants from the current event to a CSV file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String DEFAULT_EXPORT_FILE_PATH = "data/exports/participants.csv";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports participants from the current event to a CSV file.\n"
            + "Parameters: [FILE_PATH]\n"
            + "Example: " + COMMAND_WORD + " data/exports/team-event.csv";

    public static final String MESSAGE_INVALID_PATH = "The file path is invalid.";
    public static final String MESSAGE_INVALID_EXTENSION = "Only .csv files are supported for export.";
    public static final String MESSAGE_EXPORT_ERROR = "Failed to export CSV file: %1$s";

    private final String filePath;

    public ExportCommand(String filePath) {
        this.filePath = filePath == null ? "" : filePath.trim();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.isInEventParticipantsMode()) {
            throw new CommandException(Messages.MESSAGE_ENTER_EVENT_FIRST);
        }

        String requestedPath = filePath.isBlank() ? DEFAULT_EXPORT_FILE_PATH : filePath;
        if (!FileUtil.isValidPath(requestedPath)) {
            throw new CommandException(MESSAGE_INVALID_PATH);
        }

        Path preferredPath = Paths.get(requestedPath);
        if (!preferredPath.getFileName().toString().toLowerCase().endsWith(CsvUtil.EXTENSION)) {
            throw new CommandException(MESSAGE_INVALID_EXTENSION);
        }

        Path resolvedPath = CsvUtil.resolveTimestampedPathIfExists(preferredPath);
        List<Person> participants = model.getAddressBook().getPersonList();

        try {
            CsvUtil.writePersonsToCsv(participants, resolvedPath);
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_EXPORT_ERROR, e.getMessage()), e);
        }

        String suffix = preferredPath.equals(resolvedPath)
                ? ""
                : String.format(" (target existed, exported to %s instead)", resolvedPath);
        String feedback = String.format("Exported %d participant(s) to %s%s.",
                participants.size(), resolvedPath, suffix);
        return new CommandResult(feedback);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ExportCommand)) {
            return false;
        }

        ExportCommand otherExportCommand = (ExportCommand) other;
        return filePath.equals(otherExportCommand.filePath);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("filePath", filePath)
                .toString();
    }
}
