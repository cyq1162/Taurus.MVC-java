package taurus.mvc.filter;

import java.net.URI;
import java.util.Enumeration;

import taurus.microservice.Run;
import taurus.microservice.config.MsConfig;
import taurus.mvc.Controller;
import taurus.mvc.config.MvcConfig;
import taurus.mvc.http.HttpContext;
import taurus.mvc.http.HttpRequest;
import taurus.mvc.http.HttpResponse;
import taurus.mvc.reflect.ControllerCollector;
import taurus.mvc.tool.string;

public class MvcFilter {

	public static void doFilter(HttpRequest request, HttpResponse response) {
		String urlAbs = request.getRequestURL().toString();
		String urlPath = request.getRequestURI();
		String host = urlAbs.substring(0, urlAbs.length() - urlPath.length());
		Run.start(host);// 被动启动。

		if (checkCORS(request, response)) {
			invokeClass(request, response);
		}
	}

	private static Boolean checkCORS(HttpRequest request, HttpResponse response)
    {
		if(MvcConfig.getIsAllowCORS())
		{
			if (request.getMethod().equals("OPTIONS")) {
				response.setStatus(204);
				response.setHeader("Access-Control-Allow-Method", "GET,POST,PUT,DELETE");
				response.setHeader("Access-Control-Allow-Origin", "*");
				if (request.getHeader("Access-Control-Allow-Headers") != null) {
					response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Allow-Headers"));
				} else if (request.getHeader("Access-Control-Request-Headers") != null) {
					response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
				}
				return false;
			} else if (request.getHeader("Referer") == null || isCORSUrl(request,response)) {
				// 跨域访问
				response.setHeader("Access-Control-Allow-Origin", "*");
				response.setHeader("Access-Control-Allow-Credentials", "true");
			}
		}
		return true;
    }

	private static boolean isCORSUrl(HttpRequest request, HttpResponse response) {
		try {
			URI uri = new URI(request.getHeader("Referer"));
			if (!uri.getHost().equals(request.getServerName()) || uri.getPort()!=request.getServerPort()) {
				return true;
			}
		} catch (Exception err) {
			WriteError(err.getMessage(), response);
		}

		return false;
	}
	private static void invokeClass(HttpRequest request, HttpResponse response)
    {
        Class<?> t = null;
        //ViewController是由页面的前两个路径决定了。
        String localPath=request.getRequestURI();
        if(localPath.startsWith("/"))
        {
        	localPath=localPath.substring(1);
        }
        String[] items = localPath.split("/");//.trim('/')
        String className = "Default";
        if (MvcConfig.getRouteMode() == 1)
        {
            className = items.length > 2 ? items[0] + "." + items[1] : items[0];
        }
        else if (MvcConfig.getRouteMode() == 2)
        {
            className = items.length > 1 ? items[0] + "." + items[1] : items[0];
        }
        t = ControllerCollector.getController(className);
        if (t == null || t.getName() == "DefaultController")
        {
//            if (MicroService.Run.Proxy(context, false))//客户端做为网关。
//            {
//                return;
//            }
        }
        if (t == null)
        {
            WriteError("You need a " + className + " controller for coding!", response);
        }
        else
        {
            try
            {
                Controller o =(Controller)t.newInstance();//实例化
                o.ProcessRequest(request,response);
            }
            catch (Exception err)
            {
                WriteError(err.getMessage(), response);
            }
        }
        //context.Response.End();
    }
	private static void WriteError(String tip, HttpResponse response)
     {
		 try {
			 response.getWriter().write(tip);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
     }
	/**
	 * 环境（Tomcat）启动时初始化执行1次。
	 * @param context
	 */
	static void initConfig(HttpContext context) {
		context.log("Taurus.Mvc Filter.initConfig() Start on : "+context.getServerInfo());
		HttpContext.Current=context;
		Enumeration<String> configs=context.getInitParameterNames();
		 while (configs.hasMoreElements()) {
			 String name=configs.nextElement();
			 String nameLower=name.toLowerCase();
			 if(nameLower.startsWith("mvc.") || nameLower.startsWith("taurus."))
			 {
				 MvcConfig.set(nameLower, context.getInitParameter(name));
			 }
			 else if(nameLower.startsWith("microservice."))
			 {
				 MsConfig.set(nameLower, context.getInitParameter(name));
			 }
			 context.log(name+" : "+context.getInitParameter(name));
		}
		if(ControllerCollector.initController())
		{
			context.log("Taurus.MVC State : Starting.");
		}
		else
		{
			context.log("Taurus.MVC State : Stopped.");
		}
		if(!string.IsNullOrEmpty(MsConfig.getAppRunUrl()))
		{
			Run.start(MsConfig.getAppRunUrl());//主动启动。
		}
		context.log("Taurus.Mvc Filter.initConfig() End.");
	}
}
