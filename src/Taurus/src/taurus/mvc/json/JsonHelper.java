package taurus.mvc.json;

import java.util.ArrayList;
import taurus.mvc.tool.string;

public class JsonHelper extends JsonSplit {

	public JsonHelper()
	{

	}
	private boolean _AddHead;
	/// <param name="addHead">with easyui header ?<para>是否带输出头</para></param>
	public JsonHelper(boolean addHead)
	{
		_AddHead = addHead;
	}

	/// <summary>
	/// Json 返回值是否为true（包含"success":true）
	/// </summary>
	/// <param name="json"></param>
	/// <returns></returns>
	public static boolean isSuccess(String json)
	{
		return getValue(json, "success",boolean.class);
	}
	/// <summary>
	/// a easy method for you to return a json
	/// <para>返回Json格式的结果信息</para>
	/// </summary>
	public static String outResult(boolean result, String msg)
	{
		return outResult(result, msg, false);
	}

	/// <param name="noQuates">no ""</param>
	public static String outResult(boolean result, String msg, boolean noQuates)
	{
		JsonHelper js = new JsonHelper();
		js.add("success", result?"true":"false", true);
		js.add("msg", msg, noQuates);
		return js.toString();
	}

	public EscapeOp escape = EscapeOp.Default;
	/// <summary>
	/// convert filed to lower
	/// <para>是否将名称转为小写</para>
	/// </summary>
	public boolean isConvertNameToLower = false;
	/// <summary>
	/// convert enum to string
	/// <para>是否将枚举转字符串</para>
	/// </summary>
	public boolean isConvertEnumToString = false;
	/// <summary>
	/// convert enum to DescriptionAttribute
	/// <para>是否将枚举转属性描述</para>
	/// </summary>
	public boolean isConvertEnumToDescription = false;
	/// <summary>
	/// formate datetime
	/// <para>日期的格式化（默认：yyyy-MM-dd HH:mm:ss）</para>
	/// </summary>
	public String dateTimeFormatter = "yyyy-MM-dd HH:mm:ss";
	private final String brFlag = "[#<br>]";

	public boolean getSuccess()
	{
		return rowCount > 0;
	}
	public String errorMsg = "";

	public int rowCount = 0;
	public int totalCount;

	private ArrayList<String> bodyItems = new ArrayList<String>(128);
	private StringBuilder headText = new StringBuilder();
	private StringBuilder footText = new StringBuilder();

	/// <summary>
	/// flag a json is end and start a new json
	/// <para> 添加完一个Json数据后调用此方法换行</para>
	/// </summary>
	public void addBr()
	{
		bodyItems.add(brFlag);
		rowCount++;
	}

	/// <summary>
	/// attach json data (AddHead must be true)
	/// <para>添加底部数据（只有AddHead为true情况才能添加数据）</para>
	/// </summary>
	public void addFoot(String name, String value)
	{
		addFoot(name, value, false);
	}

	public void addFoot(String name, String value, boolean noQuotes)
	{
		if(_AddHead)
		{
			footText.append(",");
			footText.append(format(name, value, noQuotes));
		}
	}

	/// <summary>
	/// add json key value
	/// <para>添加一个字段的值</para>
	/// </summary>
	public void add(String name, String value)
	{
		bodyItems.add(format(name, value, false));
	}

