package taurus.mvc.json;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import taurus.mvc.tool.ConvertTool;
import taurus.mvc.tool.Debug;
import taurus.mvc.tool.string;
class JsonSplit {


	public static boolean isJson(String json)
	{
		return isJson(json, false);
	}
	public static boolean isJson(String json, boolean isStrictMode)
	{
		SplitResult result=isJsonWithError(json,isStrictMode);
		return result.IsJson;
	}
	private static SplitResult isJsonWithError(String json, boolean isStrictMode)
	{
		SplitResult result=new SplitResult();

		if (string.IsNullOrEmpty(json) || json.length() < 2 ||
				((json.charAt(0) != '{' && json.charAt(json.length() - 1) != '}') && (json.charAt(0) != '[' && json.charAt(json.length() - 1) != ']')))
		{
			return result;
		}
		CharState cs = new CharState(isStrictMode);
		for (int i = 0; i < json.length(); i++)
		{
			//char c = ;
			if (cs.IsKeyword(json.charAt(i)) && cs.childrenStart)//���ùؼ�����״̬��
			{
				int[] res=GetValueLength(isStrictMode, json, i, true);
				int err=res[1];
				int length = res[0];
				cs.childrenStart = false;
				if (err > 0)
				{
					result.errIndex = i + err;
					return result;
				}
				i = i + length - 1;
			}
			if (cs.isError)
			{
				result.errIndex = i;
				return result;
			}
		}

		result.IsJson= !cs.arrayStart && !cs.jsonStart; //ֻҪ���������رգ���ʧ��
		return result;
	}
	/// <summary>
	/// ��ȡֵ�ĳ��ȣ���JsonֵǶ����"{"��"["��ͷʱ�������Ż���
	/// </summary>
	private static int[] GetValueLength(boolean isStrictMode, String json, int startIndex, boolean breakOnErr)
	{
		int[] result=new int[2];
		int len = json.length() - 1 - startIndex;
		if (!string.IsNullOrEmpty(json))
		{
			CharState cs = new CharState(isStrictMode);
			char c;
			for (int i = startIndex; i < json.length(); i++)
			{
				c = json.charAt(i);
				if (!cs.IsKeyword(c))//���ùؼ�����״̬��
				{
					if (!cs.jsonStart && !cs.arrayStart)//json�������ֲ������飬���˳���
					{
						break;
					}
				}
				else if (cs.childrenStart)//�����ַ���ֵ״̬�¡�
				{
					int[] res= GetValueLength(isStrictMode, json, i, breakOnErr);//�ݹ���ֵ������һ�����ȡ�����
					int length =res[0];
					result[1]=res[1];
					cs.childrenStart = false;
					cs.valueStart = 0;
					i = i + length - 1;
				}
				if (breakOnErr && cs.isError)
				{
					result[1] = i;
					result[0]=i - startIndex;
					return result;
				}
				if (!cs.jsonStart && !cs.arrayStart)//��¼��ǰ����λ�á�
				{
					len = i + 1;//���ȱ�����+1
					len = len - startIndex;
					break;
				}
			}
		}
		result[0]=len;
		return result;
	}

	public static LinkedHashMap<String, String> split(String json)
	{
		List<LinkedHashMap<String, String>> result=splitArray(json, 1);
		if(result!=null && result.size()>0)
		{
			return result.get(0);
		}
		return new LinkedHashMap<String, String>();
	}

	public static List<LinkedHashMap<String, String>> splitArray(String json)
	{
		return splitArray(json, 0);
	}

	public static List<LinkedHashMap<String, String>> splitArray(String json, int topN)
	{
		List<LinkedHashMap<String, String>> result = new ArrayList<LinkedHashMap<String, String>>();

		if (!string.IsNullOrEmpty(json))
		{
			LinkedHashMap<String, String> dic = new LinkedHashMap<String, String>();
			//string key = string.Empty;
			StringBuilder key = new StringBuilder(32);
			StringBuilder value = new StringBuilder();
			CharState cs = new CharState(false);
			try
			{

				for (int i = 0; i < json.length(); i++)
				{
					char c = json.charAt(i);
					if (!cs.IsKeyword(c))//���ùؼ�����״̬��
					{
						if (cs.jsonStart)//Json�����С�����
						{
							if (cs.keyStart > 0)
							{
								key.append(c);
							}
							else if (cs.valueStart > 0)
							{
								value.append(c);
							}
						}
						else if (!cs.arrayStart)//json�������ֲ������飬���˳���
						{
							break;
						}
					}
					else if (cs.childrenStart)//�����ַ���ֵ״̬�¡�
					{
						int[] res= GetValueLength(false, json, i, false);//�Ż����ٶȿ���10��
						//int temp;
						int length =res[0];

						value.setLength(0);
						value.append(json.substring(i, i+length));
						cs.childrenStart = false;
						cs.valueStart = 0;
						cs.setDicValue = true;
						i = i + length - 1;
					}
					if (cs.setDicValue)//���ü�ֵ�ԡ�
					{
						if (key.length() > 0)
						{
							String k = key.toString();
							if (!dic.containsKey(k))
							{
								String val = value.toString();
								Boolean isNull = json.charAt(i-5)== ':' && json.charAt(i)!= '"' && value.length() == 4 && val == "null";
								if (isNull)
								{
									val = "";
								}
								dic.put(k, val);
							}
						}
						cs.setDicValue = false;
						key.setLength(0);
						value.setLength(0);
					}

					if (!cs.jsonStart && dic.size() > 0)
					{
						result.add(dic);
						if (topN > 0 && result.size() >= topN)
						{
							return result;
						}
						if (cs.arrayStart)//�������顣
						{
							dic = new LinkedHashMap<String, String>();
						}
					}
				}
			}
			catch (Exception err)
			{
				Debug.log(err, "JsonSplit.Split");
			}
			finally
			{
				key = null;
				value = null;
			}
		}
		return result;
	}

