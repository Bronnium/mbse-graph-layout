package mbse.data;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
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

import com.mxgraph.util.mxUtils;
import com.mxgraph.util.mxXmlUtils;

/**
 * This class is responsible for finding and parsing an XML file
 */
public class MbseGraphStyles {

    private static final Logger log = Logger.getLogger(MbseGraphStyles.class.getName());

    private final static String STYLES_PATH = "/styles.xml";

    private String fileContent;

    private Hashtable<String, Hashtable<String, Object>> mapStyleHashtable = new Hashtable<String, Hashtable<String, Object>>();

    private File file;

    /**
     * Constructor with MBSE default style parameters
     */
    public MbseGraphStyles() {

        // loadAndReadFile();
        // if (!fileContent.isEmpty()) {
        // parseStyleFile();
        // }
    }

    public MbseGraphStyles(String string) {
        String path = "";
        if (string.startsWith("/")) {
            path = MbseGraphStyles.class.getResource(string).getPath();
        }

        loadAndReadFile(path);
    }

    private void importedCode() throws ParserConfigurationException, SAXException, IOException {
        String styleName = "";
        // an instance of factory that gives a document builder
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        // an instance of builder to parse the specified xml file
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        doc.getDocumentElement().normalize();
        // System.out.println("Root element: " +
        // doc.getDocumentElement().getNodeName());
        NodeList nodeList = doc.getElementsByTagName("style");
        // nodeList is not iterable, so we are using for loop
        for (int itr = 0; itr < nodeList.getLength(); itr++) {
            Element nNode = (Element) nodeList.item(itr);
            styleName = nNode.getAttribute("name"); // tom sawyer, saeml etc....
            System.out.println(styleName);

            Hashtable<String, Object> stylesTable = new Hashtable<String, Object>();

            NodeList childNodes = nNode.getChildNodes();

            for (int j = 0; j < childNodes.getLength(); j++) {
                Node node = childNodes.item(j);
                NamedNodeMap attributes = node.getAttributes();
                if (attributes != null) {
                    Node child = attributes.item(0);
                    System.out.println(child);
                    // child.
                    // appliedStyle +=
                    // node.getNodeName()+"="+child.getFirstChild().getTextContent()+";";
                    stylesTable.put(node.getNodeName(), child.getFirstChild().getTextContent());
                    // String res = node.getNodeValue();
                    // System.out.println(attributes);
                }
            }

            mapStyleHashtable.put(styleName, stylesTable);
        }
    }

    private void parseStyleFile() {
        String styleName = "";
        String appliedStyle = "";
        System.out.println(fileContent);

        Document doc = mxXmlUtils.parseXml(fileContent);

        doc.getDocumentElement().normalize();

        Element styles = (Element) doc.getDocumentElement();

        NodeList list = styles.getElementsByTagName("style");

        // StyleMap MBSEstyles = new StyleMap();
        for (int i = 0; i < list.getLength(); i++) {
            Element nNode = (Element) list.item(i);
            // System.out.println("\nCurrent Element :" + nNode.getNodeName());
            // nNode.getAttributes();
            styleName = nNode.getAttribute("name"); // tom sawyer, saeml etc....
            Hashtable<String, Object> stylesTable = new Hashtable<String, Object>();

            NodeList childNodes = nNode.getChildNodes();

            for (int j = 0; j < childNodes.getLength(); j++) {
                Node node = childNodes.item(i);
                NamedNodeMap attributes = node.getAttributes();
                if (attributes != null) {
                    Node child = attributes.item(0);
                    // child.
                    // appliedStyle +=
                    // node.getNodeName()+"="+child.getFirstChild().getTextContent()+";";
                    stylesTable.put(node.getNodeName(), child.getFirstChild().getTextContent());
                    // String res = node.getNodeValue();
                    // System.out.println(attributes);
                }
            }
            // stylesTable.put(attribute, appliedStyle);
            // MBSEstyles.addStyle(nNode.getAttribute("name"), nNode);

            mapStyleHashtable.put(styleName, stylesTable);
        }
    }

    public Hashtable<String, Hashtable<String, Object>> getAvailableStyles() {
        return mapStyleHashtable;
    }

    /**
     * Load and read the content of a file with default file
     * Update <code>fileContent</code> variable
     * 
     * @return String - content or EMPTY if file not readable
     */
    public String loadAndReadFile() {
        String filename = getClass().getResource(STYLES_PATH).getPath();
        try {
            fileContent = mxUtils.readFile(filename);
            return fileContent.replaceAll("\\x0a", "");
        } catch (IOException e) {
            log.log(Level.SEVERE, "File not found.", e);
            return fileContent = "";
        }
    }

    public String getContent() {
        return fileContent;
    }

    public boolean loadAndReadFile(String path) {
        file = new File(path);

        try {
            importedCode();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            log.log(Level.SEVERE, "File not found.", e);
        }

        return file.exists();
    }
}
