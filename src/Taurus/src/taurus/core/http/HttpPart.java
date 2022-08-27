package taurus.core.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.Part;


public class HttpPart implements Part {

	javax.servlet.http.Part javaxPart;
	public HttpPart(javax.servlet.http.Part part) {
		this.javaxPart=part;
	}
	jakarta.servlet.http.Part jakartaPart;
	public HttpPart(jakarta.servlet.http.Part part) {
		this.jakartaPart=part;
	}
	@Override
	public void delete() throws IOException {
		if(javaxPart!=null)
		{
			javaxPart.delete();return;
		}
		jakartaPart.delete();
	}

	@Override
	public String getContentType() {
		if(javaxPart!=null)
		{
			return javaxPart.getContentType();
		}
		return jakartaPart.getContentType();
	}

	@Override
	public String getHeader(String arg0) {
		if(javaxPart!=null)
		{
			return javaxPart.getHeader(arg0);
		}
		return jakartaPart.getHeader(arg0);
	}

	@Override
	public Collection<String> getHeaderNames() {
		if(javaxPart!=null)
		{
			return javaxPart.getHeaderNames();
		}
		return jakartaPart.getHeaderNames();
	}

	@Override
	public Collection<String> getHeaders(String arg0) {
		if(javaxPart!=null)
		{
			return javaxPart.getHeaders(arg0);
		}
		return jakartaPart.getHeaders(arg0);
	}

	@Override
	public InputStream getInputStream() throws IOException {
		if(javaxPart!=null)
		{
			return javaxPart.getInputStream();
		}
		return jakartaPart.getInputStream();
	}

	@Override
	public String getName() {
		if(javaxPart!=null)
		{
			return javaxPart.getName();
		}
		return jakartaPart.getName();
	}

	@Override
	public long getSize() {
		if(javaxPart!=null)
		{
			return javaxPart.getSize();
		}
		return jakartaPart.getSize();
	}

	@Override
	public String getSubmittedFileName() {
		if(javaxPart!=null)
		{
			return javaxPart.getSubmittedFileName();
		}
		return jakartaPart.getSubmittedFileName();
	}

	@Override
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
