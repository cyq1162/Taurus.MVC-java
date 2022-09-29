package taurus.microservice.config;

public class MsConst {
	public final static String ClientKey="microservice.client.key";
	public final static String ClientName="microservice.client.name";
	public final static String ClientRegUrl="microservice.client.regurl";
	public final static String ClientVersion="microservice.client.version";
	public final static String AppRunUrl="microservice.app.runurl";
	/**
	 * 微服务间发送的请求头 microservice = MicroService.getClientKey()
	 */
	public final static String HeaderKey="mskey";
}