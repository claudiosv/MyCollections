package tests.it.unibz.MyCollections;

import main.it.unibz.MyCollections.Importer;
import main.it.unibz.MyCollections.portability.CsvImporter;
import main.it.unibz.MyCollections.portability.ImporterFactory;
import main.it.unibz.MyCollections.portability.XmlImporter;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Claudio on 06/06/2017.
 */
public class ImporterFactoryTest {
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
