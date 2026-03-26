package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.logic.statistics.StatisticsSummary;

/**
 * Panel containing summary statistics for event participants.
 */
public class StatisticsPanel extends UiPart<Region> {

    private static final String FXML = "StatisticsPanel.fxml";

    @FXML
    private TextArea eventSummaryText;

    @FXML
    private TextArea rsvpSummaryText;

    @FXML
    private TextArea checkinSummaryText;

    @FXML
    private TextArea tagDistributionText;

    public StatisticsPanel() {
        super(FXML);
    }

    /**
     * Updates the statistics panel display from the given summary.
     */
    public void update(StatisticsSummary summary) {
        requireNonNull(summary);
        eventSummaryText.setText(new StringBuilder()
                .append("Total participants: ").append(summary.getTotalCount()).append("\n")
                .append("RSVP Yes: ").append(summary.getRsvpYesCount()).append("\n")
                .append("Check-ins: ").append(summary.getCheckedInCount()).append("\n")
                .append("Teams: ").append(summary.getTeamCount())
                .toString());

        rsvpSummaryText.setText(new StringBuilder()
                .append(formatMetric("RSVP Yes", summary.getRsvpYesCount(), summary.getRsvpYesPercentage()))
                .append(formatMetric("RSVP No", summary.getRsvpNoCount(), summary.getRsvpNoPercentage()))
                .append(formatMetric("RSVP Pending", summary.getRsvpPendingCount(), summary.getRsvpPendingPercentage()))
                .toString());

        checkinSummaryText.setText(new StringBuilder()
                .append(formatMetric("Checked in", summary.getCheckedInCount(), summary.getCheckedInPercentage()))
                .append(formatMetric("Not checked in",
                        summary.getNotCheckedInCount(), summary.getNotCheckedInPercentage()))
                .toString());

        if (summary.getTagCounts().isEmpty()) {
            tagDistributionText.setText("No tags found.");
        } else {
            StringBuilder tagsBuilder = new StringBuilder();
            summary.getTagCounts().forEach((tag, count) ->
                    tagsBuilder.append(formatMetric(tag, count, summary.getTagPercentage(tag))));
            tagDistributionText.setText(tagsBuilder.toString());
        }
    }

    private String formatMetric(String label, int count, double percentage) {
        return String.format("%s: %d (%.1f%%)%n", label, count, percentage);
    }
}
