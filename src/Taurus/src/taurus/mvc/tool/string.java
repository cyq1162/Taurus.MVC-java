package taurus.mvc.tool;

/**
 * C# 语法兼容 String 操作方法。
 * @author 路过秋天 ：教程博客：https://www.cnblogs.com/cyq1162
 * 					  开源地址：https://github.com/cyq1162/Taurus.MVC-java
 *
 */
public final class string {

	private string() {
		
	}
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
	
	public static String Trim(String text,char...cArray)
	{
		return TrimEnd(TrimStart(text,cArray),cArray);
	}
	public static String TrimEnd(String text,char...cArray)
	{
		if(IsNullOrEmpty(text)){return text;}
		int index=-1;
		for (int i = text.length()-1; i >=0; i--) {
			char c=text.charAt(i);
			if(!IsContain(cArray, c))
			{
				break;
			}
			index=i;
		}
		if(index>-1)
		{
			return text.substring(0,index-1);
		}
		return text;
	}
	public static String TrimStart(String text,char...cArray)
	{
		if(IsNullOrEmpty(text)){return text;}
		int index=-1;
		for (int i = 0; i < text.length(); i++) {
			char c=text.charAt(i);
			if(!IsContain(cArray, c))
			{
				break;
				
			}
			index=i;
		}
		if(index>-1)
		{
			return text.substring(index+1);
		}
		return text;
	}
	private static boolean IsContain(char[] cArray,char c) {
		
		for (char ch : cArray) {
			if(ch==c)
			{
				return true;
			}
		}
		return false;
		
	}
}
