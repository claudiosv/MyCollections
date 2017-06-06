package main.it.unibz.MyCollections;

import main.it.unibz.MyCollections.exceptions.RecordNotFoundException;
import main.it.unibz.MyCollections.exceptions.UserAlreadyExistsException;
import main.it.unibz.MyCollections.exceptions.UserNotFoundException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Interface to manage records/users in a database or file.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public interface DatabaseHandler {

    /**
     * Initialises database connection, connects to database (if implementation does so).
     *
     * @author Claudio Spiess
     * @param fileName Path to file that stores the records.
     */
    public void initialise(String fileName);

    public String getFileName();

    /**
     * Adds a new user to the database.
     *
     * @author Claudio Spiess
     * @param user object to add to database.
     * @see User
     * @throws SQLException If there is an exception in JDBC.
     * @throws UserAlreadyExistsException If a user with the same username or id already exists.
     */
    public void addUser(User user) throws SQLException, UserAlreadyExistsException;

    /**
     * Updates an existing user with new properties. Requires
     * that the passed User argument already has a valid user id.
     * If the user cannot be found, a UserNotFound exception is thrown.
     *
     * @author Claudio Spiess
     * @param user object to edit in the database.
     * @see User
     * @throws SQLException If there is an exception in JDBC.
     * @throws UserNotFoundException If a user with that id doesn't already exist.
     * @throws IOException If there is an error writing the user's image to a buffer.
     */
    public void updateUser(User user)  throws UserNotFoundException, SQLException, IOException;

    /**
     * Inserts a new record into the database and returns the new
     * object with the correct record id.
     *
     * @author Claudio Spiess
     * @param record object to add to database.
     * @see Record
     * @throws SQLException If there is an exception in JDBC.
     * @throws RecordNotFoundException If the inserted record couldn't be found in the database (DB error).
     * @throws IOException If the record's image couldn't be written to a buffer.
     */
    public Record insertRecord(Record record) throws SQLException, RecordNotFoundException, IOException ;

    /**
     * Deletes an existing user from the database
     * based on the user's id.
     *
     * @author Claudio Spiess
     * @param userId user id to delete from database.
     * @see User
     * @throws SQLException If there is an exception in JDBC.
     * @throws UserNotFoundException If the user with the userId couldn't be found.
     */
    public void deleteUser(int userId) throws UserNotFoundException, SQLException;

    /**
     * Deletes an existing record from the database
     * based on the record's ID
     *
     * @author Claudio Spiess
     * @param recordId record id to delete from database.
     * @see Record
     * @throws SQLException If there is an exception in JDBC.
     * @throws RecordNotFoundException If the record couldn't be found in the database.
     */
    public void deleteRecord(int recordId) throws SQLException, RecordNotFoundException;

    /**
     * Updates an existing record in the database
     * based on the record's ID
     *
     * @author Claudio Spiess
     * @param record record update in database.
     * @see Record
     * @throws SQLException If there is an exception in JDBC.
     * @throws RecordNotFoundException If the record couldn't be found in the database.
     * @throws IOException If the record's image couldn't be written to a buffer.
     */
    public void updateRecord(Record record) throws SQLException, IOException, RecordNotFoundException;

    /**
     * Checks whether a record with specified
     * id exists in the database or not.
     *
     * @author Claudio Spiess
     * @param id record to check for existence.
     * @see Record
     * @throws SQLException If there is an exception in JDBC.
     * @return boolean true if the record exists, false if it doesn't.
     */
    public boolean recordExists(int id) throws SQLException;

    /**
     * Checks whether a user with specified
     * id exists in the database or not.
     *
     * @author Claudio Spiess
     * @param id user to check for existence.
     * @see User
     * @throws SQLException If there is an exception in JDBC.
     * @return boolean true if the user exists, false if it doesn't.
     */
    public boolean userExists(int id) throws SQLException;

    /**
     * Checks whether a user with specified
     * username exists in the database or not.
     *
     * @author Claudio Spiess
     * @param username username to check for existence.
     * @see User
     * @throws SQLException If there is an exception in JDBC.
     * @return boolean true if the user exists, false if it doesn't.
     */
    public boolean userExists(String username) throws SQLException;

    /**
     * Gets user with specified username and password.
     * If a user with specific parametres cannot be found,
     * an exception is thrown.
     *
     * @author Claudio Spiess
     * @param username of user to get.
     * @param password of user to get.
     * @see User
     * @throws SQLException If there is an exception in JDBC.
     * @throws UserNotFoundException If the user couldn't be found in the database.
     * @throws IOException If the user's image couldn't be written to a buffer.
     * @return User instance if a user is found.
     */
    public User getUser(String username, String password) throws UserNotFoundException, SQLException, IOException;

    /**
     * Gets all users in the database for administrative
     * purposes.
     *
     * @author Claudio Spiess
     * @see User
     * @throws SQLException If there is an exception in JDBC.
     * @throws IOException If the user's image couldn't be written to a buffer.
     * @return List of all users.
     */
    public List<User> getAllUsers() throws SQLException, IOException;

    /**
     * Gets a user based on a specified id.
     *
     * @author Claudio Spiess
     * @see User
     * @param id User id of user to get.
     * @throws SQLException If there is an exception in JDBC.
     * @throws IOException If the user's image couldn't be written to a buffer.
     * @throws UserNotFoundException If a user with specified id cannot be found.
     * @return User instance of specified id.
     */
    public User getUser(int id) throws UserNotFoundException, SQLException, IOException;

    /**
     * Searches a specific user id's records based on a
     * RecordSearchQuery, which builds the correct SQL string to
     * search.
     *
     * @author Claudio Spiess
     * @see Record
     * @param query RecordSearchQuery instance that has parametres set.
     * @param userId Id of user who's records are to be searched.
     * @throws SQLException If there is an exception in JDBC.
     * @throws IOException If the record's image couldn't be written to a buffer.
     * @return List of found records (can be empty).
     */
    public List<Record> searchRecords(RecordSearchQuery query, int userId) throws SQLException, IOException;

    /**
     * Searches a all records based on a
     * RecordSearchQuery, which builds the correct SQL string to
     * search.
     *
     * @author Claudio Spiess
     * @see Record
     * @param query RecordSearchQuery instance that has parametres set.
     * @throws SQLException If there is an exception in JDBC.
     * @throws IOException If the record's image couldn't be written to a buffer.
     * @return List of found records (can be empty).
     */
    public List<Record> searchRecords(RecordSearchQuery query) throws SQLException, IOException;

    /**
     * Gets all records stored in database regardless of ownership.
     *
     * @author Claudio Spiess
     * @see Record
     * @throws SQLException If there is an exception in JDBC.
     * @throws IOException If the record's image couldn't be written to a buffer.
     * @return List of all records (can be empty).
     */
    public List<Record> getAllRecords() throws SQLException, IOException;

    /**
     * Gets records stored in database based on the id of the
     * owner's id.
     *
     * @author Claudio Spiess
     * @see Record
     * @param userId User id to get record records for.
     * @throws SQLException If there is an exception in JDBC.
     * @throws IOException If the record's image couldn't be written to a buffer.
     * @return List of all records (can be empty).
     */
    public List<Record> getRecords(int userId) throws SQLException, IOException;

    /**
     * Gets count of records owned by a specific user's id.
     *
     * @author Claudio Spiess
     * @param userId User id to get record's count for.
     * @throws SQLException If there is an exception in JDBC.
     * @return int Count of records owned by a specific user ID.
     */
    public int getRecordCount(int userId) throws SQLException;

    /**
     * Gets count of records all records in the database.
     *
     * @author Claudio Spiess
     * @throws SQLException If there is an exception in JDBC.
     * @return int Count of records owned by a specific user ID.
     */
    public int getRecordCount() throws SQLException;

    /**
     * Gets a specific record based on the record id
     * and the id of the record's owner.
     *
     * @author Claudio Spiess
     * @param userId User id to get record for.
     * @param recordId Record id to find.
     * @throws SQLException If there is an exception in JDBC.
     * @throws IOException If the record's image couldn't be written to a buffer.
     * @throws RecordNotFoundException If a record with specified parametres couldn't be found.
     * @return Record found with matching record id and owner user id.
     */
    public Record getRecord(int userId, int recordId) throws SQLException, IOException, RecordNotFoundException;

    /**
     * Commits database instance to file.
     * Useless because by default JDBC Sqlite
     * is set to autocommit any changes,
     * but it's worth being safe than sorry.
     *
     * @author Claudio Spiess
     */
    public void save();

}
