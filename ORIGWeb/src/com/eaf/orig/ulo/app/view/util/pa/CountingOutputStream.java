package com.eaf.orig.ulo.app.view.util.pa;

import java.io.FilterOutputStream;
import java.io.OutputStream;
import java.io.IOException;
  
public class CountingOutputStream extends FilterOutputStream {
  
    private long count;
  
    public CountingOutputStream(OutputStream out) { super(out); }
  
    public long getCount() { return count; }
  
    public void write(int b) throws IOException {
        super.write(b);
        count++;
    }
  
    public void write(byte b[]) throws IOException {
        super.write(b);
        count += b.length;
    }
  
    public void write(byte b[], int off, int len) throws IOException {
        super.write(b);
        count += len;
    }
}