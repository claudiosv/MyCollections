package main.it.unibz.MyCollections.exceptions;

/**
 * Created by claudio on 03/06/2017.
 */
public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException()
    {
        super("User already exists");
    }
}
