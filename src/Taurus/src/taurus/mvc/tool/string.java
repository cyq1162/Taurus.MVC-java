package taurus.mvc.tool;

/**
 * C# �﷨���� String ����������
 * @author ·������ ���̳̲��ͣ�https://www.cnblogs.com/cyq1162
 * 					  ��Դ��ַ��https://github.com/cyq1162/Taurus.MVC-java
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
