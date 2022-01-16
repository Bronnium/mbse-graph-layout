package mbse.data;

import java.io.File;
import java.util.Hashtable;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mxgraph.util.mxConstants;

/**
 * StyleParser is responsible of parsing an XML file
 * @author Lucas
 *
 */
public class StyleMap {
	
	private Hashtable<String, String> stylesTable = new Hashtable<String, String>();
	
	public String getStyle(String nameStyle) {
		return stylesTable.get(nameStyle);
	}
	public StyleMap() {

	}
	public void addStyle(String attribute, Element nNode) {
		NodeList childNodes = nNode.getChildNodes();
		String appliedStyle = "";
		for (int i = 0; i < childNodes.getLength(); i++) {
		    Node node = childNodes.item(i);
		    NamedNodeMap attributes = node.getAttributes();
		    if (attributes != null) {
		    Node child = attributes.item(0);
		    //child.
		    appliedStyle += node.getNodeName()+"="+child.getFirstChild().getTextContent()+";";
		    String res = node.getNodeValue();
		    //System.out.println(attributes);
		    }
		}
		stylesTable.put(attribute, appliedStyle);
	}
	
	/*
	 * 
		
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style.put(mxConstants.STYLE_OPACITY, 50);
        
        style.put(mxConstants.STYLE_ROUNDED, true);
        style.put(mxConstants.STYLE_IMAGE, "file:/C:\\dev\\bell.png");
        style.put(mxConstants.STYLE_IMAGE_HEIGHT, 100); // padding de l'image
        style.put(mxConstants.STYLE_IMAGE_WIDTH, 100); // padding de l'image
        style.put(mxConstants.STYLE_FOLDABLE, 1); // padding de l'image
        //style.put(mxConstants.DEFAULT_IMAGESIZE, 50);
        style.put(mxConstants.STYLE_INDICATOR_IMAGE, "file:/C:\\dev\\bell.png");
        style.put(mxConstants.STYLE_IMAGE, "https://clipartmag.com/images/vintage-airplane-silhouette-8.png");
        style.put(mxConstants.STYLE_IMAGE_VERTICAL_ALIGN, mxConstants.ALIGN_TOP);
        style.put(mxConstants.STYLE_IMAGE_ALIGN, mxConstants.ALIGN_CENTER);
        //style.put(mxConstants., mxConstants.ALIGN_CENTER);
        //style.put(mxConstants.STYLE_SPACING_TOP, 5);
        style.put(mxConstants.STYLE_FILLCOLOR, "#e0f4ef");
        style.put(mxConstants.STYLE_STROKECOLOR, "#69cdb5");
        style.put(mxConstants.STYLE_STROKEWIDTH, 3);
        style.put(mxConstants.STYLE_FONTFAMILY, "Verdana");
        style.put(mxConstants.STYLE_SPACING_TOP, 25);
        style.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);
        //style.put(mxConstants.STYLE_FONTCOLOR, "#FF0000");
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_LABEL);
        style.put(mxConstants.STYLE_INDICATOR_SHAPE, mxConstants.SHAPE_LABEL);
        
	 */

}
