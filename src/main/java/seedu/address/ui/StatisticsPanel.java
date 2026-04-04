package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import seedu.address.logic.statistics.StatisticsSummary;

/**
 * Panel containing summary statistics for event participants.
 */
public class StatisticsPanel extends UiPart<Region> {

    private static final String FXML = "StatisticsPanel.fxml";

    @FXML
    private VBox rsvpLegendBox;

    @FXML
    private Label totalParticipantsValue;

    @FXML
    private Label rsvpYesValue;

    @FXML
    private Label checkinsValue;

    @FXML
    private Label teamsValue;

    @FXML
    private TextArea tagDistributionText;

    @FXML
    private HBox rsvpChartPlaceholder;

    @FXML
    private HBox checkinChartPlaceholder;

    @FXML
    private VBox checkinLegendBox;

    @FXML
    private HBox tagChartPlaceholder;

    @FXML
    private VBox tagLegendBox;

    public StatisticsPanel() {
        super(FXML);
    }

    /**
     * Updates the statistics panel display from the given summary.
     */
    public void update(StatisticsSummary summary) {
        requireNonNull(summary);
        totalParticipantsValue.setText(String.valueOf(summary.getTotalCount()));
        rsvpYesValue.setText(String.valueOf(summary.getRsvpYesCount()));
        checkinsValue.setText(String.valueOf(summary.getCheckedInCount()));
        teamsValue.setText(String.valueOf(summary.getTeamCount()));

        updateRsvpLegend(summary);
        updateCheckinLegend(summary);

        updateRsvpChart(summary);
        updateCheckinChart(summary);
        updateTagChart(summary);
        updateTagLegend(summary);
    }

    private String formatMetric(String label, int count, double percentage) {
        return String.format("%s: %d (%.1f%%)%n", label, count, percentage);
    }

    private void updateRsvpLegend(StatisticsSummary summary) {
        rsvpLegendBox.getChildren().clear();

        addLegendRow(rsvpLegendBox, "legend-yes",
                String.format("Yes (%,.1f%%)", summary.getRsvpYesPercentage()));
        addLegendRow(rsvpLegendBox, "legend-no",
                String.format("No (%,.1f%%)", summary.getRsvpNoPercentage()));
        addLegendRow(rsvpLegendBox, "legend-pending",
                String.format("Pending (%,.1f%%)", summary.getRsvpPendingPercentage()));
    }

    private void addLegendRow(VBox container, String colorStyleClass, String text) {
        HBox row = new HBox(8);

        javafx.scene.shape.Rectangle colorBox = new javafx.scene.shape.Rectangle(14, 14);
        colorBox.getStyleClass().add(colorStyleClass);

        Label label = new Label(text);
        label.getStyleClass().add("stats-legend-label");
        row.getChildren().addAll(colorBox, label);
        container.getChildren().add(row);
    }

    private void updateCheckinLegend(StatisticsSummary summary) {
        checkinLegendBox.getChildren().clear();

        addLegendRow(checkinLegendBox, "legend-yes",
                String.format("Checked in (%.1f%%)", summary.getCheckedInPercentage()));
        addLegendRow(checkinLegendBox, "legend-no",
                String.format("Not checked in (%.1f%%)", summary.getNotCheckedInPercentage()));
    }

    private void updateTagChart(StatisticsSummary summary) {
        tagChartPlaceholder.getChildren().clear();

        if (summary.getTagCounts().isEmpty()) {
            tagDistributionText.setText("No tags found.");
            return;
        }

        tagDistributionText.clear();

        CategoryAxis yAxis = new CategoryAxis();
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Count");
        double maxCount = summary.getTagCounts().values().stream()
                .mapToDouble(Integer::doubleValue)
                .max()
                .orElse(1.0);
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(Math.max(1.0, maxCount * 1.1));
        xAxis.setTickUnit(Math.max(1.0, maxCount / 4.0));

        BarChart<Number, String> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setLegendVisible(false);
        barChart.setCategoryGap(8);
        barChart.setBarGap(4);
        barChart.setAnimated(true);
        barChart.setPadding(new Insets(0, 20, 0, 0));

        BarChart.Series<Number, String> series = new BarChart.Series<>();
        List<Double> finalValues = new ArrayList<>();
        summary.getTagCounts().forEach((tag, count) -> {
            series.getData().add(new BarChart.Data<>(0.0, tag));
            finalValues.add(count.doubleValue());
        });

        barChart.getData().add(series);
        barChart.setPrefHeight(200);
        barChart.setHorizontalGridLinesVisible(false);
        barChart.setVerticalGridLinesVisible(false);

        xAxis.setTickLabelsVisible(false);
        xAxis.setTickMarkVisible(false);
        xAxis.setOpacity(0);

        barChart.setHorizontalZeroLineVisible(false);
        barChart.setVerticalZeroLineVisible(false);
        barChart.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(barChart, javafx.scene.layout.Priority.ALWAYS);

        tagChartPlaceholder.getChildren().add(barChart);

        Platform.runLater(() -> {
            // Assign stable colors.
            int index = 0;
            for (BarChart.Data<Number, String> data : series.getData()) {
                Node bar = data.getNode();
                if (bar != null) {
                    bar.getStyleClass().add("tag-bar-" + (index % 5));
                    index++;
                }
            }

            // Trigger native BarChart animation from 0 -> final values.
            Platform.runLater(() -> {
                for (int i = 0; i < series.getData().size(); i++) {
                    series.getData().get(i).setXValue(finalValues.get(i));
                }
            });
        });
    }

