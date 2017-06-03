package main.it.unibz.MyCollections;

import main.it.unibz.MyCollections.exceptions.RecordAlreadyExistsException;
import main.it.unibz.MyCollections.exceptions.RecordNotFoundException;
import main.it.unibz.MyCollections.exceptions.UserAlreadyExistsException;
import main.it.unibz.MyCollections.exceptions.UserNotFoundException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by claudio on 03/06/2017.
 */
public interface RecordsHandler {
    public void initialise(String fileName);

    public void addUser(User user) throws SQLException, UserAlreadyExistsException;

    public void updateUser(User user)  throws UserNotFoundException, SQLException, IOException;

    public Record insertRecord(Record record) throws RecordAlreadyExistsException, SQLException;

    public void deleteUser(int userId) throws UserNotFoundException, SQLException;

    public void deleteRecord(int recordId) throws SQLException, RecordNotFoundException;

    public void updateRecord(Record record) throws SQLException, IOException, RecordNotFoundException;

    public boolean recordExists(int id) throws SQLException;

    public boolean userExists(int id) throws SQLException;

    public boolean userExists(String username) throws SQLException;

    public User getUser(String username, String password) throws UserNotFoundException, SQLException, IOException;

    public List<User> getAllUsers() throws SQLException, IOException;

    public User getUser(int id) throws UserNotFoundException, SQLException, IOException;

    public List<Record> searchRecords(RecordSearchQuery query, int userId) throws SQLException, IOException;

    public List<Record> searchRecords(RecordSearchQuery query) throws SQLException, IOException;

    public List<Record> getAllRecords() throws SQLException, IOException;

    public List<Record> getRecords(int userId) throws SQLException, IOException;

    public int getRecordCount(int userId) throws SQLException, IOException;

    public int getRecordCount() throws SQLException, IOException;

    public Record getRecord(int userId, int recordId) throws SQLException, IOException, RecordNotFoundException;

    public void save();

}
