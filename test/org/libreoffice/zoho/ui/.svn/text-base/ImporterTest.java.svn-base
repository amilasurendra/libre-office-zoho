/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.libreoffice.zoho.ui;

import org.libreoffice.zoho.handler.Downloader;


/**
 *
 * @author amila
 */
public class ImporterTest {
  
    public static void main(String args[]){
        java.awt.EventQueue.invokeLater(new Runnable() {

        Downloader downloader = new Downloader(null);
        Importer importUI = new Importer(downloader);
        ImporterController imCon = new ImporterController(downloader, importUI);

            public void run() {

                importUI.setVisible(true);

            }
        });
    }

}