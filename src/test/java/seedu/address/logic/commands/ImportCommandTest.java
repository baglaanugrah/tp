package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.enterDefaultEvent;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class ImportCommandTest {

    @TempDir
    public Path tempDir;

    @Test
    public void execute_notInEventParticipantsMode_throwsCommandException() {
        Model model = new ModelManager();
        ImportCommand command = new ImportCommand("data/participants.csv");

        assertCommandFailure(command, model, Messages.MESSAGE_ENTER_EVENT_FIRST);
    }

    @Test
    public void execute_validCsv_importsAndSkipsDuplicatesAndInvalidRows() throws Exception {
        Model model = new ModelManager();
        enterDefaultEvent(model);

        Person existing = new PersonBuilder()
                .withName("Alice Tan")
                .withPhone("91234567")
                .withEmail("alice@example.com")
                .withAddress("Street 1")
                .build();
        model.addPerson(existing);

        Path csvPath = tempDir.resolve("participants.csv");
        String content = String.join(System.lineSeparator(),
            "name,phone,email,address,checkinStatus",
            "Alice Tan,91234567,alice@example.com,Street 1,no",
            "Bob Ng,92345678,bob@example.com,Street 2,yes",
                "Carl Lim,not-phone,carl@example.com,Street 3")
                + System.lineSeparator();
        Files.writeString(csvPath, content);

        ImportCommand command = new ImportCommand(csvPath.toString());
        CommandResult result = command.execute(model);

        assertTrue(result.getFeedbackToUser().contains("Imported 1 participant(s)"));
        assertTrue(result.getFeedbackToUser().contains("Skipped 1 duplicate(s), 1 invalid row(s)"));
        assertEquals(2, model.getAddressBook().getPersonList().size());
        Person importedPerson = model.getAddressBook().getPersonList().stream()
            .filter(person -> person.getName().fullName.equals("Bob Ng"))
            .findFirst()
            .orElseThrow();
        assertTrue(importedPerson.getCheckInStatus().getStatus());
    }
}
