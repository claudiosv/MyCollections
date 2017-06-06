package main.it.unibz.MyCollections;

import main.it.unibz.MyCollections.exceptions.RecordNotFoundException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Importer to import records to comma separated values file.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class CsvImporter implements Importer {
    private static final Logger logger = Logger.getLogger(CsvImporter.class.getName());

    /**
     * Imports comma separated values file into records database.
     *
     * @author Claudio Spiess
     * @param csvFile Path to CSV file to import from.
     */
    @Override
    public List<Record> importRecords(Path csvFile) {
        logger.entering(getClass().getName(), "importRecords");
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        List<Record> records = new ArrayList<>();
        try {

            br = new BufferedReader(new FileReader(csvFile.toFile()));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] recordData = line.split(cvsSplitBy);
                if (recordData[0].equals("firstname")) continue;

                Record newRecord = new Record();
                try {
                    newRecord.setFirstName(recordData[0]);
                    newRecord.setLastName(recordData[1]);
                    newRecord.setCompanyName(recordData[2]);
                    newRecord.setAddress(recordData[3]);
                    newRecord.setTelephoneNumber(recordData[4]);
                    newRecord.setEmailAddress(recordData[5]);
                } catch (ArrayIndexOutOfBoundsException ex) {
                    //TODO: logger
                }

//firstname,lastname,companyname,address,telephonenumber,email
                records.add(newRecord);
                DatabaseSession.getInstance().insertRecord(newRecord);
            }

        } catch (FileNotFoundException e) {
           logger.log(Level.SEVERE, "File not found", e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "File error", e);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error inserting into database", e);
        } catch (RecordNotFoundException e) {
            logger.log(Level.SEVERE, "Record not found", e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "File error", e);
                }
            }
        }
        logger.exiting(getClass().getName(), "importRecords");
        return records;
    }
}
