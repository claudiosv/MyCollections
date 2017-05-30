package tests.it.unibz.MyCollections;

import main.it.unibz.MyCollections.DatabaseHandler;
import main.it.unibz.MyCollections.RecordSearchQuery;
import org.junit.jupiter.api.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by claudio on 30/05/2017.
 */
public class ValidUsernameTest {
        @Test
        void ValidUsernameTest()  {
            String[] strings = new String[] {
                    "mkyong34", "mkyong_2002","mky@ong-2002" ,"mk3-4_yong", "..", "natsruhalrhtl-", "natsruhalrhtl_", "natsruhalrhtl.","natsruhalrhtl-.", "natsruhalrhtl_.", "natsruhalrhtl.."
            };
            for(String temp : strings){
                boolean valid = DatabaseHandler.getInstance().isValidUsername(temp);
                System.out.println("Username is valid : " + temp + " , " + valid);

            }
        }
}
