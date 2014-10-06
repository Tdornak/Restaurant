package model;

/**
 * Custom exception class used for all exceptions thrown when instantiating a class
 * 
 * @author tdornak
 * @version 1.0
 */
public class ClassCreationException extends ReflectiveOperationException {
    
    private static final String ACCEPTED_MESSAGE = "An error has occured instantiating"
            + " a class.  ";
    
    public ClassCreationException(String message) {
        super(ACCEPTED_MESSAGE);
    }
    
    public ClassCreationException(String message, Throwable cause) {
        super(ACCEPTED_MESSAGE, cause);
    }
}
