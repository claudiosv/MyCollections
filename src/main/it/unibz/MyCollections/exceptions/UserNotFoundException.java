package main.it.unibz.MyCollections.exceptions;

/**
 * Created by claudio on 03/06/2017.
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException()
    {
        super("User not found");
    }
}
