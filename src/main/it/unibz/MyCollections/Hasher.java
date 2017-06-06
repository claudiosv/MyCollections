package main.it.unibz.MyCollections;

/**
 * Interface to hash strings. Implementations
 * use a hashing algorithm to transform strings
 * into a non-reversible cryptographically secured
 * hashed value. This is used to store passwords
 * securely in the database.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public interface Hasher {
    /**
     * Hashes input string, for use in password storage.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     * @return String that has been hashed
     */
    public String hash(String input);
}
