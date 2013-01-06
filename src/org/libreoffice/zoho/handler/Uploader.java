
package org.libreoffice.zoho.handler;

import com.sun.star.frame.XFrame;
import com.sun.star.frame.XModel;
import com.sun.star.uno.UnoRuntime;
import java.io.File;
import java.net.URI;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.*;
import org.apache.http.entity.mime.content.*;
import org.apache.http.util.EntityUtils;
import org.libreoffice.zoho.authentication.CredentialManager;
import org.libreoffice.zoho.config.Configuration;
import org.libreoffice.zoho.config.HttpClientFactory;
import org.libreoffice.zoho.config.Logger;

/**
 * @author Amila Surendra
 *
 * This class is responsible for uploading given document to Zoho servers.
 */
public class Uploader extends Observable implements Observer {

    private XFrame frame;
    ResponseParser parser = new ResponseParser();
    private long progress;
    ProgressListner listener;

    public Uploader(XFrame frame) {
        this.frame = frame;
    }

    public Document Upload(Document document) {

        CredentialManager credentials = CredentialManager.getInstance();
        String API_KEY = Configuration.API_KEY;

        /*
         *  get client from factory. If proxy is enabled this will return a
         *  client with proxy settings setup
         */
        HttpClient httpclient = HttpClientFactory.getHttpClient();
        HttpPost httppost = new HttpPost(document.getUploadURL());
        String responseXML = null;

        try {

            FileBody fileContent = new FileBody(new File(new URI(document.getLocalPath())));

            listener = new ProgressListner(fileContent.getContentLength());
            listener.addObserver(this);
            MultipartEntity reqEntity = new CountingMultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, listener);

            //Create HTTP_POST body
            reqEntity.addPart("apikey", new StringBody(API_KEY));
            reqEntity.addPart("ticket", new StringBody(credentials.getTicket(document)));
            reqEntity.addPart("content", fileContent);

            httppost.setEntity(reqEntity);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();

            if (resEntity != null) {
                responseXML = EntityUtils.toString(resEntity);
                Logger.log("PAGE :" + responseXML);
            }

        } catch (Exception e) {
            Logger.log("Error uploading document: "+e.getMessage());
        }

        document = parser.parseUploadResponse(responseXML, document.getClass());

        setChanged();
        notifyObservers(-1);
        fillUploadDoc(document);
        return document;
    }

    private Document fillUploadDoc(Document doc) {
        doc.setAuthor(CredentialManager.getInstance().getUserName());
        doc.setLastModifyTime(new Date().getTime());
        doc.setLocalPath(getOpenedDocumentPath());
        return doc;
    }

    public Document update(Document document) {

        CredentialManager credentials = CredentialManager.getInstance();
        String API_KEY = Configuration.API_KEY;

        /*
         *  get client from factory. If proxy is enabled this will return a
         *  client with proxy settings setup
         */
        HttpClient httpclient = HttpClientFactory.getHttpClient();
        HttpPost httppost = new HttpPost(document.getUploadURL());
        String responseXML = null;

        try {

            FileBody fileContent = new FileBody(new File(new URI(document.getLocalPath())));
            listener = new ProgressListner(fileContent.getContentLength());
            listener.addObserver(this);

            //Create HTTP_POST body
            MultipartEntity reqEntity = new CountingMultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, listener);
            reqEntity.addPart("documentId", new StringBody(document.getDocumentID()));
            reqEntity.addPart("apikey", new StringBody(API_KEY));
            reqEntity.addPart("ticket", new StringBody(credentials.getTicket(document)));
            reqEntity.addPart("content", fileContent);
            httppost.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();

            if (resEntity != null) {
                responseXML = EntityUtils.toString(resEntity);
            }

        } catch (Exception e) {
             Logger.log("Error updating document: "+e.getMessage());
        }

        document = parser.parseUploadResponse(responseXML, document.getClass());
        setChanged();
        notifyObservers(-1);
        fillUploadDoc(document);
        return document;
    }

    //Return the path of currently opened file
    public String getOpenedDocumentPath() {
        XModel document = (XModel) UnoRuntime.queryInterface(XModel.class, frame.getController().getModel());
        return document.getURL();
    }

    //notify observers of the upload progress
    public void update(Observable o, Object arg) {
        progress = listener.getProgress();
        System.out.println(listener.getProgress());
        setChanged();
        notifyObservers(progress);
    }

}

class ProgressListner extends Observable implements CountingMultipartEntity.ProgressListener {

    long progress;
    private long length;

    public ProgressListner(long fileLength) {
        length = fileLength;
    }

    public void transferred(long num) {
        this.progress = num;
        setChanged();
        notifyObservers();
    }

    public int getProgress() {
        return  (int) (progress * 100 / length);
    }
}
