package taurus.mvc.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;


class WrapperResponseForJakarta extends HttpServletResponseWrapper {

	 private HttpPrintWriter httpWriter;   
	   private ByteArrayOutputStream output;   
	   public WrapperResponseForJakarta(Object httpServletResponse) {   
	      super((HttpServletResponse)httpServletResponse);   
	      output = new ByteArrayOutputStream();   
	      httpWriter = new HttpPrintWriter(output);   
	   }   
	   public void finalize() throws Throwable {   
	      super.finalize();   
	      output.close();   
	      httpWriter.close();   
	   }   
	   public String getContent() throws Exception {   
		   httpWriter.flush(); 
	       return httpWriter.getByteArrayOutputStream().toString();    
	   }   
	  
	   //×Ô¶¨ÒåWriter   
	   public PrintWriter getWriter() throws IOException {   
	      return httpWriter;   
	}

	public void close() throws IOException {
		httpWriter.close();
	}

}  