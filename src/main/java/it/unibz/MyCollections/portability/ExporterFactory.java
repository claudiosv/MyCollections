package it.unibz.MyCollections.portability;

/**
 * Factory to instantiate exporter(s).
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class ExporterFactory {

    /**
     * Instantiates an exporter depending on the type
     * specified by the caller.
     *
     * @param exporterType Which type of handler e.g. csv
     * @return exporter instance of the specified exporterType
     * @author Claudio Spiess
     * @see Exporter
     * @see CsvExporter
     */
    public Exporter getExporter(String exporterType) {
        if (exporterType == null) {
            return null;
        }

        if (exporterType.equalsIgnoreCase("csv")) {
            return new CsvExporter();
        }

        if (exporterType.equalsIgnoreCase("xml")) {
            return new XmlExporter();
        }

        return null;
    }
}
