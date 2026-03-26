package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.event.Event;

/**
 * An UI component that displays information of an {@code Event}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    public final Event event;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label date;
    @FXML
    private Label eventLocation;
    @FXML
    private Label description;

    /**
     * Creates an {@code EventCard} with the given {@code Event} and index to display.
     */
    public EventCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;
        id.setText(displayedIndex + ". ");
        name.setText(event.getName().fullName);
        date.setText("Date: " + event.getDate().toString());

        if (event.getLocation().isPresent()) {
            eventLocation.setText("Location: " + event.getLocation().get());
        } else {
            eventLocation.setVisible(false);
            eventLocation.setManaged(false);
        }

        if (event.getDescription().isPresent()) {
            description.setText("Description: " + event.getDescription().get());
        } else {
            description.setVisible(false);
            description.setManaged(false);
        }
    }
}
