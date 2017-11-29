package main.it.unibz.MyCollections.portability;

import main.it.unibz.MyCollections.Record;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.fail;


/**
 * Exporter to export records to extensible markup language file.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class XmlExporter implements Exporter {

    /**
     * Holds the logger for this class.
     */
    private static final Logger logger = Logger.getLogger(XmlExporter.class.getName());


    /**
     * Exports list of records to a file in extensible markup language.
     * Does not include photo.
     *
     * @param records  List of records to export.
     * @param filePath Path where file will be saved.
     * @author Claudio Spiess
     */
    @Override
    public void exportRecords(List<Record> records, Path filePath) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Records");
            doc.appendChild(rootElement);

            for (Record record : records) {
                Element recordElement = doc.createElement("Record");

                Element firstname = doc.createElement("firstName");
                firstname.appendChild(doc.createTextNode(record.getFirstName()));
                recordElement.appendChild(firstname);

                Element lastname = doc.createElement("lastName");
                lastname.appendChild(doc.createTextNode(record.getLastName()));
                recordElement.appendChild(lastname);

                Element companyname = doc.createElement("companyName");
                companyname.appendChild(doc.createTextNode(record.getCompanyName()));
                recordElement.appendChild(companyname);

                Element address = doc.createElement("address");
                address.appendChild(doc.createTextNode(record.getAddress()));
                recordElement.appendChild(address);

                Element telephoneNumberElement = doc.createElement("telephone");
                telephoneNumberElement.appendChild(doc.createTextNode(record.getTelephoneNumber()));
                recordElement.appendChild(telephoneNumberElement);

                Element emailElement = doc.createElement("email");
                emailElement.appendChild(doc.createTextNode(record.getEmailAddress()));
                recordElement.appendChild(emailElement);

                rootElement.appendChild(recordElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(filePath.toFile());
            transformer.transform(source, result);
        } catch (ParserConfigurationException e) {
            logger.log(Level.SEVERE, "Error configuring XML", e);
        } catch (TransformerException e) {
            logger.log(Level.SEVERE, "Error saving XML", e);
        }
    }
}
