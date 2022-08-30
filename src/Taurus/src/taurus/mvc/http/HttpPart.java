package taurus.mvc.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public class HttpPart{

	javax.servlet.http.Part javaxPart;
	public HttpPart(javax.servlet.http.Part part) {
		this.javaxPart=part;
	}
	jakarta.servlet.http.Part jakartaPart;
	public HttpPart(jakarta.servlet.http.Part part) {
		this.jakartaPart=part;
	}
	
	public void delete() throws IOException {
		if(javaxPart!=null)
		{
			javaxPart.delete();return;
		}
		jakartaPart.delete();
	}

	
	public String getContentType() {
		if(javaxPart!=null)
		{
			return javaxPart.getContentType();
		}
		return jakartaPart.getContentType();
	}

	
	public String getHeader(String arg0) {
		if(javaxPart!=null)
		{
			return javaxPart.getHeader(arg0);
		}
		return jakartaPart.getHeader(arg0);
	}

	
	public Collection<String> getHeaderNames() {
		if(javaxPart!=null)
		{
			return javaxPart.getHeaderNames();
		}
		return jakartaPart.getHeaderNames();
	}

	
	public Collection<String> getHeaders(String arg0) {
		if(javaxPart!=null)
		{
			return javaxPart.getHeaders(arg0);
		}
		return jakartaPart.getHeaders(arg0);
	}

	
	public InputStream getInputStream() throws IOException {
		if(javaxPart!=null)
		{
			return javaxPart.getInputStream();
		}
		return jakartaPart.getInputStream();
	}

	
	public String getName() {
		if(javaxPart!=null)
		{
			return javaxPart.getName();
		}
		return jakartaPart.getName();
	}

	
	public long getSize() {
		if(javaxPart!=null)
		{
			return javaxPart.getSize();
		}
		return jakartaPart.getSize();
	}

	
	public String getSubmittedFileName() {
		if(javaxPart!=null)
		{
			return javaxPart.getSubmittedFileName();
		}
		return jakartaPart.getSubmittedFileName();
	}

	
	public void write(String arg0) throws IOException {
		if(javaxPart!=null)
		{
			javaxPart.write(arg0);return;
		}
		jakartaPart.write(arg0);
	}
	static Collection<HttpPart> formatJavax(Collection<javax.servlet.http.Part> collection)
	{
		if(collection==null){return null;}
		Collection<HttpPart> collection2=new ArrayList<HttpPart>();
		for (javax.servlet.http.Part part : collection) {
			if(part.getName()==null || part.getSubmittedFileName()!=null)//part.getSubmittedFileName()!=null && 
			{
				collection2.add(new HttpPart(part));
			}
		}
		return collection2;
	}
	static Collection<HttpPart> formatJakarta(Collection<jakarta.servlet.http.Part> collection)
	{
		if(collection==null){return null;}
		Collection<HttpPart> collection2=new ArrayList<HttpPart>();
		for (jakarta.servlet.http.Part part : collection) {
			if(part.getName()==null | part.getSubmittedFileName()!=null)//part.getSubmittedFileName()!=null && 
			{
				collection2.add(new HttpPart(part));
			}
		}
		return collection2;
	}
}
