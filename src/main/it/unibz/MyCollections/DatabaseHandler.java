package main.it.unibz.MyCollections;

import javax.imageio.ImageIO;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by claudio on 30/03/2017.
 */
public class DatabaseHandler {
    private static DatabaseHandler instance = new DatabaseHandler();
    public Connection c = null;

    private DatabaseHandler() {
    }

    public static DatabaseHandler getInstance() {
        return instance;
    }

    public static String get_SHA_1_SecurePassword(String passwordToHash) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public void initialise() {

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
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
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Opened database successfully");
    }

    public void addUser(User user) throws Exception {

        //TODO: throw exception rather than return
        try {
            if (userExists(user.getUsername())) return;
            String sql = "INSERT INTO users (username,password,admin,deleted) VALUES (?,?,?,0);";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setBoolean(3, user.isAdmin());
            //stmt.setBytes(3, user.getUserImageArray());
            /*first name, last name, company name, address, telephone number, e-mail address,*/
            stmt.execute();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //TODO: update user
    public void updateUser(User user) throws Exception {
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
        throw new Exception("user doesn't exist"); //TODO: custom exception
    }

    //TODO: insert record
    public Record insertRecord(Record record) throws Exception {
        if (recordExists(record.getRecordId())) throw new Exception("record exists");
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

            stmt.setBytes(8, record.getRecordImageArray());

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
            ex.printStackTrace();
        }
        return null;
    }


    public void deleteUser(int userId) throws Exception {
        if (userExists(userId)) {
            String sql = "UPDATE users SET deleted = 1 WHERE id = ?;";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.execute();
            stmt.close();
            return;
        }
        throw new Exception("user doesn't exist"); //TODO: custom exception
    }

    public void deleteRecord(int recordId) {
        try {
            if (recordExists(recordId)) {
                String sql = "UPDATE records SET deleted = 1 WHERE id = ?;";
                PreparedStatement stmt = c.prepareStatement(sql);
                stmt.setInt(1, recordId);
                stmt.execute();
                stmt.close();
                Session.setRecordsDeleted(Session.getRecordsDeleted() + 1);
                return;
            }
        } catch (Exception ex) {
        }
        //throw new Exception("record doesn't exist"); //TODO: custom exception
    }

    //TODO: update record
    public void updateRecord(Record record) throws SQLException, IOException, Exception {
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

            stmt.setBytes(8, record.getRecordImageArray());

            stmt.setInt(9, record.getRecordId());

            stmt.execute();
            stmt.close();
            return;
        }
        throw new Exception("record doesn't exist"); //TODO: custom exception
    }

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

    public User getUser(String username, String password) throws Exception {
        String passwordHash = get_SHA_1_SecurePassword(password);
        String sql = "SELECT id,username,password FROM users WHERE username = ? AND password = ? AND deleted=0 LIMIT 1;";
        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setString(2, passwordHash);

            /*first name, last name, company name, address, telephone number, e-mail address,*/
        ResultSet set = stmt.executeQuery();
        int userId = 0;
        while (set.next()) {
            userId = set.getInt("id");
        }
        set.close();
        stmt.close();
        if (userId == 0) throw new Exception("User not found");
        return getUser(userId); //we could also make a JOIN query instead of a separate one
    }

    public ArrayList<User> getAllUsers() throws Exception {
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

    public User getUser(int id) throws Exception {
        if (id < 1) throw new Exception("Invalid user: " + Integer.toString(id));
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

    /**
     * Read the file and returns the byte array
     *
     * @param file
     * @return the bytes of the file
     */
    private byte[] readFile(String file) {
        ByteArrayOutputStream bos = null;
        try {
            File f = new File(file);
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1; ) {
                bos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e2) {
            System.err.println(e2.getMessage());
        }
        return bos != null ? bos.toByteArray() : null;
    }

    public ArrayList<Record> searchRecords(RecordSearchQuery query, int userId) throws SQLException, IOException {
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

    public ArrayList<Record> searchRecords(RecordSearchQuery query) throws SQLException, IOException {
        String sql = query.toLikeQuery(query.isExclusive());

        PreparedStatement s = c.prepareStatement("SELECT * FROM records WHERE deleted = 0 AND " + sql);
        int counter = 1;
        for (String part : query.getParametreValueBuilder()) {
            s.setString(counter, part);
            counter++;
        }

        return resultSetConverter(s.executeQuery());
    }

    public ArrayList<Record> getAllRecords() throws SQLException, IOException {
        Statement s = c.createStatement();
        return resultSetConverter(s.executeQuery("SELECT * FROM records WHERE deleted = 0"));
    }

    public ArrayList<Record> getRecords(int userId) throws SQLException, IOException {
        PreparedStatement s = c.prepareStatement("SELECT * FROM records WHERE userid = ? AND deleted = 0");
        s.setInt(1, userId);
        return resultSetConverter(s.executeQuery());
    }

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

    public Record getRecord(int userId, int recordId) throws SQLException, IOException, Exception {
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
            databaseRecord.setBufImage(ImageIO.read(result.getBinaryStream("picture")));
            return databaseRecord;
        }

        result.close();
        s.close();
        throw new Exception("record not found"); //TODO: record not found
    }

    public ArrayList<Record> resultSetConverter(ResultSet result) throws SQLException, IOException {
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
            byte[] imageBytes = result.getBytes("picture");
            if (imageBytes != null)
                databaseRecord.setBufImage(ImageIO.read(new ByteArrayInputStream(imageBytes)));
            records.add(databaseRecord);
        }

        result.close();
        return records;
    }

    public boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z0-9._-]{5,}$") && stringMatchesChars(username, Arrays.asList('-', '_', '.'));
    }

    private boolean stringMatchesChars(final String str, final List<Character> characters) { //TODO: this is stolen!
        return (str.chars()
                .filter(ch -> characters.contains((char) ch))
                .count() < 2);
    }

    public boolean isValidPassword(String password) {
        return password.length() > 4;
    }
}
