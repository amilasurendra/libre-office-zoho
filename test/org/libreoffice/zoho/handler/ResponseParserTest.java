/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.libreoffice.zoho.handler;

import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author amila
 */
public class ResponseParserTest {

    private static ResponseParser parser;

    public ResponseParserTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        parser = new ResponseParser();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testParseListResponse() {
        String xml = "<response><result><documents><document><documentId><![CDATA[201957000000021002]]></documentId><documentName><![CDATA[Test doc 1]]></documentName></document><document><documentId><![CDATA[201957000000021002]]></documentId><documentName><![CDATA[Test doc 2]]></documentName></document></documents></result></response>";
        List<Document> result =  parser.parseListResponse(xml, Document.class);
        assertTrue(result.get(0).getName().equals("Test doc 1"));
    }

}