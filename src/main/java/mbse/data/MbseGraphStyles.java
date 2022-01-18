package mbse.data;

import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.mxgraph.util.mxUtils;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.mxStylesheet;

public class MbseGraphStyles {
    
    private static final Logger log = Logger.getLogger(MbseGraphStyles.class.getName());

    private final static String STYLES_PATH = "/styles.xml"; 

    private mxStylesheet availableStyles;

    private String filename;

    private String fileContent;

    public MbseGraphStyles() {
    }

    /**
     * 
     */
    public void parseStyleFile() {
		
		Document doc;
			doc = mxXmlUtils.parseXml(fileContent);
			
			Element styles = (Element) doc.getDocumentElement();
			NodeList list = styles.getElementsByTagName("style");

			StyleMap MBSEstyles = new StyleMap();
			for (int i = 0; i < list.getLength(); i++) {
				Element nNode = (Element) list.item(i);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
				nNode.getAttributes();
				System.out.println(nNode.getAttribute("name"));
				MBSEstyles.addStyle(nNode.getAttribute("name"), nNode);	
			}
	}

    public Hashtable<String, Hashtable<String, Object>> getAvailableStyles() {
        return null;
    }

    /**
     * 
     */
    public void loadStyleFile() {
        filename = getClass().getResource(STYLES_PATH).getPath();
        try {
            fileContent = mxUtils.readFile(filename);
        } catch (IOException e) {
            log.log(Level.SEVERE, "File not found.", e);
        }
    }

    public String getContent() {
        return fileContent;
    }
}
