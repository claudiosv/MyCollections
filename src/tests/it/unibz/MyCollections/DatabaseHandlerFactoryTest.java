package tests.it.unibz.MyCollections;

import main.it.unibz.MyCollections.DatabaseHandler;
import main.it.unibz.MyCollections.DatabaseHandlerFactory;
import main.it.unibz.MyCollections.SQLiteHandler;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link DatabaseHandlerFactory}.
 */
public class DatabaseHandlerFactoryTest {

    /**
     * Tests whether the DatabaseHandlerFactory is working as expected.
     */
    @Test
    public void DatabaseHandlerFactoryTest() {
        DatabaseHandlerFactory factory = new DatabaseHandlerFactory();
        DatabaseHandler handler = factory.getDatabaseHandler("sqlite");
        assertTrue(handler instanceof SQLiteHandler);
        DatabaseHandler nullTest = factory.getDatabaseHandler("xml");
        assertNull(nullTest);
    }

}
