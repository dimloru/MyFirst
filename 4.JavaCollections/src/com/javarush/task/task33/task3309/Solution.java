package com.javarush.task.task33.task3309;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import org.w3c.dom.*;

/*
Комментарий внутри xml
*/
public class Solution {
    public static String toXmlWithComment(Object obj, String tagName, String comment) throws Exception { //throws added
        String str = null;
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(obj, stringWriter);
            str = stringWriter.toString();


        } catch (JAXBException e) {
            e.printStackTrace();
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new ByteArrayInputStream(str.getBytes())); // null pointer

        Element root = doc.getDocumentElement();
//        addCdata(root);
        NodeList nodes = root.getElementsByTagName(tagName);

        for (int i = 0; i < nodes.getLength(); i++) {
            Node commentNode = doc.createTextNode("");
            commentNode.setNodeValue(comment);
            nodes.item(i).getParentNode().insertBefore(commentNode, nodes.item(i)); // should work
        }

        DOMSource domSource = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult streamResult = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(domSource, streamResult);

        return writer.toString();
    }

    private static void addCdata (Node start) {
        // need symbol check
        if (start.getNodeType() == Node.TEXT_NODE) {
            String content = start.getTextContent();
            start.setNodeValue("<![CDATA[" + content + "]]>");
        }
        for (Node child = start.getFirstChild();
             child != null;
             child = child.getNextSibling()) {
             addCdata(child);
        }
    }

    public static void main(String[] args) throws Exception {
        Cat cat = new Cat("<Murzik>", 13);
        System.out.println(toXmlWithComment(cat, "name", "<!-- comment -->"));

    }
}
