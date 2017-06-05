package main.it.unibz.MyCollections;

/**
 * Created by claudio on 03/06/2017.
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
