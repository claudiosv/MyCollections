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
     * @param fileName Path to file that stores the records.
     * @author Claudio Spiess
     */
    void initialise(String fileName);

    /**
     * Gets the file name of the database.
     *
     * @return String containing file name of database
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    String getFileName();

    /**
     * Adds a new user to the database.
     *
     * @param user object to add to database.
     * @throws SQLException               If there is an exception in JDBC.
     * @throws UserAlreadyExistsException If a user with the same username or id already exists.
     * @author Claudio Spiess
     * @see User
     */
    void addUser(User user) throws SQLException, UserAlreadyExistsException;

    /**
     * Updates an existing user with new properties. Requires
     * that the passed User argument already has a valid user id.
     * If the user cannot be found, a UserNotFound exception is thrown.
     *
     * @param user object to edit in the database.
     * @throws SQLException          If there is an exception in JDBC.
     * @throws UserNotFoundException If a user with that id doesn't already exist.
     * @author Claudio Spiess
     * @see User
     */
    void updateUser(User user) throws UserNotFoundException, SQLException;

    /**
     * Inserts a new record into the database and returns the new
     * object with the correct record id.
     *
     * @param record object to add to database.
     * @throws SQLException            If there is an exception in JDBC.
     * @throws RecordNotFoundException If the inserted record couldn't be found in the database (DB error).
     * @throws IOException             If the record's image couldn't be written to a buffer.
     * @throws UserNotFoundException   If the record's owner id doesn't exist
     * @author Claudio Spiess
     * @see Record
     */
    Record insertRecord(Record record) throws SQLException, RecordNotFoundException, IOException, UserNotFoundException;

    /**
     * Deletes an existing user from the database
     * based on the user's id.
     *
     * @param userId user id to delete from database.
     * @throws SQLException          If there is an exception in JDBC.
     * @throws UserNotFoundException If the user with the userId couldn't be found.
     * @author Claudio Spiess
     * @see User
     */
    void deleteUser(int userId) throws UserNotFoundException, SQLException;

    /**
     * Deletes an existing record from the database
     * based on the record's ID
     *
     * @param recordId record id to delete from database.
     * @throws SQLException            If there is an exception in JDBC.
     * @throws RecordNotFoundException If the record couldn't be found in the database.
     * @author Claudio Spiess
     * @see Record
     */
    void deleteRecord(int recordId) throws SQLException, RecordNotFoundException;

    /**
     * Updates an existing record in the database
     * based on the record's ID
     *
     * @param record record update in database.
     * @throws SQLException            If there is an exception in JDBC.
     * @throws RecordNotFoundException If the record couldn't be found in the database.
     * @author Claudio Spiess
     * @see Record
     */
    void updateRecord(Record record) throws SQLException, RecordNotFoundException;

    /**
     * Checks whether a record with specified
     * id exists in the database or not.
     *
     * @param id record to check for existence.
     * @return boolean true if the record exists, false if it doesn't.
     * @throws SQLException If there is an exception in JDBC.
     * @author Claudio Spiess
     * @see Record
     */
    boolean recordExists(int id) throws SQLException;

    /**
     * Checks whether a user with specified
     * id exists in the database or not.
     *
     * @param id user to check for existence.
     * @return boolean true if the user exists, false if it doesn't.
     * @throws SQLException If there is an exception in JDBC.
     * @author Claudio Spiess
     * @see User
     */
    boolean userExists(int id) throws SQLException;

    /**
     * Checks whether a user with specified
     * username exists in the database or not.
     *
     * @param username username to check for existence.
     * @return boolean true if the user exists, false if it doesn't.
     * @throws SQLException If there is an exception in JDBC.
     * @author Claudio Spiess
     * @see User
     */
    boolean userExists(String username) throws SQLException;

    /**
     * Gets user with specified username and password.
     * If a user with specific parametres cannot be found,
     * an exception is thrown.
     *
     * @param username of user to get.
     * @param password of user to get.
     * @return User instance if a user is found.
     * @throws SQLException          If there is an exception in JDBC.
     * @throws UserNotFoundException If the user couldn't be found in the database.
     * @throws IOException           If the user's image couldn't be written to a buffer.
     * @author Claudio Spiess
     * @see User
     */
    User getUser(String username, String password) throws UserNotFoundException, SQLException, IOException;

    /**
     * Gets all users in the database for administrative
     * purposes.
     *
     * @return List of all users.
     * @throws SQLException If there is an exception in JDBC.
     * @throws IOException  If the user's image couldn't be written to a buffer.
     * @author Claudio Spiess
     * @see User
     */
    List<User> getAllUsers() throws SQLException, IOException;

    /**
     * Gets a user based on a specified id.
     *
     * @param id User id of user to get.
     * @return User instance of specified id.
     * @throws SQLException          If there is an exception in JDBC.
     * @throws IOException           If the user's image couldn't be written to a buffer.
     * @throws UserNotFoundException If a user with specified id cannot be found.
     * @author Claudio Spiess
     * @see User
     */
    User getUser(int id) throws UserNotFoundException, SQLException, IOException;

    /**
     * Searches a specific user id's records based on a
     * RecordSearchQuery, which builds the correct SQL string to
     * search.
     *
     * @param query  RecordSearchQuery instance that has parametres set.
     * @param userId Id of user who's records are to be searched.
     * @return List of found records (can be empty).
     * @throws SQLException If there is an exception in JDBC.
     * @throws IOException  If the record's image couldn't be written to a buffer.
     * @author Claudio Spiess
     * @see Record
     */
    List<Record> searchRecords(RecordSearchQuery query, int userId) throws SQLException, IOException;

    /**
     * Searches a all records based on a
     * RecordSearchQuery, which builds the correct SQL string to
     * search.
     *
     * @param query RecordSearchQuery instance that has parametres set.
     * @return List of found records (can be empty).
     * @throws SQLException If there is an exception in JDBC.
     * @throws IOException  If the record's image couldn't be written to a buffer.
     * @author Claudio Spiess
     * @see Record
     */
    List<Record> searchRecords(RecordSearchQuery query) throws SQLException, IOException;

    /**
     * Gets all records stored in database regardless of ownership.
     *
     * @return List of all records (can be empty).
     * @throws SQLException If there is an exception in JDBC.
     * @throws IOException  If the record's image couldn't be written to a buffer.
     * @author Claudio Spiess
     * @see Record
     */
    List<Record> getAllRecords() throws SQLException, IOException;

    /**
     * Gets records stored in database based on the id of the
     * owner's id.
     *
     * @param userId User id to get record records for.
     * @return List of all records (can be empty).
     * @throws SQLException If there is an exception in JDBC.
     * @throws IOException  If the record's image couldn't be written to a buffer.
     * @author Claudio Spiess
     * @see Record
     */
    List<Record> getRecords(int userId) throws SQLException, IOException;

    /**
     * Gets count of records owned by a specific user's id.
     *
     * @param userId User id to get record's count for.
     * @return int Count of records owned by a specific user ID.
     * @throws SQLException If there is an exception in JDBC.
     * @author Claudio Spiess
     */
    int getRecordCount(int userId) throws SQLException;

    /**
     * Gets count of records all records in the database.
     *
     * @return int Count of records owned by a specific user ID.
     * @throws SQLException If there is an exception in JDBC.
     * @author Claudio Spiess
     */
    int getRecordCount() throws SQLException;

    /**
     * Gets a specific record based on the record id
     * and the id of the record's owner.
     *
     * @param userId   User id to get record for.
     * @param recordId Record id to find.
     * @return Record found with matching record id and owner user id.
     * @throws SQLException            If there is an exception in JDBC.
     * @throws IOException             If the record's image couldn't be written to a buffer.
     * @throws RecordNotFoundException If a record with specified parametres couldn't be found.
     * @author Claudio Spiess
     */
    Record getRecord(int userId, int recordId) throws SQLException, IOException, RecordNotFoundException;

    /**
     * Commits database instance to file.
     * Useless because by default JDBC Sqlite
     * is set to autocommit any changes,
     * but it's worth being safe than sorry.
     *
     * @author Claudio Spiess
     */
    void save();

}
