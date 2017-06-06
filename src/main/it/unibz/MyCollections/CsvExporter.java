package main.it.unibz.MyCollections;

import main.it.unibz.MyCollections.views.UserView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Exporter to export records to comma separated values file.
 * This implementation of the Exporter interface exports
 * only to CSV files.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 * @see Exporter
 */
public class CsvExporter implements Exporter {
    private static final Logger logger = Logger.getLogger(CsvExporter.class.getName());

    /**
     * Exports list of records to a file in comma separated values format.
     * Does not include photo.
     *
     * @param records  List of records to export.
     * @param filePath Path where file will be saved.
     * @author Claudio Spiess
     */
    @Override
    public void exportRecords(List<Record> records, Path filePath) {
        logger.entering(getClass().getName(), "exportRecords");
        File file = filePath.toFile();
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            file.createNewFile();
            logger.log(Level.INFO, "Writing to: {0}", filePath.toString());
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);

            bw.write("firstname,lastname,companyname,address,telephonenumber,email");
            bw.newLine();
            for (Record record : records) {
                logger.log(Level.INFO, "Writing record: {0}", record.getRecordId());
                String firstName = record.getFirstName() == null ? "" : record.getFirstName();
                String lastName = record.getLastName() == null ? "" : record.getLastName();
                String companyName = record.getCompanyName() == null ? "" : record.getCompanyName();
                String address = record.getAddress() == null ? "" : record.getAddress();
                String telephoneNumber = record.getTelephoneNumber() == null ? "" : record.getTelephoneNumber();
                String email = record.getEmailAddress() == null ? "" : record.getEmailAddress();
                bw.write(String.format("%s,%s,%s,%s,%s,%s",
                        firstName,
                        lastName,
                        companyName,
                        address,
                        telephoneNumber,
                        email));
                bw.newLine();
            }

        } catch (IOException e) {

            logger.log(Level.SEVERE, "Error saving file", e);

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                logger.log(Level.SEVERE, "Error saving file", ex);

            }

        }
        logger.exiting(getClass().getName(), "exportRecords");
    }
}
