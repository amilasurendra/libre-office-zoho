
package org.libreoffice.zoho.ui.models;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.libreoffice.zoho.handler.Downloader;
import org.libreoffice.zoho.handler.Document;

/**
 * @author Amila Surendra
 *
 * This class is responsible for provide view model for import table.
 */
public class ImportTableModel extends AbstractTableModel {

    List<Document> docList;
    Downloader downloader;

    public ImportTableModel(List<Document> docList) {
        this.docList = docList;
    }


    public int getRowCount() {
        return docList.size();
    }

    public int getColumnCount() {
        return 4;
    }

    public Object getValueAt(int row, int column) {
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 'at' h:mm a");

        switch(column){
            case 0: result = docList.get(row).getName();break;
            case 1: result = docList.get(row).getVersion(); break;
            case 2: result = docList.get(row).getAuthor(); break;
            case 3: result = format.format( docList.get(row).getLastModifyTime() ); break;
            case 4: result = docList.get(row).getDocumentID();break;

            default: result = "";
        }
        return result;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Class<?> type = String.class;
        return type;
    }


    @Override
    public String getColumnName(int column) {

        String name = null;

        switch (column) {
            case 0: name =  "Document Name"; break;
            case 1: name = "Version"; break;
            case 2: name = "Author"; break;
            case 3: name = "Last Modified"; break;
        }

        return name;
    }


}
