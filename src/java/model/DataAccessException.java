package model;

/**
 *
 * Database exception class used for all exceptions thrown for a database
 * 
 * @author tdornak
 * @version 1.0
 */
public class DataAccessException extends Exception {
    
    private static final String ACCEPTED_MESSAGE = "An error has occured when"
            + "accessing the database.  Invalid SQL, Invalid Driver, or Database "
            + "not found";
    
    public DataAccessException(String message) {
        super(ACCEPTED_MESSAGE);
    }
    
    public DataAccessException(String message, Throwable cause) {
        super(ACCEPTED_MESSAGE, cause);
    }
}
