package main.it.unibz.MyCollections;


import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by claudio on 16/05/2017.
 */
public interface Importer {
    public List<Record> importRecords(Path file);
}
