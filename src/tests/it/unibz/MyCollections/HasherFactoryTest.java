package tests.it.unibz.MyCollections;

import it.unibz.MyCollections.Hasher;
import it.unibz.MyCollections.HasherFactory;
import it.unibz.MyCollections.SHA512Hasher;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests whether the HasherFactory works.
 */
public class HasherFactoryTest {
    /**
     * Tests whether the HasherFactory acts as expected.
     */
    @Test
    public void HasherFactoryTest() {
        HasherFactory factory = new HasherFactory();
        Hasher hasher = factory.getHasher("sha512");
        assertTrue(hasher instanceof SHA512Hasher);
        Hasher nullTest = factory.getHasher("md5");
        assertNull(nullTest);
    }
}