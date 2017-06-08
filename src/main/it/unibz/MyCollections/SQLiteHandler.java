package main.it.unibz.MyCollections;

import javafx.embed.swing.SwingFXUtils;
import main.it.unibz.MyCollections.exceptions.RecordNotFoundException;
import main.it.unibz.MyCollections.exceptions.UserAlreadyExistsException;
import main.it.unibz.MyCollections.exceptions.UserNotFoundException;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DatabaseHandler implementation for SQLite JDBC.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class SQLiteHandler implements DatabaseHandler {
    /**
     * Holds the logger of this class.
     */
    private static final Logger logger = Logger.getLogger(SQLiteHandler.class.getName());

    /**
     * Holds the JDBC {@link Connection} of this class.
     */
    private Connection sqliteConnection = null;

    /**
     * Holds the file name of the database.
     */
    private String fileName;

    /**
     * Initialises database connection, connects to database (if implementation does so).
     *
     * @param fileName Path to file that stores the records.
     * @author Claudio Spiess
     */
    @Override
    public void initialise(String fileName) {
        logger.entering(getClass().getName(), "initialise");
        this.fileName = fileName;
        try {
            logger.log(Level.INFO, "Creating connection, filename: {0}", fileName);
            Class.forName("org.sqlite.JDBC");
            sqliteConnection = DriverManager.getConnection("jdbc:sqlite:" + fileName);

            logger.info("Creating schema");
            Statement stmt1 = sqliteConnection.createStatement();
            String sql1 = "CREATE TABLE IF NOT EXISTS users (\n"
                    + "	id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "	username TEXT,\n"
                    + "	password TEXT,\n"
                    + "	picture BLOB\n,"
                    + "	admin INTEGER NOT NULL\n,"
                    + "	deleted INTEGER NOT NULL\n"
                    + ");";
            logger.log(Level.FINEST, "Creating user schema", stmt1.execute(sql1));
            stmt1.close();

            Statement stmt = sqliteConnection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS records (\n"
                    + "	id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "	userid INTEGER NOT NULL,\n"
                    + "	firstname TEXT,\n"
                    + "	lastname TEXT,\n"
                    + "	companyname TEXT,\n"
                    + "	address TEXT,\n"
                    + "	telephonenumber TEXT,\n"
                    + "	email TEXT,\n"
                    + "	picture BLOB\n,"
                    + "	deleted INTEGER NOT NULL\n,"
                    + " FOREIGN KEY(userid) REFERENCES users(id)"
                    + ");";
            logger.log(Level.FINEST, "Creating records schema", stmt.execute(sql));
            stmt.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error loading database", e);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error loading SQLite JDBC", e);
        }
        logger.info("Opened database successfully");
        logger.exiting(getClass().getName(), "initialise");
    }

    /**
     * Gets the file name of the database.
     *
     * @return String containing file name of database
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    @Override
    public String getFileName() {
        return fileName;
    }

    /**
     * Adds a new user to the database.
     *
     * @param user object to add to database.
     * @throws SQLException               If there is an exception in JDBC.
     * @throws UserAlreadyExistsException If a user with the same username or id already exists.
     * @author Claudio Spiess
     * @see User
     */
    @Override
    public void addUser(User user) throws SQLException, UserAlreadyExistsException {
        logger.entering(getClass().getName(), "addUser");
        if (userExists(user.getUsername())) throw new UserAlreadyExistsException();
        String sql = "INSERT INTO users (username,password,admin,deleted) VALUES (?,?,?,0);";
        PreparedStatement stmt = sqliteConnection.prepareStatement(sql);
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPasswordHash());
        stmt.setBoolean(3, user.isAdmin());
        stmt.execute();
        stmt.close();
        logger.exiting(getClass().getName(), "addUser");
    }

    /**
     * Updates an existing user with new properties. Requires
     * that the passed User argument already has a valid user id.
     * If the user cannot be found, a UserNotFound exception is thrown.
     *
     * @param user object to edit in the database.
     * @throws SQLException          If there is an exception in JDBC.
     * @throws UserNotFoundException If a user with that id doesn't already exist.
     * @throws IOException           If there is an error writing the user's image to a buffer.
     * @author Claudio Spiess
     * @see User
     */
    @Override
    public void updateUser(User user) throws UserNotFoundException, SQLException {
        logger.entering(getClass().getName(), "updateUser");
        if (userExists(user.getId())) {
            String sql = "UPDATE users SET " +
                    "username = ?," +
                    "password = ?," +
                    "picture = ?," +
                    "admin = ?" +
                    " WHERE id = ?;";
            PreparedStatement stmt = sqliteConnection.prepareStatement(sql);
            stmt.setString(1, user.getUsername());

            stmt.setString(2, user.getPasswordHash());

            stmt.setBytes(3, user.getImageArray());

            stmt.setInt(4, user.isAdmin() ? 1 : 0);

            stmt.setInt(5, user.getId());


            stmt.execute();
            stmt.close();
        } else {
            throw new UserNotFoundException();
        }
    }

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
    @Override
    public Record insertRecord(Record record) throws SQLException, RecordNotFoundException, IOException, UserNotFoundException {
        logger.entering(getClass().getName(), "insertRecord");
        logger.log(Level.FINE, "Debug record id insertion: {0}", record.getRecordId());
        if (userExists(record.getOwnerUserId())) {
            String sql = "INSERT INTO records (userid, firstname, lastname, companyname, address, telephonenumber, email, picture, deleted) VALUES (?,?,?,?,?,?,?,?,0);";
            PreparedStatement stmt = sqliteConnection.prepareStatement(sql);

            stmt.setInt(1, record.getOwnerUserId());

            stmt.setString(2, record.getFirstName());

            stmt.setString(3, record.getLastName());

            stmt.setString(4, record.getCompanyName());

            stmt.setString(5, record.getAddress());

            stmt.setString(6, record.getTelephoneNumber());

            stmt.setString(7, record.getEmailAddress());

            stmt.setBytes(8, record.getImageArray());

            stmt.execute();
            stmt.close();

            Statement lastId = sqliteConnection.createStatement();
            ResultSet result = lastId.executeQuery("SELECT last_insert_rowid() FROM records;");
            int recordId = 0;
            while (result.next()) {
                recordId = result.getInt(1);
            }
            lastId.close();
            Session.getInstance().incrementRecordsAdded();
            return getRecord(record.getOwnerUserId(), recordId);
        } else {
            throw new UserNotFoundException();
        }
    }

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
    @Override
    public void deleteUser(int userId) throws UserNotFoundException, SQLException {
        logger.entering(getClass().getName(), "deleteUser");
        if (userExists(userId)) {
            String sql = "UPDATE users SET deleted = 1 WHERE id = ?;";
            PreparedStatement stmt = sqliteConnection.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.execute();
            stmt.close();
        } else {
            throw new UserNotFoundException();
        }
    }

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
    @Override
    public void deleteRecord(int recordId) throws SQLException, RecordNotFoundException {
        logger.entering(getClass().getName(), "deleteRecord");
        if (recordExists(recordId)) {
            String sql = "UPDATE records SET deleted = 1 WHERE id = ?;";
            PreparedStatement stmt = sqliteConnection.prepareStatement(sql);
            stmt.setInt(1, recordId);
            stmt.execute();
            stmt.close();
            Session.getInstance().incrementRecordsDeleted();
        } else {
            throw new RecordNotFoundException();
        }
    }

    /**
     * Updates an existing record in the database
     * based on the record's ID
     *
     * @param record record update in database.
     * @throws SQLException            If there is an exception in JDBC.
     * @throws RecordNotFoundException If the record couldn't be found in the database.
     * @throws IOException             If the record's image couldn't be written to a buffer.
     * @author Claudio Spiess
     * @see Record
     */
    @Override
    public void updateRecord(Record record) throws SQLException, RecordNotFoundException {
        logger.entering(getClass().getName(), "updateRecord");
        if (recordExists(record.getRecordId())) {
            String sql = "UPDATE records SET " +
                    "userid = ?," +
                    "firstname = ?," +
                    "lastname = ?," +
                    "companyname = ?," +
                    "address = ?," +
                    "telephonenumber = ?," +
                    "email = ?," +
                    "picture = ?" +
                    " WHERE id = ?;";
            PreparedStatement stmt = sqliteConnection.prepareStatement(sql);
            stmt.setInt(1, record.getOwnerUserId());

            stmt.setString(2, record.getFirstName());

            stmt.setString(3, record.getLastName());

            stmt.setString(4, record.getCompanyName());

            stmt.setString(5, record.getAddress());

            stmt.setString(6, record.getTelephoneNumber());

            stmt.setString(7, record.getEmailAddress());

            stmt.setBytes(8, record.getImageArray());

            stmt.setInt(9, record.getRecordId());

            stmt.execute();
            stmt.close();
        } else {
            throw new RecordNotFoundException();
        }
    }

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
    @Override
    public boolean recordExists(int id) throws SQLException {
        logger.entering(getClass().getName(), "recordExists");
        String sql = "SELECT COUNT(*) FROM records WHERE id = ? AND deleted = 0;";
        PreparedStatement stmt = sqliteConnection.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            if (result.getInt(1) > 0) return true;
        }
        result.close();
        stmt.close();
        return false;
    }

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
    @Override
    public boolean userExists(int id) throws SQLException {
        logger.entering(getClass().getName(), "userExists");
        String sql = "SELECT COUNT(*) FROM users WHERE id = ? AND deleted = 0;";
        PreparedStatement stmt = sqliteConnection.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            if (result.getInt(1) > 0) return true;
        }
        result.close();
        stmt.close();
        return false;
    }

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
    @Override
    public boolean userExists(String username) throws SQLException {
        logger.entering(getClass().getName(), "userExists");
        String sql = "SELECT COUNT(*) FROM users WHERE deleted = 0 AND username = ?;";
        PreparedStatement stmt = sqliteConnection.prepareStatement(sql);
        stmt.setString(1, username);
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            if (result.getInt(1) > 0) return true;
        }
        result.close();
        stmt.close();
        return false;
    }

    /**
     * Gets user with specified username and password.
     * If a user with specific parametres cannot be found,
     * an exception is thrown.
     *
     * @param username     of user to get.
     * @param passwordHash of user to get.
     * @return User instance if a user is found.
     * @throws SQLException          If there is an exception in JDBC.
     * @throws UserNotFoundException If the user couldn't be found in the database.
     * @throws IOException           If the user's image couldn't be written to a buffer.
     * @author Claudio Spiess
     * @see User
     */
    @Override
    public User getUser(String username, String passwordHash) throws UserNotFoundException, SQLException, IOException {
        logger.entering(getClass().getName(), "getUser");
        String sql = "SELECT id,username,password FROM users WHERE username = ? AND password = ? AND deleted=0 LIMIT 1;";
        PreparedStatement stmt = sqliteConnection.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setString(2, passwordHash);
        ResultSet set = stmt.executeQuery();
        int userId = 0;
        while (set.next()) {
            userId = set.getInt("id");
        }
        set.close();
        stmt.close();
        if (userId == 0) throw new UserNotFoundException();
        return getUser(userId);
    }

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
    @Override
    public List<User> getAllUsers() throws SQLException, IOException {
        logger.entering(getClass().getName(), "getAllUsers");
        ArrayList<User> users = new ArrayList<>();
        Statement s = sqliteConnection.createStatement();
        ResultSet result = s.executeQuery("SELECT * FROM users WHERE deleted = 0");
        while (result.next()) {
            User user = new User();
            user.setDefaultImage();
            user.setId(result.getInt("id"));
            user.setUsername(result.getString("username"));
            user.setPassword(result.getString("password"));
            user.setAdmin(result.getBoolean("admin"));
            byte[] pictureBytes = result.getBytes("picture");
            if (pictureBytes != null && pictureBytes.length > 1) {
                InputStream pictureStream = result.getBinaryStream("picture");
                user.setImage(SwingFXUtils.toFXImage(ImageIO.read(pictureStream), null));
            }
            users.add(user);
        }

        result.close();
        s.close();
        return users;
    }

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
    @Override
    public User getUser(int id) throws UserNotFoundException, SQLException, IOException {
        logger.entering(getClass().getName(), "getUser");
        if (id < 1) throw new UserNotFoundException();
        String sql = "SELECT username,password,picture,admin FROM users WHERE id = ? AND deleted=0;";
        PreparedStatement stmt = sqliteConnection.prepareStatement(sql);
        stmt.setInt(1, id);

        ResultSet set = stmt.executeQuery();
        User user = null;

        while (set.next()) {
            user = new User();
            user.setDefaultImage();
            user.setId(id);
            user.setUsername(set.getString("username"));
            user.setPassword(set.getString("password"));
            byte[] pictureBytes = set.getBytes("picture");
            if (pictureBytes != null && pictureBytes.length > 1) {
                InputStream pictureStream = set.getBinaryStream("picture");
                user.setImage(SwingFXUtils.toFXImage(ImageIO.read(pictureStream), null));
            }
            user.setAdmin(set.getInt("admin") == 1);
        }
        set.close();
        stmt.close();
        if (user == null) throw new UserNotFoundException();
        else
            return user;
    }

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
    @Override
    public List<Record> searchRecords(RecordSearchQuery query, int userId) throws SQLException, IOException {
        logger.entering(getClass().getName(), "searchRecords");
        String sql = query.toLikeQuery();

        PreparedStatement s = sqliteConnection.prepareStatement("SELECT * FROM records WHERE deleted = 0 AND userid = ? AND " + sql);
        int counter = 2;
        s.setInt(1, userId);
        for (String part : query.getParameterValueBuilder()) {
            s.setString(counter, part);
            counter++;
        }

        return resultSetConverter(s.executeQuery());
    }

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
    @Override
    public List<Record> searchRecords(RecordSearchQuery query) throws SQLException, IOException {
        logger.entering(getClass().getName(), "searchRecords");
        String sql = query.toLikeQuery();

        PreparedStatement s = sqliteConnection.prepareStatement("SELECT * FROM records WHERE deleted = 0 AND " + sql);
        int counter = 1;
        for (String part : query.getParameterValueBuilder()) {
            s.setString(counter, part);
            counter++;
        }

        return resultSetConverter(s.executeQuery());
    }

    /**
     * Gets all records stored in database regardless of ownership.
     *
     * @return List of all records (can be empty).
     * @throws SQLException If there is an exception in JDBC.
     * @throws IOException  If the record's image couldn't be written to a buffer.
     * @author Claudio Spiess
     * @see Record
     */
    @Override
    public List<Record> getAllRecords() throws SQLException, IOException {
        logger.entering(getClass().getName(), "getAllRecords");
        Statement s = sqliteConnection.createStatement();
        return resultSetConverter(s.executeQuery("SELECT * FROM records WHERE deleted = 0"));
    }

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
    @Override
    public List<Record> getRecords(int userId) throws SQLException, IOException {
        logger.entering(getClass().getName(), "getRecords");
        PreparedStatement s = sqliteConnection.prepareStatement("SELECT * FROM records WHERE userid = ? AND deleted = 0");
        s.setInt(1, userId);
        return resultSetConverter(s.executeQuery());
    }

    /**
     * Gets count of records owned by a specific user's id.
     *
     * @param userId User id to get record's count for.
     * @return int Count of records owned by a specific user ID.
     * @throws SQLException If there is an exception in JDBC.
     * @author Claudio Spiess
     */
    @Override
    public int getRecordCount(int userId) throws SQLException {
        logger.entering(getClass().getName(), "getRecordCount");
        PreparedStatement s = sqliteConnection.prepareStatement("SELECT COUNT(*) FROM records WHERE userid = ? AND deleted = 0");
        s.setInt(1, userId);
        ResultSet result = s.executeQuery();
        int count = 0;
        while (result.next()) {
            if (result.getInt(1) > -1) count = result.getInt(1);
        }
        result.close();
        s.close();
        return count;
    }

    /**
     * Gets count of records all records in the database.
     *
     * @return int Count of records owned by a specific user ID.
     * @throws SQLException If there is an exception in JDBC.
     * @author Claudio Spiess
     */
    @Override
    public int getRecordCount() throws SQLException {
        logger.entering(getClass().getName(), "getRecordCount");
        Statement s = sqliteConnection.createStatement();
        ResultSet result = s.executeQuery("SELECT COUNT(*) FROM records WHERE deleted = 0");
        int count = 0;
        while (result.next()) {
            if (result.getInt(1) > -1) count = result.getInt(1);
        }
        result.close();
        s.close();
        return count;
    }

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
    @Override
    public Record getRecord(int userId, int recordId) throws SQLException, IOException, RecordNotFoundException {
        logger.entering(getClass().getName(), "getRecord");
        PreparedStatement s = sqliteConnection.prepareStatement("SELECT * FROM records WHERE userid = ? AND id = ? AND deleted = 0");
        s.setInt(1, userId);
        s.setInt(2, recordId);
        ResultSet result = s.executeQuery();

        if (result.next()) {
            Record databaseRecord = new Record();
            databaseRecord.setDefaultImage();
            databaseRecord.setRecordId(result.getInt("id"));
            databaseRecord.setOwnerUserId(result.getInt("userid"));
            databaseRecord.setFirstName(result.getString("firstname"));
            databaseRecord.setLastName(result.getString("lastname"));
            databaseRecord.setCompanyName(result.getString("companyname"));
            databaseRecord.setAddress(result.getString("address"));
            databaseRecord.setTelephoneNumber(result.getString("telephonenumber"));
            databaseRecord.setEmailAddress(result.getString("email"));
            byte[] pictureBytes = result.getBytes("picture");
            if (pictureBytes != null && pictureBytes.length > 1) {
                InputStream pictureStream = result.getBinaryStream("picture");
                databaseRecord.setImage(SwingFXUtils.toFXImage(ImageIO.read(pictureStream), null));
            }

            return databaseRecord;
        }

        result.close();
        s.close();
        throw new RecordNotFoundException();
    }

    /**
     * Commits database instance to file.
     * Useless because by default JDBC Sqlite
     * is set to autocommit any changes,
     * but it's worth being safe than sorry.
     *
     * @author Claudio Spiess
     */
    @Override
    public void save() {
        logger.entering(getClass().getName(), "save");
        try {
            if (!sqliteConnection.getAutoCommit()) sqliteConnection.commit();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Save failed", e);
        }

    }

    /**
     * Converts a JDBC ResultSet into a list
     * of Record with the correct properties set.
     *
     * @param result A ResultSet from a JDBC SQL query
     * @return List of Records from the ResultSet
     * @throws SQLException If there is an exception loading values from the ResultSet
     * @throws IOException  If there is an exception writing to the image buffer.
     * @author Claudio Spiess
     */
    private List<Record> resultSetConverter(ResultSet result) throws SQLException, IOException {
        logger.entering(getClass().getName(), "resultSetConverter");
        List<Record> records = new ArrayList<>();
        while (result.next()) {
            Record databaseRecord = new Record();
            databaseRecord.setDefaultImage();
            databaseRecord.setRecordId(result.getInt("id"));
            databaseRecord.setOwnerUserId(result.getInt("userid"));
            databaseRecord.setFirstName(result.getString("firstname"));
            databaseRecord.setLastName(result.getString("lastname"));
            databaseRecord.setCompanyName(result.getString("companyname"));
            databaseRecord.setAddress(result.getString("address"));
            databaseRecord.setTelephoneNumber(result.getString("telephonenumber"));
            databaseRecord.setEmailAddress(result.getString("email"));
            byte[] pictureBytes = result.getBytes("picture");
            if (pictureBytes != null && pictureBytes.length > 1) {
                InputStream pictureStream = result.getBinaryStream("picture");
                databaseRecord.setImage(SwingFXUtils.toFXImage(ImageIO.read(pictureStream), null));
            }
            records.add(databaseRecord);
        }

        result.close();
        return records;
    }

}
