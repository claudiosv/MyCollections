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
 * @see XmlImporter
 * @see CsvImporter
 * @since 1.0
 */
public interface Importer {

    /**
     * Imports file into records database.
     *
     * @param file Path to file to import from.
     * @return List of records imported.
     * @author Claudio Spiess
     */
    List<Record> importRecords(Path file);
}
