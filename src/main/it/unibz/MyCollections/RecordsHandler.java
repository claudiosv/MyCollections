package main.it.unibz.MyCollections;

import java.util.ArrayList;

/**
 * Created by claudio on 03/06/2017.
 */
public interface RecordsHandler {
    public void initialise();

    public void addUser(User user);

    public void updateUser(User user);

    public void deleteUser(int userId);

    public boolean userExists(int id);

    public boolean userExists(String username);

    public User getUser(String username, String password);

    public ArrayList<User> getAllUsers();

    public User getUser(int id);

    public Record insertRecord(Record record);

    public void deleteRecord(int recordId);

    public void updateRecord(Record record);

    public boolean recordExists(int id);

    public Record getRecord(int userId, int recordId);

    public ArrayList<Record> getAllRecords();

    public ArrayList<Record> getRecords(int userId);

    public ArrayList<Record> searchRecords(RecordSearchQuery query, int userId);

    public ArrayList<Record> searchRecords(RecordSearchQuery query);

    public int getRecordCount();

    public int getRecordCount(int userId);
}
