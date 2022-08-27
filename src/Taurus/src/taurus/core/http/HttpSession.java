package taurus.core.http;
import java.util.Enumeration;
public class HttpSession {

	jakarta.servlet.http.HttpSession jakartaSession;
	javax.servlet.http.HttpSession javaxSession;
	public HttpSession(jakarta.servlet.http.HttpSession session) {
		this.jakartaSession=session;
	}
	public HttpSession(javax.servlet.http.HttpSession session) {
		this.javaxSession=session;
	}
	
	public Object getAttribute(String arg0) {
		if(javaxSession!=null)
		{
			return javaxSession.getAttribute(arg0);
		}
		return jakartaSession.getAttribute(arg0);
	}

	
	public Enumeration<String> getAttributeNames() {
		if(javaxSession!=null)
		{
			return javaxSession.getAttributeNames();
		}
		return jakartaSession.getAttributeNames();
	}

	
	public long getCreationTime() {
		if(javaxSession!=null)
		{
			return javaxSession.getCreationTime();
		}
		return jakartaSession.getCreationTime();
	}

	
	public String getId() {
		if(javaxSession!=null)
		{
			return javaxSession.getId();
		}
		return jakartaSession.getId();
	}

	
	public long getLastAccessedTime() {
		if(javaxSession!=null)
		{
			return javaxSession.getMaxInactiveInterval();
		}
		return jakartaSession.getMaxInactiveInterval();
	}

	
	public int getMaxInactiveInterval() {
		if(javaxSession!=null)
		{
			return javaxSession.getMaxInactiveInterval();
		}
		return jakartaSession.getMaxInactiveInterval();
	}
	public void invalidate() {
		if(javaxSession!=null)
		{
			javaxSession.invalidate();
		}
		else
		{
			jakartaSession.invalidate();
		}
		
	}

	public boolean isNew() {
		if(javaxSession!=null)
		{
			return javaxSession.isNew();
		}
		return jakartaSession.isNew();
	}

	public void removeAttribute(String arg0) {
		if(javaxSession!=null)
		{
			javaxSession.removeAttribute(arg0);
		}
		else
		{
			jakartaSession.removeAttribute(arg0);
		}
		
	}

	public void setAttribute(String arg0, Object arg1) {
		if(javaxSession!=null)
		{
			javaxSession.setAttribute(arg0,arg1);
		}
		else
		{
			jakartaSession.setAttribute(arg0,arg1);
		}
		
	}

	
	public void setMaxInactiveInterval(int arg0) {
		if(javaxSession!=null)
		{
			javaxSession.setMaxInactiveInterval(arg0);
		}
		else
		{
			jakartaSession.setMaxInactiveInterval(arg0);
		}
		
	}

}
