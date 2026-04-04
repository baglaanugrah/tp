package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.Team;

/**
 * Panel for displaying detailed information of a {@code Person}.
 */
public class PersonDetailPanel extends UiPart<Region> {

    private static final String FXML = "PersonDetailPanel.fxml";

    @FXML
    private Label nameValue;
    @FXML
    private Label phoneValue;
    @FXML
    private Label emailValue;
    @FXML
    private Label addressValue;
    @FXML
    private Label githubValue;
    @FXML
    private Label rsvpValue;
    @FXML
    private Label attendanceValue;
    @FXML
    private Label teamKeyLabel;
    @FXML
    private Label teamValue;
    @FXML
    private FlowPane tagsFlow;

    /**
     * Creates a {@code PersonDetailPanel}.
     */
    public PersonDetailPanel() {
        super(FXML);
        ensureTeamChipStyles();
        setDetailText("Details coming soon. Use: view INDEX");
    }

    /**
     * Updates the skeleton message shown in the detail pane.
     */
    public void setDetailText(String text) {
        requireNonNull(text);
        nameValue.setText(text);
        clearDetailValues();
        tagsFlow.getChildren().clear();
        setTeamVisibility(false);
        clearChipStyles();
    }

    /**
     * Updates this panel to display details of the given {@code person}.
     */
    public void setPerson(Person person) {
        requireNonNull(person);
        setBasicInfo(person);
        setGitHub(person);
        setRsvpChip(person);
        setAttendanceChip(person);
        setTeamChip(person);
        setTags(person);
    }

    private void clearChipStyles() {
        rsvpValue.getStyleClass().removeIf(style -> style.startsWith("rsvp-"));
        attendanceValue.getStyleClass().removeIf(style -> style.startsWith("attendance-"));
    }

    private void setBasicInfo(Person person) {
        nameValue.setText(person.getName().fullName);
        phoneValue.setText(person.getPhone().value);
        emailValue.setText(person.getEmail().value);
        addressValue.setText(person.getAddress().value);
    }

    private void setGitHub(Person person) {
        String githubText = person.getGitHub()
                .map(github -> github.value)
                .orElse("No GitHub");
        githubValue.setText(githubText);
    }

    private void setRsvpChip(Person person) {
        String rsvp = person.getRsvpStatus().value;
        rsvpValue.setText(rsvp);
        rsvpValue.getStyleClass().removeIf(style -> style.startsWith("rsvp-"));
        rsvpValue.getStyleClass().add(resolveRsvpStyleClass(rsvp));
    }

    private void setAttendanceChip(Person person) {
        boolean checkedIn = person.getCheckInStatus().getStatus();
        attendanceValue.setText(checkedIn ? "Checked-In" : "Not Checked-In");
        attendanceValue.getStyleClass().removeIf(style -> style.startsWith("attendance-"));
        attendanceValue.getStyleClass().add(checkedIn ? "attendance-yes" : "attendance-no");
    }

    private void setTeamChip(Person person) {
        if (person.getTeam().isPresent()) {
            Team team = person.getTeam().get();
            teamValue.setText(team.teamName);
            setTeamVisibility(true);
        } else {
            teamValue.setText("");
            setTeamVisibility(false);
        }
    }

    private void setTags(Person person) {
        tagsFlow.getChildren().clear();
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label chip = new Label(tag.tagName);
                    chip.getStyleClass().addAll("detail-chip", "tag-pill");
                    tagsFlow.getChildren().add(chip);
                });
    }

    private String resolveRsvpStyleClass(String rsvpStatus) {
        if (rsvpStatus.equalsIgnoreCase("Yes")) {
            return "rsvp-yes";
        }
        if (rsvpStatus.equalsIgnoreCase("No")) {
            return "rsvp-no";
        }
        return "rsvp-pending";
    }

    private void setTeamVisibility(boolean isVisible) {
        teamKeyLabel.setVisible(isVisible);
        teamKeyLabel.setManaged(isVisible);
        teamValue.setVisible(isVisible);
        teamValue.setManaged(isVisible);
    }

    private void clearDetailValues() {
        phoneValue.setText("");
        emailValue.setText("");
        addressValue.setText("");
        githubValue.setText("");
        rsvpValue.setText("");
        attendanceValue.setText("");
        teamValue.setText("");
    }

    private void ensureTeamChipStyles() {
        if (!teamValue.getStyleClass().contains("detail-chip")) {
            teamValue.getStyleClass().add("detail-chip");
        }
        if (!teamValue.getStyleClass().contains("team-pill")) {
            teamValue.getStyleClass().add("team-pill");
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nameValue", nameValue.getText())
                .toString();
    }
}

