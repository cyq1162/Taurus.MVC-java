package taurus.mvc.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

public class HttpResponse{

	Object getServletResponse() {
		if(javaxResponse!=null)
		{
			return javaxResponse;
		}
		return jakartaResponse;
	}
	jakarta.servlet.http.HttpServletResponse jakartaResponse;
	javax.servlet.http.HttpServletResponse javaxResponse;
	HttpIncludeResponse includeResponse;
	public HttpResponse(jakarta.servlet.http.HttpServletResponse response) {
		this.jakartaResponse=response;
		includeResponse=new HttpIncludeResponse(response);
	}
	public HttpResponse(javax.servlet.http.HttpServletResponse response) {
		this.javaxResponse=response;
		includeResponse=new HttpIncludeResponse(response);
	}
	
	public void flushBuffer() throws IOException {
		if(javaxResponse!=null)
		{
			javaxResponse.flushBuffer();
		}
		else
		{
			jakartaResponse.flushBuffer();
		}
		
	}
	
	public int getBufferSize() {
		if(javaxResponse!=null)
		{
			return javaxResponse.getBufferSize();
		}
		return jakartaResponse.getBufferSize();
	}
	
	public String getCharacterEncoding() {
		if(javaxResponse!=null)
		{
			return javaxResponse.getCharacterEncoding();
		}
		return jakartaResponse.getCharacterEncoding();
	}
	
	public String getContentType() {
		if(javaxResponse!=null)
		{
			return javaxResponse.getContentType();
		}
		return jakartaResponse.getContentType();
	}
	
	public Locale getLocale() {
		if(javaxResponse!=null)
		{
			return javaxResponse.getLocale();
		}
		return jakartaResponse.getLocale();
	}
	
	public HttpOutputStream getOutputStream() throws IOException {
		if(javaxResponse!=null)
		{
			return new HttpOutputStream(javaxResponse.getOutputStream());
		}
		return new HttpOutputStream(jakartaResponse.getOutputStream());
	}
	
	public PrintWriter getWriter() throws IOException {
		if(javaxResponse!=null)
		{
			return javaxResponse.getWriter();
		}
		return jakartaResponse.getWriter();
	}
	
	public boolean isCommitted() {
		if(javaxResponse!=null)
		{
			return javaxResponse.isCommitted();
		}
		return jakartaResponse.isCommitted();
	}
	
	public void reset() {
		if(javaxResponse!=null)
		{
			javaxResponse.reset();
		}
		else
		{
			jakartaResponse.reset();
		}
		
	}
	
	public void resetBuffer() {
		if(javaxResponse!=null)
		{
			javaxResponse.resetBuffer();
		}
		else
		{
			jakartaResponse.resetBuffer();
		}
		
	}
	
	public void setBufferSize(int arg0) {
		if(javaxResponse!=null)
		{
			javaxResponse.setBufferSize(arg0);
		}
		else
		{
			jakartaResponse.setBufferSize(arg0);
		}
		
	}
	
	public void setCharacterEncoding(String arg0) {
		if(javaxResponse!=null)
		{
			javaxResponse.setCharacterEncoding(arg0);
		}
		else
		{
			jakartaResponse.setCharacterEncoding(arg0);
		}
		
	}
	
	public void setContentLength(int arg0) {
		if(javaxResponse!=null)
		{
			javaxResponse.setContentLength(arg0);
		}
		else
		{
			jakartaResponse.setContentLength(arg0);
		}
		
	}
	
	public void setContentLengthLong(long arg0) {
		if(javaxResponse!=null)
		{
			javaxResponse.setContentLengthLong(arg0);
		}
		else
		{
			jakartaResponse.setContentLengthLong(arg0);
		}
		
	}
	
	public void setContentType(String arg0) {
		if(javaxResponse!=null)
		{
			javaxResponse.setContentType(arg0);
		}
		else
		{
			jakartaResponse.setContentType(arg0);
		}
	}
	
	public void setLocale(Locale arg0) {
		if(javaxResponse!=null)
		{
			javaxResponse.setLocale(arg0);
		}
		else
		{
			jakartaResponse.setLocale(arg0);
		}
	}
	
