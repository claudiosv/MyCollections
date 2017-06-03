package main.it.unibz.MyCollections;

/**
 * Created by Claudio on 01/06/2017.
 */
public class Session {
    private static User activeUser;
    private static int recordsAdded;
    private static int recordsDeleted;

    public static User getActiveUser() {
        return activeUser;
    }

    public static void setActiveUser(User activeUser) {
        Session.activeUser = activeUser;
    }

    public static int getRecordsAdded() {
        return recordsAdded;
    }

    public static void setRecordsAdded(int recordsAdded) {
        Session.recordsAdded = recordsAdded;
    }

    public static int getRecordsDeleted() {
        return recordsDeleted;
    }

    public static void setRecordsDeleted(int recordsDeleted) {
        Session.recordsDeleted = recordsDeleted;
    }
}
