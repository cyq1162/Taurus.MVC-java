package taurus.mvc.http;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

class HttpPrintWriter  extends PrintWriter {   
    ByteArrayOutputStream myOutput; 
	  
    public HttpPrintWriter(ByteArrayOutputStream output) {   
       super(output);   
       myOutput = output;   
    }   
    public ByteArrayOutputStream getByteArrayOutputStream() {   
       return myOutput;   
    }   
 }   