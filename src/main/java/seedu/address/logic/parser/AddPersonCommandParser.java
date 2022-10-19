package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddPersonCommandParser implements Parser<AddPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPersonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args,
                        PREFIX_NAME,
                        PREFIX_EMAIL,
                        PREFIX_PHONE,
                        PREFIX_TAG,
                        PREFIX_LINK_INDEX,
                        PREFIX_COMPANY);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonCommand.MESSAGE_USAGE));
        }
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        String emailStr = argMultimap.getValue(PREFIX_EMAIL).orElse(null);
        Email email = null;
        if (emailStr != null) {
            email = ParserUtil.parseEmail(emailStr);
        }
        String phoneStr = argMultimap.getValue(PREFIX_PHONE).orElse(null);
        Phone phone = null;
        if (phoneStr != null) {
            phone = ParserUtil.parsePhone(phoneStr);
        }
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        String linkIndexStr = argMultimap.getValue(PREFIX_LINK_INDEX).orElse(null);
        Index linkIndex = null;
        if (linkIndexStr != null) {
            linkIndex = ParserUtil.parseIndex(linkIndexStr);
        }
        String companyStr = argMultimap.getValue(PREFIX_COMPANY).orElse(null);
        Company company = null;
        if (companyStr != null) {
            company = ParserUtil.parseCompany(companyStr);
        }

        return new AddPersonCommand(name, email, phone, tagList, linkIndex, company);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
