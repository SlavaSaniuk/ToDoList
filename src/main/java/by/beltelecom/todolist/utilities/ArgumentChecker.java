package by.beltelecom.todolist.utilities;

import by.beltelecom.todolist.data.wrappers.Identification;

import java.util.Objects;

/**
 * ArgumentChecker utilities class used to check methods arguments.
 */
public abstract class ArgumentChecker {

    /**
     * Check specified method argument at null. Throw {@link NullPointerException} exception, if method argument is null.
     * @param aObj - method argument.
     * @param anArgName - method argument name.
     */
    public static void nonNull(Object aObj, String anArgName) {
        Objects.requireNonNull(aObj, String.format("Required argument [%s] must be not null.", anArgName));
    }

    /**
     * Check specified argument at cases when it's ID property is zero. Throw {@link IllegalArgumentException} exception,
     * if ID property is zero.
     * @param aObj - {@link Identification} method argument.
     */
    public static void idNotZero(Identification aObj)  {
        if ( aObj.getIdentifier().longValue() == 0)
            throw new IllegalArgumentException(String.format("Required property[id] in argument of class [%s] must be not null.", aObj.getClass()));
    }
}
