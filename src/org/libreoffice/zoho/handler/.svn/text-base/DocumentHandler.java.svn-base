package org.libreoffice.zoho.handler;

import com.sun.star.frame.XFrame;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.libreoffice.zoho.LibreUtils;
import org.libreoffice.zoho.authentication.CredentialManager;
import org.libreoffice.zoho.config.Configuration;
import org.libreoffice.zoho.ui.ConfigWindow;
import org.libreoffice.zoho.ui.Exporter;
import org.libreoffice.zoho.ui.Importer;
import org.libreoffice.zoho.ui.ImporterController;
import org.libreoffice.zoho.ui.LocalDocuments;
import org.libreoffice.zoho.ui.LoginPanel;
import org.libreoffice.zoho.ui.Progress;

/**
 *
 * @author amila
 *
 * This is the main Facade for document handling.
 * LibreOffice interacts directly with this interface
 */
public class DocumentHandler {

    private Downloader downloader;
    private XFrame frame;
    public static LibreUtils utilities;

    public DocumentHandler(XFrame frame) {
        downloader = new Downloader(frame);
        this.frame = frame;
        utilities = new LibreUtils(frame);
    }

    public void uploadDocument() {

        if (!checkLogin()) {
            return;
        }

        final Uploader uploader = new Uploader(frame);

        String path = uploader.getOpenedDocumentPath();
        if (path.equals("")) {
            JOptionPane.showMessageDialog(null, "Save the document first", "Exporting Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        //Check for the local copy
        if (LocalStorage.getInstance().getCurrentDoc() != null) {
            if (path.equals(LocalStorage.getInstance().getCurrentDoc().getLocalPath())) {

                //Show progress window
                final Progress progress = new Progress(new JFrame(), false, "Updating File...");
                java.awt.EventQueue.invokeLater(new Runnable() {

                    public void run() {
                        progress.setVisible(true);
                    }
                });
                uploader.addObserver(progress);

                Thread updateThread = new Thread() {

                    @Override
                    public void run() {
                        Document uploadedDoc = uploader.update(LocalStorage.getInstance().getCurrentDoc());
                        LocalStorage.getInstance().setCurrentDoc(uploadedDoc);
                        LocalStorage.getInstance().addToList(uploadedDoc);
                    }
                };
                updateThread.start();
                return;
            }
        }
        //if a new file show export window
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                Exporter exportUI = new Exporter(uploader);
                exportUI.setVisible(true);
            }
        });


    }

    public void downloadDocument() {

        if (!checkLogin()) {
            return;
        }

        java.awt.EventQueue.invokeLater(new Runnable() {

            Importer importUI = new Importer(downloader);
            ImporterController imCon = new ImporterController(downloader, importUI);

            public void run() {

                importUI.setVisible(true);

            }
        });
    }

    public void changeConfig() {

        java.awt.EventQueue.invokeLater(new Runnable() {

            ConfigWindow configUI = new ConfigWindow();

            public void run() {

                configUI.setVisible(true);

            }
        });

    }

    public void openLocalDocument() {
        if (!checkLogin()) {
            return;
        }
        java.awt.EventQueue.invokeLater(new Runnable() {

            LocalDocuments localDocs = new LocalDocuments(downloader);

            public void run() {
                localDocs.setVisible(true);
            }
        });
    }

    //Checks whether user logged in, if not shows login window
    private boolean checkLogin() {
        CredentialManager credentials = CredentialManager.getInstance();

        if (credentials.getPassword() != null && credentials.getUserName() != null) {
            return true;
        } else {
            try {
                UIManager.setLookAndFeel(Configuration.getInstance().getLookAndFeel());
            } catch (Exception e) {
            }
            JOptionPane.showMessageDialog(null, "Please login in to Zoho account first", "Zoho login", JOptionPane.INFORMATION_MESSAGE);

            java.awt.EventQueue.invokeLater(new Runnable() {

                LoginPanel login = new LoginPanel();

                public void run() {

                    login.setVisible(true);

                }
            });

            return false;
        }
    }
}
