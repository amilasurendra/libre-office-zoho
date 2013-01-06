/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.libreoffice.zoho.handler;

import java.io.File;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author amila
 */
public class DownloaderTest {

    static Downloader downloader;
    static List<Document> x;

    public DownloaderTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        downloader = new Downloader(null);

    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testGetDocumentList() {
        x = downloader.getDocumentList(Document.ALL, new WriterDocument());
        assertTrue(x != null);
        System.out.println(x.get(0).getName());
    }

    @Test
    public void testdownloadDocument(){
        WriterDocument doc = new WriterDocument();
        doc.setDocumentID("201957000000025001");
        doc.setName("Test Doc");
        downloader.downloadDocument(doc);
        File file = new File("/home/amila/"+doc.getName()+".odt");
        assertTrue(file.isFile() && file.length()>0);

    }

}