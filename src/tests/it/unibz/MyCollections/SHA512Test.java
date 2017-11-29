package tests.it.unibz.MyCollections;

import main.it.unibz.MyCollections.Hasher;
import main.it.unibz.MyCollections.HasherFactory;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Tests whether SHA512 implementation produces accurate output.
 */
public class SHA512Test {
    /**
     * Tests whether SHA512 implementation produces accurate output.
     */
    @Test
    public void SHAHasherTest() {
        HasherFactory factory = new HasherFactory();
        Hasher hasher = factory.getHasher("sha512");
        HashMap<String, String> valueMap = new HashMap<>();
        valueMap.put("test",
                "EE26B0DD4AF7E749AA1A8EE3C10AE9923F618980772E473F8819A5D4940E0DB27AC185F8A0E1D5F84F88BC887FD67B143732C304CC5FA9AD8E6F57F50028A8FF");

        valueMap.put("one",
                "05F70341078ACF6A06D423D21720F9643D5F953626D88A02636DC3A9E79582AEB0C820857FD3F8DC502AA8360D2C8FA97A985FDA5B629B809CAD18FFB62D3899");

        valueMap.put("two", "928D50D1E24DAB7CCA62CFE84FCDCF9FC695160A278F91B5C0AF22B709D82F8AA3B4955B3DE9BA6D0A0EB7D932DC64C4D5C63FC2DE87441AD2E5B929F9B67C5E");

        valueMap.put("093489(&^#&^%#(T*@^T&shgdhabsd", "5BFE9F31DD5168845FA7290FE1865E6BAB9771F1E68EED55C9CA8E5761AFDB62F2F58C0948D55438DF0728766C565038B0E58C6C24DCF855C74086BE06407390");
        for (Map.Entry<String, String> entry : valueMap.entrySet()) {
            assertEquals(entry.getValue(), hasher.hash(entry.getKey()));
        }
    }
}
