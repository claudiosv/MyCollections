package it.unibz.MyCollections.portability;

import it.unibz.MyCollections.Importer;
import it.unibz.MyCollections.AddressRecord;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Importer to import records from comma separated values file.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class CsvImporter implements Importer {
    /**
     * Holds the logger for this class.
     */
    private static final Logger logger = Logger.getLogger(CsvImporter.class.getName());

    /**
     * Imports comma separated values file into records database.
     *
     * @param csvFile Path to CSV file to import from.
     * @author Claudio Spiess
     */
    @Override
    public List<AddressRecord> importRecords(Path csvFile) {
        logger.entering(getClass().getName(), "importRecords");
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        List<AddressRecord> records = new ArrayList<>();
        try {

            br = new BufferedReader(new FileReader(csvFile.toFile()));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] recordData = line.split(cvsSplitBy);
                if (recordData[0].equals("firstname")) continue;

                AddressRecord newRecord = new AddressRecord();
                try {
                    newRecord.setDefaultImage();
                    newRecord.setFirstName(recordData[0]);
                    newRecord.setLastName(recordData[1]);
                    newRecord.setCompanyName(recordData[2]);
                    newRecord.setAddress(recordData[3]);
                    newRecord.setTelephoneNumber(recordData[4]);
                    newRecord.setEmailAddress(recordData[5]);
                } catch (ArrayIndexOutOfBoundsException ex) {
                    logger.log(Level.SEVERE, "Error importing CSV", ex);
                }

                records.add(newRecord);
            }

        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "File not found", e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "File error", e);
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
