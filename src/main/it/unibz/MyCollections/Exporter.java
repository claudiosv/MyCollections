package main.it.unibz.MyCollections;

import java.nio.file.Path;
import java.util.List;

/**
 * Created by claudio on 30/03/2017.
 */
public interface Exporter {
    //create interface, create implementations for format, create factories
    //export to vcard, export to csv, export to xml
    public void exportRecords(List<Record> records, Path filePath);
}
