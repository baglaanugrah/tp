package seedu.address.logic.commands;

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
import seedu.address.testutil.PersonBuilder;

public class ExportCommandTest {

    @TempDir
    public Path tempDir;

    @Test
    public void execute_notInEventParticipantsMode_throwsCommandException() {
        Model model = new ModelManager();
        ExportCommand command = new ExportCommand("");

        assertCommandFailure(command, model, Messages.MESSAGE_ENTER_EVENT_FIRST);
    }

    @Test
    public void execute_validPath_exportsCsv() throws Exception {
        Model model = new ModelManager();
        enterDefaultEvent(model);
        model.addPerson(new PersonBuilder().withName("Bob Ng").withPhone("92345678").build());

        Path exportPath = tempDir.resolve("participants.csv");
        ExportCommand command = new ExportCommand(exportPath.toString());

        CommandResult result = command.execute(model);

        assertTrue(Files.exists(exportPath));
        assertTrue(result.getFeedbackToUser().contains("Exported 1 participant(s)"));
    }

    @Test
    public void execute_existingTarget_createsTimestampedFile() throws Exception {
        Model model = new ModelManager();
        enterDefaultEvent(model);
        model.addPerson(new PersonBuilder().withName("Bob Ng").withPhone("92345678").build());

        Path exportPath = tempDir.resolve("participants.csv");
        Files.writeString(exportPath, "old data");

        ExportCommand command = new ExportCommand(exportPath.toString());
        CommandResult result = command.execute(model);

        assertTrue(result.getFeedbackToUser().contains("target existed"));
    }
}
