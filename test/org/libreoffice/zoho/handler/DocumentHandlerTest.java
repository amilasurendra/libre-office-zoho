/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.libreoffice.zoho.handler;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.libreoffice.zoho.authentication.CredentialManager;
import static org.junit.Assert.*;

/**
 *
 * @author amila
 */
public class DocumentHandlerTest {

    static DocumentHandler handler;

    public DocumentHandlerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        handler = new DocumentHandler(null);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    //@Test
    public void testGetOpenedDocumentPath() {
    }

    //@Test
    public void testUploadDocument() {
    }

    //@Test
    public void testDownloadDocument() {
        handler.downloadDocument();
    }


    //@Test
    public void testDownloadNullUsername(){

        CredentialManager credentials = CredentialManager.getInstance();

        credentials.setUserName(null, true);
        
        handler.downloadDocument();

        
    }

}