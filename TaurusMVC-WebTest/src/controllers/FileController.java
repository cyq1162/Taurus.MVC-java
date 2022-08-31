package controllers;

import java.io.IOException;
import java.util.Collection;

import taurus.mvc.Controller;
import taurus.mvc.annotation.IgnoreDefaultController;
import taurus.mvc.http.HttpPart;

/**
 * 文件接收处理示例：getParts方法，需要设置：allowCasualMultipartParsing=true
 * 参考地址：https://www.cnblogs.com/cyq1162/p/16629711.html
 * @author 123
 *
 */
@IgnoreDefaultController
public class FileController extends Controller {


	
	public void upload(HttpPart myFile) throws IOException {
		
		if(myFile!=null)
		{
			write("get fileName from myFile para : " + myFile.getSubmittedFileName() + "<br/>");
		}
		else
		{
			write("request content-type : "+getRequest().getContentType()+"<br/>");
			write("make sure tomcat server.xml ：Context docBase=... allowCasualMultipartParsing=\"true\"/><br/>");
			write("make sure Content-Type: multipart/form-data<br/>");
			
		}
		if(getRequest().getParameter("name")!=null)
		{
			write("get name from request.getParameter : " + getRequest().getParameter("name") + "<br/>");
		}
		Collection<HttpPart> parts = getRequest().getParts();
		if (parts != null) {
			for (HttpPart part : parts) {
				write("get fileName from request.getParts : " + part.getName() + "<br/>");
			}
		}
	}
	
	public void showControllerName() {
	write("Controller Name : "+this.getClass().getName()+"<br/>");
	write("控制器名称可以规范以：Controller结尾。<br/>");
}
}
