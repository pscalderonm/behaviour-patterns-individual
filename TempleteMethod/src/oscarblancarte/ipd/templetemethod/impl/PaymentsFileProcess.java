package oscarblancarte.ipd.templetemethod.impl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import oscarblancarte.ipd.templetemethod.util.OnMemoryDataBase;
import oscarblancarte.ipd.templetemethod.util.XmlDocumentFactory;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

public class PaymentsFileProcess extends AbstractFileProcessTemplete {

    private final XmlLogger xmlLogger = new XmlLogger();

    public PaymentsFileProcess(File file, String logPath, String movePath) throws Exception {
        super(file, logPath, movePath);
    }

    @Override
    protected void validateName() throws Exception {
        String filename = file.getName();
        if(!filename.endsWith(".xml")){
            throw new Exception("Invalid file name"+
                    ", must end with .xml");
        }

        XmlDocumentFactory.getDefaultReadableXmlDocument(file);
    }

    @Override
    protected void processFile() throws Exception {
        Document doc = XmlDocumentFactory.getDefaultReadableXmlDocument(file);
        doc.getDocumentElement().normalize();

        NodeList list = doc.getElementsByTagName("payment");
        for(int idx=0; idx< list.getLength(); idx++) {
            Node node = list.item(idx);
            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }

            Element element = (Element) node;
            String uid = element.getElementsByTagName("uid").item(0).getTextContent();
            String customerId = element.getElementsByTagName("customer").item(0).getChildNodes().item(1).getTextContent();
            BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(element.getElementsByTagName("amount").item(0).getTextContent()));
            String date = element.getElementsByTagName("transactionDate").item(0).getTextContent();
            boolean exists = OnMemoryDataBase.customerExist(Integer.parseInt(customerId));

            if(!exists){
                xmlLogger.writeLog(uid, customerId, date, "Customer not exists");
            } else if(amount.compareTo(new BigDecimal(1000)) >= 0){
                xmlLogger.writeLog(uid, customerId, date, "The amount exceeds the maximum");
            }else{
                xmlLogger.writeLog(uid, customerId, date, "Successfully applied");
            }
        }
    }



    @Override
    protected void createLog() throws Exception {
        FileOutputStream out = null;
        try{
            File outFile =  new File(logPath+"/"+file.getName());
            if(!outFile.exists()){
                outFile.createNewFile();
            }
            out = new FileOutputStream(outFile, false);
            xmlLogger.writeXML(out);
            out.flush();
        }finally {
            out.close();
        }
    }

    private static class XmlLogger {

        private final Document document;
        private final Element rootElement;

        public XmlLogger() throws Exception{
            document = XmlDocumentFactory.getDefaultWritableXmlDocument();
            rootElement = document.createElement("logs");
            document.appendChild(rootElement);
        }

        public void writeLog(String id, String customer, String date, String message){
            Element logElement = document.createElement("log");

            Element idElement = document.createElement("id");
            idElement.setTextContent(id);
            logElement.appendChild(idElement);

            Element customerElement = document.createElement("customer");
            customerElement.setTextContent(customer);
            logElement.appendChild(customerElement);

            Element dateElement = document.createElement("date");
            dateElement.setTextContent(date);
            logElement.appendChild(dateElement);

            Element messageElement = document.createElement("message");
            messageElement.setTextContent(message);
            logElement.appendChild(messageElement);

            rootElement.appendChild(logElement);
        }

        public void writeXML(OutputStream outputStream) {

            try {
                Transformer transformer =
                        TransformerFactory.newInstance().newTransformer();

                transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(outputStream);
                transformer.transform(source, result);
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }
    }
}
