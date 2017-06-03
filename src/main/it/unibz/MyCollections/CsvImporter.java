package main.it.unibz.MyCollections;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Created by Claudio on 03/06/2017.
 */
public class CsvImporter implements Importer {
    @Override
    public ArrayList<Record> importRecords(Path csvFile) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        ArrayList<Record> records = new ArrayList<>();
        try {

            br = new BufferedReader(new FileReader(csvFile.toFile()));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] recordData = line.split(cvsSplitBy);
                if (recordData[0].equals("firstname")) continue;
                //TODO: make sure to ignore first line!
                Record newRecord = new Record();
                try {
                    newRecord.setFirstName(recordData[0]);
                    newRecord.setLastName(recordData[1]);
                    newRecord.setCompanyName(recordData[2]);
                    newRecord.setAddress(recordData[3]);
                    newRecord.setTelephoneNumber(recordData[4]);
                    newRecord.setEmailAddress(recordData[5]);
                } catch (ArrayIndexOutOfBoundsException ex) {

                }

//firstname,lastname,companyname,address,telephonenumber,email
                records.add(newRecord);
                DatabaseHandler.getInstance().insertRecord(newRecord);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return records;
    }
}
