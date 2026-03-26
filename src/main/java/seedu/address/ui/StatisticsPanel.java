package seedu.address.ui;

import javafx.scene.layout.Region;

/**
 * Panel containing summary statistics for event participants.
 */
public class StatisticsPanel extends UiPart<Region> {

    private static final String FXML = "StatisticsPanel.fxml";

    public StatisticsPanel() {
        super(FXML);
    }
}
