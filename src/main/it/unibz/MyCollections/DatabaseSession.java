package main.it.unibz.MyCollections;

/**
 * Singleton to hold DatabaseHandler instance.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class DatabaseSession {
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private static DatabaseHandler instance = new SQLiteHandler();

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private DatabaseSession() {
    }

    /**
     * Returns single instance of the DatabaseHandler
     *
     * @author Claudio Spiess
     * @return Instance of RecordHandler
     */
    public static DatabaseHandler getInstance() {
        return instance;
    }
}
