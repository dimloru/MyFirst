package com.javarush.task.task33.task3309;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
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
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(obj, stringWriter);
            str = stringWriter.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        if (str == null) return "";                                        // null pointer check

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new ByteArrayInputStream(str.getBytes()));
        doc.normalize(); //??

        NodeList nodes = doc.getElementsByTagName(tagName);

        for (int i = 0; i < nodes.getLength(); i++) {
            Node commentNode = doc.createComment(comment);
            nodes.item(i).getParentNode().insertBefore(commentNode, nodes.item(i));
        }

        addCdata(doc);

        DOMSource domSource = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult streamResult = new StreamResult(writer);

        TransformerFactory tf = TransformerFactory.newInstance();
        tf.setAttribute("indent-number", 4);                    //отступы при форматировании xml
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");      // включить отступы и переносы
        transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
        transformer.transform(domSource, streamResult);

        return writer.toString();
    }

    private static void addCdata (Node start) {
        if (start.getNodeType() == Node.TEXT_NODE &&
                start.getTextContent().matches(".*[<>\"'&].*")) {
            String content = start.getTextContent();
            Node cdataNode = start.getOwnerDocument().createCDATASection(content);
            Node parent = start.getParentNode();
            start.setNodeValue("");
            parent.insertBefore(cdataNode, start);
        }

        for (Node child = start.getFirstChild();
             child != null;
             child = child.getNextSibling()) {
             addCdata(child);
        }
    }

    public static void main(String[] args) throws Exception {
        Cat cat = new Cat("Murzik", 13);
        System.out.println(toXmlWithComment(cat, "name", "<!-- comment -->"));

        System.out.println(Solution.toXmlWithComment(new First(), "second", "it's a comment"));
    }
}
