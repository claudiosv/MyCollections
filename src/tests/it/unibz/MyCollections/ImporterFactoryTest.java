package tests.it.unibz.MyCollections;

import main.it.unibz.MyCollections.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Claudio on 06/06/2017.
 */
public class ImporterFactoryTest {
    @Test
    public void ImporterFactoryTest() {
        ImporterFactory factory = new ImporterFactory();
        Importer handler = factory.getImporter("csv");
        assertTrue(handler instanceof CsvImporter);
        Importer nullTest = factory.getImporter("blah");
        assertNull(nullTest);
    }
}
