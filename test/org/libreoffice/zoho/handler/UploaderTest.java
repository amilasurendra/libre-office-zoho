/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.libreoffice.zoho.handler;

import java.net.URI;
import java.io.File;
import javax.swing.JFrame;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.libreoffice.zoho.ui.Progress;
import static org.junit.Assert.*;

/**
 *
 * @author amila
 */
public class UploaderTest {

    static Uploader uploader;

    public UploaderTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        uploader = new Uploader(null);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testUpload() throws Exception {
        //uploader.Upload(null);
        WriterDocument test = new WriterDocument();
        File file = new File("/home/amila/TestDoc13.odt");
        test.setLocalPath("file://" + file.getAbsolutePath());

        

        final Progress progress = new Progress(new JFrame(), false, "Uploading");

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                  progress.setVisible(true);
            }
        });

        uploader.addObserver(progress);
        Document result = uploader.Upload(test);
        System.out.println(result.getDocumentID());
    }

    //@Test
    public void testUpdate() {

        final WriterDocument test = new WriterDocument();
        test.setDocumentID("201957000000037009");
        File file = new File("/home/amila/TestDoc9.odt");
        System.out.println(file.getAbsolutePath());
        test.setLocalPath("file://" + file.getAbsolutePath());

         final Progress progress = new Progress(new JFrame(), true, "Updating");

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                progress.setVisible(true);
                
            }
        });

        uploader.addObserver(progress);
        uploader.update(test);

    }
}
