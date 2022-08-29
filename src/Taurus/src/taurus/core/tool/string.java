package taurus.core.tool;

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
}
