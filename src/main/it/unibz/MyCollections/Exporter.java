package main.it.unibz.MyCollections;

import java.nio.file.Path;
import java.util.List;

/** Interface to export records.
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public interface Exporter {
    //create interface, create implementations for format, create factories
    //export to vcard, export to csv, export to xml
    public void exportRecords(List<Record> records, Path filePath);
}
