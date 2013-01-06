/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.libreoffice.zoho.handler;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author amila
 *
 * This class is responsible for parsing and interpreting XML responses returned
 * by ZohoAPI
 */
public class ResponseParser {

    public List<Document> parseListResponse(String XML, Class<?> documentType) {
        List<Document> documentList = new LinkedList<Document>();

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

        try {

            //Using factory get an instance of document builder
            DocumentBuilder db = builderFactory.newDocumentBuilder();

            //parse using builder to get DOM representation of the XML String
            InputSource source = new InputSource(new StringReader(XML));
            org.w3c.dom.Document dom = db.parse(source);


            Node root = dom.getFirstChild().getFirstChild().getFirstChild();
            NodeList test = root.getChildNodes();

            for (int i = 0; i < test.getLength(); i++) {

                Element x1 = (Element) test.item(i);

                Document tmp = (Document) documentType.newInstance();

                tmp.setName(x1.getElementsByTagName("documentName").item(0).getTextContent());
                tmp.setDocumentID(x1.getElementsByTagName("documentId").item(0).getTextContent());
                tmp.setVersion(x1.getElementsByTagName("version").item(0).getTextContent());
                long lastModTime = Long.parseLong(x1.getElementsByTagName("lastModifiedTime").item(0).getTextContent());
                tmp.setLastModifyTime(lastModTime);
                tmp.setAuthor(x1.getElementsByTagName("authorName").item(0).getTextContent());


                documentList.add(tmp);
            }

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }


        return documentList;
    }

    public Document parseUploadResponse(String XML, Class<?> documentType) {
        
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        Document document = null;
        
        try {

            document = (Document) documentType.newInstance();


            //Using factory get an instance of document builder
            DocumentBuilder db = builderFactory.newDocumentBuilder();

            //parse using builder to get DOM representation of the XML String
            InputSource source = new InputSource(new StringReader(XML));
            org.w3c.dom.Document dom = db.parse(source);


            Node root = dom.getFirstChild();
            NodeList test = root.getChildNodes();

            Element x1 = (Element) test.item(0);

            document.setName(x1.getElementsByTagName("documentName").item(0).getTextContent());
            document.setDocumentID(x1.getElementsByTagName("documentId").item(0).getTextContent());
            document.setVersion(x1.getElementsByTagName("documentVersion").item(0).getTextContent());



        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }


        return document;
    }
}
