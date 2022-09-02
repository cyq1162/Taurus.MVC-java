package taurus.mvc.tool;

import java.lang.reflect.InvocationTargetException;
import taurus.mvc.http.HttpContext;

public class Debug {
	public static void log(Exception err,String method) {
		if(err instanceof InvocationTargetException)
		{
			 err=(Exception)err.getCause();
			 log(err,method);
			 return;
		}
		StringBuilder sb=new StringBuilder();
		sb.append(method);
		sb.append(" : ");
		sb.append(err.toString());
		sb.append("\r\n");
		sb.append(err.getMessage());
		sb.append("\r\n");
		StackTraceElement[] stackTraceElements=err.getStackTrace();
		if(stackTraceElements!=null)
		{
			for (StackTraceElement stackTraceElement : stackTraceElements) {
				sb.append(stackTraceElement.toString());
				sb.append("\r\n");
			}
		}
		log(sb.toString());
	}
	private static void log(String mString) {
		HttpContext.Current.log(mString);
	}
}
