package taurus.mvc.http;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;


public class HttpContext {

	public static HttpContext Current;
	
	public javax.servlet.ServletContext javaxContext;
	public jakarta.servlet.ServletContext jakartaContext;

	public HttpContext(javax.servlet.ServletContext servletContext) {
		this.javaxContext = servletContext;
	}

	public HttpContext(jakarta.servlet.ServletContext servletContext) {
		this.jakartaContext = servletContext;
	}

	public Object getAttribute(String arg0) {
		if (javaxContext != null) {
			return javaxContext.getAttribute(arg0);
		}
		return jakartaContext.getAttribute(arg0);
	}

	
	public Enumeration<String> getAttributeNames() {
		if (javaxContext != null) {
			return javaxContext.getAttributeNames();
		}
		return jakartaContext.getAttributeNames();
	}

	
	public ClassLoader getClassLoader() {
		if (javaxContext != null) {
			return javaxContext.getClassLoader();
		}
		return jakartaContext.getClassLoader();
	}



	
	public String getContextPath() {
		if (javaxContext != null) {
			return javaxContext.getContextPath();
		}
		return jakartaContext.getContextPath();
	}

	
	public int getEffectiveMajorVersion() {
		if (javaxContext != null) {
			return javaxContext.getEffectiveMajorVersion();
		}
		return jakartaContext.getEffectiveMajorVersion();
	}

	
	public int getEffectiveMinorVersion() {
		if (javaxContext != null) {
			return javaxContext.getEffectiveMinorVersion();
		}
		return jakartaContext.getEffectiveMinorVersion();
	}


	
	public String getInitParameter(String arg0) {
		if (javaxContext != null) {
			return javaxContext.getInitParameter(arg0);
		}
		return jakartaContext.getInitParameter(arg0);
	}

	
	public Enumeration<String> getInitParameterNames() {
		if (javaxContext != null) {
			return javaxContext.getInitParameterNames();
		}
		return jakartaContext.getInitParameterNames();
	}

	
	public int getMajorVersion() {
		if (javaxContext != null) {
			return javaxContext.getMajorVersion();
		}
		return jakartaContext.getMajorVersion();
	}

	
	public String getMimeType(String arg0) {
		if (javaxContext != null) {
			return javaxContext.getMimeType(arg0);
		}
		return jakartaContext.getMimeType(arg0);
	}

	
	public int getMinorVersion() {
		if (javaxContext != null) {
			return javaxContext.getMinorVersion();
		}
		return jakartaContext.getMinorVersion();
	}

	
	public String getRealPath(String arg0) {
		if (javaxContext != null) {
			return javaxContext.getRealPath(arg0);
		}
		return jakartaContext.getRealPath(arg0);
	}

	
	public String getRequestCharacterEncoding() {
		if (javaxContext != null) {
			return javaxContext.getRequestCharacterEncoding();
		}
		return jakartaContext.getRequestCharacterEncoding();
	}

	
	public URL getResource(String arg0) throws MalformedURLException {
		if (javaxContext != null) {
			return javaxContext.getResource(arg0);
		}
		return jakartaContext.getResource(arg0);
	}

	
	public InputStream getResourceAsStream(String arg0) {
		if (javaxContext != null) {
			return javaxContext.getResourceAsStream(arg0);
		}
		return jakartaContext.getResourceAsStream(arg0);
	}

	
	public Set<String> getResourcePaths(String arg0) {
		if (javaxContext != null) {
			return javaxContext.getResourcePaths(arg0);
		}
		return jakartaContext.getResourcePaths(arg0);
	}

	
	public String getResponseCharacterEncoding() {
		if (javaxContext != null) {
			return javaxContext.getResponseCharacterEncoding();
		}
		return jakartaContext.getResponseCharacterEncoding();
	}

	
	public String getServerInfo() {
		if (javaxContext != null) {
			return javaxContext.getServerInfo();
		}
		return jakartaContext.getServerInfo();
	}


	public String getServletContextName() {
		if (javaxContext != null) {
			return javaxContext.getServletContextName();
		}
		return jakartaContext.getServletContextName();
	}

	public int getSessionTimeout() {
		if (javaxContext != null) {
			return javaxContext.getSessionTimeout();
		}
		return jakartaContext.getSessionTimeout();
	}

	
	public String getVirtualServerName() {
		if (javaxContext != null) {
			return javaxContext.getVirtualServerName();
		}
		return jakartaContext.getVirtualServerName();
	}

	
	public void removeAttribute(String arg0) {
		if (javaxContext != null) {
			javaxContext.removeAttribute(arg0);return;
		}
		jakartaContext.removeAttribute(arg0);
		
	}

	
	public void setAttribute(String arg0, Object arg1) {
		if (javaxContext != null) {
			javaxContext.setAttribute(arg0,arg1);return;
		}
		jakartaContext.setAttribute(arg0,arg1);
		
	}

	public boolean setInitParameter(String arg0, String arg1) {
		if (javaxContext != null) {
			return javaxContext.setInitParameter(arg0, arg1);
		}
		return jakartaContext.setInitParameter(arg0, arg1);
	}
	
	public void setRequestCharacterEncoding(String arg0) {
		if (javaxContext != null) {
			javaxContext.setRequestCharacterEncoding(arg0);return;
		}
		jakartaContext.setRequestCharacterEncoding(arg0);
		
	}

	
	public void setResponseCharacterEncoding(String arg0) {
		if (javaxContext != null) {
			javaxContext.setResponseCharacterEncoding(arg0);return;
		}
		jakartaContext.setResponseCharacterEncoding(arg0);
		
	}

	public void setSessionTimeout(int arg0) {
		if (javaxContext != null) {
			javaxContext.setSessionTimeout(arg0);return;
		}
		jakartaContext.setSessionTimeout(arg0);
	}
	public void log(String arg0) {
		if (javaxContext != null) {
			javaxContext.log("-----------------"+arg0);return;
		}
		jakartaContext.log("-----------------"+arg0);
	}
}
