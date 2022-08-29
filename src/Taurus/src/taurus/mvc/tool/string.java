package taurus.mvc.tool;

/**
 * C# 语法兼容 String 操作方法。
 * @author 路过秋天 ：教程博客：https://www.cnblogs.com/cyq1162
 * 					  开源地址：https://github.com/cyq1162/Taurus.MVC-java
 *
 */
public class string {

	public static Boolean IsNullOrEmpty(String value) {
		return value==null || value.trim().length()==0;
	}
	public static String Format(String format,String arg0) {
		if(!IsNullOrEmpty(format))
		{
			return format.replace("{0}", arg0);
		}
		return format;
	}
	public static String Format(String format,String arg0,String arg1) {
		if(!IsNullOrEmpty(format))
		{
			return format.replace("{0}", arg0).replace("{1}", arg1);
		}
		return format;
	}
	public static String Format(String format,String arg0,String arg1,String arg2) {
		if(!IsNullOrEmpty(format))
		{
			return format.replace("{0}", arg0).replace("{1}", arg1).replace("{2}", arg2);
		}
		return format;
	}
}
