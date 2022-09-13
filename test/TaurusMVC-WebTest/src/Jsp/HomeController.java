package Jsp;

import java.util.ArrayList;
import java.util.List;

import entity.MyEntity;
import taurus.mvc.Controller;

public class HomeController extends Controller {

	/**
	 * 加载Jsp页面：对应路径根目录：/WEB-INF/jsp    控制器方法名路径：/Jsp/Index
	 * 完整对应路径：/WEB-INF/jsp/jsp/index.jsp 
	 */
	public void Index() {
		List<MyEntity> list=new ArrayList<MyEntity>();
		list.add(new MyEntity("1","chen yu qiang"));
		list.add(new MyEntity("2","路过秋天"));
		request.setAttribute("list", list);
	}
	@Override 
	public void endInvoke() {
		if(getMethodName().equals("index"))
		{
			//后期修改html输出内容。
			String html=outputString.toString();
			html=html.replace("good boy", "very good boy");
			outputString.setLength(0);
			outputString.append(html);
			
		}
	}
}
