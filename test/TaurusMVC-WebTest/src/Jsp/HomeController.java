package Jsp;

import java.util.ArrayList;
import java.util.List;

import entity.MyEntity;
import taurus.mvc.Controller;

public class HomeController extends Controller {

	/**
	 * ����Jspҳ�棺��Ӧ·����Ŀ¼��/WEB-INF/jsp    ������������·����/Jsp/Index
	 * ������Ӧ·����/WEB-INF/jsp/jsp/index.jsp 
	 */
	public void Index() {
		List<MyEntity> list=new ArrayList<MyEntity>();
		list.add(new MyEntity("1","chen yu qiang"));
		list.add(new MyEntity("2","·������"));
		request.setAttribute("list", list);
	}
	@Override 
	public void endInvoke(String methodName) {
		if(methodName.equals("index"))
		{
			//�����޸�html������ݡ�
			String html=outputString.toString();
			html=html.replace("good boy", "very good boy");
			outputString.setLength(0);
			outputString.append(html);
			
		}
	}
}
