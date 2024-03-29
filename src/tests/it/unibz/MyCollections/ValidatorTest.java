package tests.it.unibz.MyCollections;

import it.unibz.MyCollections.Validator;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests if the Validator class works as expected.
 */
public class ValidatorTest {
    /**
     * Tests if the Validator class works as expected.
     */
    @Test
    public void InvalidUsernameTest() {
        String[] strings = new String[]{
                "..",
                "admi",
                "12ad",
                "mk3-4_yong",
                "mky@ong-2002",
                "natsruhalrhtl-.",
                "natsruhalrhtl_.",
                "natsruhalrhtl.."
        };
        for (String username : strings) {
            boolean invalid = Validator.isValidUsername(username);
            assertFalse(username, invalid);
        }
    }

    /**
     * Tests if the Validator class works as expected.
     */
    @Test
    public void ValidUsernameTest() {
        String[] strings = new String[]{
                "admin",
                "admin.",
                "admin-",
                "admin_",
                "00000"
        };
        for (String username : strings) {
            boolean valid = Validator.isValidUsername(username);
            assertTrue(username, valid);
        }
    }

    /**
     * Tests if the Validator class works as expected.
     */
    @Test
    public void ValidPasswordTest() {
        String[] strings = new String[]{
                "admin",
                "admin.",
                "admin-",
                "admin_",
                "00000",
                "!#valid",
                "tes%6",
                "va lid"
        };
        for (String password : strings) {
            boolean valid = Validator.isValidPassword(password);
            assertTrue(password, valid);
        }
    }

    /**
     * Tests if the Validator class works as expected.
     */
    @Test
    public void InvalidPasswordTest() {
        String[] strings = new String[]{
                "af",
                "agm.",
                "Двв",
                "00000Двв",
                "ДввДввДввДвв",
                "test"
        };
        for (String password : strings) {
            boolean valid = Validator.isValidPassword(password);
            assertFalse(password, valid);
        }
    }

}
