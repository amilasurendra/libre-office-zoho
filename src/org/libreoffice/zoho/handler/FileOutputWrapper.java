
package org.libreoffice.zoho.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author amila
 *
 * Wrapper for the FileOutputStream - monitors number of transfered bytes
 */
public class FileOutputWrapper extends FileOutputStream {

    private long transfered;
    private ProgressListener listener;

    public FileOutputWrapper(File file, ProgressListener listener) throws FileNotFoundException {
        super(file);
        this.listener = listener;
        transfered = 0;
    }

    public FileOutputWrapper(String name, ProgressListener listener) throws FileNotFoundException {
        super(name);
        this.listener = listener;
        transfered = 0;
    }



    @Override
    public void write(int b) throws IOException {
        super.write(b);
        this.transfered++;
        listener.transfered(transfered);
    }

    @Override
    public void write(byte[] b) throws IOException {
        super.write(b);
        this.transfered++;
        listener.transfered(transfered);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        super.write(b, off, len);
        this.transfered += len;
        listener.transfered(transfered);
    }

    public static interface ProgressListener {
        public void transfered(long bytes);
    }
}
