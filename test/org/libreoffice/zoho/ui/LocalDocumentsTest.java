/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.libreoffice.zoho.ui;

import com.sun.star.frame.XFrame;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.uno.XComponentContext;
import org.libreoffice.zoho.handler.Downloader;

/**
 *
 * @author amila
 */
public class LocalDocumentsTest {

    static XComponentContext xContext;

    public static void main(String args[]) {

        try {

           // xContext = com.sun.star.comp.helper.Bootstrap.bootstrap();

            java.awt.EventQueue.invokeLater(new Runnable() {

                //XMultiComponentFactory xMCF = xContext.getServiceManager();
                //Object oDesktop = xMCF.createInstanceWithContext("com.sun.star.frame.XFrame", xContext);
                //Downloader downloader = new Downloader((XFrame) oDesktop);
                LocalDocuments local = new LocalDocuments(null);

                public void run() {

                    local.setVisible(true);

                }
            });

        } catch (Exception e) {
        }
    }
}
