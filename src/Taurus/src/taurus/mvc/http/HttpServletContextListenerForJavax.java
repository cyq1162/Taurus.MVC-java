package taurus.mvc.http;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import taurus.mvc.tool.Debug;

@WebListener()
public class HttpServletContextListenerForJavax implements ServletContextListener  {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		HttpContext.IsDestroyed=true;
		Debug.log("ServletContext Destroyed!");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
