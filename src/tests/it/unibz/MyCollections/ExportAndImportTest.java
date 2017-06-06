package tests.it.unibz.MyCollections;

import main.it.unibz.MyCollections.*;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Claudio on 06/06/2017.
 */
public class ExportAndImportTest
{
    @Test
    public void ExportAndImportTest()
    {
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
        Path testPath = new File("csvTest.csv").toPath();
        csvExporter.exportRecords(recordsTest, testPath);

        Importer csvImporter = new CsvImporter();
        List<Record> importedRecords = csvImporter.importRecords(testPath);

        for(int i = 0; i < recordsTest.size(); i++)
        {
            assertEquals(recordsTest.get(i), importedRecords.get(i));
        }
    }
}
