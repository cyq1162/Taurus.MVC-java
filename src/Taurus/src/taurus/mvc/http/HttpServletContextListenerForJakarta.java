package taurus.mvc.http;


import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import taurus.mvc.tool.Debug;

@WebListener()
public class HttpServletContextListenerForJakarta implements ServletContextListener {
	
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