	/// <summary>
	/// ��json����ֳ��ַ���List
	/// </summary>
	/// <param name="jsonArray">["a,","bbb,,"]</param>
	/// <returns></returns>
	static List<String> SplitEscapeArray(String jsonArray)
	{
		if (!string.IsNullOrEmpty(jsonArray))
		{
			jsonArray =string.Trim(jsonArray,' ', '[', ']');//["a,","bbb,,"]
			List<String> list = new ArrayList<String>();
			if (jsonArray.length() > 0)
			{
				String[] items = jsonArray.split(",");
				String objStr = "";
				for (String value : items)
				{
					String item = string.Trim(value,'\r', '\n', '\t', ' ');
					if (objStr.equals(""))
					{
						objStr = item;
					}
					else
					{
						objStr += "," + item;
					}
					char firstChar = objStr.charAt(0);
					if (firstChar == '"' || firstChar == '\'')
					{
						//���˫���ŵ�����
						if (GetCharCount(objStr, firstChar) % 2 == 0)//���ų�˫
						{
							list.add(string.Trim(objStr,firstChar).replace("\\" + firstChar,String.valueOf(firstChar)));
							objStr = "";
						}
					}
					else
					{
						list.add(item);
						objStr = "";
					}
				}
			}
			return list;

		}
		return null;
	}
	/// <summary>
	/// ��ȡ�ַ����ַ������ֵĴ���
	/// </summary>
	/// <param name="c"></param>
	/// <returns></returns>
	private static int GetCharCount(String item, char c)
	{
		int num = 0;
		for (int i = 0; i < item.length(); i++)
		{
			if (item.charAt(i) == '\\')
			{
				i++;
			}
			else if (item.charAt(i) == c)
			{
				num++;
			}
		}
		return num;
	}
	public static <T> T getValue(String json, String key,Class<T> object)
	{
		String v = getValue(json, key);
		return ConvertTool.tryChangeType(v,object);
	}
	public static <T> T getValue(String json, String key, EscapeOp op,Class<T> object)
	{
		String v = getValue(json, key, op);
		return ConvertTool.tryChangeType(v,object);
	}
	public static String getValue(String json, String key)
	{
		return getValue(json, key, EscapeOp.Default);
	}
	public static String getValue(String json, String key, EscapeOp op)
	{
		String value =JsonSplit.GetValueWithoutEscape(json, key);
		return JsonEscape.unEscape(value, op);
	}
	private static String GetValueWithoutEscape(String json, String key)
	{
		String result = "";
		if (!string.IsNullOrEmpty(json))
		{
			String[] items = key.split("\\.");
			String fKey = items[0];
			int i = ConvertTool.tryChangeType(fKey, int.class);
			int fi = key.indexOf('.');

			if (i>0 || fKey.equals("0"))//����
			{
				List<LinkedHashMap<String, String>> jsonList = splitArray(json, i + 1);
				if (i < jsonList.size())
				{
					LinkedHashMap<String, String> numJson = jsonList.get(i);
					if (items.length == 1)
					{
						result = toJson(numJson);
					}
					else if (items.length == 2) // 0.xxx
					{
						String sKey = items[1];
						if (numJson.containsKey(sKey))
						{
							result = numJson.get(sKey);
						}
					}
					else
					{
						return GetValueWithoutEscape(toJson(numJson), key.substring(fi + 1));
					}
				}
			}
			else // ������
			{
				LinkedHashMap<String, String> jsonDic = split(json);//ֻȡtop1
				if (jsonDic != null && jsonDic.size() > 0)
				{
					if (items.length == 1)
					{
						if (jsonDic.containsKey(fKey))
						{
							result = jsonDic.get(fKey);
						}
					}
					else if (jsonDic.containsKey(fKey)) // ȡ�Ӽ�
					{
						return GetValueWithoutEscape(jsonDic.get(fKey), key.substring(fi + 1));
					}
				}
			}
		}
		return result;
	}

	public static String toJson(Map<String, String> map) {

		if(map==null || map.size()==0){return "{}";}
		JsonHelper js=new JsonHelper();
		for (Entry<String, String> kv : map.entrySet()) {
			js.add(kv.getKey(), kv.getValue());
		}
		return js.toString();
	}
	
//	public static <T> T  toEntity(String json,Class<T> t) {
//		
//		return null;
//	}
	
	
}
