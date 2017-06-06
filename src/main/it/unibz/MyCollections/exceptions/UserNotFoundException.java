package main.it.unibz.MyCollections.exceptions;

/**
 * Exception for when a method attempts to update or delete a User
 * from the database, but the User does not exist. Specifically,
 * this exception is thrown when no matching row id is found for the
 * User's id.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @see main.it.unibz.MyCollections.User
 * @see Exception
 * @since 1.0
 */
public class UserNotFoundException extends Exception {

    /**
     * Instantiates the exception for a User that could not be found
     * by calling the super constructor in the Exception class
     *
     * @author Claudio Spiess
     * @see main.it.unibz.MyCollections.User
     */
    public UserNotFoundException() {
        super("User not found");
    }
}
