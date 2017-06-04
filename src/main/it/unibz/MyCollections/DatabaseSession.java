package main.it.unibz.MyCollections;

/**
 * Created by claudio on 30/03/2017.
 */
public class DatabaseSession {
    private static RecordsHandler instance = new SQLiteHandler();

    private DatabaseSession() {
    }

    public static RecordsHandler getInstance() {
        return instance;
    }
}
