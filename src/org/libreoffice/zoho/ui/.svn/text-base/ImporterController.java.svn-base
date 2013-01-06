package org.libreoffice.zoho.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.JFrame;
import org.libreoffice.zoho.handler.Document;
import org.libreoffice.zoho.handler.Downloader;
import org.libreoffice.zoho.handler.WriterDocument;
import org.libreoffice.zoho.ui.models.ImportTableModel;

/**
 * @author Amila Surendra
 *
 * Controller for the Importer UI
 */
public class ImporterController {

    private Downloader downloader;
    private Importer importer;

    public ImporterController(Downloader downloader, Importer importer) {
        this.downloader = downloader;
        this.importer = importer;

        importer.addDownloadListner(new DownloadListner());
        importer.addSearchListener(new FilterListener());
        importer.addRefreshBtnListener(new RefreshListener());
    }

    class DownloadListner implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            Progress downloading = new Progress(new JFrame(), false, "Downloading File...", true);
            downloader.addObserver(downloading);
            downloading.setVisible(true);

            //Using another thread for long running download task
            Thread downThread = new Thread() {

                @Override
                public void run() {
                    WriterDocument downDoc = (WriterDocument) downloader.getDocument(importer.getSelectedDocumentID());
                    downloader.downloadDocument(downDoc);
                    downloader.openDownloadedDoc(downDoc.getLocalPath());
                }
            };

            downThread.start();
            importer.dispose();
        }
    }

    class FilterListener implements KeyListener {

        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
        }

        public void keyReleased(KeyEvent e) {
            importer.filterTable(importer.getFiltertext());
        }
    }

    class RefreshListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            final Progress refreshProg = new Progress(importer, true, "Refreshing Document List", true);
            refreshProg.setVisible(true);

            Thread refreshThread = new Thread() {

                @Override
                public void run() {
                    List<Document> documentList = downloader.getDocumentList(Document.ALL, new WriterDocument());
                    importer.setTableModel(new ImportTableModel(documentList));
                    importer.resize();
                    refreshProg.dispose();
                }
            };
            refreshThread.start();

        }
    }
}
