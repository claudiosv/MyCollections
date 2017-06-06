package main.it.unibz.MyCollections.exceptions;

/**
 * Exception for when a method attempts to add a user to the database,
 * but a user with the same username or id already exists. This is thrown
 * to ensure no two users with the same username may exist.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 * @see main.it.unibz.MyCollections.User
 * @see Exception
 */
public class UserAlreadyExistsException extends Exception {

    /**
     * Instantiates the exception for a User that already exist
     * by calling the super constructor in the Exception class
     * @author Claudio Spiess
     * @see main.it.unibz.MyCollections.User
     */
    public UserAlreadyExistsException()
    {
        super("User already exists");
    }
}
