package main.it.unibz.MyCollections.portability;


import main.it.unibz.MyCollections.Importer;
import main.it.unibz.MyCollections.Record;
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
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private static final Logger logger = Logger.getLogger(CsvImporter.class.getName());

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    @Override
    public List<Record> importRecords(Path file) {
        logger.entering(getClass().getName(), "importRecords");

        List<Record> records = new ArrayList<>();


        try {

            File fXmlFile = file.toFile();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Record");


            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {


                    Element eElement = (Element) nNode;
                    Record newRecord = new Record();
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
