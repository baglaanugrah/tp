package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyEventBook;

/**
 * A class to access EventBook data stored as a json file on the hard disk.
 */
public class JsonEventBookStorage implements EventBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonEventBookStorage.class);

    private final Path filePath;

    /**
     * Creates a {@code JsonEventBookStorage} with the given file path.
     */
    public JsonEventBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getEventBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEventBook> readEventBook() throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableEventBook> jsonEventBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableEventBook.class);
        if (!jsonEventBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonEventBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveEventBook(ReadOnlyEventBook eventBook) throws IOException {
        requireNonNull(eventBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableEventBook(eventBook), filePath);
    }

}
