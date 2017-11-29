package tests.it.unibz.MyCollections;

import main.it.unibz.MyCollections.Importer;
import main.it.unibz.MyCollections.Record;
import main.it.unibz.MyCollections.portability.*;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests whether the exporters and importers work.
 */
public class ExportAndImportTest {

    /**
     * Tests whether the csv exporter, csv importer, xml exporter, and xml importer
     * produce the same values as their input.
     */
    @Test
    public void ExportAndImportCsvTest() {
        Exporter csvExporter = new CsvExporter();
        ArrayList<Record> recordsTest = new ArrayList<>();

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

        recordsTest.add(testRecord);
        Path testPath = Paths.get("tests", new SimpleDateFormat("yyyyMMddHHmmsS'_csvTest.csv'").format(new Date()));
        csvExporter.exportRecords(recordsTest, testPath);

        Importer csvImporter = new CsvImporter();
        List<Record> importedRecords = csvImporter.importRecords(testPath);

        for (int i = 0; i < recordsTest.size(); i++) {
            assertEquals(recordsTest.get(i), importedRecords.get(i));
        }
    }

    @Test
    public void ExportAndImportXmlTest() {
        Exporter xmlExporter = new XmlExporter();
        ArrayList<Record> recordsTest = new ArrayList<>();

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

        recordsTest.add(testRecord);
        Path testPath = Paths.get("tests", new SimpleDateFormat("yyyyMMddHHmmsS'_xmlTest.xml'").format(new Date()));
        xmlExporter.exportRecords(recordsTest, testPath);

        Importer xmlImporter = new XmlImporter();
        List<Record> importedRecords = xmlImporter.importRecords(testPath);

        for (int i = 0; i < recordsTest.size(); i++) {
            assertEquals(recordsTest.get(i), importedRecords.get(i));
        }
    }
}
