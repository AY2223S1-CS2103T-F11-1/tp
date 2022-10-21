package seedu.address.model.person;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Name}, {@code Phone},
 * {@code Email}, and {@code Tags} match any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> nameKeywords;
    private final List<String> phoneKeywords;
    private final List<String> emailKeywords;
    private final List<String> tagKeywords;

    /**
     * Constructs a predicate for checking whether a {@code Person}'s
     * {@code Name}, {@code Phone}, {@code Email}, and {@code Tags}
     * match any of the keywords given.
     */
    public PersonContainsKeywordsPredicate(
            List<String> nameKeywords,
            List<String> phoneKeywords,
            List<String> emailKeywords,
            List<String> tagKeywords) {
        this.nameKeywords = nameKeywords;
        this.phoneKeywords = phoneKeywords;
        this.emailKeywords = emailKeywords;
        this.tagKeywords = tagKeywords;
    }

    @Override
    public boolean test(Person person) {
        return (nameKeywords.isEmpty() || nameKeywords.stream().anyMatch(
                    keyword -> person.getName().fullName.toLowerCase().contains(keyword.toLowerCase())))
                && (phoneKeywords.isEmpty() || phoneKeywords.stream().anyMatch(
                    keyword -> person.getPhone() == null
                            || person.getPhone().value.toLowerCase().contains(keyword.toLowerCase())))
                && (emailKeywords.isEmpty() || emailKeywords.stream().anyMatch(
                    keyword -> person.getEmail() == null
                            || person.getEmail().value.toLowerCase().contains(keyword.toLowerCase())))
                && (tagKeywords.isEmpty() || tagKeywords.stream().anyMatch(
                        keyword -> person.getTags() == null || person.getTags().contains(new Tag(keyword))));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonContainsKeywordsPredicate)) {
            return false;
        }

        PersonContainsKeywordsPredicate otherPredicate = (PersonContainsKeywordsPredicate) other;

        return Objects.equals(nameKeywords, otherPredicate.nameKeywords)
                && Objects.equals(phoneKeywords, otherPredicate.phoneKeywords)
                && Objects.equals(emailKeywords, otherPredicate.emailKeywords);
    }

}
