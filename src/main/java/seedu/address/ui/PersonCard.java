package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label team;
    @FXML
    private Label checkInStatus;
    @FXML
    private FlowPane tags;
    @FXML
    private Label github;
    @FXML
    private Label rsvpStatus;


    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        if (person.getTeam().isPresent()) {
            team.setText("Team: " + person.getTeam().get().teamName);
        } else {
            team.setVisible(false);
            team.setManaged(false);
        }
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        if (person.getGitHub().isPresent()) {
            github.setText("GitHub: " + person.getGitHub().get().value);
        } else {
            github.setVisible(false);
            github.setManaged(false);
        }
        rsvpStatus.setText("RSVP: " + person.getRsvpStatus().value);
        if (person.getCheckInStatus().getStatus()) {
            checkInStatus.setText("Checked-In");
            checkInStatus.getStyleClass().add("checked-in");
        } else {
            checkInStatus.setText("Not Checked-In");
            checkInStatus.getStyleClass().add("not-checked-in");
        }

    }
}
