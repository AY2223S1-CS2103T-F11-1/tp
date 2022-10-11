package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERNSHIP_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_INDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INTERNSHIPS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.InternshipId;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;

/**
 * Links a Person and an Internship.
 */
public class LinkCommand extends Command {

    public static final String COMMAND_WORD = "link";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Links a Person and an Internship. "
            + "Parameters: "
            + PREFIX_PERSON_INDEX + "PERSON_INDEX "
            + PREFIX_INTERNSHIP_INDEX + "INTERNSHIP_ID "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PERSON_INDEX + "0 "
            + PREFIX_INTERNSHIP_INDEX + "0 ";

    // TODO: change
    public static final String MESSAGE_SUCCESS = "Person %1$s linked to Internship %2$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String MESSAGE_DUPLICATE_INTERNSHIP = "This internship already exists in the address book";


    private final Index personIndex;
    private final Index internshipIndex;

    /*
    Not sure if LinkCommand(Person, Internship) is needed, hence commenting this for now
    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    /*
    public LinkCommand(Person person, Internship internship) {

    }
     */

    /**
     * Creates an LinkCommand to link a {@code Person} and an {@code Internship}
     * based on the given {@code personIndex} and {@code internshipIndex}
     */
    public LinkCommand(Index personIndex, Index internshipIndex) {
        this.personIndex = personIndex;
        this.internshipIndex = internshipIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownPersonList = model.getFilteredPersonList();

        //checking if person index and internship index are valid
        if (personIndex.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        List<Internship> lastShownInternshipList = model.getFilteredInternshipList();

        if (internshipIndex.getZeroBased() >= lastShownInternshipList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
        }

        Person personToLink = lastShownPersonList.get(personIndex.getZeroBased());
        Internship internshipToLink = lastShownInternshipList.get(internshipIndex.getZeroBased());

        PersonId nextPersonId = new PersonId(model.getNextPersonId());
        InternshipId nextInternshipId = new InternshipId(model.getNextInternshipId());

        Person linkedPerson = new Person(
                nextPersonId,
                personToLink.getName(),
                personToLink.getPhone(),
                personToLink.getEmail(),
                nextInternshipId,
                personToLink.getTags()
        );

        Internship linkedInternship = new Internship(
                nextInternshipId,
                internshipToLink.getCompanyName(),
                internshipToLink.getInternshipRole(),
                internshipToLink.getInternshipStatus(),
                nextPersonId,
                internshipToLink.getInterviewDate()
        );

        if (!personToLink.isSamePerson(linkedPerson) && model.hasPerson(linkedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        if (!internshipToLink.isSameInternship(linkedInternship) && model.hasInternship(linkedInternship)) {
            throw new CommandException(MESSAGE_DUPLICATE_INTERNSHIP);
        }

        model.setPerson(personToLink, linkedPerson);
        model.setInternship(internshipToLink, linkedInternship);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredInternshipList(PREDICATE_SHOW_ALL_INTERNSHIPS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, linkedPerson, linkedInternship));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LinkCommand)) {
            return false;
        }

        LinkCommand otherCommand = (LinkCommand) other;
        return personIndex.equals(otherCommand.personIndex)
                && internshipIndex.equals(otherCommand.internshipIndex);
    }
}
