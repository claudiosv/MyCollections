package tests.it.unibz.MyCollections;

import main.it.unibz.MyCollections.*;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Claudio on 06/06/2017.
 */
public class ExporterFactoryTest {
    @Test
    public void ExporterFactoryTest() {
        ExporterFactory factory = new ExporterFactory();
        Exporter handler = factory.getExporter("csv");
        assertTrue(handler instanceof CsvExporter);
        Exporter nullTest = factory.getExporter("blah");
        assertNull(nullTest);
    }
}
