package main.it.unibz.MyCollections;

import java.util.Arrays;
import java.util.List;

/** Validates user inputs.
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class Validator {

    /**
     * Checks if a username is a valid username based on the requirements that
     * <ul>
     *     <li>Usernames can contain any combination of letters (a-z) and numbers (0-9)</li>
     *     <li>Usernames can contain only 1 dash (-), 1 underscore (_) or 1 period (.).</li>
     *     <li>Minimum length of a username is 5.</li>
     * </ul>
     *
     * @author Claudio Spiess
     * @author <a href="https://stackoverflow.com/a/6782475">sarkiroka</a>
     * @return String of the User's username.
     */
    public static boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z0-9._-]{5,}$") && stringMatchesChars(username, Arrays.asList('-', '_', '.'));
    }

    /**
     * Checks if a string is valid based on whether the
     * string contains contain only 1 of the specified characters at most.
     *
     * @author <a href="https://stackoverflow.com/a/23930339">skiwi</a>
     * @param str String to check if it matches property.
     * @param characters Characters to check that only 1 in the list occurs at most.
     * @return boolean True if string matches properties, false if it doesn't.
     */
    private static boolean stringMatchesChars(final String str, final List<Character> characters) {
        return str.chars()
                .filter(ch -> characters.contains((char) ch))
                .count() < 2;
    }

    /**
     * Checks if a password is valid based on the properties:
     * <ul>
     *   <li>Password must be 5 characters or greater.</li>
     *   <li>Password may contain only ASCII characters.</li>
     * </ul>
     *
     * @author Claudio Spiess
     * @author <a href="https://stackoverflow.com/a/3585284">Arne</a>
     * @return boolean True if password is valid, false if password is invalid.
     */
    public static boolean isValidPassword(String password) {
        return password.length() >= 5 && password.matches("\\A\\p{ASCII}*\\z");
    }
}
