/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.libreoffice.zoho.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JOptionPane;
import org.apache.http.HttpHost;
import org.libreoffice.zoho.handler.FileFormats;

/**
 * @author amila
 * 
 *  This Class handles the persistent configuration.
 *  This will save configuration to a configuration file and load content from the
 *  file when needed.
 */
public class Configuration {

    public static final String API_KEY = "d4b7acde67e7fab266c03c5b18eb402d";
    private Properties properties = null;
    private static Configuration instance = null;

    //Private constructor for singleton instance
    private Configuration() {
        properties = new Properties();
    }

    public static synchronized Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();

            File configFile = new File(instance.getWorkingPath()+"/configuration.properties");
            if(!configFile.exists()){
                try {
                    configFile.createNewFile();
                } catch (IOException ex) {
                    Logger.log("Cant create config file");
                }
            }
        }
        return instance;
    }

    public void setProxyEnabled(boolean enabled) {
        int proxyType = enabled ? 1 : 0;
        setProperty("proxy_type", Integer.toString(proxyType));
    }

    public boolean isProxyEnabled() {
        int proxy_type = Integer.parseInt(getProperty("proxy_type"));

        if (proxy_type == 0) {
            return false;
        } else {
            return true;
        }
    }

    public HttpHost getProxy() {
        int port = Integer.parseInt(getProperty("http_proxy_port"));
        HttpHost proxy = new HttpHost(getProperty("http_proxy"), port, "http");
        return proxy;
    }

    public void setProxy(String server, int port){
        setProperty("http_proxy", server);
        setProperty("http_proxy_port", Integer.toString(port));
    }

    //return path for local storage and configuration
    public String getWorkingPath(){
        File workingDirectory = new File(System.getProperty("user.home")+"/zoho");
        if(!workingDirectory.exists()){
            workingDirectory.mkdir();
        }
        return workingDirectory.getAbsolutePath();
    }

    //return path for saving documents
    public String getDocumentPath(){
        String path = getProperty("documentPath");
        if(path == null) path = getWorkingPath() + "/documents";
        return path;
    }

    public void setDocumentPath(String path){
        setProperty("documentPath", path);
    }

    public void setDefaultExtensions(int fileFormat, String extension ){
        switch (fileFormat){
            case FileFormats.WRITER_DOCUMENT:
                setProperty("writer_ext", extension);
                break;
        }
    }
    
    public String getDefaultExtension(int fileFormat ){

        String extension = null;
        
        switch (fileFormat){
            case FileFormats.WRITER_DOCUMENT:
                extension = getProperty("writer_ext");
                break;
        }

        return extension;
    }


    private String getProperty(String key) {

        String value = null;

        try {
            FileInputStream fis = new FileInputStream(new File(getWorkingPath()+"/configuration.properties"));
            properties.load(fis);
            value = properties.getProperty(key);
            fis.close();
        } catch (Exception e) {
            // catch Configuration Exception right here
            Logger.log("configuration.properties file could not be found");
            JOptionPane.showMessageDialog(null, "Error loding config file : configuration.properties ", "Error", JOptionPane.ERROR_MESSAGE);

        }

        return value;
    }

    private  void setProperty(String key, String value) {
        try {

            properties.load(new FileInputStream(new File(getWorkingPath()+"/configuration.properties")));
            FileOutputStream fos = new FileOutputStream(new File(getWorkingPath()+"/configuration.properties"));
            properties.setProperty(key, value);
            properties.store(fos, "Configuration File");
            fos.close();

        } catch (Exception ex) {
            System.out.println("Error: Cannot Write to Config file");
        }

    }

    public String getLookAndFeel(){
        //return "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
        //return "javax.swing.plaf.nimbus.NimbusLookAndFeel";
        return "javax.swing.plaf.metal.MetalLookAndFeel";
    }
       
}
