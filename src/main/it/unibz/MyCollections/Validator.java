package main.it.unibz.MyCollections;

import java.util.Arrays;
import java.util.List;

/**
 * Created by claudio on 03/06/2017.
 */
public class Validator {
    public static boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z0-9._-]{5,}$") && stringMatchesChars(username, Arrays.asList('-', '_', '.'));
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
