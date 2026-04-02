package seedu.address.ui;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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
    private final List<String> highlightKeywords;

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
        this(person, displayedIndex, List.of());
    }

    /**
     * Creates a {@code PersonCode} with highlighted matching text segments.
     */
    public PersonCard(Person person, int displayedIndex, List<String> highlightKeywords) {
        super(FXML);
        this.person = person;
        this.highlightKeywords = highlightKeywords.stream()
                .map(keyword -> keyword.toLowerCase(Locale.ROOT))
                .filter(keyword -> !keyword.isBlank())
                .toList();
        id.setText(displayedIndex + ". ");
        setHighlightedLabelText(name, person.getName().fullName);
        setHighlightedLabelText(phone, person.getPhone().value);
        setHighlightedLabelText(address, person.getAddress().value);
        setHighlightedLabelText(email, person.getEmail().value);
        if (person.getTeam().isPresent()) {
            setHighlightedLabelText(team, "Team: " + person.getTeam().get().teamName);
        } else {
            team.setVisible(false);
            team.setManaged(false);
        }
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
            .forEach(tag -> tags.getChildren().add(createTagLabel(tag.tagName)));

        if (person.getGitHub().isPresent()) {
            setHighlightedLabelText(github, "GitHub: " + person.getGitHub().get().value);
        } else {
            github.setVisible(false);
            github.setManaged(false);
        }
        setPlainLabelText(rsvpStatus, "RSVP: " + person.getRsvpStatus().value);
        if (person.getCheckInStatus().getStatus()) {
            setStatusLabelText("Checked-In");
            checkInStatus.getStyleClass().add("checked-in");
        } else {
            setStatusLabelText("Not Checked-In");
            checkInStatus.getStyleClass().add("not-checked-in");
        }

    }

    private Label createTagLabel(String value) {
        Label label = new Label();
        label.setText(value);
        return label;
    }

    private void setHighlightedLabelText(Label label, String value) {
        label.setText("");
        label.setGraphic(createHighlightedTextFlow(value));
        label.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    private void setStatusLabelText(String value) {
        checkInStatus.setGraphic(null);
        checkInStatus.setContentDisplay(ContentDisplay.TEXT_ONLY);
        checkInStatus.setText(value);
    }

    private void setPlainLabelText(Label label, String value) {
        label.setGraphic(null);
        label.setContentDisplay(ContentDisplay.TEXT_ONLY);
        label.setText(value);
    }

    private TextFlow createHighlightedTextFlow(String value) {
        TextFlow textFlow = new TextFlow();
        if (value.isEmpty()) {
            return textFlow;
        }

        boolean[] highlightedPositions = new boolean[value.length()];
        String lowerCasedValue = value.toLowerCase(Locale.ROOT);
        for (String keyword : highlightKeywords) {
            markHighlightedSegments(lowerCasedValue, keyword, highlightedPositions);
        }

        int segmentStart = 0;
        while (segmentStart < value.length()) {
            boolean highlighted = highlightedPositions[segmentStart];
            int segmentEnd = segmentStart + 1;
            while (segmentEnd < value.length() && highlightedPositions[segmentEnd] == highlighted) {
                segmentEnd++;
            }

            Text text = new Text(value.substring(segmentStart, segmentEnd));
            text.getStyleClass().add(highlighted ? "highlight-match" : "highlight-normal");
            textFlow.getChildren().add(text);
            segmentStart = segmentEnd;
        }

        return textFlow;
    }

    private void markHighlightedSegments(String text, String keyword, boolean[] highlightedPositions) {
        int startIndex = text.indexOf(keyword);
        while (startIndex >= 0) {
            int endIndex = startIndex + keyword.length();
            for (int i = startIndex; i < endIndex; i++) {
                highlightedPositions[i] = true;
            }
            startIndex = text.indexOf(keyword, startIndex + 1);
        }
    }
}
