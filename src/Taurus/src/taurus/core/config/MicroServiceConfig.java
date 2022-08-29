package taurus.core.config;

import java.util.HashMap;
import java.util.Map;

import taurus.core.tool.ConvertTool;
import taurus.core.tool.string;

/**
 * 读写Taurus.Mvc 微服务应用中心可用的配置类库。
 * @author 路过秋天 ：教程博客：https://www.cnblogs.com/cyq1162
 * 					  开源地址：https://github.com/cyq1162/Taurus.MVC-java
 *
 */
public class MicroServiceConfig {

	private static Map<String, String> keyValue=new HashMap<String, String>();
	/**
	 * 可以对配置项设置初始值
	 * @param name 配置项名称
	 * @param value 值
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
	 * 微服务间调用密钥串【任意字符串，由注册中心统一给出】
	 * @return
	 */
	
	public static String getClientKey() {
		String key=keyValue.get(Const.MicroServiceClientKey);
		if(key==null){return "Taurus.MicroService";}
		return key;
	}

	/**
	 * 客户端模块名称，多个可以用“,”逗号分隔。
	 * 【示例：test 或绑定的域名：www.a.com】
	 * @return
	 */
	public static String getClientName() {
		return keyValue.get(Const.MicroServiceClientName);
	}

	/**
	 * 注册中心的WebUrl
	 * @return
	 */
	public static String getClientRegUrl() {
		return keyValue.get(Const.MicroServiceClientRegUrl);
	}

	/**
	 * 客户端模块版本号（用于版本间升级）【示例：1】
	 * @return
	 */
	public static int getClientVersion() {
		return ConvertTool.tryChangeType(keyValue.get(Const.MicroServiceClientVersion),int.class);
	}

	/**
	 * 当前Web程序运行时对外可访问的WebUrl【微服务初始启动会发送此WEbUrl注册地址】
	 * @return
	 */
	public static String getAppRunUrl() {
		return keyValue.get(Const.MicroServiceAppRunUrl);
	}
}
