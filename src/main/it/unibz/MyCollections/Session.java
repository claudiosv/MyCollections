package main.it.unibz.MyCollections;

/**
 * Singleton to hold active logged in user session.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class Session {

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private static Session instance = new Session();
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private User activeUser;
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private boolean loggedIn;
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private int recordsAdded;
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private int recordsDeleted;

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private Session() {
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public static Session getInstance() {
        return instance;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    @SuppressWarnings("unused")
    public static void setInstance(Session instance) {
        Session.instance = instance;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public User getActiveUser() {
        return activeUser;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    @SuppressWarnings("unused")
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    @SuppressWarnings("unused")
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public int getRecordsAdded() {
        return recordsAdded;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public int getRecordsDeleted() {
        return recordsDeleted;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void incrementRecordsAdded() {
        this.recordsAdded++;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void incrementRecordsDeleted() {
        this.recordsDeleted++;
    }
}

