package main.it.unibz.MyCollections;

import java.util.Arrays;
import java.util.List;

/** Validates user inputs.
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class Validator {
    public static boolean isValidUsername(String username) {
        return username.length() >= 5 && username.matches("^[a-zA-Z0-9._-]{5,}$") && stringMatchesChars(username, Arrays.asList('-', '_', '.'));
    }

    private static boolean stringMatchesChars(final String str, final List<Character> characters) { //TODO: this is stolen!
        return (str.chars()
                .filter(ch -> characters.contains((char) ch))
                .count() < 2);
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 5;
    }
}
