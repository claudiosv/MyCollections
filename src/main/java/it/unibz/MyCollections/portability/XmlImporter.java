package it.unibz.MyCollections.portability;


import it.unibz.MyCollections.Importer;
import it.unibz.MyCollections.AddressRecord;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Importer to import records from extensible markup language file.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class XmlImporter implements Importer {

    /**
     * Holds logger for this class.
     */
    private static final Logger logger = Logger.getLogger(XmlImporter.class.getName());

    /**
     * Imports extensible markup language file into records database.
     *
     * @param file Path to XML file to import from.
     * @author Claudio Spiess
     */
    @Override
    public List<AddressRecord> importRecords(Path file) {
        logger.entering(getClass().getName(), "importRecords");

        List<AddressRecord> records = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file.toFile());
            doc.getDocumentElement().normalize();
            NodeList recordNodes = doc.getElementsByTagName("Record");
            for (int temp = 0; temp < recordNodes.getLength(); temp++) {

                Node recordNode = recordNodes.item(temp);

                if (recordNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) recordNode;
                    AddressRecord newRecord = new AddressRecord();
                    newRecord.setDefaultImage();
                    newRecord.setFirstName(eElement.getElementsByTagName("firstName").item(0).getTextContent());
                    newRecord.setLastName(eElement.getElementsByTagName("lastName").item(0).getTextContent());
                    newRecord.setCompanyName(eElement.getElementsByTagName("companyName").item(0).getTextContent());
                    newRecord.setAddress(eElement.getElementsByTagName("address").item(0).getTextContent());
                    newRecord.setTelephoneNumber(eElement.getElementsByTagName("telephone").item(0).getTextContent());
                    newRecord.setEmailAddress(eElement.getElementsByTagName("email").item(0).getTextContent());
                    records.add(newRecord);
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error parsing XML", e);
        }

        logger.exiting(getClass().getName(), "importRecords");
        return records;
    }
}
