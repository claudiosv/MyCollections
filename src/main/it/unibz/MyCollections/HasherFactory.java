package main.it.unibz.MyCollections;

/** Factory to create hashers.
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class HasherFactory {

    /**
     * Instantiates a hasher
     *
     * @param hasherType Which type of hashing algorithm to instantiate e.g. sha512
     * @author Claudio Spiess
     */
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