	/// <param name="noQuotes">value is no quotes
	/// <para>值不带引号</para></param>
	public void add(String name, String value, boolean noQuotes)
	{
		bodyItems.add(format(name, value, noQuotes));
	}
	//     public void add(String name, object value)
	//     {
	//         if (value != null)
	//         {
	//        	 String v = null;
	//             Type t = value.GetType();
	//             if (t.IsEnum)
	//             {
	//                 bool descriptionNoValue = true;
	//                 if (IsConvertEnumToDescription)
	//                 {
	//                     FieldInfo field = t.GetField(value.ToString());
	//                     if (field != null)
	//                     {
	//                         DescriptionAttribute da = Attribute.GetCustomAttribute(field, typeof(DescriptionAttribute)) as DescriptionAttribute;
	//                         if (da != null)
	//                         {
	//                             Add(name, da.Description, false);
	//                             descriptionNoValue = false;
	//                         }
	//                     }
	//
	//                 }
	//                 if (descriptionNoValue)
	//                 {
	//                     if (IsConvertEnumToString)
	//                     {
	//                         Add(name, value.ToString(), false);
	//                     }
	//                     else
	//                     {
	//                         Add(name, ((int)value).ToString(), true);
	//                     }
	//                 }
	//
	//             }
	//             else
	//             {
	//                 DataGroupType group = DataType.GetGroup(DataType.GetSqlType(t));
	//                 bool noQuotes = group == DataGroupType.Number || group == DataGroupType.Bool;
	//                 if (group == DataGroupType.Object)
	//                 {
	//                     v = ToJson(value);
	//                 }
	//                 else
	//                 {
	//                     v = Convert.ToString(value);
	//                     if (group == DataGroupType.Bool)
	//                     {
	//                         v = v.ToLower();
	//                     }
	//                 }
	//
	//                 Add(name, v, noQuotes);
	//             }
	//         }
	//         else
	//         {
	//             Add(name, "null", true);
	//         }
	//     }
	private String format(String name, String value, boolean children)
	{
		if(value==null)
		{
			value ="";
		}
		children = children && !string.IsNullOrEmpty(value);
		if (!children && value.length() > 1 &&
				((value.charAt(0) == '{' && value.charAt(value.length() - 1) == '}') || (value.charAt(0)  == '[' && value.charAt(value.length() - 1) == ']')))
		{
			children = isJson(value);
		}
		if (!children)
		{
			value=JsonEscape.enEscape(value,escape);
		}
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(name);
		sb.append("\":");
		sb.append(!children ? "\"" : "");
		sb.append(value);
		sb.append(!children ? "\"" : "");
		return sb.toString();
		//return "\"" + name + "\":" + (!children ? "\"" : "") + value + (!children ? "\"" : "");//;//.Replace("\",\"", "\" , \"").Replace("}{", "} {").Replace("},{", "}, {")
	}

	/// <summary>
	/// out json result
	/// <para>输出Json字符串</para>
	/// </summary>
	public String toString()
	{
		int capacity = 100;
		if (bodyItems.size() > 0)
		{
			capacity = bodyItems.size() * bodyItems.get(0).length();
		}
		StringBuilder sb = new StringBuilder(capacity);//

		if (_AddHead)
		{
			if (headText.length() == 0)
			{
				sb.append("{");
				sb.append("\"rowcount\":");
				sb.append(rowCount);
				sb.append(",");

				sb.append("\"total\":");
				sb.append(totalCount);
				sb.append(",");

				sb.append("\"errorMsg\":\"");
				sb.append(errorMsg);
				sb.append("\",");

				sb.append("\"success\":");
				sb.append(rowCount>0?"true":"false");
				sb.append(",");
				sb.append("\"rows\":");
			}
			else
			{
				sb.append(headText.toString());
			}
		}
		if (bodyItems.size() == 0)
		{
			if (_AddHead)
			{
				sb.append("[]");
			}
		}
		else
		{
			if (bodyItems.get(bodyItems.size() - 1) != brFlag)
			{
				addBr();
			}
			if (_AddHead || rowCount > 1)
			{
				sb.append("[");
			}
			char left = '{', right = '}';
			String[] items = bodyItems.get(0).split(":");
			if (bodyItems.get(0) != brFlag && (items.length == 1 || !string.Trim(bodyItems.get(0),'"').contains("\"")))
			{
				//说明为数组
				left = '[';
				right = ']';
			}
			sb.append(left);
			int index = 0;
			for (String val : bodyItems)
			{
				index++;

				if (val != brFlag)
				{
					sb.append(val);
					sb.append(",");
				}
				else
				{
					if (sb.charAt(sb.length() - 1) == ',')
					{
						sb.delete(sb.length() - 1, 1);//性能优化（内部时，必须多了一个“，”号）。
					}
					sb.append(right);
					sb.append(",");
					if (index < bodyItems.size())
					{
						sb.append(left);
					}
				}
			}
			if (sb.charAt(sb.length() - 1) == ',')
			{
				sb.delete(sb.length() - 1, 1);
			}
			if (_AddHead || rowCount > 1)
			{
				sb.append("]");
			}
		}
		if (_AddHead)
		{
			sb.append(footText.toString());
			sb.append("}");
	}
		return sb.toString();
	}

	/// <param name="arrayEnd">end with [] ?</param>
	/// <returns></returns>
	public String toString(boolean arrayEnd)
	{
		String result = toString();
		if (arrayEnd && !result.startsWith("["))
		{
			result = '[' + result + ']';
		}
		return result;
	}
}
