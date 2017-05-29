package tests.it.unibz.MyCollections;

import main.it.unibz.MyCollections.DatabaseHandler;
import main.it.unibz.MyCollections.RecordSearchQuery;
import org.junit.jupiter.api.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Created by claudio on 17/05/2017.
 */
public class RecordSearchQueryTest {
    @Test
    void toLikeQuery() throws SQLException {
        RecordSearchQuery query = new RecordSearchQuery();
        query.setAddress("Druso");
        query.setFirstName("Test*");

        //query.setAddress("Druso");
        //String sql = query.toLikeQuery(true);

        DatabaseHandler.getInstance().initialise();
        DatabaseHandler.getInstance().insertRecord(null);
        assertTrue(DatabaseHandler.getInstance().userExists(1));
        assertFalse(DatabaseHandler.getInstance().userExists(5000));

    }

}
