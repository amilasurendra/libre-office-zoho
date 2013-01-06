
package org.libreoffice.zoho.handler;

import com.sun.star.beans.PropertyValue;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XFrame;
import com.sun.star.lang.XComponent;
import com.sun.star.uno.UnoRuntime;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.libreoffice.zoho.authentication.CredentialManager;
import org.libreoffice.zoho.config.Configuration;
import org.libreoffice.zoho.config.HttpClientFactory;
import org.libreoffice.zoho.config.Logger;

/**
 * @author amila
 */
public class Downloader extends Observable implements Observer {

    ResponseParser parser;
    CredentialManager credentials;
    List<Document> docList = null;
    XFrame frame;
    private DownloadListener downListener;

    public Downloader(XFrame frame) {
        this.frame = frame;
        this.parser = new ResponseParser();
        credentials = CredentialManager.getInstance();
        downListener = new DownloadListener();
    }

    /**
     * Returns the document list in XML format.
     *
    @param documentState  Integer that identifies state of document needed.
    Possible values are Document.SHARED, Document.PUBLIC, Document.ALL
     *
    @param targetDocument Target document object for document type identification.
     *
    @return String of list of documents in XML format.
     */
    public List<Document> getDocumentList(int documentState, Document targetDocument) {

        String responseXML = null;
        docList = new LinkedList<Document>();


        /*
         *  get client from factory. If proxy is enabled this will return a
         *  client with proxy settings setup
         */
        HttpClient httpclient = HttpClientFactory.getHttpClient();


        HttpPost httppost = new HttpPost(targetDocument.getDocumentListURL(documentState));

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("apikey", Configuration.API_KEY));
        nameValuePairs.add(new BasicNameValuePair("ticket", credentials.getTicket(targetDocument)));

        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();

            if (resEntity != null) {
                responseXML = EntityUtils.toString(resEntity);
            }

        } catch (Exception e) {
            Logger.log(e.getMessage());
        }

        if (responseXML == null) {
            return null;
        }

        docList = parser.parseListResponse(responseXML, targetDocument.getClass());
        return docList;
    }

    public Document getDocument(String docID) {

        Document tmp = null;
        if (docList == null) {
            // TODO - Fetch Individual Record
            System.err.println("Doc list is null");
            return tmp;
        }

        for (Document doc : docList) {
            if (docID.equals(doc.getDocumentID())) {
                tmp = doc;
            }
        }

        return tmp;
    }

    public void downloadDocument(Document targetDocument) {

        /*
         *  get client from factory. If proxy is enabled this will return a
         *  client with proxy settings setup
         */
        HttpClient httpclient = HttpClientFactory.getHttpClient();


        HttpPost httppost = new HttpPost(targetDocument.getDownloadURL() + targetDocument.getDocumentID());

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("apikey", Configuration.API_KEY));
        nameValuePairs.add(new BasicNameValuePair("ticket", credentials.getTicket(targetDocument)));

        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();

            //TO-DO change odt to dynamic
            if (targetDocument.getLocalPath() == null) {
                targetDocument.setLocalPath("file://" + Configuration.getInstance().getDocumentPath() + "/" + targetDocument.getName() + ".odt");
            }

            File test = new File(new URI(targetDocument.getLocalPath()));
            test.createNewFile();

            downListener.addObserver(this);
            FileOutputStream fos = new FileOutputWrapper(test, downListener);
            resEntity.writeTo(fos);

            fos.flush();
            fos.close();
            
            addToList(targetDocument);
            setChanged();
            notifyObservers(-1);
            
        } catch (Exception e) {
            Logger.log(e.getMessage());
        }
    }

    public void openDownloadedDoc(String path) {
        try {
            // get the remote office component context
            XComponentLoader xCompLoader = (XComponentLoader) UnoRuntime.queryInterface(com.sun.star.frame.XComponentLoader.class, frame);
            // Load the document, which will be displayed. More param info in apidoc
            XComponent xComp = xCompLoader.loadComponentFromURL(path, "_blank", 0, new PropertyValue[0]);
        } catch (com.sun.star.uno.Exception ex) {
            Logger.log(ex.getMessage());
        }

    }

    private void addToList(Document doc) {
        LocalStorage.getInstance().setCurrentDoc(doc);
        LocalStorage.getInstance().addToList(doc);
    }

    public void update(Observable o, Object arg) {
        long progress = downListener.getProgress();
        setChanged();
        notifyObservers((int) ( progress));
    }
}

class DownloadListener extends Observable implements FileOutputWrapper.ProgressListener {

    private long progress;

    public void transfered(long bytes) {
        this.progress = bytes;
        setChanged();
        notifyObservers();
    }

    public long getProgress() {
        return progress;
    }
}
