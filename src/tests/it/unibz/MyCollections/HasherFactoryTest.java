package tests.it.unibz.MyCollections;

import main.it.unibz.MyCollections.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Claudio on 06/06/2017.
 */
public class HasherFactoryTest {
    @Test
    public void HasherFactoryTest() {
        HasherFactory factory = new HasherFactory();
        Hasher hasher = factory.getHasher("sha512");
        assertTrue(hasher instanceof SHA512Hasher);
        Hasher nullTest = factory.getHasher("md5");
        assertNull(nullTest);
    }
}