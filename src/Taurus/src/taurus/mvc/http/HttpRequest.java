package taurus.mvc.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

public class HttpRequest {

	Object getServletRequest() {
		if(javaxRequest!=null)
		{
			return javaxRequest;
		}
		return jakartaRequest;
	}
	javax.servlet.http.HttpServletRequest javaxRequest;
	jakarta.servlet.http.HttpServletRequest jakartaRequest;
	public HttpRequest(jakarta.servlet.http.HttpServletRequest request) {
		this.jakartaRequest=request;
//		if(request.getCharacterEncoding()==null)
//		{
//			try {
//				request.setCharacterEncoding("utf-8");
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}

	public HttpRequest(javax.servlet.http.HttpServletRequest request) {
		this.javaxRequest = request;
//		if (request.getCharacterEncoding() == null) {
//			try {
//				request.setCharacterEncoding("utf-8");
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}

	private HttpContext _HttpContext;

	public HttpContext getContext() {
		if (_HttpContext == null) {
			if (javaxRequest != null) {
				_HttpContext = new HttpContext(javaxRequest.getServletContext());
			} else {
				_HttpContext = new HttpContext(jakartaRequest.getServletContext());
			}
		}
		return _HttpContext;
	}

	public Object getAttribute(String arg0) {
		if(javaxRequest!=null)
		{
			return javaxRequest.getAttribute(arg0);
		}
		return jakartaRequest.getAttribute(arg0);
	}
	
