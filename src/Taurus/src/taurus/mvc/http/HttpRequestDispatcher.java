package taurus.mvc.http;
public class HttpRequestDispatcher{

	javax.servlet.RequestDispatcher javaxDispatcher;
	jakarta.servlet.RequestDispatcher jakartaDispatcher;

	public HttpRequestDispatcher(javax.servlet.RequestDispatcher requestDispatcher) {
		this.javaxDispatcher = requestDispatcher;
	}

	public HttpRequestDispatcher(jakarta.servlet.RequestDispatcher requestDispatcher) {
		this.jakartaDispatcher = requestDispatcher;
	}

	public void forward(HttpRequest arg0, HttpResponse arg1) throws Exception {
		if(javaxDispatcher!=null)
		{
			javaxDispatcher.forward((javax.servlet.ServletRequest)arg0.getServletRequest(), (javax.servlet.ServletResponse)arg1.getServletResponse());
			return;
		}
		jakartaDispatcher.forward((jakarta.servlet.ServletRequest)arg0.getServletRequest(), (jakarta.servlet.ServletResponse)arg1.getServletResponse());
	}

	public String include(HttpRequest arg0, HttpResponse arg1) throws Exception {
		arg0.setAttribute("IsDispatcherInclude", "1");
		if (javaxDispatcher != null) {
			javaxDispatcher.include((javax.servlet.ServletRequest) arg0.getServletRequest(),
					(javax.servlet.ServletResponse) arg1.includeResponse.getServletResponse());
			return arg1.includeResponse.getContent();
		}
		jakartaDispatcher.include((jakarta.servlet.ServletRequest) arg0.getServletRequest(),
				(jakarta.servlet.ServletResponse) arg1.includeResponse.getServletResponse());
		return arg1.includeResponse.getContent();
	}

}
