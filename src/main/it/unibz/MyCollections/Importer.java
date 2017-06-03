package main.it.unibz.MyCollections;


import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Created by claudio on 16/05/2017.
 */
public interface Importer {
    //create interface, create implementations for format, create factories
    //import: export to vcard, export to csv, export to xml
    //toFile
    //toString
    public ArrayList<Record> importRecords(Path file);
}
