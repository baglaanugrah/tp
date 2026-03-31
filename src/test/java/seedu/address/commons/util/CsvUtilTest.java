package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class CsvUtilTest {

    @TempDir
    public Path tempDir;

    @Test
    public void readPersonsFromCsv_validAndInvalidRows_returnsStructuredResult() throws Exception {
        Path csvPath = tempDir.resolve("participants.csv");
        String csvContent = String.join(System.lineSeparator(),
            "name,phone,email,address,team,github,rsvpStatus,tags,checkinStatus",
            "Alice Tan,91234567,alice@example.com,Street 1,TeamA,alicehub,yes,java;ml,yes",
                "Bob Ng,not-a-phone,bob@example.com,Street 2,,,,",
                "Carl Lim,92345678,carl@example.com,Street 3,,,,")
                + System.lineSeparator();
        Files.writeString(csvPath, csvContent);

        CsvImportResult result = CsvUtil.readPersonsFromCsv(csvPath);

        assertEquals(2, result.getPersons().size());
        assertTrue(result.getPersons().get(0).getCheckInStatus().getStatus());
        assertEquals(1, result.getRowErrors().size());
        assertTrue(result.getRowErrors().get(0).toString().contains("line 3"));
    }

    @Test
    public void writePersonsToCsv_escapeSpecialCharacters_writesQuotedValues() throws IOException {
        Path csvPath = tempDir.resolve("escaped.csv");
        Person person = new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("Blk 1, \"Street\" 2")
                .withEmail("alice@example.com")
                .withPhone("91234567")
                .withTags("ml", "java")
                .build();

        CsvUtil.writePersonsToCsv(List.of(person), csvPath);
        String written = Files.readString(csvPath);

        assertTrue(written.contains("\"Blk 1, \"\"Street\"\" 2\""));
        assertTrue(written.contains("java;ml") || written.contains("ml;java"));
        assertTrue(written.contains(",no"));
    }

    @Test
    public void resolveTimestampedPathIfExists_existingFile_returnsDifferentPath() throws IOException {
        Path existing = tempDir.resolve("participants.csv");
        Files.writeString(existing, "header\n");

        Path resolved = CsvUtil.resolveTimestampedPathIfExists(existing);

        assertTrue(!resolved.equals(existing));
        assertTrue(resolved.getFileName().toString().startsWith("participants-"));
        assertTrue(resolved.getFileName().toString().endsWith(".csv"));
    }
}
