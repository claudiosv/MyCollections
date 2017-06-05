package tests.it.unibz.MyCollections;

import main.it.unibz.MyCollections.Validator;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by claudio on 30/05/2017.
 */
public class ValidatorTest {
        @Test
        public void InvalidUsernameTest()  {
            String[] strings = new String[] {
                    "..",
                    "admi",
                    "12ad",
                    "mk3-4_yong",
                    "mky@ong-2002" ,
                    "natsruhalrhtl-.",
                    "natsruhalrhtl_.",
                    "natsruhalrhtl.."
            };
            for(String username : strings){
                boolean invalid = Validator.isValidUsername(username);
                assertFalse(username, invalid);
            }
        }

    @Test
    public void ValidUsernameTest()  {
        String[] strings = new String[] {
                "admin",
                "admin.",
                "admin-",
                "admin_",
                "00000"
        };
        for(String username : strings){
            boolean valid = Validator.isValidUsername(username);
            assertTrue(username, valid);
        }
    }
}
