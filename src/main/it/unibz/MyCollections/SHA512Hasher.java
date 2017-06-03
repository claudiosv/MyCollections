package main.it.unibz.MyCollections;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by claudio on 03/06/2017.
 */
public class SHA512Hasher implements Hasher {
    private static final Logger logger = Logger.getLogger(SHA512Hasher.class.getName());
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
            hashedValue = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, "Hashing error, algorithm not supported", e);
        }
        return hashedValue;
    }
}