	public Enumeration<String> getAttributeNames() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getAttributeNames();
		}
		return jakartaRequest.getAttributeNames();
	}
	
	public String getCharacterEncoding() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getCharacterEncoding();
		}
		return jakartaRequest.getCharacterEncoding();
	}
	
	public int getContentLength() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getContentLength();
		}
		return jakartaRequest.getContentLength();
	}
	
	public long getContentLengthLong() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getContentLengthLong();
		}
		return jakartaRequest.getContentLengthLong();
	}
	
	public String getContentType() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getContentType();
		}
		return jakartaRequest.getContentType();
	}
	
	public HttpInputStream getInputStream() throws IOException {
		if(javaxRequest!=null)
		{
			return new HttpInputStream(javaxRequest.getInputStream());
		}
		return new HttpInputStream(jakartaRequest.getInputStream());
	}
	
	public String getLocalAddr() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getLocalAddr();
		}
		return jakartaRequest.getLocalAddr();
	}
	
	public String getLocalName() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getLocalName();
		}
		return jakartaRequest.getLocalName();
	}
	
	public int getLocalPort() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getLocalPort();
		}
		return jakartaRequest.getLocalPort();
	}
	
	public Locale getLocale() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getLocale();
		}
		return jakartaRequest.getLocale();
	}
	
	public Enumeration<Locale> getLocales() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getLocales();
		}
		return jakartaRequest.getLocales();
	}
	
	public String getParameter(String arg0) {
		if(javaxRequest!=null)
		{
			return javaxRequest.getParameter(arg0);
		}
		return jakartaRequest.getParameter(arg0);
	}
	
	public Map<String, String[]> getParameterMap() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getParameterMap();
		}
		return jakartaRequest.getParameterMap();
	}
	
	public Enumeration<String> getParameterNames() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getParameterNames();
		}
		return jakartaRequest.getParameterNames();
	}
	
	public String[] getParameterValues(String arg0) {
		if(javaxRequest!=null)
		{
			return javaxRequest.getParameterValues(arg0);
		}
		return jakartaRequest.getParameterValues(arg0);
	}
	
	public String getProtocol() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getProtocol();
		}
		return jakartaRequest.getProtocol();
	}
	
	public BufferedReader getReader() throws IOException {
		if(javaxRequest!=null)
		{
			return javaxRequest.getReader();
		}
		return jakartaRequest.getReader();
	}

	public String getRemoteAddr() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getRemoteAddr();
		}
		return jakartaRequest.getRemoteAddr();
	}
	
	public String getRemoteHost() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getRemoteHost();
		}
		return jakartaRequest.getRemoteHost();
	}
	
	public int getRemotePort() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getRemotePort();
		}
		return jakartaRequest.getRemotePort();
	}

	public String getScheme() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getScheme();
		}
		return jakartaRequest.getScheme();
	}
	
	public String getServerName() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getServerName();
		}
		return jakartaRequest.getServerName();
	}
	
	public int getServerPort() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getServerPort();
		}
		return jakartaRequest.getServerPort();
	}

	public boolean isAsyncStarted() {
		if(javaxRequest!=null)
		{
			return javaxRequest.isAsyncStarted();
		}
		return jakartaRequest.isAsyncStarted();
	}
	
	public boolean isAsyncSupported() {
		if(javaxRequest!=null)
		{
			return javaxRequest.isAsyncSupported();
		}
		return jakartaRequest.isAsyncSupported();
	}
	
	public boolean isSecure() {
		if(javaxRequest!=null)
		{
			return javaxRequest.isSecure();
		}
		return jakartaRequest.isSecure();
	}
	
	public void removeAttribute(String arg0) {
		if(javaxRequest!=null)
		{
			javaxRequest.removeAttribute(arg0);
		}
		else
		{
			jakartaRequest.removeAttribute(arg0);
		}
	}
	
	public void setAttribute(String arg0, Object arg1) {
		if(javaxRequest!=null)
		{
			javaxRequest.setAttribute(arg0,arg1);
		}
		else
		{
			jakartaRequest.setAttribute(arg0,arg1);
		}
		
	}
	
	public void setCharacterEncoding(String arg0) {
		try {

			if (javaxRequest != null) {
				javaxRequest.setCharacterEncoding(arg0);
			} else {
				jakartaRequest.setCharacterEncoding(arg0);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public String changeSessionId() {
		if(javaxRequest!=null)
		{
			return javaxRequest.changeSessionId();
		}
		return jakartaRequest.changeSessionId();
	}
	
	public String getAuthType() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getAuthType();
		}
		return jakartaRequest.getAuthType();
	}
	
	public String getContextPath() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getContextPath();
		}
		return jakartaRequest.getContextPath();
	}
	public String getRealPath(String arg0) {
		if(javaxRequest!=null)
		{
			return javaxRequest.getServletContext().getRealPath(arg0);
		}
		return jakartaRequest.getServletContext().getRealPath(arg0);
	}
	public HttpCookie[] getCookies() {
		if(javaxRequest!=null)
		{
			return  HttpCookie.format(javaxRequest.getCookies());
		}
		return HttpCookie.format(jakartaRequest.getCookies());
	}
	
	public long getDateHeader(String arg0) {
		if(javaxRequest!=null)
		{
			return javaxRequest.getDateHeader(arg0);
		}
		return jakartaRequest.getDateHeader(arg0);
	}
	
	public String getHeader(String arg0) {
		if(javaxRequest!=null)
		{
			return javaxRequest.getHeader(arg0);
		}
		return jakartaRequest.getHeader(arg0);
	}
	
	public Enumeration<String> getHeaderNames() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getHeaderNames();
		}
		return jakartaRequest.getHeaderNames();
	}
	
	public Enumeration<String> getHeaders(String arg0) {
		if(javaxRequest!=null)
		{
			return javaxRequest.getHeaders(arg0);
		}
		return jakartaRequest.getHeaders(arg0);
	}
	
	public int getIntHeader(String arg0) {
		if(javaxRequest!=null)
		{
			return javaxRequest.getIntHeader(arg0);
		}
		return jakartaRequest.getIntHeader(arg0);
	}
	
	public String getMethod() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getMethod();
		}
		return jakartaRequest.getMethod();
	}

	
	public HttpPart getPart(String arg0) {
		try {

			if (javaxRequest != null) {
				if(javaxRequest.getPart(arg0)!=null)
				{
					return new HttpPart(javaxRequest.getPart(arg0));
				}
				return null;
			}
			if(jakartaRequest.getPart(arg0)!=null)
			{
				return new HttpPart(jakartaRequest.getPart(arg0));
			}
		} catch (Exception err) {
			
		}
		return null;
	}

	
	public Collection<HttpPart> getParts() {
		try {

			if (javaxRequest != null) {
				String con = javaxRequest.getContentType();
				if (con != null && con.startsWith("multipart/form-data")) {
					return HttpPart.formatJavax(javaxRequest.getParts());
				}
			} else {

				String con = jakartaRequest.getContentType();
				if (con != null && con.startsWith("multipart/form-data")) {
					return HttpPart.formatJakarta(jakartaRequest.getParts());
				}

			}
		} catch (Exception e) {
		}
		return null;
	}

	public String getPathInfo() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getPathInfo();
		}
		return jakartaRequest.getPathInfo();
	}
	
	public String getPathTranslated() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getPathTranslated();
		}
		return jakartaRequest.getPathTranslated();
	}
	
	public String getQueryString() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getQueryString();
		}
		return jakartaRequest.getQueryString();
	}
	
	public String getRemoteUser() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getRemoteUser();
		}
		return jakartaRequest.getRemoteUser();
	}
	
	public String getRequestURI() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getRequestURI();
		}
		return jakartaRequest.getRequestURI();
	}
	
	public StringBuffer getRequestURL() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getRequestURL();
		}
		return jakartaRequest.getRequestURL();
	}
	
	public String getRequestedSessionId() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getRequestedSessionId();
		}
		return jakartaRequest.getRequestedSessionId();
	}
	
	public String getServletPath() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getServletPath();
		}
		return jakartaRequest.getServletPath();
	}
	public HttpRequestDispatcher getRequestDispatcher(String arg0) {
		if(javaxRequest!=null)
		{
			return new HttpRequestDispatcher(javaxRequest.getRequestDispatcher(arg0));
		}
		return new HttpRequestDispatcher(jakartaRequest.getRequestDispatcher(arg0));
	}

	private HttpSession _Session;
	public HttpSession getSession() {
		if (_Session == null) {
			if (javaxRequest != null) {
				_Session = new HttpSession(javaxRequest.getSession());
			} else {
				_Session = new HttpSession(jakartaRequest.getSession());
			}
		}
		return _Session;
	}

	public Principal getUserPrincipal() {
		if(javaxRequest!=null)
		{
			return javaxRequest.getUserPrincipal();
		}
		return jakartaRequest.getUserPrincipal();
	}
	
	public boolean isRequestedSessionIdFromCookie() {
		if(javaxRequest!=null)
		{
			return javaxRequest.isRequestedSessionIdFromCookie();
		}
		return jakartaRequest.isRequestedSessionIdFromCookie();
	}
	
	public boolean isRequestedSessionIdFromURL() {
		if(javaxRequest!=null)
		{
			return javaxRequest.isRequestedSessionIdFromURL();
		}
		return jakartaRequest.isRequestedSessionIdFromURL();
	}

	public boolean isRequestedSessionIdValid() {
		if(javaxRequest!=null)
		{
			return javaxRequest.isRequestedSessionIdValid();
		}
		return jakartaRequest.isRequestedSessionIdValid();
	}
	
	public boolean isUserInRole(String arg0) {
		if(javaxRequest!=null)
		{
			return javaxRequest.isUserInRole(arg0);
		}
		return jakartaRequest.isUserInRole(arg0);
	}
	
	public void login(String arg0, String arg1) {
		try {

			if (javaxRequest != null) {
				javaxRequest.login(arg0,arg1);
			} else {
				jakartaRequest.login(arg0,arg1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public void logout() {
		try {

			if (javaxRequest != null) {
				javaxRequest.logout();
			} else {
				jakartaRequest.logout();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
