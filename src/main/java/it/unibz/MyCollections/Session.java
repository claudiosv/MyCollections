package it.unibz.MyCollections;

/**
 * Singleton to hold active logged in user session.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class Session {

    /**
     * Holds the instance of the singleton Session.
     */
    private static Session instance = new Session();

    /**
     * Holds the active user of the application.
     */
    private User activeUser;

    /**
     * Holds the number of records added in a session.
     */
    private int recordsAdded;

    /**
     * Holds the number of records deleted in a session.
     */
    private int recordsDeleted;

    /**
     * Private constructor to ensure no outside class can instantiate the singleton.
     */
    private Session() {
    }

    /**
     * Gets the instance of the singleton.
     *
     * @return The instance of the singleton.
     */
    public static Session getInstance() {
        return instance;
    }

    /**
     * Gets the active user.
     *
     * @return The active User instance.
     */
    public User getActiveUser() {
        return activeUser;
    }

    /**
     * Sets the active user.
     *
     * @param activeUser Active user to set.
     */
    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    /**
     * Gets the number of records added.
     *
     * @return Numbers of records added.
     */
    public int getRecordsAdded() {
        return recordsAdded;
    }

    /**
     * Gets the number of records deleted.
     *
     * @return Numbers of records deleted.
     */
    public int getRecordsDeleted() {
        return recordsDeleted;
    }

    /**
     * Increments the number of records added.
     */
    public void incrementRecordsAdded() {
        this.recordsAdded++;
    }

    /**
     * Increments the number of records deleted.
     */
    public void incrementRecordsDeleted() {
        this.recordsDeleted++;
    }
}

