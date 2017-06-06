package main.it.unibz.MyCollections;

/**
 * Singleton to hold active logged in user session.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class Session {

    private static Session instance = new Session();

    private Session(){}

    public static Session getInstance(){
        return instance;
    }
    private User activeUser;
    private boolean loggedIn;
    private int recordsAdded;
    private int recordsDeleted;
    private DatabaseHandler databaseHandler;

    public static void setInstance(Session instance) {
        Session.instance = instance;
    }

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public int getRecordsAdded() {
        return recordsAdded;
    }

    public void setRecordsAdded(int recordsAdded) {
        this.recordsAdded = recordsAdded;
    }

    public int getRecordsDeleted() {
        return recordsDeleted;
    }

    public void setRecordsDeleted(int recordsDeleted) {
        this.recordsDeleted = recordsDeleted;
    }

    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    public void setDatabaseHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    public void incrementRecordsAdded()
    {
        this.recordsAdded++;
    }

    public void incrementRecordsDeleted()
    {
        this.recordsDeleted++;
    }
}
