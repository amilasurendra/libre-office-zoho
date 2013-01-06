/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.libreoffice.zoho.handler;

import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author amila
 */
public class LocalStorageTest {

    private static LocalStorage storage;

    public LocalStorageTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        storage = LocalStorage.getInstance();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    //@Test
    public void testAddToList() {
        WriterDocument x = new WriterDocument();
        x.setDocumentID("3");
        storage.addToList(x);
    }

    @Test
    public void testGetList() {
        List<Document> x = storage.getList();

        for (Document document : x) {
            System.out.println(document.getLocalPath());
            System.out.println(document.getDocumentID());
        }
    }

    //@Test
    public void testRemoveFromList(){
        storage.removeFromList("3");
        System.out.println("Removed 3");
        testGetList();
    }

    @Test
    public void testGetCurrentDoc() {
        
        

    }
}