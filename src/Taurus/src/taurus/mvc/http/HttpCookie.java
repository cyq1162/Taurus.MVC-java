package taurus.mvc.http;

import javax.servlet.http.Cookie;

public class HttpCookie extends Cookie {

	private static final long serialVersionUID = 1L;

	HttpCookie(Cookie cookie) {
		super(cookie.getName(), cookie.getValue());
		this.setComment(cookie.getComment());
		this.setDomain(cookie.getDomain());
		this.setHttpOnly(cookie.isHttpOnly());
		this.setMaxAge(cookie.getMaxAge());
		this.setPath(cookie.getPath());
		this.setSecure(cookie.getSecure());
		this.setVersion(cookie.getVersion());
	}
	HttpCookie(jakarta.servlet.http.Cookie cookie) {
		super(cookie.getName(), cookie.getValue());
		this.setComment(cookie.getComment());
		this.setDomain(cookie.getDomain());
		this.setHttpOnly(cookie.isHttpOnly());
		this.setMaxAge(cookie.getMaxAge());
		this.setPath(cookie.getPath());
		this.setSecure(cookie.getSecure());
		this.setVersion(cookie.getVersion());
	}

	public HttpCookie(String name, String value) {
		super(name, value);
		// TODO Auto-generated constructor stub
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
		cookie.setComment(arg0.getComment());
		cookie.setHttpOnly(arg0.isHttpOnly());
		cookie.setMaxAge(arg0.getMaxAge());
		cookie.setPath(arg0.getPath());
		cookie.setSecure(arg0.getSecure());
		cookie.setVersion(arg0.getVersion());
		return cookie;
	}
}
