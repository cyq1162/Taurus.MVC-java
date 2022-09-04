package taurus.mvc;
import java.io.File;

import taurus.mvc.tool.string;

public class ViewEngine {

	private static int hasJspOrHtml=-1;
	public static void Load(Controller controller) throws Exception {
		
		String realPath = controller.request.getRealPath("/");
		if(hasJspOrHtml==-1)
		{
			if(new File(realPath + "/WEB-INF/jsp").exists() ||new File(realPath + "/WEB-INF/html").exists())
			{
				hasJspOrHtml=1;
			} else {
				hasJspOrHtml = 0;
			}
		}
		if (hasJspOrHtml == 0) {
			return;
		}
		String filePath = "";
		if (!string.IsNullOrEmpty(controller.getModuleName())) {
			filePath += controller.getModuleName().toLowerCase() + "/";
		}
		filePath += controller.getControllerName().toLowerCase() + "/" + controller.getMethodName().toLowerCase();

		String path = "/WEB-INF/jsp/" + filePath + ".jsp";
		if (!new File(realPath + path).exists()) {
			path = realPath + "/WEB-INF/html/" + filePath + ".html";
			if (!new java.io.File(realPath + path).exists()) {
				return;
			}
		}
		String html = controller.request.getRequestDispatcher(path).include(controller.request, controller.response);
		controller.write(html);
	}
}
