package main.it.unibz.MyCollections;


import java.nio.file.Path;
import java.util.List;

/**
 * Interface to import records. This interface
 * serves to allow implementations to
 * import records from a file into
 * the database.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 * @see XmlImporter
 * @see CsvImporter
 */
public interface Importer {

    /**
     * Imports file into records database.
     *
     * @author Claudio Spiess
     * @param file Path to file to import from.
     * @return List of records imported.
     */
    public List<Record> importRecords(Path file);
}
