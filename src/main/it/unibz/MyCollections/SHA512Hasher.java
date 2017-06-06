package main.it.unibz.MyCollections;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hasher to implement secure hash algorithm 512.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class SHA512Hasher implements Hasher {
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private static final Logger logger = Logger.getLogger(SHA512Hasher.class.getName());

    /**
     * Hash method that hashes given input using
     * SHA512 algorithm. Implemented by author
     * in link given in the Project requirements pdf.
     *
     * @author <a href="http://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/">Lokesh Gupta</a>
     * @version 1.0
     * @since 1.0
     */
    @Override
    public String hash(String input) {
        String hashedValue = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            hashedValue = sb.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, "Hashing error, algorithm not supported", e);
        }
        return hashedValue;
    }
}
