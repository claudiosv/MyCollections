package main.it.unibz.MyCollections;


import java.nio.file.Path;
import java.util.List;

/** Interface to import records.
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public interface Importer {

    /**
     * Imports file into records database.
     *
     * @author Claudio Spiess
     * @param file Path to file to import from.
     */
    public List<Record> importRecords(Path file);
}
