
package org.libreoffice.zoho.authentication;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.libreoffice.zoho.handler.Document;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.libreoffice.zoho.config.HttpClientFactory;
import org.libreoffice.zoho.config.Logger;
import org.libreoffice.zoho.handler.SpreadSheetDocument;
import org.libreoffice.zoho.handler.WriterDocument;

/**
 *
 * @author amila
 *
 * Manages Authentication and ticket generation.
 * The class is singleton
 */
public class CredentialManager {

    private static CredentialManager instance = null;
    private Wallet wallet;
    private String tmpUsername = null;
    private String tmpPassword = null;

    private CredentialManager() {
        wallet = WalletFactory.getWallet();
        tmpUsername = wallet.getUserName();
        tmpPassword = wallet.getPassword();
    }

    public static synchronized CredentialManager getInstance() {
        if (instance == null) {
            instance = new CredentialManager();
        }
        return instance;
    }

    //Return current ticket or genearte new ticket if required - Using specified Document type
    public String getTicket(Document document) {
        String ticket = null;

        if(document instanceof WriterDocument){
            ticket = wallet.getTicket(Wallet.ticketType.Writer);
        }

        if(ticket == null) {
            ticket = getNewTicket(document);
            storeTicket(document, ticket);
        }

        return ticket;
    }

    //Should get new ticket once existing ticket expired or for a new user
    private String getNewTicket(Document document) {

        String ticket = null;

        try {
            String loginURL = "https://accounts.zoho.com/login";

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("LOGIN_ID", getUserName()));
            nameValuePairs.add(new BasicNameValuePair("PASSWORD", getPassword()));
            nameValuePairs.add(new BasicNameValuePair("FROM_AGENT", "true"));
            nameValuePairs.add(new BasicNameValuePair("servicename", document.getServicename()));

            String result = connectToZoho(loginURL, new UrlEncodedFormEntity(nameValuePairs));

            BufferedReader reader = new BufferedReader(new StringReader(result));

            String line;
            while ((line = reader.readLine()) != null) {

                if (line.startsWith("TICKET")) {
                    ticket = line.split("=")[1];
                }

            }

        } catch (Exception e) {
            Logger.log("Error:ticket Generation");
        }

        return ticket;


    }

    private void removeTicket(String ticket) {
        //TODO removeTicket
    }

    private String connectToZoho(String targetURL, HttpEntity content) {
        HttpPost post = new HttpPost(targetURL);
        post.setEntity(content);

        HttpClient httpclient = HttpClientFactory.getHttpClient();

        try {
            HttpResponse response = httpclient.execute(post);
            HttpEntity resEntity = response.getEntity();

            if (resEntity != null) {
                return EntityUtils.toString(resEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error connectToZoho");
        }

        return null;
    }

     public void setUserName(String username, boolean permenant){
        tmpUsername = username;
        if(permenant) wallet.setUserName(username);
        else wallet.setUserName(null);
    }

    public void setPassword(String password, boolean permenant){
        tmpPassword = password;
        if(permenant) wallet.setPassword(password);
        else wallet.setPassword(null);
    }

    //get username from encrypted storage according to the system.
    public String getUserName() {
        return tmpUsername;
    }

    //get username from encrypted storage according to the system.
    public String getPassword() {
        return tmpPassword;
    }

    //store each type of generated API tickets in secure storage
    private void storeTicket(Document documentType, String ticket){
        if(documentType instanceof WriterDocument){
            wallet.setTicket(Wallet.ticketType.Writer, ticket);
        }else if (documentType instanceof SpreadSheetDocument){
            wallet.setTicket(Wallet.ticketType.SpreadSheet, ticket);
        }
    }

    //sign out current user - clears all generated tickets and credentials from storage
    public void signOut(){
        tmpUsername = null;
        tmpPassword = null;
        wallet.setUserName(null);
        wallet.setPassword(null);
        wallet.setTicket(Wallet.ticketType.Writer, null);
        wallet.setTicket(Wallet.ticketType.Presentation, null);
        wallet.setTicket(Wallet.ticketType.SpreadSheet, null);
    }

    public boolean isUsernameSaved(){
        return wallet.getUserName() != null;
    }

    public boolean isPasswordSaved(){
        return wallet.getPassword() != null;
    }
}
