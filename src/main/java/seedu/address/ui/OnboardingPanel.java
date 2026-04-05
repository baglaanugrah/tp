package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 * Read-only panel for first-launch onboarding; separate from command result / error output.
 */
public class OnboardingPanel extends UiPart<VBox> {

    private static final String FXML = "OnboardingPanel.fxml";

    @FXML
    private TextArea messageText;

    public OnboardingPanel() {
        super(FXML);
    }

    public void setMessage(String message) {
        requireNonNull(message);
        messageText.setText(message);
    }
}
