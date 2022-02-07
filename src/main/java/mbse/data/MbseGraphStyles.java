package mbse.data;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class is responsible for finding and parsing an XML file
 */
public class MbseGraphStyles {

    private static final Logger log = Logger.getLogger(MbseGraphStyles.class.getName());

    private static final String DEFAULT_STYLES_FILE = "styles.xml";

    private Map<String, Map<String, String>> mapStyleHashtable = new HashMap<>();

    // private File file;

    /**
     * Constructor with MBSE default style parameters
     */
    public MbseGraphStyles() {
        String path = MbseGraphStyles.class.getResource("/" + DEFAULT_STYLES_FILE).getPath();

        loadAndReadFile(path);
    }

    /**
     * After the constructor, this function with return a dictionnary of styles
     * 
     * @return dictionnary of available styles extracted from XML file
     */
    public Map<String, Map<String, String>> getAvailableStyles() {
        return mapStyleHashtable;
    }

    /**
     * Load and read the content of a default file
     */
    private void loadAndReadFile(String path) {
        File file = new File(path);
        if (file.exists())
            parseXMLFile(file);
    }

    /**
     * Read XML file and create the dictionnary
     * 
     * @param file - path to existing file
     */
    private void parseXMLFile(File file) {
        String styleName = "";
        // creates an instance of factory that gives a document builder
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            // to be compliant, completely disable DOCTYPE declaration:
            dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(file);

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("style");
            // nodeList is not iterable, so we are using for loop
            for (int itr = 0; itr < nodeList.getLength(); itr++) {
                Element nNode = (Element) nodeList.item(itr);
                styleName = nNode.getAttribute("name"); // tom sawyer, saeml, etc....

                // a style is found shall define properties
                NodeList childNodes = nNode.getChildNodes();

                HashMap<String, String> stylesTable = new HashMap<>(); // will store each property and value
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node node = childNodes.item(j);
                    NamedNodeMap attributes = node.getAttributes();
                    if (attributes != null) {
                        Node child = attributes.item(0);
                        stylesTable.put(node.getNodeName(), child.getFirstChild().getTextContent());
                    }
                }
                mapStyleHashtable.put(styleName, stylesTable);
            }
        } catch (SAXException | IOException | ParserConfigurationException e) {
            log.log(Level.SEVERE, "Parsing xml file failed.", e);
        }
    }
}
