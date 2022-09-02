package global;

import taurus.mvc.Controller;
import taurus.mvc.annotation.Ack;
import taurus.mvc.annotation.MicroService;
import taurus.mvc.annotation.Token;
import taurus.mvc.config.MvcConst;
import taurus.mvc.http.HttpRequest;

/**
 * ȫ��Ĭ�Ͽ�����
 * @author ·������
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
		write("OldQueryString:"+request.getAttribute(MvcConst.OldQueryString)+"<br/>");
		write("OldUri :"+request.getAttribute(MvcConst.OldUri)+"<br/>");
		write("OldUrl :"+request.getAttribute(MvcConst.OldUrl)+"<br/>");
	}
	@Ack
	@Token
	@MicroService
	public void showControllerName() {
		write("exe common method.<br/>");
	}
	
	/**
	 * ����ȫ��404��
	 */
	@Override
	public void Default() {
		write("Global : default for 404 :<br/>");
	}

    /**
     * �������е�����Ϸ�����֤�����[Ack]���ԣ�����ʱ���ֲ�����ִ�У�������)���޾ֲ���ִ��ȫ�֡�
     * @param controller
     * @return
     */
	public static Boolean checkAck(Controller controller)
    {
        controller.write("Global : checkAck :"+ controller.query("ack")+"<br/>");
        return controller.query("ack")!=null;

    }

    /**
     * ������Ҫ��½��������֤�����[Token]���ԣ�����ʱ���ֲ�����ִ�У�������)���޾ֲ���ִ��ȫ�֡�
     * @param controller
     * @return
     */
    public static Boolean checkToken(Controller controller)
    {
    	controller.write("Global : checkToken "+ controller.query("token")+"<br/>");
    	 return controller.query("token")!=null;
    }

    /**
     * ����У��΢������ڲ������֤�����[MicroService]����
     * @param controller
     * @return
     */
    public static Boolean checkMicroService(Controller controller)
    {
    	controller.write("Global : checkMicroService "+ controller.query("microservice")+"<br/>");
    	 return controller.query("microservice")!=null;
    }

    /**
     * ȫ�֡�·��ӳ�䡿������ʱ���������󶼽���˵���ӳ��(��Ҫӳ��ʱ������ӳ��ĵ�ַ������Ҫӳ��ķ��ؿռ��ɣ���
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
     * ȫ�֡�����ִ��ǰ���ء�������ʱ����ȫ�֣���ִ�оֲ��������ڣ���
     * @param controller
     * @return
     */
    public static Boolean beforeInvoke(Controller controller)
    {
    	controller.write("Global : beforeInvoke "+ controller.getMethodName()+"<br/>");
        return true;
    }
    /**
     * ȫ�֡�����ִ�к�ҵ�񡿣�����ʱ����ִ�оֲ��������ڣ�����ִ��ȫ�֡�
     * @param controller
     */
    public static void endInvoke(Controller controller)
    {
    	controller.write("Global : endInvoke "+ controller.getMethodName()+"<br/>");
    }
    /**
     * �������������쳣�׳�ʱ���˴�����ͳһ���մ���
     * @param controller
     * @param err
     */
    public static void onError(Controller controller,Exception err)
    {
    	controller.write("Global : onError Msg :"+ err.getMessage()+"<br/>");
    }
}
