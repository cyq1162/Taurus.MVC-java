package taurus.core.config;

import taurus.core.tool.ConvertTool;

/**
 * 读写Taurus.Mvc 微服务应用中心可用的配置类库。
 * @author 路过秋天 ：教程博客：https://www.cnblogs.com/cyq1162
 * 					  开源地址：https://github.com/cyq1162/Taurus.MVC-java
 *
 */
public class MicroServiceConfig {

	/**
	 * 可以对配置项设置初始值
	 * @param name 配置项名称
	 * @param value 值
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
	 * 微服务间调用密钥串【任意字符串，由注册中心统一给出】
	 * @return
	 */
	
	public static String getClientKey() {
		return _ClientKey;
	}
	private static String _ClientName="";
	/**
	 * 客户端模块名称，多个可以用“,”逗号分隔。
	 * 【示例：test 或绑定的域名：www.a.com】
	 * @return
	 */
	public static String getClientName() {
		return _ClientName;
	}
	private static String _ClientRegUrl="";
	/**
	 * 注册中心的WebUrl
	 * @return
	 */
	public static String getClientRegUrl() {
		return _ClientRegUrl;
	}
	private static int _ClientVersion=0;
	/**
	 * 客户端模块版本号（用于版本间升级）【示例：1】
	 * @return
	 */
	public static int getClientVersion() {
		return _ClientVersion;
	}
	private static String _AppRunUrl="";
	/**
	 * 当前Web程序运行时对外可访问的WebUrl【微服务初始启动会发送此WEbUrl注册地址】
	 * @return
	 */
	public static String getAppRunUrl() {
		return _AppRunUrl;
	}
}
