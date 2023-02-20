package it.unibz.MyCollections;

import java.util.Arrays;
import java.util.List;

/**
 * Validates user inputs.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class Validator {

    /**
     * Checks if a username is a valid username based on the requirements that
     * <ul>
     * <li>Usernames can contain any combination of letters (a-z) and numbers (0-9)</li>
     * <li>Usernames can contain only 1 dash (-), 1 underscore (_) or 1 period (.).</li>
     * <li>Minimum length of a username is 5.</li>
     * </ul>
     *
     * @param username String of the User's username.
     * @return <code>true</code> if the username is valid.
     * @author Claudio Spiess
     * @author <a href="https://stackoverflow.com/a/6782475">sarkiroka</a>
     */
    public static boolean isValidUsername(String username) {
        if(username == null || username.equals("")) return  false;
        return username.matches("^[a-zA-Z0-9._-]{5,}$") && stringMatchesChars(username, Arrays.asList('-', '_', '.'));
    }

    /**
     * Checks if a string is valid based on whether the
     * string contains contain only 1 of the specified characters at most.
     *
     * @param str        String to check if it matches property.
     * @param characters Characters to check that only 1 in the list occurs at most.
     * @return boolean True if string matches properties, false if it doesn't.
     * @author <a href="https://stackoverflow.com/a/23930339">skiwi</a>
     */
    private static boolean stringMatchesChars(final String str, final List<Character> characters) {
        return str.chars()
                .filter(ch -> characters.contains((char) ch))
                .count() < 2;
    }

    /**
     * Checks if a password is valid based on the properties:
     * <ul>
     * <li>Password must be 5 characters or greater.</li>
     * <li>Password may contain only ASCII characters.</li>
     * </ul>
     *
     * @param password The password to be validated.
     * @return <code>true</code> if password is valid, false if password is invalid.
     * @author Claudio Spiess
     * @author <a href="https://stackoverflow.com/a/3585284">Arne</a>
     */
    public static boolean isValidPassword(String password) {
        if(password == null || password.equals("")) return  false;
        return password.length() >= 5 && password.matches("\\A\\p{ASCII}*\\z");
    }
}