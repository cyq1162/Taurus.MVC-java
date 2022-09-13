package taurus.mvc.http;

public class HttpCookie {

	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}
	public String getDomain() {
		return Domain;
	}
	public void setDomain(String domain) {
		Domain = domain;
	}
	public String getPath() {
		return Path;
	}
	public void setPath(String path) {
		Path = path;
	}
	public boolean isHttpOnly() {
		return isHttpOnly;
	}
	public void setHttpOnly(boolean isHttpOnly) {
		this.isHttpOnly = isHttpOnly;
	}
	public int getMaxAge() {
		return MaxAge;
	}
	public void setMaxAge(int maxAge) {
		MaxAge = maxAge;
	}
	public boolean isSecure() {
		return isSecure;
	}
	public void setSecure(boolean isSecure) {
		this.isSecure  = isSecure;
	}
	String Name;
	String Value;
	String Domain;
	String Path;
	boolean isHttpOnly;
	int MaxAge;
	boolean isSecure;
	
	HttpCookie(javax.servlet.http.Cookie cookie) {
		this.setName(cookie.getName());
		this.setValue(cookie.getValue());
		this.setDomain(cookie.getDomain());
		this.setHttpOnly(cookie.isHttpOnly());
		this.setMaxAge(cookie.getMaxAge());
		this.setPath(cookie.getPath());
		this.setSecure(cookie.getSecure());
	}
	HttpCookie(jakarta.servlet.http.Cookie cookie) {
		this.setName(cookie.getName());
		this.setValue(cookie.getValue());
		this.setDomain(cookie.getDomain());
		this.setHttpOnly(cookie.isHttpOnly());
		this.setMaxAge(cookie.getMaxAge());
		this.setPath(cookie.getPath());
		this.setSecure(cookie.getSecure());
	}

	public HttpCookie(String name, String value) {
		this.setName(name);
		this.setValue(value);
	}

	static HttpCookie[] format(javax.servlet.http.Cookie[] cookie) {
		if (cookie != null) {
			HttpCookie[] cookies=new HttpCookie[cookie.length];
			for (int i = 0; i < cookies.length; i++) {
				cookies[i]=new HttpCookie(cookie[i]);
			}
			return cookies;
		}
		return null;
	}
	static HttpCookie[] format(jakarta.servlet.http.Cookie[] cookie) {
		if (cookie != null) {
			HttpCookie[] cookies=new HttpCookie[cookie.length];
			for (int i = 0; i < cookies.length; i++) {
				cookies[i]=new HttpCookie(cookie[i]);
			}
			return cookies;
		}
		return null;
	}
	static jakarta.servlet.http.Cookie toJakartaCookie(HttpCookie arg0) {
		jakarta.servlet.http.Cookie cookie=new jakarta.servlet.http.Cookie(arg0.getName(), arg0.getValue());
		cookie.setDomain(arg0.getDomain());
		cookie.setHttpOnly(arg0.isHttpOnly());
		cookie.setMaxAge(arg0.getMaxAge());
		cookie.setPath(arg0.getPath());
		cookie.setSecure(arg0.isSecure());
		return cookie;
	}
	static javax.servlet.http.Cookie toJavaxCookie(HttpCookie arg0) {
		javax.servlet.http.Cookie cookie=new javax.servlet.http.Cookie(arg0.getName(), arg0.getValue());
		cookie.setDomain(arg0.getDomain());
		cookie.setHttpOnly(arg0.isHttpOnly());
		cookie.setMaxAge(arg0.getMaxAge());
		cookie.setPath(arg0.getPath());
		cookie.setSecure(arg0.isSecure());
		return cookie;
	}
}
