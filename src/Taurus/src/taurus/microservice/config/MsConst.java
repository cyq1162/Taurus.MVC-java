package taurus.microservice.config;

public class MsConst {
	public final static String MicroServiceClientKey="microservice.client.key";
	public final static String MicroServiceClientName="microservice.client.name";
	public final static String MicroServiceClientRegUrl="microservice.client.regurl";
	public final static String MicroServiceClientVersion="microservice.client.version";
	public final static String MicroServiceAppRunUrl="microservice.app.runurl";
	/**
	 * 微服务间发送的请求头 microservice = MicroService.getClientKey()
	 */
	public final static String MicroServiceHeaderKey="mskey";
}