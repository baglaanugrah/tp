package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;
    private List<String> highlightKeywords = List.of();
    private boolean compactMode;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    void setHighlightKeywords(List<String> keywords) {
        List<String> normalizedKeywords = List.copyOf(keywords);
        if (highlightKeywords.equals(normalizedKeywords)) {
            return;
        }
        highlightKeywords = normalizedKeywords;
        personListView.refresh();
    }

    /**
     * Replaces the data backing this list view.
     */
    public void setPersonList(ObservableList<Person> personList) {
        personListView.setItems(personList);
    }

    /**
     * Enables compact cards for participants mode where full details are shown in the detail pane.
     */
    public void setCompactMode(boolean compactMode) {
        this.compactMode = compactMode;
        personListView.refresh();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1, highlightKeywords, compactMode).getRoot());
            }
        }
    }

}
