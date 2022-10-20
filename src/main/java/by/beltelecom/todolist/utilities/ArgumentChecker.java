package by.beltelecom.todolist.utilities;

import by.beltelecom.todolist.data.models.Identification;
import by.beltelecom.todolist.utilities.logging.Checks;

import java.util.Objects;

public abstract class ArgumentChecker {

    public static void nonNull(Object aObj, String anArgName) {
        Objects.requireNonNull(aObj, Checks.argumentNotNull(anArgName, aObj.getClass()));
    }

    public static void idNotZero(Identification aObj)  {
        if ( aObj.getIdentifier().longValue() == 0)
            throw new IllegalArgumentException(String.format("Required property[id] in argument of class [%s] must be not null.", aObj.getClass()));
    }
}
