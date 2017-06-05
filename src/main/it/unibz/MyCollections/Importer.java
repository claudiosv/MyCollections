package main.it.unibz.MyCollections;


import java.nio.file.Path;
import java.util.List;

/** Interface to import records.
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public interface Importer {
    public List<Record> importRecords(Path file);
}
