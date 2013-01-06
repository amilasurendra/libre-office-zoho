/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.libreoffice.zoho.handler;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import org.libreoffice.zoho.config.Logger;

/**
 * @author amila
 *
 * This class created a Document class according to given local URl of the file.
 */
public class DocumentFactory {

    public Document createUploadDoc(String filePath) {

        String extension = filePath.substring(filePath.lastIndexOf('.') + 1);
        Logger.log(extension);
        Document uploadDoc = null;

        if ("odt".equals(extension)) {
            uploadDoc = new WriterDocument();
        }else if("ods".equals(extension)){
            uploadDoc = new SpreadSheetDocument();
        }



        try {
            File file = new File(new URI(filePath));
            uploadDoc.setLocalPath(filePath);
            uploadDoc.setExtension(extension);
            uploadDoc.setName(file.getName());


        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger(DocumentFactory.class.getName()).log(Level.SEVERE, null, ex);
        }



        return uploadDoc;
    }
}
