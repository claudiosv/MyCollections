package main.it.unibz.MyCollections;

/** Factory to create hashers.
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class HasherFactory {
    public Hasher getHasher(String hasherType) {
        if (hasherType == null) {
            return null;
        }

        if (hasherType.equalsIgnoreCase("sha512")) {
            return new SHA512Hasher();
        }

        return null;
    }
}
