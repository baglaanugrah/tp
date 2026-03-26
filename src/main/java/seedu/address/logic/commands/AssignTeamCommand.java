package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGN_TEAM;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Team;

/**
 * Assigns a participant to a team.
 */
public class AssignTeamCommand extends Command {

    public static final String COMMAND_WORD = "assign";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns a participant to a specific hackathon team. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ASSIGN_TEAM + "TEAM_NAME\n"
            + "Example: " + COMMAND_WORD + " 2 " + PREFIX_ASSIGN_TEAM + "Alpha";

    public static final String MESSAGE_ASSIGN_TEAM_SUCCESS = "Assigned %1$s to Team %2$s.";

    private final Index index;
    private final Team team;

    /**
     * Creates an assign-team command targeting the participant at {@code index}.
     */
    public AssignTeamCommand(Index index, Team team) {
        requireNonNull(index);
        requireNonNull(team);
        this.index = index;
        this.team = team;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAssign = lastShownList.get(index.getZeroBased());
        Person assignedPerson = new Person(
                personToAssign.getName(),
                personToAssign.getPhone(),
                personToAssign.getEmail(),
                personToAssign.getAddress(),
                Optional.of(team),
                personToAssign.getTags(),
                personToAssign.getCheckInStatus(),
                personToAssign.getGitHub().orElse(null),
                personToAssign.getRsvpStatus());

        model.setPerson(personToAssign, assignedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ASSIGN_TEAM_SUCCESS,
                personToAssign.getName().fullName, team.teamName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AssignTeamCommand)) {
            return false;
        }
        AssignTeamCommand otherCommand = (AssignTeamCommand) other;
        return index.equals(otherCommand.index) && team.equals(otherCommand.team);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("team", team)
                .toString();
    }
}
