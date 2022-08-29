package taurus.core.config;

import java.util.HashMap;
import java.util.Map;

import taurus.core.tool.ConvertTool;
import taurus.core.tool.string;

/**
 * 读写Taurus.Mvc可用的配置类库。
 * @author 路过秋天 ：教程博客：https://www.cnblogs.com/cyq1162
 * 					  开源地址：https://github.com/cyq1162/Taurus.MVC-java
 *
 */
public class MvcConfig {
	
	private static Map<String, String> keyValue=new HashMap<String, String>();
	/**
	 * 可以对配置项设置初始值
	 * @param name 配置项名称
	 * @param value 值
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
	 * 是否允许跨域：配置项：mvc.isallowcors
	 * @return 默认：true
	 */
	public static Boolean getIsAllowCORS() {
		return ConvertTool.tryChangeType(keyValue.get(Const.MvcIsAllowCORS), boolean.class) ;
	}
	/**
	 * 路由模式：配置项：mvc.routemode
	 * 0：方法/参数 （特殊：适用整个项目仅有一个DefaultController控制器）
	 * 1：控制器/方法/参数 
	 * 2：模块名/控制器/方法/参数
	 * @return 默认：1
	 */
	public static int getRouteMode() {
		String routeMode=keyValue.get(Const.MvcRouteMode);
		if(routeMode==null){return 1;}
		return ConvertTool.tryChangeType(routeMode, int.class) ;
	}

	/**
	 * 控制器所在Jar包名称：配置项：mvc.controllerjarnames
	 * 如果控制器独立在一个外部项目被工程引用，则配置名称，多个可用“,”逗号分隔。
	 * 示例：”MyCtroller.jar,projectController.jar“
	 * @return 
	 */
	public static String getControllerJarNames() {
		return keyValue.get(Const.MvcControllerJarNames);
	}
}
