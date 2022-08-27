package taurus.core.config;

import taurus.core.tool.ConvertTool;

/**
 * 读写Taurus.Mvc可用的配置类库。
 * @author 路过秋天 ：教程博客：https://www.cnblogs.com/cyq1162
 * 					  开源地址：https://github.com/cyq1162/Taurus.MVC-java
 *
 */
public class MvcConfig {
	
	/**
	 * 可以对配置项设置初始值
	 * @param name 配置项名称
	 * @param value 值
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
	 * 是否允许跨域：配置项：mvc.isallowcors
	 * @return 默认true
	 */
	public static Boolean getIsAllowCORS() {
		return _IsAllowCORS;
	}
	private static int _RouteMode=1;
	/**
	 * 路由模式：配置项：mvc.routemode
	 * 0：方法/参数 （特殊：整个项目只有一个：DefaultController控制器）
	 * 1：控制器/方法/参数 （默认）
	 * 2：模块名/控制器/方法/参数
	 * @return
	 */
	public static int getRouteMode() {
		return _RouteMode;
	}
	private static String _ControllerJarNames="";
	/**
	 * 控制器所在Jar包名称：配置项：mvc.controllerjarnames
	 * 如果控制器独立在一个外部项目被工程引用，则配置名称，多个可用“,”逗号分隔。
	 * 示例：”MyCtroller.jar,projectController.jar“
	 * @return
	 */
	public static String getControllerJarNames() {
		return _ControllerJarNames;
	}
}
