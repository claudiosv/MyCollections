package main.it.unibz.MyCollections;

import javafx.embed.swing.SwingFXUtils;
import main.it.unibz.MyCollections.exceptions.RecordAlreadyExistsException;
import main.it.unibz.MyCollections.exceptions.RecordNotFoundException;
import main.it.unibz.MyCollections.exceptions.UserAlreadyExistsException;
import main.it.unibz.MyCollections.exceptions.UserNotFoundException;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by claudio on 03/06/2017.
 */
public class SQLiteHandler implements RecordsHandler {
    private Connection c = null;
    private static final Logger logger = Logger.getLogger(SQLiteHandler.class.getName());

    @Override
    public void initialise(String fileName) {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + fileName);
            Statement stmt1 = c.createStatement();
            String sql1 = "CREATE TABLE IF NOT EXISTS users (\n"
                    + "	id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "	username TEXT,\n"
                    + "	password TEXT,\n"
                    + "	picture BLOB\n,"
                    + "	admin INTEGER NOT NULL\n,"
                    + "	deleted INTEGER NOT NULL\n"
                    + ");";
            stmt1.execute(sql1);
            stmt1.close();


            Statement stmt = c.createStatement();
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
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error loading database", e);
        }
        catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error loading SQLite JDBC", e);
        }
        logger.info("Opened database successfully");
    }

    @Override
    public void addUser(User user) throws SQLException, UserAlreadyExistsException {
        logger.entering(getClass().getName(), "addUser");
        try {
            if (userExists(user.getUsername())) throw new UserAlreadyExistsException();
            String sql = "INSERT INTO users (username,password,admin,deleted) VALUES (?,?,?,0);";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setBoolean(3, user.isAdmin());
            stmt.execute();
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace(); //TODO: logger
        }
        logger.exiting(getClass().getName(), "addUser");
    }

    @Override
    public void updateUser(User user) throws UserNotFoundException, SQLException, IOException {
        if (userExists(user.getId())) {
            String sql = "UPDATE users SET " +
                    "username = ?," +
                    "password = ?," +
                    "picture = ?," +
                    "admin = ?" +
                    " WHERE id = ?;";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, user.getUsername());

            stmt.setString(2, user.getPasswordHash());

            stmt.setBytes(3, user.getUserImageArray());

            stmt.setInt(4, user.isAdmin() ? 1 : 0);

            stmt.setInt(5, user.getId());


            stmt.execute();
            stmt.close();
            return;
        }
        throw new UserNotFoundException();
    }

    @Override
    public Record insertRecord(Record record) throws RecordAlreadyExistsException, SQLException {
        if (recordExists(record.getRecordId())) throw new RecordAlreadyExistsException();
        try {

            String sql = "INSERT INTO records (userid, firstname, lastname, companyname, address, telephonenumber, email, picture, deleted) VALUES (?,?,?,?,?,?,?,?,0);";
            PreparedStatement stmt = c.prepareStatement(sql);

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

            Statement lastId = c.createStatement();
            ResultSet result = lastId.executeQuery("SELECT last_insert_rowid() FROM records;");
            int recordId = 0;
            while (result.next()) {
                recordId = result.getInt(1);
            }
            lastId.close();
            Session.setRecordsAdded(Session.getRecordsAdded() + 1);
            return getRecord(record.getOwnerUserId(), recordId);
            //int id = c. select last_insert_rowid();
        } catch (Exception ex) {
            ex.printStackTrace(); //TODO: logger
        }
        return null;
    }

    @Override
    public void deleteUser(int userId) throws UserNotFoundException, SQLException {
        if (userExists(userId)) {
            String sql = "UPDATE users SET deleted = 1 WHERE id = ?;";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.execute();
            stmt.close();
            return;
        }
        throw new UserNotFoundException();
    }

    @Override
    public void deleteRecord(int recordId) throws SQLException, RecordNotFoundException {
        if (recordExists(recordId)) {
            String sql = "UPDATE records SET deleted = 1 WHERE id = ?;";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, recordId);
            stmt.execute();
            stmt.close();
            Session.setRecordsDeleted(Session.getRecordsDeleted() + 1);
        }
        else {
            throw new RecordNotFoundException();
        }
    }

    @Override
    public void updateRecord(Record record) throws SQLException, IOException, RecordNotFoundException {
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
            PreparedStatement stmt = c.prepareStatement(sql);
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
        }
        else {
            throw new RecordNotFoundException();
        }
    }

    @Override
    public boolean recordExists(int id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM records WHERE id = ? AND deleted = 0;";
        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            if (result.getInt(1) > 0) return true;
        }
        result.close();
        stmt.close();
        return false;
    }

    @Override
    public boolean userExists(int id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE id = ?;";
        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            if (result.getInt(1) > 0) return true;
        }
        result.close();
        stmt.close();
        return false;
    }

    @Override
    public boolean userExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?;";
        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setString(1, username);
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            if (result.getInt(1) > 0) return true;
        }
        result.close();
        stmt.close();
        return false;
    }

    @Override
    public User getUser(String username, String passwordHash) throws UserNotFoundException, SQLException, IOException {
        String sql = "SELECT id,username,password FROM users WHERE username = ? AND password = ? AND deleted=0 LIMIT 1;";
        PreparedStatement stmt = c.prepareStatement(sql);
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

    @Override
    public List<User> getAllUsers() throws SQLException, IOException {
        ArrayList<User> users = new ArrayList<>();
        Statement s = c.createStatement();
        ResultSet result = s.executeQuery("SELECT * FROM users WHERE deleted = 0");
        while (result.next()) {
            User user = new User();
            user.setId(result.getInt("id"));
            user.setUsername(result.getString("username"));
            user.setPassword(result.getString("password"));
            user.setAdmin(result.getBoolean("admin"));
            byte[] imageBytes = result.getBytes("picture");
            if (imageBytes != null)
                user.setUserImage(ImageIO.read(new ByteArrayInputStream(imageBytes)));
            users.add(user);
        }

        result.close();
        s.close();
        return users;
    }

    @Override
    public User getUser(int id) throws UserNotFoundException, SQLException, IOException {
        if (id < 1) throw new UserNotFoundException();
        String sql = "SELECT username,password,picture,admin FROM users WHERE id = ? AND deleted=0;";
        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setInt(1, id);

        ResultSet set = stmt.executeQuery();
        User user = null;

        while (set.next()) {
            user = new User();
            user.setId(id);
            user.setUsername(set.getString("username"));
            user.setPassword(set.getString("password"));
            InputStream stream = set.getBinaryStream("picture");
            if (stream != null) user.setUserImage(ImageIO.read(stream));
            user.setAdmin(set.getInt("admin") == 1 ? true : false);
        }
        set.close();
        stmt.close();
        return user;
    }

    @Override
    public List<Record> searchRecords(RecordSearchQuery query, int userId) throws SQLException, IOException {
        String sql = query.toLikeQuery(true);

        PreparedStatement s = c.prepareStatement("SELECT * FROM records WHERE deleted = 0 AND userid = ? AND " + sql);
        int counter = 2;
        s.setInt(1, userId);
        for (String part : query.getParametreValueBuilder()) {
            s.setString(counter, part);
            counter++;
        }

        return resultSetConverter(s.executeQuery());
    }

    @Override
    public List<Record> searchRecords(RecordSearchQuery query) throws SQLException, IOException {
        String sql = query.toLikeQuery(query.isExclusive());

        PreparedStatement s = c.prepareStatement("SELECT * FROM records WHERE deleted = 0 AND " + sql);
        int counter = 1;
        for (String part : query.getParametreValueBuilder()) {
            s.setString(counter, part);
            counter++;
        }

        return resultSetConverter(s.executeQuery());
    }

    @Override
    public List<Record> getAllRecords() throws SQLException, IOException {
        Statement s = c.createStatement();
        return resultSetConverter(s.executeQuery("SELECT * FROM records WHERE deleted = 0"));
    }

    @Override
    public List<Record> getRecords(int userId) throws SQLException, IOException {
        PreparedStatement s = c.prepareStatement("SELECT * FROM records WHERE userid = ? AND deleted = 0");
        s.setInt(1, userId);
        return resultSetConverter(s.executeQuery());
    }

    @Override
    public int getRecordCount(int userId) throws SQLException, IOException {
        PreparedStatement s = c.prepareStatement("SELECT COUNT(*) FROM records WHERE userid = ? AND deleted = 0");
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

    @Override
    public int getRecordCount() throws SQLException, IOException {
        Statement s = c.createStatement();
        ResultSet result = s.executeQuery("SELECT COUNT(*) FROM records WHERE deleted = 0");
        int count = 0;
        while (result.next()) {
            if (result.getInt(1) > -1) count = result.getInt(1);
        }
        result.close();
        s.close();
        return count;
    }

    @Override
    public Record getRecord(int userId, int recordId) throws SQLException, IOException, RecordNotFoundException {
        PreparedStatement s = c.prepareStatement("SELECT * FROM records WHERE userid = ? AND id = ? AND deleted = 0");
        s.setInt(1, userId);
        s.setInt(2, recordId);
        ResultSet result = s.executeQuery();

        while (result.next()) {
            Record databaseRecord = new Record();
            databaseRecord.setRecordId(result.getInt("id"));
            databaseRecord.setOwnerUserId(result.getInt("userid"));
            databaseRecord.setFirstName(result.getString("firstname"));
            databaseRecord.setLastName(result.getString("lastname"));
            databaseRecord.setCompanyName(result.getString("companyname"));
            databaseRecord.setAddress(result.getString("address"));
            databaseRecord.setTelephoneNumber(result.getString("telephonenumber"));
            databaseRecord.setEmailAddress(result.getString("email"));
            databaseRecord.setImage(SwingFXUtils.toFXImage(ImageIO.read(result.getBinaryStream("picture")), null));
            return databaseRecord;
        }

        result.close();
        s.close();
        throw new RecordNotFoundException();
    }

    @Override
    public void save() {
        try {
            c.commit();
        } catch (SQLException e)
        {

        }

    }

    private List<Record> resultSetConverter(ResultSet result) throws SQLException, IOException {
        ArrayList<Record> records = new ArrayList<>();
        while (result.next()) {
            Record databaseRecord = new Record();
            databaseRecord.setRecordId(result.getInt("id"));
            databaseRecord.setOwnerUserId(result.getInt("userid"));
            databaseRecord.setFirstName(result.getString("firstname"));
            databaseRecord.setLastName(result.getString("lastname"));
            databaseRecord.setCompanyName(result.getString("companyname"));
            databaseRecord.setAddress(result.getString("address"));
            databaseRecord.setTelephoneNumber(result.getString("telephonenumber"));
            databaseRecord.setEmailAddress(result.getString("email"));
            databaseRecord.setImage(SwingFXUtils.toFXImage(ImageIO.read(result.getBinaryStream("picture")), null));
            records.add(databaseRecord);
        }

        result.close();
        return records;
    }

}
