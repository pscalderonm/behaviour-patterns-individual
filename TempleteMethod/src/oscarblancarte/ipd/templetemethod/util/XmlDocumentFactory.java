package oscarblancarte.ipd.templetemethod.util;

import org.w3c.dom.Document;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XmlDocumentFactory {

    public static Document getDefaultWritableXmlDocument() throws Exception{
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        return docBuilder.newDocument();
    }

    public static Document getDefaultReadableXmlDocument(File file) throws Exception{
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        docFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        return docBuilder.parse(file);
    }
}
