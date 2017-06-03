package main.it.unibz.MyCollections;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by claudio on 03/06/2017.
 */
public class CsvExporter implements Exporter {

    @Override
    public void exportRecords(List<Record> records, Path filePath) {
        File file = filePath.toFile();
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            file.createNewFile();
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);

            bw.write("firstname,lastname,companyname,address,telephonenumber,email");
            bw.newLine();
            for (Record record : records) {
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

            e.printStackTrace(); //TODO: logger

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace(); //TODO: logger

            }

        }
    }
}
