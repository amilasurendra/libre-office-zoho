package org.libreoffice.zoho.handler;

/**
 * @author amila
 *
 * Specialized class for Writer Documents.
 * Provides API URLs and specialized information fields for writer documents.
 */
public class WriterDocument extends Document {

    public WriterDocument(){
        //Should remove at the End
        setExtension("odt");
    }

    @Override
    public String getUploadURL() {
        return "https://exportwriter.zoho.com/api/private/xml/uploadDocument";
    }

    @Override
    public String getServicename() {
        return "ZohoWriter";
    }

    @Override
    public String getDocumentListURL(int type) {

        String URL = null;
        
        switch(type){
            case Document.ALL:
                URL = "https://exportwriter.zoho.com/api/private/xml/documents?";
                break;
            case Document.SHARED:
                URL = "https://exportwriter.zoho.com/api/private/xml/incomingdocuments?";
                break;
        }

        return URL;
    }

    @Override
    public String getDownloadURL() {
        return "https://exportwriter.zoho.com/api/private/"+ getExtension() +"/download/";
    }

}
