package taurus.core.config;

import java.util.HashMap;
import java.util.Map;

import taurus.core.tool.ConvertTool;
import taurus.core.tool.string;

/**
 * ��дTaurus.Mvc ΢����Ӧ�����Ŀ��õ�������⡣
 * @author ·������ ���̳̲��ͣ�https://www.cnblogs.com/cyq1162
 * 					  ��Դ��ַ��https://github.com/cyq1162/Taurus.MVC-java
 *
 */
public class MicroServiceConfig {

	private static Map<String, String> keyValue=new HashMap<String, String>();
	/**
	 * ���Զ����������ó�ʼֵ
	 * @param name ����������
	 * @param value ֵ
	 */
	public static void set(String name, String value) {
		if(!string.IsNullOrEmpty(name) && !string.IsNullOrEmpty(value))
		{
			keyValue.put(name.toLowerCase(), value);
		}
	}
	public static void init(String name,String value) {
		name=name.toLowerCase();
		if(!keyValue.containsKey(name))
		{
			keyValue.put(name, value);
		}
	}

	/**
	 * ΢����������Կ���������ַ�������ע������ͳһ������
	 * @return
	 */
	
	public static String getClientKey() {
		String key=keyValue.get(Const.MicroServiceClientKey);
		if(key==null){return "Taurus.MicroService";}
		return key;
	}

	/**
	 * �ͻ���ģ�����ƣ���������á�,�����ŷָ���
	 * ��ʾ����test ��󶨵�������www.a.com��
	 * @return
	 */
	public static String getClientName() {
		return keyValue.get(Const.MicroServiceClientName);
	}

	/**
	 * ע�����ĵ�WebUrl
	 * @return
	 */
	public static String getClientRegUrl() {
		return keyValue.get(Const.MicroServiceClientRegUrl);
	}

	/**
	 * �ͻ���ģ��汾�ţ����ڰ汾����������ʾ����1��
	 * @return
	 */
	public static int getClientVersion() {
		return ConvertTool.tryChangeType(keyValue.get(Const.MicroServiceClientVersion),int.class);
	}

	/**
	 * ��ǰWeb��������ʱ����ɷ��ʵ�WebUrl��΢�����ʼ�����ᷢ�ʹ�WEbUrlע���ַ��
	 * @return
	 */
	public static String getAppRunUrl() {
		return keyValue.get(Const.MicroServiceAppRunUrl);
	}
}
