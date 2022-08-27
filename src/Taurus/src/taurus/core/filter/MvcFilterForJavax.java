package taurus.core.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import taurus.core.entity.MethodInfo;
import taurus.core.http.HttpContext;
import taurus.core.http.HttpRequest;
import taurus.core.http.HttpResponse;
import taurus.core.reflect.MethodCollector;
import taurus.core.tool.string;

@WebFilter(value="*",dispatcherTypes={DispatcherType.REQUEST,DispatcherType.FORWARD} )
@MultipartConfig
public class MvcFilterForJavax implements Filter {

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)arg0;
		HttpServletResponse response=(HttpServletResponse)arg1;
		if(request.getRequestURI().contains("."))
		{
			arg2.doFilter(arg0, arg1);
			return;
		}
		MethodInfo methodInfo = MethodCollector.getGlobalRouteMapInvoke();
		if (methodInfo != null) {
			try {

				String url = (String) methodInfo.getMethod().invoke(null, new Object[] { new HttpRequest(request) });
				if (!string.IsNullOrEmpty(url) && !url.equals(request.getRequestURI())) {
					request.setAttribute("oldUrl", request.getRequestURL());
					request.setAttribute("oldUri", request.getRequestURI());
					request.setAttribute("oldQuery", request.getQueryString());
					arg0.getRequestDispatcher(url).forward(arg0, arg1);
					return;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		MvcFilter.doFilter(new HttpRequest(request), new HttpResponse(response));
	}
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Filter.super.init(filterConfig);
		MvcFilter.initConfig(new HttpContext(filterConfig.getServletContext()));
	}
}
