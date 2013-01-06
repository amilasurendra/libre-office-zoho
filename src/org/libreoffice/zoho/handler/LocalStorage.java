package org.libreoffice.zoho.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.libreoffice.zoho.config.Configuration;
import org.libreoffice.zoho.config.Logger;

/**
 * @author Amila Surendra
 *
 * Stores the details of currently downloaded documents
 * class is singleton
 */
public class LocalStorage {

    private String filePath = null;
    List<Document> localList = null;
    private Document currentDoc = null;
    private static LocalStorage instance = null;

    private LocalStorage() {
        filePath = Configuration.getInstance().getWorkingPath() + "/storage.dat";
        localList = new ArrayList<Document>();
        restoreFromDisk();
    }

    public synchronized static LocalStorage getInstance() {
        if (instance == null) {
            instance = new LocalStorage();
        }
        return instance;
    }

    public Document getCurrentDoc() {

        if (currentDoc == null) {
            String currentDocPath = DocumentHandler.utilities.getOpenedDocumentPath();
            String docId = getDocID(currentDocPath);
            Logger.log(docId);

            if (docId != null) {
                currentDoc = LocalStorage.getInstance().getDocument(docId);
                Logger.log("Newly Opened Doc ID :" + currentDoc.getDocumentID());
            }
        }

        return currentDoc;
    }

    public void setCurrentDoc(Document currentDoc) {
        this.currentDoc = currentDoc;
    }

    public void addToList(Document doc) {

        if (contains(doc.getDocumentID())) {
            removeFromList(doc.getDocumentID());
        }
        localList.add(doc);
        storeToDisk();
    }

    public void removeFromList(String documentID) {
        int index = -1;
        for (Document document : localList) {
            if (documentID.equals(document.getDocumentID())) {
                index = localList.indexOf(document);
                break;
            }
        }
        if (index != -1) {
            localList.remove(index);
            storeToDisk();
        }
    }

    public List<Document> getList() {
        return localList;
    }

    public boolean contains(String DocID) {
        for (Document document : localList) {
            if (DocID.equals(document.getDocumentID())) {
                return true;
            }
        }
        return false;
    }

    public Document getDocument(String docID) {
        Document tmp = null;
        if (localList == null) {
            // TODO - Fetch Individual Record
            System.err.println("Doc list is null");
            return tmp;
        }
        for (Document doc : localList) {
            if (docID.equals(doc.getDocumentID())) {
                tmp = doc;
            }
        }
        return tmp;
    }

    private String getDocID(String filePath) {
        String docId = null;
        for (Document doc : localList) {
            if (filePath.equals(doc.getLocalPath())) {
                docId = doc.getDocumentID();
                return docId;
            }
        }
        return docId;
    }

    private void storeToDisk() {

        File file = new File(filePath);

        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(localList);
            oos.close();
            fos.close();

        } catch (FileNotFoundException fnfe) {
        } catch (IOException ioe) {
            Logger.log("Cannot create storage file on : " + file.getAbsolutePath() + " Please check permissions");
        }
    }

    private void restoreFromDisk() {
        File file = new File(filePath);

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            localList = (List<Document>) ois.readObject();

        } catch (FileNotFoundException fnfe) {
            Logger.log("Cannot read storage file on : " + file.getAbsolutePath());
            localList = new ArrayList<Document>();
        } catch (IOException ioe) {
            Logger.log("Cannot create storage file on : " + file.getAbsolutePath() + " Please check permission");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Class not found");
            cnfe.printStackTrace();
        }
    }
}
