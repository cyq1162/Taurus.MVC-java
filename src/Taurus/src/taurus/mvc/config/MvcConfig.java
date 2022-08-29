package taurus.mvc.config;

import java.util.HashMap;
import java.util.Map;

import taurus.mvc.tool.ConvertTool;
import taurus.mvc.tool.string;

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
			name=name.replace("taurus.", "mvc.");
			keyValue.put(name.toLowerCase(), value);
		}
	}
	public static void init(String name,String value) {
		name=name.toLowerCase();
		name=name.replace("taurus.", "mvc.");
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
		return ConvertTool.tryChangeType(keyValue.get(MvcConst.MvcIsAllowCORS), boolean.class) ;
	}
	/**
	 * ·��ģʽ�������mvc.routemode
	 * 0������/���� �����⣺����������Ŀ����һ��DefaultController��������
	 * 1��������/����/���� 
	 * 2��ģ����/������/����/����
	 * @return Ĭ�ϣ�1
	 */
	public static Integer getRouteMode() {
		String routeMode=keyValue.get(MvcConst.MvcRouteMode);
		if(routeMode==null){return 1;}
		return ConvertTool.tryChangeType(routeMode, Integer.class) ;
	}

	/**
	 * ����������Jar�����ƣ������mvc.controllerjarnames
	 * ���������������һ���ⲿ��Ŀ���������ã����������ƣ�������á�,�����ŷָ���
	 * ʾ������MyCtroller.jar,projectController.jar��
	 * @return 
	 */
	public static String getControllerJarNames() {
		return keyValue.get(MvcConst.MvcControllerJarNames);
	}
}
