package by.beltelecom.todolist.utilities.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation mark it targets values as its throws exceptions.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface RuntimeExceptionThrows {

    /**
     * Throws exception classes array.
     * @return array of classes.
     */
    Class<? extends RuntimeException>[] value();
}
