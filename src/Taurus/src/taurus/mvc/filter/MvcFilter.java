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
		Run.start(host);// ����������

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
				// �������
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
        //ViewController����ҳ���ǰ����·�������ˡ�
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
//            if (MicroService.Run.Proxy(context, false))//�ͻ�����Ϊ���ء�
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
                Controller o =(Controller)t.newInstance();//ʵ����
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
	 * ������Tomcat������ʱ��ʼ��ִ��1�Ρ�
	 * @param context
	 */
	static void initConfig(HttpContext context) {
		HttpContext.Current=context;
		context.log("Taurus.MVC Start : ");
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
		ControllerCollector.initController(); 
		if(!string.IsNullOrEmpty(MsConfig.getAppRunUrl()))
		{
			Run.start(MsConfig.getAppRunUrl());//����������
		}
	}
}