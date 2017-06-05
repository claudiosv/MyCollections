package tests.it.unibz.MyCollections;


import main.it.unibz.MyCollections.RecordSearchQuery;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.SQLException;

/**
 * Created by claudio on 17/05/2017.
 */
public class RecordSearchQueryTest {
    @Test
    public void exclusiveAddressToLikeQueryTest() {
        RecordSearchQuery query = new RecordSearchQuery();
        query.setAddress("test");
        assertEquals("address LIKE ?", query.toLikeQuery(true));
        assertEquals("test", query.getParametreValueBuilder().get(0));
    }

}