    private void updateRsvpChart(StatisticsSummary summary) {
        rsvpChartPlaceholder.getChildren().clear();

        if (summary.getTotalCount() == 0) {
            return;
        }

        PieChart rsvpChart = new PieChart();
        rsvpChart.setLegendVisible(false);
        rsvpChart.getStyleClass().add("donut-chart");

        // Always add slices in fixed order so colors match legend.
        rsvpChart.getData().add(new PieChart.Data("Yes", summary.getRsvpYesCount()));
        rsvpChart.getData().add(new PieChart.Data("No", summary.getRsvpNoCount()));
        rsvpChart.getData().add(new PieChart.Data("Pending", summary.getRsvpPendingCount()));

        if (!rsvpChart.getData().isEmpty()) {
            String percentageLabel = String.format("%.0f%%", summary.getRsvpYesPercentage());
            StackPane donut = createDonutContainer(rsvpChart, percentageLabel);
            rsvpChartPlaceholder.getChildren().add(donut);
        }
    }

    private void updateCheckinChart(StatisticsSummary summary) {
        checkinChartPlaceholder.getChildren().clear();

        if (summary.getTotalCount() == 0) {
            return;
        }

        PieChart checkinChart = new PieChart();
        checkinChart.setLegendVisible(false);
        checkinChart.getStyleClass().add("donut-chart");

        int checkedIn = summary.getCheckedInCount();
        int notCheckedIn = summary.getNotCheckedInCount();

        // Always add slices in fixed order so colors match legend.
        checkinChart.getData().add(new PieChart.Data("Checked in", checkedIn));
        checkinChart.getData().add(new PieChart.Data("Not checked in", notCheckedIn));

        if (!checkinChart.getData().isEmpty()) {
            String percentageLabel = String.format("%.0f%%", summary.getCheckedInPercentage());
            StackPane donut = createDonutContainer(checkinChart, percentageLabel);
            checkinChartPlaceholder.getChildren().add(donut);
        }
    }

    private void updateTagLegend(StatisticsSummary summary) {
        tagLegendBox.getChildren().clear();

        if (summary.getTagCounts().isEmpty()) {
            return;
        }

        int index = 0;
        double total = summary.getTotalCount();
        for (var entry : summary.getTagCounts().entrySet()) {
            String tag = entry.getKey();
            int count = entry.getValue();
            double percentage = total == 0 ? 0 : (count * 100.0 / total);

            String styleClass = "tag-bar-" + (index % 5);
            String text = String.format("%s (%.1f%%)", tag, percentage);
            addLegendRow(tagLegendBox, styleClass, text);
            index++;
        }
    }

    private StackPane createDonutContainer(PieChart chart, String centerText) {
        StackPane container = new StackPane();
        container.setPrefSize(180, 180);
        container.getChildren().add(chart);

        double holeRadius = 50;
        Circle hole = new Circle(holeRadius);
        hole.setFill(Color.web("#2b2b2b"));

        Label label = new Label(centerText);
        label.getStyleClass().add("donut-center-label");

        container.getChildren().addAll(hole, label);

        RotateTransition spin = new RotateTransition(Duration.seconds(4), chart);
        spin.setFromAngle(0);
        spin.setToAngle(360);
        spin.setCycleCount(1);
        spin.setAutoReverse(false);
        spin.playFromStart();

        return container;
    }
}
