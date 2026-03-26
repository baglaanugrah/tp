package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGN_TEAM;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignTeamCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Team;

/**
 * Parses input arguments and creates a new AssignTeamCommand object.
 */
public class AssignTeamCommandParser implements Parser<AssignTeamCommand> {

    @Override
    public AssignTeamCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ASSIGN_TEAM);

        if (argMultimap.getPreamble().isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTeamCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ASSIGN_TEAM);

        Optional<String> teamValue = argMultimap.getValue(PREFIX_ASSIGN_TEAM);
        if (teamValue.isEmpty() || teamValue.get().isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTeamCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble().trim());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTeamCommand.MESSAGE_USAGE),
                    pe);
        }

        Team team = ParserUtil.parseTeam(teamValue.get());
        return new AssignTeamCommand(index, team);
    }
}
