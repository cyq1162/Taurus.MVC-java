package taurus.microservice;

 /**
 * һ�����ڿͻ��ˣ�������RPC����
 * @author ·������
 *
 */
public class Rpc {

	private Rpc() {
		
	}
	/**
	 * ��ȡ΢�������ڵ�����
	 * @param name ΢����ע������
	 * @return
	 */
	 public static String getHost(String name)
     {
        return Client.getHost(name);
     }
}
