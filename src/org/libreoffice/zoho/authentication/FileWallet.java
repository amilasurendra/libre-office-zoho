package org.libreoffice.zoho.authentication;

/**
 * @author Amila Surendra
 *
 * This class provides storage of credentials in a encrypted file
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.libreoffice.zoho.config.Configuration;
import org.libreoffice.zoho.config.Logger;

public class FileWallet implements Wallet {

    private static final String SECRET = "SBYUW&@H";
    private String credentionalsFileName;
    private Credentials credentials;
    private static FileWallet instance = null;

    public static synchronized FileWallet getInstance() {
        if (instance == null) {
            instance = new FileWallet();
        }
        return instance;
    }

    private FileWallet() {

        credentionalsFileName = Configuration.getInstance().getWorkingPath() + "/credentials.dat";
        File file = new File(credentionalsFileName);

        if (!file.exists()) {
            try {
                file.createNewFile();
                credentials = new Credentials();
            } catch (IOException ex) {
                Logger.log(ex.getMessage());
            }
        } else {
            readCreditionals();
        }
    }

    public String getPassword() {
        return xorString(credentials.password, SECRET);
    }

    public String getUserName() {
        return xorString(credentials.username, SECRET);
    }

    public String getTicket(ticketType type) {
        String ticket = null;

        switch (type) {
            case Writer:
                ticket = xorString(credentials.writer, SECRET);
                break;
            case Presentation:
                ticket = xorString(credentials.presentation, SECRET);
                break;
            case SpreadSheet:
                ticket = xorString(credentials.spreadsheet,SECRET);
                break;

        }

        return ticket;
    }

    private void readCreditionals() {

        File file = new File(credentionalsFileName);
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            credentials = (Credentials) ois.readObject();
        } catch (Exception e) {
            System.err.println("readCreditionals");
            Logger.log(e.getMessage());
        }
    }

    private void storeCreditionals() {

        File file = new File(credentionalsFileName);

        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(credentials);
            oos.close();
            fos.close();
        } catch (FileNotFoundException fnfe) {
        } catch (IOException ioe) {
            Logger.log("Cannot create credentials file on : " + file.getAbsolutePath() + " Please check permissions");
        }
    }

    private String xorString(String phrase, String secret) {
        if(phrase == null) return null;

        char[] keyChars = secret.toCharArray();
        char[] inputChars = phrase.toCharArray();
        for (int i = 0; i < inputChars.length; i++) {
            inputChars[i] ^= keyChars[i % keyChars.length];
        }
        return new String(inputChars);
    }

    public void setPassword(String password) {
        credentials.password = xorString(password, SECRET);
        storeCreditionals();
    }

    public void setUserName(String username) {
        credentials.username = xorString(username, SECRET);
        storeCreditionals();
    }

    public void setTicket(ticketType type, String ticket) {

        switch (type) {
            case Writer:
                credentials.writer = xorString(ticket, SECRET);
                break;
            case Presentation:
                credentials.presentation = xorString(ticket, SECRET);
                break;
            case SpreadSheet:
                credentials.spreadsheet = xorString(ticket, SECRET);
                break;

        }
        storeCreditionals();
    }
}

class Credentials implements Serializable {

    public String writer;
    public String presentation;
    public String spreadsheet;
    public String username;
    public String password;
}
