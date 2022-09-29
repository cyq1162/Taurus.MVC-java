package taurus.microservice;

 /**
 * 一般用于客户端：服务间的RPC调用
 * @author 路过秋天
 *
 */
public class Rpc {

	private Rpc() {
		
	}
	/**
	 * 获取微服务所在的主机
	 * @param name 微服务注册名称
	 * @return
	 */
	 public static String getHost(String name)
     {
        return Client.getHost(name);
     }
}
