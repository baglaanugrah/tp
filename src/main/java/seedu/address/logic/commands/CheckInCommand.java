package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;


/**
 * Marks the attendance of the person.
 */
public class CheckInCommand extends Command {

    public static final String COMMAND_WORD = "checkin";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the attendance of the person identified "
            + "by the index number used in the last person listing. "
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d";
    public static final String MESSAGE_CHECK_IN_PERSON_SUCCESS = "Checked-in person: %1$s";
    public static final String MESSAGE_PERSON_ALREADY_CHECKED_IN = "This participant is already checked in.";

    private final Index index;

    /**
     * Creates a checkInCommand to check in the specified {@code Person}
     */
    public CheckInCommand(Index index) {
        requireAllNonNull(index);

        this.index = index;
    }

    /**
     * Marks the participant at the given index as checked in within the current event context.
     *
     * @param model {@link Model} containing event-scoped participant data
     * @return command result message indicating the checked-in participant
     * @throws CommandException if not currently inside an event or if the index is invalid
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.isInEventParticipantsMode()) {
            throw new CommandException(Messages.MESSAGE_ENTER_EVENT_FIRST);
        }
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToCheckIn = lastShownList.get(index.getZeroBased());
        if (personToCheckIn.getCheckInStatus().getStatus()) {
            throw new CommandException(MESSAGE_PERSON_ALREADY_CHECKED_IN);
        }
        model.checkInPerson(personToCheckIn);
        return new CommandResult(String.format(MESSAGE_CHECK_IN_PERSON_SUCCESS, Messages.format(personToCheckIn)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CheckInCommand)) {
            return false;
        }

        CheckInCommand e = (CheckInCommand) other;
        return index.equals(e.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", index)
                .toString();
    }
}
