package tests.it.unibz.MyCollections;


import main.it.unibz.MyCollections.RecordSearchQuery;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by claudio on 17/05/2017.
 */
public class RecordSearchQueryTest {
    @Test
    public void dirtyInputWildcareToLikeQueryTest() {
        final String dirtySQLString = "testLIKE;'%#%* ';";
        RecordSearchQuery query = new RecordSearchQuery();
        query.setAddress(dirtySQLString);
        assertEquals("address LIKE ?",
                query.toLikeQuery());
        assertEquals("testLIKE;'%#%% ';", query.getParametreValueBuilder().get(0));
    }

    @Test
    public void exclusiveToLikeQueryTest() {
        //final String dirtySQLString = "testLIKE;'%#%* ';";
        RecordSearchQuery query = new RecordSearchQuery();
        query.setAddress("test");
        query.setTelephoneNumber("test");
        query.setFirstName("test");
        query.setLastName("test");
        query.setEmailAddress("test");
        query.setCompanyName("test");
        query.setExclusive(true);
        assertEquals("firstname LIKE ? AND address LIKE ? AND telephonenumber LIKE ? AND companyname LIKE ? AND email LIKE ? AND lastname LIKE ?",
                query.toLikeQuery());
        for (int i = 0; i < 6; i++)
            assertEquals("test", query.getParametreValueBuilder().get(i));
    }

    @Test
    public void inclusiveToLikeQueryTest() {
        //final String dirtySQLString = "testLIKE;'%#%* ';";
        RecordSearchQuery query = new RecordSearchQuery();
        query.setAddress("test");
        query.setTelephoneNumber("test");
        query.setFirstName("test");
        query.setLastName("test");
        query.setEmailAddress("test");
        query.setExclusive(false);
        assertEquals("firstname LIKE ? OR address LIKE ? OR telephonenumber LIKE ? OR email LIKE ? OR lastname LIKE ?",
                query.toLikeQuery());
        for (int i = 0; i < 5; i++)
            assertEquals("test", query.getParametreValueBuilder().get(i));
    }

}
