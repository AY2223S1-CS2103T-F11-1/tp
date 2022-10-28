package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.util.AppUtil;
import seedu.address.model.internship.InternshipId;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final String NO_INTERNSHIP = "No internship linked.";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label internship;
    @FXML
    private Label company;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");

        name.setText(person.getName().fullName);

        phone.setText(person.getPhone().toString());

        email.setText(person.getEmail().toString());

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        if (person.getInternshipId() == null) {
            internship.setText(NO_INTERNSHIP);
        } else {
            internship.setText(person.getInternshipId().toString());
        }

        company.setText(person.getCompany().toString());
    }

    public InternshipId getInternshipId() {
        return person.getInternshipId();
    }

    public void setInternship(String internshipName) {
        if (internshipName == null) {
            internship.setText(NO_INTERNSHIP);
        } else {
            internship.setText(internshipName);
        }
    }

    public void copyName() {
        AppUtil.copy(name.getText());
    }

    public void copyPhone() {
        AppUtil.copy(phone.getText());
    }

    public void copyEmail() {
        AppUtil.copy(email.getText());
    }

    /**
     * Formats the Person's tags to a string then copies it to the system clipboard.
     */
    public void copyTags() {
        StringBuilder result = new StringBuilder();
        person.getTags().stream().forEach(t -> {
            result.append(t.tagName);
            result.append(", ");
        });
        AppUtil.copy(result.substring(0, result.length() - 2));
    }

    public void copyInternship() {
        AppUtil.copy(internship.getText());
    }

    public void copyCompany() {
        AppUtil.copy(company.getText());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
