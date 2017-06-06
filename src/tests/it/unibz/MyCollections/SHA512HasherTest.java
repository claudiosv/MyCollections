package tests.it.unibz.MyCollections;

import main.it.unibz.MyCollections.Hasher;
import main.it.unibz.MyCollections.HasherFactory;
import main.it.unibz.MyCollections.SHA512Hasher;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.*;

/**
 * Created by Claudio on 06/06/2017.
 */
class SHA512HasherTest {

    @Test
    public void SHAHasherTest()
    {
        HasherFactory factory = new HasherFactory();
        Hasher hasher = factory.getHasher("sha512");
        HashMap<String, String> valueMap = new HashMap<>();
        valueMap.put("test",
                "EE26B0DD4AF7E749AA1A8EE3C10AE9923F618980772E473F8819A5D4940E0DB27AC185F8A0E1D5F84F88BC887FD67B143732C304CC5FA9AD8E6F57F50028A8FF");

        for (Map.Entry<String, String> entry : valueMap.entrySet()) {
            assertEquals(entry.getValue(), hasher.hash(entry.getKey()));
        }
    }
}
