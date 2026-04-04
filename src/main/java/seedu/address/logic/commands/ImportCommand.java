package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.util.CsvDataException;
import seedu.address.commons.util.CsvImportResult;
import seedu.address.commons.util.CsvRowError;
import seedu.address.commons.util.CsvUtil;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Imports participants from a CSV file into the current event.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports participants from a CSV file into the current event.\n"
            + "Parameters: FILE_PATH | list\n"
            + "Examples: " + COMMAND_WORD + " data/participants.csv"
            + " | " + COMMAND_WORD + " list";

    public static final String MESSAGE_INVALID_PATH = "The file path is invalid.";
    public static final String MESSAGE_FILE_NOT_FOUND = "CSV file not found: %1$s";
    public static final String MESSAGE_INVALID_EXTENSION = "Only .csv files are supported for import.";
    public static final String MESSAGE_IMPORT_IO_ERROR = "Failed to read CSV file: %1$s";
    public static final String MESSAGE_IMPORT_DATA_ERROR = "Invalid CSV data: %1$s";
    public static final String MESSAGE_LIST_IO_ERROR = "Failed to list CSV files: %1$s";

    private static final int MAX_ERROR_DETAILS = 3;
    private static final int MAX_LISTED_CSV_FILES = 100;

    private final String filePath;
    private final boolean shouldListCsvFiles;

    /**
     * Creates an import command with the source CSV file path.
     */
    public ImportCommand(String filePath) {
        requireNonNull(filePath);
        this.filePath = filePath.trim();
        this.shouldListCsvFiles = false;
    }

    /**
     * Creates an import command that lists discovered CSV files.
     */
    public ImportCommand(boolean shouldListCsvFiles) {
        this.filePath = "";
        this.shouldListCsvFiles = shouldListCsvFiles;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.isInEventParticipantsMode()) {
            throw new CommandException(Messages.MESSAGE_ENTER_EVENT_FIRST);
        }

        if (shouldListCsvFiles) {
            return new CommandResult(buildCsvListingFeedback());
        }

        if (!FileUtil.isValidPath(filePath)) {
            throw new CommandException(MESSAGE_INVALID_PATH);
        }

        Path csvPath = Paths.get(filePath);
        if (!FileUtil.isFileExists(csvPath)) {
            throw new CommandException(String.format(MESSAGE_FILE_NOT_FOUND, csvPath));
        }
        if (!csvPath.getFileName().toString().toLowerCase().endsWith(CsvUtil.EXTENSION)) {
            throw new CommandException(MESSAGE_INVALID_EXTENSION);
        }

        CsvImportResult result;
        try {
            result = CsvUtil.readPersonsFromCsv(csvPath);
        } catch (CsvDataException e) {
            throw new CommandException(String.format(MESSAGE_IMPORT_DATA_ERROR, e.getMessage()));
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_IMPORT_IO_ERROR, e.getMessage()), e);
        }

        int importedCount = 0;
        int duplicateCount = 0;
        for (Person person : result.getPersons()) {
            if (model.hasPerson(person)) {
                duplicateCount++;
                continue;
            }
            model.addPerson(person);
            importedCount++;
        }

        int invalidCount = result.getRowErrors().size();
        StringBuilder feedback = new StringBuilder();
        feedback.append(String.format(
                "Imported %d participant(s) from %s. Skipped %d duplicate(s), %d invalid row(s).",
                importedCount, csvPath, duplicateCount, invalidCount));

        if (invalidCount > 0) {
            feedback.append(" Invalid rows: ");
            List<String> snippets = new ArrayList<>();
            List<CsvRowError> errors = result.getRowErrors();
            for (int i = 0; i < errors.size() && i < MAX_ERROR_DETAILS; i++) {
                snippets.add(errors.get(i).toString());
            }
            feedback.append(String.join("; ", snippets));
            if (invalidCount > MAX_ERROR_DETAILS) {
                feedback.append(String.format("; ...and %d more", invalidCount - MAX_ERROR_DETAILS));
            }
        }

        return new CommandResult(feedback.toString());
    }

    private String buildCsvListingFeedback() throws CommandException {
        Path currentDirectory = Paths.get("").toAbsolutePath().normalize();
        try (Stream<Path> fileStream = Files.walk(currentDirectory)) {
            List<Path> csvFiles = fileStream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().toLowerCase().endsWith(CsvUtil.EXTENSION))
                    .sorted(Comparator.comparing(path -> path.toString().toLowerCase()))
                    .limit(MAX_LISTED_CSV_FILES + 1L)
                    .collect(Collectors.toList());

            if (csvFiles.isEmpty()) {
                return "No CSV files found under " + currentDirectory + ".";
            }

            boolean isTruncated = csvFiles.size() > MAX_LISTED_CSV_FILES;
            List<Path> displayedFiles = isTruncated
                    ? csvFiles.subList(0, MAX_LISTED_CSV_FILES)
                    : csvFiles;

            StringBuilder feedback = new StringBuilder();
            feedback.append(String.format("Found %d CSV file(s) under %s:", displayedFiles.size(), currentDirectory));

            int index = 1;
            for (Path csvFile : displayedFiles) {
                Path normalizedFile = csvFile.toAbsolutePath().normalize();
                Path relativePath = currentDirectory.relativize(normalizedFile);
                Path parent = relativePath.getParent();
                String directory = parent == null ? "." : parent.toString();
                feedback.append(System.lineSeparator())
                        .append(index++)
                        .append(". ")
                        .append(relativePath)
                        .append(" (directory: ")
                        .append(directory)
                        .append(")");
            }

            if (isTruncated) {
                feedback.append(System.lineSeparator())
                        .append(String.format("Showing first %d result(s).", MAX_LISTED_CSV_FILES));
            }

            return feedback.toString();
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_LIST_IO_ERROR, e.getMessage()), e);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ImportCommand)) {
            return false;
        }

        ImportCommand otherImportCommand = (ImportCommand) other;
        return filePath.equals(otherImportCommand.filePath)
                && shouldListCsvFiles == otherImportCommand.shouldListCsvFiles;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("filePath", filePath)
                .add("shouldListCsvFiles", shouldListCsvFiles)
                .toString();
    }
}
