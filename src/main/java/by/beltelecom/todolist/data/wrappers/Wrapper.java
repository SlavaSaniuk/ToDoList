package by.beltelecom.todolist.data.wrappers;

/**
 * Interface define wrapper class for T object.
 * Wrapper provide methods for work with wrapped object and other static method.
 * @param <W> - Wrapper class.
 * @param <T> - wrapped instance class.
 */
public interface Wrapper<W, T> {

    /**
     * Unwrap wrapped instance.
     * @param aW - Wrapper instance.
     * @return - wrapped instance.
     */
    T unwrap(W aW);
}
