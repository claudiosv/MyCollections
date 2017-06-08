package tests.it.unibz.MyCollections;

import main.it.unibz.MyCollections.*;
import main.it.unibz.MyCollections.exceptions.RecordNotFoundException;
import main.it.unibz.MyCollections.exceptions.UserAlreadyExistsException;
import main.it.unibz.MyCollections.exceptions.UserNotFoundException;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests whether the core functionality of the application works as expected.
 */
public class SQLiteHandlerTest {

    /**
     * Extensive testing of every database function available. This ensures that the core functionality
     * of the application works as expected.
     *
     * @throws SQLException If there was an SQL error.
     * @throws UserAlreadyExistsException
     * @throws UserNotFoundException
     * @throws IOException
     * @throws RecordNotFoundException
     */
    @Test
    public void SQLiteHandlerTest() throws SQLException, UserAlreadyExistsException, UserNotFoundException, IOException, RecordNotFoundException {
        SQLiteHandler sqliteHandler = new SQLiteHandler();
        String randomFilename = Paths.get("tests", new SimpleDateFormat("yyyyMMddHHmmsS'_TestDatabase.db'").format(new Date())).toString();
        sqliteHandler.initialise(randomFilename);
        assertEquals(randomFilename, sqliteHandler.getFileName());

        assertEquals(0, sqliteHandler.getAllRecords().size());
        assertEquals(0, sqliteHandler.getRecordCount());
        assertEquals(0, sqliteHandler.getAllUsers().size());

        User testUser = new User();
        String password = new SHA512Hasher().hash("test");
        testUser.setAdmin(true);
        testUser.setPassword(password);
        testUser.setUsername("TestUser");
        sqliteHandler.addUser(testUser);


        User newUser = sqliteHandler.getUser("TestUser", password);

        assertEquals(1, sqliteHandler.getAllUsers().size());
        assertEquals(0, sqliteHandler.getRecordCount());
        assertEquals(0, sqliteHandler.getRecordCount(newUser.getId()));
        assertEquals(0, sqliteHandler.getRecords(newUser.getId()).size());


        assertEquals("TestUser", newUser.getUsername());
        assertEquals(password, newUser.getPasswordHash());
        assertTrue(newUser.isAdmin());
        try {
            sqliteHandler.addUser(newUser);
            fail("User should already exist.");
        } catch (UserAlreadyExistsException ex) {
            //success
        }
        String newPassword = new SHA512Hasher().hash("newTest");
        newUser.setUsername("NewUsername");
        newUser.setPassword(newPassword);
        newUser.setAdmin(false);
        sqliteHandler.updateUser(newUser);
        User updatedUser = sqliteHandler.getUser(newUser.getId());
        assertEquals("NewUsername", updatedUser.getUsername());
        assertEquals(newPassword, updatedUser.getPasswordHash());
        assertFalse(updatedUser.isAdmin());
        assertTrue(sqliteHandler.userExists(updatedUser.getId()));
        assertTrue(sqliteHandler.userExists(updatedUser.getUsername()));
        assertFalse(sqliteHandler.userExists("nonexistantTest"));
        assertFalse(sqliteHandler.userExists(0xDEADBEEF));
        assertEquals(updatedUser.getId(), sqliteHandler.getAllUsers().get(0).getId());
        assertEquals(1, sqliteHandler.getAllUsers().size());

        Record testRecord = new Record();
        final String testFirstname = "Test firstname";
        final String testLastname = "Test lastname";
        final String testEmailAddress = "testemail@unibz.it";
        final String testTelephone = "+1 800 - (504) (1337)";
        final String testAddress = "Piazza Unversita 1";
        final String testCompany = "Test Company";
        testRecord.setFirstName(testFirstname);
        testRecord.setLastName(testLastname);
        testRecord.setEmailAddress(testEmailAddress);
        testRecord.setTelephoneNumber(testTelephone);
        testRecord.setAddress(testAddress);
        testRecord.setCompanyName(testCompany);
        try {
            sqliteHandler.insertRecord(testRecord);
        } catch (UserNotFoundException ex) {
            //succes
        } catch (Exception ex) {
            fail("Wrong exception thrown.");
        }
        testRecord.setOwnerUserId(updatedUser.getId());
        Record insertedRecord = sqliteHandler.insertRecord(testRecord);
        testRecord.setRecordId(insertedRecord.getRecordId());
        assertEquals(testRecord, insertedRecord);
        Record fetchedRecord = sqliteHandler.getRecord(updatedUser.getId(), insertedRecord.getRecordId());
        assertEquals(testRecord, fetchedRecord);
        assertEquals(insertedRecord, fetchedRecord);
        final String updatedtestFirstname = "Tupdatedest firstname";
        final String updatedtestLastname = "Testupdated lastname";
        final String updatedtestEmailAddress = "testupdatedemail@unibz.it";
        final String updatedtestTelephone = "+1 800 - (updated504) (1337)";
        final String updatedtestAddress = "Piazza Unversiupdatedta 1";
        final String updatedtestCompany = "Test Comupdatedpany";
        fetchedRecord.setFirstName(updatedtestFirstname);
        fetchedRecord.setLastName(updatedtestLastname);
        fetchedRecord.setEmailAddress(updatedtestEmailAddress);
        fetchedRecord.setTelephoneNumber(updatedtestTelephone);
        fetchedRecord.setAddress(updatedtestAddress);
        fetchedRecord.setCompanyName(updatedtestCompany);
        sqliteHandler.updateRecord(fetchedRecord);
        Record updatedRecord = sqliteHandler.getRecord(fetchedRecord.getOwnerUserId(), fetchedRecord.getRecordId());
        assertEquals(fetchedRecord, updatedRecord);
        assertTrue(sqliteHandler.recordExists(fetchedRecord.getRecordId()));
        assertFalse(sqliteHandler.recordExists(0xDEADBEEF));
        assertEquals(1, sqliteHandler.getAllRecords().size());
        assertEquals(1, sqliteHandler.getRecordCount());
        assertEquals(1, sqliteHandler.getRecordCount(updatedRecord.getOwnerUserId()));
        assertEquals(updatedRecord, sqliteHandler.getAllRecords().get(0));
        assertEquals(updatedRecord, sqliteHandler.getRecords(updatedRecord.getOwnerUserId()).get(0));
        RecordSearchQuery query = new RecordSearchQuery();
        query.setExclusive(false);
        query.setFirstName("Tup*");

        assertEquals(updatedRecord, sqliteHandler.searchRecords(query).get(0));

        RecordSearchQuery query1 = new RecordSearchQuery();
        query1.setExclusive(true);
        query1.setFirstName("Tup*");

        assertEquals(updatedRecord, sqliteHandler.searchRecords(query1).get(0));
        List<Record> records = sqliteHandler.searchRecords(query1, updatedRecord.getOwnerUserId());
        assertEquals(updatedRecord, records.get(0));
        sqliteHandler.save();

        //Deletion tests
        sqliteHandler.deleteRecord(updatedRecord.getRecordId());
        assertEquals(0, sqliteHandler.getAllRecords().size());
        assertEquals(0, sqliteHandler.getRecordCount());
        assertEquals(0, sqliteHandler.getRecordCount(updatedRecord.getOwnerUserId()));
        assertEquals(0, sqliteHandler.getRecords(updatedRecord.getOwnerUserId()).size());
        try {
            sqliteHandler.getRecord(updatedRecord.getOwnerUserId(), updatedRecord.getRecordId());
            fail("Record still exists");
        } catch (RecordNotFoundException ex) {
            //success
        } catch (Exception ex) {
            fail("Wrong exception");
        }
        assertFalse(sqliteHandler.recordExists(updatedRecord.getRecordId()));

        sqliteHandler.deleteUser(updatedUser.getId());
        assertEquals(0, sqliteHandler.getAllUsers().size());
        assertFalse(sqliteHandler.userExists(updatedUser.getId()));
        assertFalse(sqliteHandler.userExists(updatedUser.getUsername()));

        try {
            sqliteHandler.getUser(updatedUser.getUsername(), updatedUser.getPasswordHash());
            fail("Exception didn't fire");
        } catch (UserNotFoundException ex) {
            //success
        } catch (Exception ex) {
            fail("Wrong exception");
        }

        try {
            sqliteHandler.getUser(updatedUser.getId());
            fail("Exception didn't fire");
        } catch (UserNotFoundException ex) {
            //success
        } catch (Exception ex) {
            fail("Wrong exception");
        }

    }
}
