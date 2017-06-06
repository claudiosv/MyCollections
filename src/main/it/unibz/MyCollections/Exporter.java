package main.it.unibz.MyCollections;

import java.nio.file.Path;
import java.util.List;

/**
 * Interface to export records. Implementations of
 * this interface accept a list of records
 * and a path to a file.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 * @see XmlExporter
 * @see CsvExporter
 */
public interface Exporter {

    /**
     * Exports list of records to a file.
     *
     * @param records  List of records to export.
     * @param filePath Path where file will be saved.
     * @author Claudio Spiess
     */
    public void exportRecords(List<Record> records, Path filePath);
}
