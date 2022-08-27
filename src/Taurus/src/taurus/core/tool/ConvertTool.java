package taurus.core.tool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ConvertTool {

	public static Object changeType(Object value,Class<?> t) throws Exception {
		 if (t == null) { return null; }
         String strValue =value==null?"":value.toString();
         if (t.isEnum())
         {

//             if (strValue != "")
//             {
//
//                 if (Enum.IsDefined(t, strValue))
//                 {
//                     return Enum.Parse(t, strValue);
//                 }
//                 int v = 0;
//                 if (int.TryParse(strValue, out v))
//                 {
//                     object v1 = Enum.Parse(t, strValue);
//                     if (v1.ToString() != strValue)
//                     {
//                         return v1;
//                     }
//                 }
//                 string[] names = Enum.GetNames(t);
//                 string lower = strValue.ToLower();
//                 foreach (string name in names)
//                 {
//                     if (name.ToLower() == lower)
//                     {
//                         return Enum.Parse(t, name);
//                     }
//                 }
//
//             }
//
//             //取第一个值。
//             string firstKey = Enum.GetName(t, -1);
//             if (!string.IsNullOrEmpty(firstKey))
//             {
//                 return Enum.Parse(t, firstKey);
//             }
//             return Enum.Parse(t, Enum.GetNames(t)[0]);

         }
         if (value == null)
         {
             return t.isPrimitive() ? t.newInstance() : null;
         }
         if (Object.class.equals(t))
         {
             return value;
         }

//         if (className== "System.Type")
//         {
//             return (Type)value;
//         }
//         if (t.FullName == "System.IO.Stream" && value is HttpPostedFile)
//         {
//             return ((HttpPostedFile)value).InputStream;
//         }

//         if (t.IsGenericType && t.Name.StartsWith("Nullable"))
//         {
//             t = Nullable.GetUnderlyingType(t);
//             if (strValue == "")
//             {
//                 return null;
//             }
//         }
         if (String.class.equals(t))
         {
             if (value instanceof byte[])
             {
                 return java.util.Base64.getEncoder().encodeToString((byte[])value);
             }
             return strValue;
         }
         if (StringBuffer.class.equals(t))
         {
             return new StringBuffer(strValue);
         }
         if (StringBuilder.class.equals(t))
         {
             return new StringBuilder(strValue);
         }
//         if (t.FullName == "System.Text.Encoding")
//         {
//             return value as Encoding;
//         }
         if (strValue.trim().equals(""))
         {
             if (t.getName().endsWith("[]")) { return null; }
             return t.newInstance();
         }
         else if (t.isPrimitive())
         {
             strValue = strValue.trim();//'\r', '\n', '\t', ' '
             if (Date.class.equals(t))
             {
                 switch (strValue.toLowerCase())//.trimEnd(')', '(')
                 {
                     case "now":
                     case "getdate":
                     case "current_timestamp":
                    	 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
                    	 return dateFormat.format( new Date() );
                 }
                 return  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strValue);
//                 if (Date.parse(strValue) == DateTime.MinValue)
//                 {
//                     return (DateTime)SqlDateTime.MinValue;
//                 }
//                 return Convert.ChangeType(value, t);//这里用value，避免丢失毫秒
             }
             else if (UUID.class.equals(t))
             {
                 if (strValue.startsWith("newid"))
                 {
                     return UUID.randomUUID();
                 }
                 else if (strValue.length()!=32 || strValue.split("-").length!=5)
                 {
                     return UUID.fromString("00000000-0000-0000-0000-000000000000");
                 }
                 return UUID.fromString(strValue);
             }
             else
             {
                 switch (strValue.toLowerCase())
                 {
                     case "yes":
                     case "true":
                     case "success":
                     case "1":
                     case "on":
                     case "是":
                         if (Boolean.class.equals(t) || boolean.class.equals(t))
                             return true;
                         else strValue = "1";
                         break;
                     case "no":
                     case "false":
                     case "fail":
                     case "0":
                     case "":
                     case "否":
                     case "null":
                         if (Boolean.class.equals(t) || boolean.class.equals(t))
                             return false;
                         else strValue = "0";
                         break;
                     case "infinity":
                     case "正无穷大":
                         if (Double.class.equals(t) || Float.class.equals(t)|| double.class.equals(t) || float.class.equals(t))
                             return Double.POSITIVE_INFINITY;
                         break;
                     case "-infinity":
                     case "负无穷大":
                         if (Double.class.equals(t) || Float.class.equals(t)|| double.class.equals(t) || float.class.equals(t))
                             return Double.NEGATIVE_INFINITY;
                         break;
                     default:
                         if (Boolean.class.equals(t) || boolean.class.equals(t))
                             return false;
                         break;
                 }
                 if(value.getClass().isEnum())
                 {
                	 strValue=((Integer)value).toString();
                 }
             }
             return stringToNum(strValue, t);
         }
//         else
//         {
//             Type valueType = value.GetType();
//             //if(valueType.IsEnum && t.is)
//
//             if (valueType.FullName != t.FullName)
//             {
//                 if ((strValue.StartsWith("{") || strValue.StartsWith("[")) && (strValue.EndsWith("}") || strValue.EndsWith("]")))
//                 {
//                     return JsonHelper.ToEntity(t, strValue, EscapeOp.Default);
//                 }
//                 switch (ReflectTool.GetSystemType(ref t))
//                 {
//                     case SysType.Custom:
//                         return MDataRow.CreateFrom(value).ToEntity(t);
//                     case SysType.Generic:
//                     case SysType.Collection:
//                         return MDataTable.CreateFrom(value).ToList(t);
//                         //case SysType.Generic:
//                         //    if (t.Name.StartsWith("List") || t.Name.StartsWith("IList") || t.Name.StartsWith("MList"))
//                         //    {
//                         //        return JsonSplit.ToEntity(t, strValue, EscapeOp.Default);
//                         //        //return MDataTable.CreateFrom(strValue).ToList(t);
//                         //    }
//                        // break;
//                     case SysType.Array:
//                         if (t.Name == "Byte[]")
//                         {
//                             if (valueType.Name == "String")
//                             {
//                                 return Convert.FromBase64String(strValue);
//                             }
//                             using (MemoryStream ms = new MemoryStream())
//                             {
//                                 new BinaryFormatter().Serialize(ms, value);
//                                 return ms.ToArray();
//                             }
//                         }
//                         break;
//                 }
//             }
//             return Convert.ChangeType(value, t);
//         }
         return value;
	}
	
	public static Object stringToNum(String value, Class<?> t) {

		try {
			if (value.split("\\.").length > 2) {
				return 0;
			}
			for (int i = 0; i < value.length(); i++) {
				char c = value.charAt(i);
				if (i == 0 && (c == '+' || c == '-')) {
					continue;
				} else if (!Character.isDigit(c) && c != '.') {
					return 0;
				}
			}
			if (double.class.equals(t) || Double.class.equals(t)) {
				return Double.parseDouble(value);
			} else if (long.class.equals(t) || Long.class.equals(t)) {
				return Long.parseLong(format(value));
			} else if (int.class.equals(t) || Integer.class.equals(t)) {
				return Integer.parseInt(format(value));
			} else if (float.class.equals(t) || Float.class.equals(t)) {
				return Float.parseFloat(value);
			} else if (short.class.equals(t) || Short.class.equals(t)) {
				return Short.parseShort(format(value));
			} else if (boolean.class.equals(t) || Boolean.class.equals(t)) {
				return Boolean.parseBoolean(format(value));
			} else if (Number.class.isAssignableFrom(t)) {
				return t.getConstructor(String.class).newInstance(value);
			} else {
				return t.getConstructor(String.class).newInstance(value);
			}
		} catch (Exception e) {
			return 0;
		}
    } 
	private static String format(String value) {
        if (value.indexOf('.') > -1)//11.22
        {
            return value.split("\\.")[0];
        }
        return value;
	}
}
