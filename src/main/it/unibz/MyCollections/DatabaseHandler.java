package main.it.unibz.MyCollections;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by claudio on 30/03/2017.
 */
public class DatabaseHandler {
    public Connection c = null;

    public void initialise()
    {

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt1 = c.createStatement();
            String sql1 = "CREATE TABLE IF NOT EXISTS users (\n"
                    + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                    + "	username TEXT,\n"
                    + "	password TEXT,\n"
                    + "	picture BLOB\n"
                    + ");";
            stmt1.execute(sql1);


            Statement stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS records (\n"
                    + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                    + "	userid integer NOT NULL,\n"
                    + "	firstname TEXT,\n"
                    + "	lastname TEXT,\n"
                    + "	companyname TEXT,\n"
                    + "	address TEXT,\n"
                    + "	telephonenumber TEXT,\n"
                    + "	email TEXT,\n"
                    + "	picture BLOB\n,"
                    + " FOREIGN KEY(userid) REFERENCES users(id)"
                    + ");";
            stmt.execute(sql);


            String sql3 = "INSERT INTO users (username,password) VALUES ('test', ?);";
                    String sql4 =
                    "INSERT INTO records (userid,firstname,lastname,companyname,address,telephonenumber,email) VALUES (1,'Test first','Test last','None','Druso','9438583','myemail');";
            PreparedStatement create = c.prepareStatement(sql3);
            String pass = get_SHA_1_SecurePassword("test1");
            create.setString(1, pass);
           // create.execute();

           // c.createStatement().execute(sql4);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        User user = new User();
        user.setPassword(get_SHA_1_SecurePassword("test1"));
        user.setUsername("test");
        //addUser(user);
        User test = getUser("test", "test1");
        test.getUsername();
        System.out.println("Opened database successfully");
    }

    public void addUser(User user)
    {

        //TODO: check if user exists!
        try {
            if(userExists(user.getId())) return;
            String sql = "INSERT INTO users (username,password) VALUES (?,?);";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            //stmt.setBytes(3, user.getUserImageArray());
            /*first name, last name, company name, address, telephone number, e-mail address,*/
            stmt.execute();
        } catch (Exception ex){ex.printStackTrace();}
    }


    //TODO: update user
    public void updateUser(int userId)
    {

    }

    //TODO: insert record
    public void insertRecord(Record record)
    {
        try {
            String sql = "INSERT INTO records (userid,firstname) VALUES (?,?);";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, 1);
            stmt.setString(2, "test");
            //stmt.setBytes(3, user.getUserImageArray());
            /*first name, last name, company name, address, telephone number, e-mail address,*/
            stmt.execute();
        } catch (Exception ex){ex.printStackTrace();}
    }

    //TODO: delete user
    public void deleteUser(int userId)
    {

    }

    //TODO: delete record
    public void deleteRecord(int recordId)
    {

    }

    //TODO: update record
    public void updateRecord(Record record)
    {

    }

    public boolean userExists(int id) throws SQLException
    {
        String sql = "SELECT COUNT(*) FROM users WHERE id = ?;";
        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet result = stmt.executeQuery();
        while(result.next())
        {
            if(result.getInt(1) > 0) return true;
        }

        return false;
    }

    public User getUser(String username, String password)
    {
        String passwordHash = get_SHA_1_SecurePassword(password);
        try {
            String sql = "SELECT id,username,password FROM users WHERE username = ? AND password = ? LIMIT 1;";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, passwordHash);

            /*first name, last name, company name, address, telephone number, e-mail address,*/
            ResultSet set = stmt.executeQuery();
            int userId = 0;
            while(set.next()) {
                userId = set.getInt("id");
            }

            return getUser(userId); //we could also make a JOIN query instead of a separate one
        } catch (Exception ex){ex.printStackTrace();}
        return null;
    }

    public User getUser(int id) throws Exception {
        if(id < 1) throw new Exception(Integer.toString(id));
        String sql = "SELECT id,username,password,picture FROM users WHERE id = ?;";
        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setInt(1, id);


            /*first name, last name, company name, address, telephone number, e-mail address,*/
        ResultSet set = stmt.executeQuery();
        User user = new User();
        user.setId(id);

        while(set.next()) {
            user.setUsername(set.getString("username"));
            user.setPassword(set.getString("password"));
            InputStream stream = set.getBinaryStream("picture");
            if(stream != null) user.setUserImage(ImageIO.read(stream));
        }

        return user;
    }
    /**
     * Read the file and returns the byte array
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
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e2) {
            System.err.println(e2.getMessage());
        }
        return bos != null ? bos.toByteArray() : null;
    }

    private static String get_SHA_1_SecurePassword(String passwordToHash)
    {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public ArrayList<Record> searchRecords(RecordSearchQuery query, int userId) throws SQLException, IOException
    {
        String sql = query.toLikeQuery(true);

        PreparedStatement s = c.prepareStatement("SELECT * FROM records WHERE userid = ? AND " +sql);
        int counter = 2;
        s.setInt(1, userId);
        for(String part : query.getParametreValueBuilder())
        {
            s.setString(counter, part);
            counter++;
        }

        return resultSetConverter(s.executeQuery());
    }

    public ArrayList<Record> getAllRecords() throws SQLException, IOException {
        Statement s = c.createStatement();
        return resultSetConverter(s.executeQuery("SELECT * FROM records"));
    }

    public ArrayList<Record> getRecords(int userId) throws SQLException, IOException
    {
        PreparedStatement s = c.prepareStatement("SELECT * FROM records WHERE userid = ?");
        s.setInt(1, userId);
        return resultSetConverter(s.executeQuery());

    }

    public ArrayList<Record> resultSetConverter(ResultSet result) throws SQLException, IOException
    {
        ArrayList<Record> records = new ArrayList<>();
        while(result.next()) {
            Record databaseRecord = new Record();
            databaseRecord.setRecordId(result.getInt("id"));
            databaseRecord.setOwnerUserId(result.getInt("userid"));
            databaseRecord.setFirstName(result.getString("firstname"));
            databaseRecord.setLastName(result.getString("lastname"));
            databaseRecord.setCompanyName(result.getString("companyname"));
            databaseRecord.setAddress(result.getString("address"));
            databaseRecord.setTelephoneNumber(result.getString("telephonenumber"));
            databaseRecord.setEmailAddress(result.getString("email"));
            databaseRecord.setImage(new Image(result.getBinaryStream("picture")));

            records.add(databaseRecord);
        }
        return records;
    }
}
