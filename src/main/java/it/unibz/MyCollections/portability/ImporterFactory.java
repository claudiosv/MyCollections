package it.unibz.MyCollections.portability;

import it.unibz.MyCollections.Importer;

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
     * @return Importer instance of the specified importerType
     * @author Claudio Spiess
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

        if (importerType.equalsIgnoreCase("xml")) {
            return new XmlImporter();
        }

        return null;
    }
}