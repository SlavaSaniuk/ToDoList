package by.beltelecom.todolist.data.wrappers;

import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.utilities.ArgumentChecker;
import lombok.Getter;

public class UserWrapper {

    // Class variables:
    @Getter
    private final User wrappedUser;
    private final Printer printer = new Printer(); // Printer instance;
    private static final Creator creator = new Creator(); // Creator instance;

    /**
     * Getter for {@link  Printer} printer field.
     * @return - printer instance.
     */
    public Printer printer() {
        return this.printer;
    }

    /**
     * Getter for {@link Creator} creator field/
     * @return - creator instance.
     */
    public static Creator creator() {
        return UserWrapper.creator;
    }

    private UserWrapper(User aUser) {
        ArgumentChecker.nonNull(aUser, "aUser");
        this.wrappedUser = aUser;
    }

    public static UserWrapper wrap(User aUser) {
        return new UserWrapper(aUser);
    }

    public static class Creator {

        private Creator() {}

        public UserWrapper ofId(long aId) {
            User user = new User();
            user.setId(aId);
            return UserWrapper.wrap(user);
        }
    }

    public class Printer {

        private Printer() {}

        public String printUserOnlyId() {
            return String.format("User[id: %d]", wrappedUser.getId());
        }
    }
}
