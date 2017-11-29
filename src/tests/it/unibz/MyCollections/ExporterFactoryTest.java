package tests.it.unibz.MyCollections;

import main.it.unibz.MyCollections.portability.CsvExporter;
import main.it.unibz.MyCollections.portability.Exporter;
import main.it.unibz.MyCollections.portability.ExporterFactory;
import main.it.unibz.MyCollections.portability.XmlExporter;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests whether the ExporterFactory works.
 */
public class ExporterFactoryTest {

    /**
     * Tests whether the ExporterFactory produces the right objects.
     */
    @Test
    public void ExporterFactoryTest() {
        ExporterFactory factory = new ExporterFactory();
        Exporter csvHandler = factory.getExporter("csv");
        assertTrue(csvHandler instanceof CsvExporter);
        Exporter xmlHandler = factory.getExporter("xml");
        assertTrue(xmlHandler instanceof XmlExporter);
        Exporter nullTest = factory.getExporter("blah");
        assertNull(nullTest);
    }
}
