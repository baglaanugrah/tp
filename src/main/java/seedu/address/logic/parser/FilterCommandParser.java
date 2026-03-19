package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GITHUB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RSVP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonMatchesFilterPredicate;
import seedu.address.model.person.RsvpStatus;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * FilterCommand
     * and returns a FilterCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args); //Checks if the argument is not null

        //checks if there is any input after filter
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
            PREFIX_ADDRESS, PREFIX_GITHUB, PREFIX_RSVP, PREFIX_TAG);

        isValidFormat(argMultimap);

        PersonMatchesFilterPredicate predicate = buildPredicate(argMultimap);
        return new FilterCommand(predicate);
    }

    /**
     * Checks if any of the prefixes given in {@code prefixes} are present in the argument multimap
     */
    private boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }

    /**
     * Builds a {@code PersonMatchesFilterPredicate} from the argument multimap
     */
    private PersonMatchesFilterPredicate buildPredicate(ArgumentMultimap argMultimap) throws ParseException {
        Optional<RsvpStatus> rsvpStatus = Optional.empty();
        Set<Tag> tags = Set.of();

        if (argMultimap.getValue(PREFIX_RSVP).isPresent()) {
            rsvpStatus = Optional.of(ParserUtil.parseRsvpStatus(argMultimap.getValue(PREFIX_RSVP).get()));
        } else if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        return new PersonMatchesFilterPredicate(rsvpStatus, tags);
    }

    private boolean isValidFormat(ArgumentMultimap argMultimap) throws ParseException {
        //check for multiple prefixes
        if (argMultimap.countPrefixes(PREFIX_RSVP, PREFIX_TAG, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
            PREFIX_GITHUB) > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        //checks if correct arguments are provided
        if (!arePrefixesPresent(argMultimap, PREFIX_RSVP, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_RSVP, PREFIX_TAG);

        return true;
    }
}
