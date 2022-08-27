package taurus.core.config;

import taurus.core.tool.ConvertTool;

/**
 * ��дTaurus.Mvc ΢����Ӧ�����Ŀ��õ�������⡣
 * @author ·������ ���̳̲��ͣ�https://www.cnblogs.com/cyq1162
 * 					  ��Դ��ַ��https://github.com/cyq1162/Taurus.MVC-java
 *
 */
public class MicroServiceConfig {

	/**
	 * ���Զ����������ó�ʼֵ
	 * @param name ����������
	 * @param value ֵ
	 */
	public static void init(String name, String value) {
		try {
		switch (name.toLowerCase()) {
		case "microservice.client.key":
			_ClientKey = value;
			break;
		case "microservice.client.name":
			_ClientName = value;
			break;
		case "microservice.client.regurl":
			_ClientRegUrl = value;
			break;
		case "microservice.client.version":
			_ClientVersion = (int)ConvertTool.changeType(value, int.class);;
			break;
		case "microservice.app.runurl":
			_AppRunUrl = value;
			break;
		default:
			break;
		}
		} catch (Exception e) {
			
		}
	}
	private static String _ClientKey="Taurus.MicroService";
	/**
	 * ΢����������Կ���������ַ�������ע������ͳһ������
	 * @return
	 */
	
	public static String getClientKey() {
		return _ClientKey;
	}
	private static String _ClientName="";
	/**
	 * �ͻ���ģ�����ƣ���������á�,�����ŷָ���
	 * ��ʾ����test ��󶨵�������www.a.com��
	 * @return
	 */
	public static String getClientName() {
		return _ClientName;
	}
	private static String _ClientRegUrl="";
	/**
	 * ע�����ĵ�WebUrl
	 * @return
	 */
	public static String getClientRegUrl() {
		return _ClientRegUrl;
	}
	private static int _ClientVersion=0;
	/**
	 * �ͻ���ģ��汾�ţ����ڰ汾����������ʾ����1��
	 * @return
	 */
	public static int getClientVersion() {
		return _ClientVersion;
	}
	private static String _AppRunUrl="";
	/**
	 * ��ǰWeb��������ʱ����ɷ��ʵ�WebUrl��΢�����ʼ�����ᷢ�ʹ�WEbUrlע���ַ��
	 * @return
	 */
	public static String getAppRunUrl() {
		return _AppRunUrl;
	}
}
