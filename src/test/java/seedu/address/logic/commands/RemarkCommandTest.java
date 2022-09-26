package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;

class RemarkCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private static final Remark TEST_REMARK = new Remark("Test remark");


    @Test
    void execute_addRemarkUnfilteredList_success() {
        RemarkCommand remarkCommand = new RemarkCommand(Index.fromOneBased(1), TEST_REMARK);
        try {
            remarkCommand.execute(model);
            Person updatedPerson = model.getFilteredPersonList().get(0);
            assertEquals(updatedPerson.getRemark(), TEST_REMARK);
        } catch (CommandException e) {
            fail("Error in command execution");
        }
    }
}
