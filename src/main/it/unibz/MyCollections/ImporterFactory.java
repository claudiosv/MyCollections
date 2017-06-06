package main.it.unibz.MyCollections;

/**
 * Factory to instantiate Importer(s).
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class ImporterFactory {

    /**
     * Instantiates an Importer depending on the type
     * specified by the caller.
     *
     * @param importerType Which type of handler e.g. csv
     * @author Claudio Spiess
     * @return Importer instance of the specified importerType
     * @see Importer
     * @see CsvImporter
     */
    public Importer getImporter(String importerType) {
        if (importerType == null) {
            return null;
        }

        if (importerType.equalsIgnoreCase("csv")) {
            return new CsvImporter();
        }

        return null;
    }
}