package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.statistics.StatisticsCalculator;
import seedu.address.logic.statistics.StatisticsSummary;
import seedu.address.model.Model;

/**
 * Shows a summary of event participant statistics.
 */
public class StatisticsCommand extends Command {

    public static final String COMMAND_WORD = "statistics";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays a summary of participant statistics.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Showing participant statistics summary.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        StatisticsCalculator calculator = new StatisticsCalculator();
        StatisticsSummary summary = calculator.calculate(model.getAddressBook().getPersonList());
        String feedback = summary.formatForDisplay();
        return new CommandResult(MESSAGE_SUCCESS, false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof StatisticsCommand;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
