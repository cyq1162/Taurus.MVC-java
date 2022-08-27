package taurus.core.config;

import taurus.core.tool.ConvertTool;

/**
 * ��дTaurus.Mvc���õ�������⡣
 * @author ·������ ���̳̲��ͣ�https://www.cnblogs.com/cyq1162
 * 					  ��Դ��ַ��https://github.com/cyq1162/Taurus.MVC-java
 *
 */
public class MvcConfig {
	
	/**
	 * ���Զ����������ó�ʼֵ
	 * @param name ����������
	 * @param value ֵ
	 */
	public static void init(String name, String value) {
		try {
			switch (name.toLowerCase()) {
			case "mvc.isallowcors":
				_IsAllowCORS = (Boolean)ConvertTool.changeType(value, boolean.class);
				break;
			case "mvc.routemode":
				_RouteMode = (int)ConvertTool.changeType(value, int.class);
				break;
			case "mvc.controllerjarnames":
				_ControllerJarNames = value;
				break;
				
			default:
				break;
			}
		} catch (Exception e) {
		
		}
	}
	
	
	private static Boolean _IsAllowCORS=true;
	/**
	 * �Ƿ�������������mvc.isallowcors
	 * @return Ĭ��true
	 */
	public static Boolean getIsAllowCORS() {
		return _IsAllowCORS;
	}
	private static int _RouteMode=1;
	/**
	 * ·��ģʽ�������mvc.routemode
	 * 0������/���� �����⣺������Ŀֻ��һ����DefaultController��������
	 * 1��������/����/���� ��Ĭ�ϣ�
	 * 2��ģ����/������/����/����
	 * @return
	 */
	public static int getRouteMode() {
		return _RouteMode;
	}
	private static String _ControllerJarNames="";
	/**
	 * ����������Jar�����ƣ������mvc.controllerjarnames
	 * ���������������һ���ⲿ��Ŀ���������ã����������ƣ�������á�,�����ŷָ���
	 * ʾ������MyCtroller.jar,projectController.jar��
	 * @return
	 */
	public static String getControllerJarNames() {
		return _ControllerJarNames;
	}
}
