package main.it.unibz.MyCollections;

/** Singleton to hold RecordsHandler instances.
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class DatabaseSession {
    private static RecordsHandler instance = new SQLiteHandler();

    private DatabaseSession() {
    }

    public static RecordsHandler getInstance() {
        return instance;
    }
}
