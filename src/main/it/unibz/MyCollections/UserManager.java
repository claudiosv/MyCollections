package main.it.unibz.MyCollections;

/**
 * Created by claudio on 30/03/2017.
 */
public class UserManager {
    public User createUser()
    {
        return null;
    }

    public boolean isValidUsername(String username)
    {
        if(username.length() < 5) return false;
        return username.contains("-") ^ username.contains("_") ^ username.contains(".");
    }

    public boolean isValidPassword(String password)
    {
        return password.length() >= 5;
    }
}
