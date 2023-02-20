package tests.it.unibz.MyCollections;

import it.unibz.MyCollections.Importer;
import it.unibz.MyCollections.portability.CsvImporter;
import it.unibz.MyCollections.portability.ImporterFactory;
import it.unibz.MyCollections.portability.XmlImporter;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests whether the ImporterFactory works as expected.
 */
public class ImporterFactoryTest {
    /**
     * Tests whether the ImporterFactory works as expected.
     */
    @Test
    public void ImporterFactoryTest() {
        ImporterFactory factory = new ImporterFactory();
        Importer csvHandler = factory.getImporter("csv");
        assertTrue(csvHandler instanceof CsvImporter);

        Importer xmlHandler = factory.getImporter("xml");
        assertTrue(xmlHandler instanceof XmlImporter);
        Importer nullTest = factory.getImporter("blah");
        assertNull(nullTest);
    }
}
