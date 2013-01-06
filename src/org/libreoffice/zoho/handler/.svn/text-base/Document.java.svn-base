/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.libreoffice.zoho.handler;

import java.io.Serializable;
import java.util.Date;

/**
 * @author amila
 */
public abstract  class Document implements Serializable{

    public static final int ALL = 0;
    public static final int SHARED = 1;
    public static final int PUBLIC = 2;

    private String name;
    private String localPath;
    private String documentID;
    private String version;
    private String extension;
    private String author;
    private Date lastModifyTime;

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(long lastModifyTime) {
        this.lastModifyTime = new Date(lastModifyTime);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
   
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLocalPath(){
        return this.localPath;
    }

    public void setLocalPath(String path){
        this.localPath = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    //Should be implemented by specific doc
    public abstract String getUploadURL();

    //Should supply zoho authentication service name
    public abstract String getServicename();


    //Should supply document listing url for each listing type
    public abstract String getDocumentListURL(int type);


    public abstract String getDownloadURL();
}
