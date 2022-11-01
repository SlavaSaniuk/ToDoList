package by.beltelecom.todolist.data.wrappers;

/**
 * Interface define wrapper class for T object.
 * Wrapper provide methods for work with wrapped object and other static method.
 * @param <T> - wrapped instance class.
 */
public interface Wrapper<T> {

    /**
     * Unwrap wrapped instance.
     * @return - wrapped instance.
     */
    T unwrap();
}
