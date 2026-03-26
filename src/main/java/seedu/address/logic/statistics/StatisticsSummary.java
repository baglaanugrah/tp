package seedu.address.logic.statistics;

import static java.util.Objects.requireNonNull;

import java.util.Map;

/**
 * Immutable summary statistics DTO with display formatting.
 */
public class StatisticsSummary {
    private static final String SECTION_DIVIDER = "================";

    private final int totalCount;
    private final int checkedInCount;
    private final int rsvpYesCount;
    private final int rsvpNoCount;
    private final int rsvpPendingCount;
    private final int teamCount;
    private final Map<String, Integer> tagCounts;

    /**
     * Creates an immutable statistics summary with precomputed counts.
     */
    public StatisticsSummary(int totalCount, int checkedInCount,
                             int rsvpYesCount, int rsvpNoCount, int rsvpPendingCount,
                             int teamCount,
                             Map<String, Integer> tagCounts) {
        this.totalCount = totalCount;
        this.checkedInCount = checkedInCount;
        this.rsvpYesCount = rsvpYesCount;
        this.rsvpNoCount = rsvpNoCount;
        this.rsvpPendingCount = rsvpPendingCount;
        this.teamCount = teamCount;
        this.tagCounts = Map.copyOf(tagCounts);
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getCheckedInCount() {
        return checkedInCount;
    }

    public int getNotCheckedInCount() {
        return totalCount - checkedInCount;
    }

    public int getRsvpYesCount() {
        return rsvpYesCount;
    }

    public int getRsvpNoCount() {
        return rsvpNoCount;
    }

    public int getRsvpPendingCount() {
        return rsvpPendingCount;
    }

    public int getTeamCount() {
        return teamCount;
    }

    public Map<String, Integer> getTagCounts() {
        return tagCounts;
    }

    public double getCheckedInPercentage() {
        return toPercentage(checkedInCount, totalCount);
    }

    public double getNotCheckedInPercentage() {
        return toPercentage(getNotCheckedInCount(), totalCount);
    }

    public double getRsvpYesPercentage() {
        return toPercentage(rsvpYesCount, totalCount);
    }

    public double getRsvpNoPercentage() {
        return toPercentage(rsvpNoCount, totalCount);
    }

    public double getRsvpPendingPercentage() {
        return toPercentage(rsvpPendingCount, totalCount);
    }

    public double getTagPercentage(String tagName) {
        requireNonNull(tagName);
        int count = tagCounts.getOrDefault(tagName.toLowerCase(), 0);
        return toPercentage(count, totalCount);
    }

    /**
     * Formats this summary for display in the statistics panel.
     */
    public String formatForDisplay() {
        StringBuilder sb = new StringBuilder();
        appendSectionHeader(sb, "Event Summary");
        sb.append("Total participants: ").append(getTotalCount()).append("\n")
                .append("RSVP Yes: ").append(getRsvpYesCount()).append("\n")
                .append("Check-ins: ").append(getCheckedInCount()).append("\n")
                .append("Teams: ").append(getTeamCount()).append("\n\n");

        appendSectionHeader(sb, "Engagement Summary");
        sb.append(formatMetric("RSVP Yes", getRsvpYesCount(), getRsvpYesPercentage()))
                .append(formatMetric("RSVP No", getRsvpNoCount(), getRsvpNoPercentage()))
                .append(formatMetric("RSVP Pending", getRsvpPendingCount(), getRsvpPendingPercentage()))
                .append(formatMetric("Checked in", getCheckedInCount(), getCheckedInPercentage()))
                .append(formatMetric("Not checked in", getNotCheckedInCount(), getNotCheckedInPercentage()))
                .append("\n");

        appendSectionHeader(sb, "Other Statistics");
        if (getTagCounts().isEmpty()) {
            sb.append("No tags found.");
        } else {
            for (Map.Entry<String, Integer> entry : getTagCounts().entrySet()) {
                sb.append(formatMetric(entry.getKey(), entry.getValue(), getTagPercentage(entry.getKey())));
            }
        }
        return sb.toString();
    }

    private void appendSectionHeader(StringBuilder sb, String title) {
        sb.append(title).append("\n")
                .append(SECTION_DIVIDER).append("\n");
    }

    private String formatMetric(String label, int count, double percentage) {
        return String.format("%s: %d (%.1f%%)%n", label, count, percentage);
    }

    private static double toPercentage(int count, int total) {
        if (total == 0) {
            return 0.0;
        }
        return (count * 100.0) / total;
    }
}
