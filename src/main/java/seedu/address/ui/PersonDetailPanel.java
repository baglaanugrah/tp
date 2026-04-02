package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Panel for displaying detailed information of a {@code Person}.
 */
public class PersonDetailPanel extends UiPart<Region> {

    private static final String FXML = "PersonDetailPanel.fxml";

    @FXML
    private Label detailLabel;

    /**
     * Creates a {@code PersonDetailPanel}.
     */
    public PersonDetailPanel() {
        super(FXML);
        setDetailText("Details coming soon. Use: view INDEX");
    }

    /**
     * Updates the skeleton message shown in the detail pane.
     */
    public void setDetailText(String text) {
        requireNonNull(text);
        detailLabel.setText(text);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("detailLabel", detailLabel.getText())
                .toString();
    }
}

