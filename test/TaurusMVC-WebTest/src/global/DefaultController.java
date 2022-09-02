package global;

import taurus.mvc.Controller;
import taurus.mvc.annotation.Ack;
import taurus.mvc.annotation.MicroService;
import taurus.mvc.annotation.Token;
import taurus.mvc.config.MvcConst;
import taurus.mvc.http.HttpRequest;

/**
 * 全局默认控制器
 * @author 路过秋天
 *
 */
public class DefaultController extends Controller {

	public void exeMethodAndThrowExcection() throws Exception {
		write("exe exeMethodAndThrowExcection method.<br/>");
		throw new Exception("I throw a Exception..hehehe.<br/>");
	}
	public void first() {
		write("exe first method.<br/>");
	}
	public void second(String value) {
		write("exe second method : value="+value+".<br/>");
		write("OldQueryString:"+getRequest().getAttribute(MvcConst.OldQueryString)+"<br/>");
		write("OldUri :"+getRequest().getAttribute(MvcConst.OldUri)+"<br/>");
		write("OldUrl :"+getRequest().getAttribute(MvcConst.OldUrl)+"<br/>");
	}
	@Ack
	@Token
	@MicroService
	public void showControllerName() {
		write("exe common method.<br/>");
	}
	
	/**
	 * 接收全局404。
	 */
	@Override
	public void Default() {
		write("Global : default for 404 :<br/>");
	}

    /**
     * 用于所有的请求合法性验证，配合[Ack]属性：启用时：局部的先执行（若存在)，无局部才执行全局。
     * @param controller
     * @return
     */
	public static Boolean checkAck(Controller controller)
    {
        controller.write("Global : checkAck :"+ controller.query("ack")+"<br/>");
        return controller.query("ack")!=null;

    }

    /**
     * 用于需要登陆后的身份验证，配合[Token]属性：启用时：局部的先执行（若存在)，无局部才执行全局。
     * @param controller
     * @return
     */
    public static Boolean checkToken(Controller controller)
    {
    	controller.write("Global : checkToken "+ controller.query("token")+"<br/>");
    	 return controller.query("token")!=null;
    }

    /**
     * 用于校验微服务的内部身份验证，配合[MicroService]属性
     * @param controller
     * @return
     */
    public static Boolean checkMicroService(Controller controller)
    {
    	controller.write("Global : checkMicroService "+ controller.query("microservice")+"<br/>");
    	 return controller.query("microservice")!=null;
    }

    /**
     * 全局【路由映射】：启用时：所有请求都进入此地做映射(需要映射时，返回映射的地址；不需要映射的返回空即可）。
     * @param request
     * @return
     */
    public static String routeMapInvoke(HttpRequest request)
    {
    	if(request.getRequestURI().startsWith("/default/first"))
    	{
    		return "/default/second?"+request.getQueryString();
    	}
    	return "";
    }
    /**
     * 全局【方法执行前拦截】：启用时：先全局，再执行局部（若存在）。
     * @param controller
     * @return
     */
    public static Boolean beforeInvoke(Controller controller)
    {
    	controller.write("Global : beforeInvoke "+ controller.getMethodName()+"<br/>");
        return true;
    }
    /**
     * 全局【方法执行后业务】：启用时：先执行局部（若存在），再执行全局。
     * @param controller
     */
    public static void endInvoke(Controller controller)
    {
    	controller.write("Global : endInvoke "+ controller.getMethodName()+"<br/>");
    }
    /**
     * 控制器方法有异常抛出时，此处可以统一接收处理。
     * @param controller
     * @param err
     */
    public static void onError(Controller controller,Exception err)
    {
    	controller.write("Global : onError Msg :"+ err.getMessage()+"<br/>");
    }
}
