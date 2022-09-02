package taurus.mvc.tool;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
/**
 * ��������ת���ࡣ
 * @author ·������ ���̳̲��ͣ�https://www.cnblogs.com/cyq1162
 * 					  ��Դ��ַ��https://github.com/cyq1162/Taurus.MVC-java
 *
 */
public class ConvertTool {

	private static String checkIsEnum(Object value,String strValue)
	{
		if(value!=null && value.getClass().isEnum())
		{
			return ((Enum<?>)value).ordinal()+"";
		}
		return strValue;
	}
	private static String format(String value) {
		if (value.indexOf('.') > -1)// 11.22
		{
			return value.split("\\.")[0];
		}
		return value;
	}

	private static Boolean isNum(String value) {
		if (value.length()==0 || value.split("\\.").length > 2) {
			return false;
		}
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			if (i == 0 && (c == '+' || c == '-')) {
				continue;
			} else if (!Character.isDigit(c) && c != '.') {
				return false;
			}
		}
		return true;
	}

	private static <T> T toEnum(String strValue, Class<T> t) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		T[] enumConstants = t.getEnumConstants();
		if (strValue != "") {
			for (T ec : enumConstants) {
				Enum<?> item = (Enum<?>) ec;
				Integer a = item.ordinal();
				if (strValue.compareToIgnoreCase(item.name()) == 0 || strValue.compareTo(a.toString()) == 0) {
					return ec;
				}
			}

		}
		return enumConstants[0];
	}

	private static Boolean toBoolean(String strValue, Class<?> t) {
		switch (strValue.toLowerCase()) {
		case "ok":
		case "yes":
		case "true":
		case "success":
		case "1":
		case "on":
		case "��":
			return true;
		default:
			return false;
		// case "no":
		// case "false":
		// case "fail":
		// case "0":
		// case "":
		// case "��":
		// case "null":
		// if (Boolean.class.equals(t) || boolean.class.equals(t))
		// return false;
		// else strValue = "0";
		// break;
		// case "infinity":
		// case "�������":
		// if (Double.class.equals(t) || Float.class.equals(t)||
		// double.class.equals(t) || float.class.equals(t))
		// return Double.POSITIVE_INFINITY;
		// break;
		// case "-infinity":
		// case "�������":
		// if (Double.class.equals(t) || Float.class.equals(t)||
		// double.class.equals(t) || float.class.equals(t))
		// return Double.NEGATIVE_INFINITY;
		// break;
		// default:
		// if (Boolean.class.equals(t) || boolean.class.equals(t))
		// return false;
		// break;
		}
	}

	private static UUID toUUID(String strValue, Class<?> t) {
		if (strValue.toLowerCase().startsWith("newid")) {
			return UUID.randomUUID();
		} else if (strValue.length() != 36 || strValue.split("-").length != 5) {
			return UUID.fromString("00000000-0000-0000-0000-000000000000");
		}
		return UUID.fromString(strValue);
	}

	private static Date toDate(String strValue, Class<?> t) {
		switch (strValue.toLowerCase())// .trimEnd(')', '(')
		{
			case "now":
			case "getdate":
			case "current_timestamp":
			// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd
			// HH:mm:ss");//���Է�����޸����ڸ�ʽ
			return new Date();
		}
		String formatter = "yyyy-MM-dd HH:mm:ss:SSS";
		if (strValue.equals("") || strValue.length() > formatter.length()) {
			return getMinDate();
		}
		if(strValue.contains("/"))
		{
			strValue=strValue.replace("/", "-");
		}
		if(strValue.contains("."))
		{
			strValue=strValue.replace(".", ":");
		}
		if(strValue.length()<formatter.length())
		{
			formatter=formatter.substring(0, strValue.length());
		}
		try {
			return new SimpleDateFormat(formatter).parse(strValue);
		} catch (Exception err) {
			Debug.log(err,"ConvertTool.toDate("+strValue+")");
			return getMinDate();
		}
		
	}
    private static Date getMinDate() {
    	Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	private static Integer toInt(String strValue, Class<?> t) {

		if (!isNum(strValue)) {
			return 0;
		}
		return Integer.parseInt(format(strValue));
	}

	private static Short toShort(String strValue, Class<?> t) {
		if (!isNum(strValue)) {
			return 0;
		}
		return Short.parseShort(format(strValue));
	}

	private static Long toLong(String strValue, Class<?> t) {
		if (!isNum(strValue)) {
			return 0l;
		}
		return Long.parseLong(format(strValue));
	}

	private static Float toFloat(String strValue, Class<?> t) {
		if (!isNum(strValue)) {
			return 0f;
		}
		return Float.parseFloat(strValue);
	}

	private static Double toDouble(String strValue, Class<?> t) {
		if (!isNum(strValue)) {
			return 0d;
		}
		return Double.parseDouble(strValue);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T tryChangeType(Object value, Class<T> t)
	{
		try {
			return (T)changeType(value, t);
		} catch (Exception e) {
			Debug.log(e,"ConvertTool.tryChangeType("+value+")");
			return null;
		}
	}
	public static Object changeType(Object value, Class<?> t) throws Exception {
		if (t == null) {
			return null;
		}
		String strValue = value == null ? "" : value.toString();
		if (String.class.equals(t)) {
			if (value instanceof byte[]) {
				return java.util.Base64.getEncoder().encodeToString((byte[]) value);
			}
			return strValue;
		}
		if (t.isEnum()) {
			return toEnum(strValue, t);
		}
		if (int.class.equals(t) || Integer.class.equals(t)) {
			return toInt(checkIsEnum(value,strValue), t);
		}
		
		if (Boolean.class.equals(t) || boolean.class.equals(t)) {
			return toBoolean(checkIsEnum(value,strValue), t);
		}
		
		if (UUID.class.equals(t)) {
			return toUUID(strValue, t);
		}
		if (Date.class.equals(t)) {
			return toDate(strValue, t);
		}
		if (StringBuffer.class.equals(t)) {
			return new StringBuffer(strValue);
		}
		if (StringBuilder.class.equals(t)) {
			return new StringBuilder(strValue);
		}

		if (short.class.equals(t) || Short.class.equals(t)) {
			return toShort(checkIsEnum(value,strValue), t);
		}
		if (long.class.equals(t) || Long.class.equals(t)) {
			return toLong(checkIsEnum(value,strValue), t);
		}
		if (double.class.equals(t) || Double.class.equals(t)) {
			return toDouble(checkIsEnum(value,strValue), t);
		}
		if (float.class.equals(t) || Float.class.equals(t)) {
			return toFloat(checkIsEnum(value,strValue), t);
		}
		if (Number.class.isAssignableFrom(t)) {
			return t.getConstructor(String.class).newInstance(checkIsEnum(value,strValue));
		}
		if (value == null) {
			return null;
		}
		return t.cast(value);

		// if (className== "System.Type")
		// {
		// return (Type)value;
		// }
		// if (t.FullName == "System.IO.Stream" && value is HttpPostedFile)
		// {
		// return ((HttpPostedFile)value).InputStream;
		// }

		// if (t.IsGenericType && t.Name.StartsWith("Nullable"))
		// {
		// t = Nullable.GetUnderlyingType(t);
		// if (strValue == "")
		// {
		// return null;
		// }
		// }

		// if (t.FullName == "System.Text.Encoding")
		// {
		// return value as Encoding;
		// }
		// if (strValue.trim().equals(""))
		// {
		// if (t.getName().endsWith("[]")) { return null; }
		// return t.newInstance();
		// }

		// else
		// {
		// Type valueType = value.GetType();
		// //if(valueType.IsEnum && t.is)
		//
		// if (valueType.FullName != t.FullName)
		// {
		// if ((strValue.StartsWith("{") || strValue.StartsWith("[")) &&
		// (strValue.EndsWith("}") || strValue.EndsWith("]")))
		// {
		// return JsonHelper.ToEntity(t, strValue, EscapeOp.Default);
		// }
		// switch (ReflectTool.GetSystemType(ref t))
		// {
		// case SysType.Custom:
		// return MDataRow.CreateFrom(value).ToEntity(t);
		// case SysType.Generic:
		// case SysType.Collection:
		// return MDataTable.CreateFrom(value).ToList(t);
		// //case SysType.Generic:
		// // if (t.Name.StartsWith("List") || t.Name.StartsWith("IList") ||
		// t.Name.StartsWith("MList"))
		// // {
		// // return JsonSplit.ToEntity(t, strValue, EscapeOp.Default);
		// // //return MDataTable.CreateFrom(strValue).ToList(t);
		// // }
		// // break;
		// case SysType.Array:
		// if (t.Name == "Byte[]")
		// {
		// if (valueType.Name == "String")
		// {
		// return Convert.FromBase64String(strValue);
		// }
		// using (MemoryStream ms = new MemoryStream())
		// {
		// new BinaryFormatter().Serialize(ms, value);
		// return ms.ToArray();
		// }
		// }
		// break;
		// }
		// }
		// return Convert.ChangeType(value, t);
		// }
		// return value;
	}

}
