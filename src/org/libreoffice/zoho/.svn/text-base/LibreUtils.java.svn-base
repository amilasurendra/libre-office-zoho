/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.libreoffice.zoho;

import com.sun.star.frame.XFrame;
import com.sun.star.frame.XModel;
import com.sun.star.uno.UnoRuntime;

/**
 * @author amila
 *
 * Provides LibreOffice API calls for general tasks
 */
public class LibreUtils {
    private XFrame frame;

    public LibreUtils(XFrame frame){
        this.frame = frame;
    }

    //Return the path of currently opened file
    public String getOpenedDocumentPath() {
        XModel document = (XModel) UnoRuntime.queryInterface(XModel.class, frame.getController().getModel());
        return document.getURL();
    }
}