	public void addCookie(HttpCookie arg0) {
		if(javaxResponse!=null)
		{
			javaxResponse.addCookie(HttpCookie.toJavaxCookie(arg0));
		}
		else
		{
			jakartaResponse.addCookie(HttpCookie.toJakartaCookie(arg0));
		}
	}
	
	public void addDateHeader(String arg0, long arg1) {
		if(javaxResponse!=null)
		{
			javaxResponse.addDateHeader(arg0,arg1);
		}
		else
		{
			jakartaResponse.addDateHeader(arg0,arg1);
		}
		
	}
	
	public void addHeader(String arg0, String arg1) {
		if(javaxResponse!=null)
		{
			javaxResponse.addHeader(arg0,arg1);
		}
		else
		{
			jakartaResponse.addHeader(arg0,arg1);
		}
		
	}
	
	public void addIntHeader(String arg0, int arg1) {
		if(javaxResponse!=null)
		{
			javaxResponse.addIntHeader(arg0,arg1);
		}
		else
		{
			jakartaResponse.addIntHeader(arg0,arg1);
		}
		
	}
	
	public boolean containsHeader(String arg0) {
		if(javaxResponse!=null)
		{
			return javaxResponse.containsHeader(arg0);
		}
		return jakartaResponse.containsHeader(arg0);
	}
	
	public String encodeRedirectURL(String arg0) {
		if(javaxResponse!=null)
		{
			return javaxResponse.encodeRedirectURL(arg0);
		}
		return jakartaResponse.encodeRedirectURL(arg0);
	}

	public String encodeURL(String arg0) {
		if(javaxResponse!=null)
		{
			return javaxResponse.encodeURL(arg0);
		}
		return jakartaResponse.encodeURL(arg0);
	}

	public String getHeader(String arg0) {
		if(javaxResponse!=null)
		{
			return javaxResponse.getHeader(arg0);
		}
		return jakartaResponse.getHeader(arg0);
	}
	
	public Collection<String> getHeaderNames() {
		if(javaxResponse!=null)
		{
			return javaxResponse.getHeaderNames();
		}
		return jakartaResponse.getHeaderNames();
	}
	
	public Collection<String> getHeaders(String arg0) {
		if(javaxResponse!=null)
		{
			return javaxResponse.getHeaders(arg0);
		}
		return jakartaResponse.getHeaders(arg0);
	}
	
	public int getStatus() {
		if(javaxResponse!=null)
		{
			return javaxResponse.getStatus();
		}
		return jakartaResponse.getStatus();
	}
	
	public void sendError(int arg0) throws IOException {
		if(javaxResponse!=null)
		{
			javaxResponse.sendError(arg0);
		}
		else
		{
			jakartaResponse.sendError(arg0);
		}
		
	}
	
	public void sendError(int arg0, String arg1) throws IOException {
		if(javaxResponse!=null)
		{
			javaxResponse.sendError(arg0,arg1);
		}
		else
		{
			jakartaResponse.sendError(arg0,arg1);
		}
		
	}
	
	public void sendRedirect(String arg0) throws IOException {
		if(javaxResponse!=null)
		{
			javaxResponse.sendRedirect(arg0);
		}
		else
		{
			jakartaResponse.sendRedirect(arg0);
		}
		
	}
	
	public void setDateHeader(String arg0, long arg1) {
		if(javaxResponse!=null)
		{
			javaxResponse.setDateHeader(arg0,arg1);
		}
		else
		{
			jakartaResponse.setDateHeader(arg0,arg1);
		}
		
	}
	
	public void setHeader(String arg0, String arg1) {
		if(javaxResponse!=null)
		{
			javaxResponse.setHeader(arg0,arg1);
		}
		else
		{
			jakartaResponse.setHeader(arg0,arg1);
		}
		
	}
	
	public void setIntHeader(String arg0, int arg1) {
		if(javaxResponse!=null)
		{
			javaxResponse.setIntHeader(arg0,arg1);
		}
		else
		{
			jakartaResponse.setIntHeader(arg0,arg1);
		}
		
	}
	
	public void setStatus(int arg0) {
		if(javaxResponse!=null)
		{
			javaxResponse.setStatus(arg0);
		}
		else
		{
			jakartaResponse.setStatus(arg0);
		}
		
	}

}
