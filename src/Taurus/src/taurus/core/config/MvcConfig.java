package taurus.core.config;

import java.util.HashMap;
import java.util.Map;

import taurus.core.tool.ConvertTool;
import taurus.core.tool.string;

/**
 * ��дTaurus.Mvc���õ�������⡣
 * @author ·������ ���̳̲��ͣ�https://www.cnblogs.com/cyq1162
 * 					  ��Դ��ַ��https://github.com/cyq1162/Taurus.MVC-java
 *
 */
public class MvcConfig {
	
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
	 * �Ƿ�������������mvc.isallowcors
	 * @return Ĭ�ϣ�true
	 */
	public static Boolean getIsAllowCORS() {
		return ConvertTool.tryChangeType(keyValue.get(Const.MvcIsAllowCORS), boolean.class) ;
	}
	/**
	 * ·��ģʽ�������mvc.routemode
	 * 0������/���� �����⣺����������Ŀ����һ��DefaultController��������
	 * 1��������/����/���� 
	 * 2��ģ����/������/����/����
	 * @return Ĭ�ϣ�1
	 */
	public static int getRouteMode() {
		String routeMode=keyValue.get(Const.MvcRouteMode);
		if(routeMode==null){return 1;}
		return ConvertTool.tryChangeType(routeMode, int.class) ;
	}

	/**
	 * ����������Jar�����ƣ������mvc.controllerjarnames
	 * ���������������һ���ⲿ��Ŀ���������ã����������ƣ�������á�,�����ŷָ���
	 * ʾ������MyCtroller.jar,projectController.jar��
	 * @return 
	 */
	public static String getControllerJarNames() {
		return keyValue.get(Const.MvcControllerJarNames);
	}
}
