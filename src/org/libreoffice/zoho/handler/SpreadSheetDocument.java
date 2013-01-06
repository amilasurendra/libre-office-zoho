

package org.libreoffice.zoho.handler;

/**
 *
 * @author Amila Surendra
 */
public class SpreadSheetDocument extends Document{

    @Override
    public String getUploadURL() {
        return "https://sheet.zoho.com/api/private/xml/uploadbook";
    }

    @Override
    public String getServicename() {
        return "ZohoSheet";
    }

    @Override
    public String getDocumentListURL(int type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getDownloadURL() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
