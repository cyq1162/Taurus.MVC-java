package taurus.microservice;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import taurus.microservice.config.MsConfig;
import taurus.microservice.config.MsConst;
import taurus.mvc.http.HttpContext;
import taurus.mvc.tool.string;

/**
 * 微服务 客户端 启动
 * @author 123
 *
 */
public class Run {

	private static Boolean isStart = false;
	public static void start(String host) {
		 if (!isStart)
         {
             isStart = true;
             if (Client.getIsRunAsClient())
             {
            	 HttpContext.Current.log("Taurus.MicroService.Client V"+getVersion()+" Start : ");
                 if (string.IsNullOrEmpty(MsConfig.getAppRunUrl()))
                 {
                	 MsConfig.set(MsConst.MicroServiceAppRunUrl, host.toLowerCase());//设置当前程序运行的请求网址。
                 }
                 new Thread(new Runnable() {
					public void run() {
						 while (true)
			                {
			                    try
			                    {
			                        AfterRegHost(RegHost());
			                        Thread.sleep(5000+new Random().nextInt(5000));//5-10秒循环1次。
			                    }
			                    catch (Exception err)
			                    {
			                    	try
			                    	{
			                    		Thread.sleep(5000+new Random().nextInt(5000));//5-10秒循环1次。
			                    	}catch (Exception e)
				                    {}
			                    }
			                }
					}
				}).start();
             }
         }
	}
	private static void AfterRegHost(String result) {
		
	}
	/// <summary>
    /// 微服务应用中心调用：服务注册。
    /// </summary>
    /// <returns></returns>
	private static String RegHost() {
		String regUrl = MsConfig.getClientRegUrl() + "/MicroService/Reg";
		StringBuffer stringBuffer = new StringBuffer();
		try {
			URL url = new URL(regUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setReadTimeout(5 * 1000);
			conn.setRequestProperty(MsConst.MicroServiceHeaderKey, MsConfig.getClientKey());
			conn.setRequestProperty("Referer", MsConfig.getAppRunUrl());
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// 设置请求方式，默认是get
			conn.setRequestMethod("POST");// 大写的POST
			// 设置允许输出
			conn.setDoOutput(true);// 允许向服务器提交数据、
			// 获得输出流写数据 "&page=1"
			String data = "name={0}&host={1}&version={2}";
			data = string.Format(data, MsConfig.getClientName(), MsConfig.getAppRunUrl(),
					MsConfig.getClientVersion().toString());
			conn.getOutputStream().write(data.getBytes());// 请求参数放到请求体
			if (conn.getResponseCode() == 200) {
				InputStream inputStream = conn.getInputStream();
				byte[] bytes = new byte[1024];
				int len = 0;
				while ((len = inputStream.read(bytes)) != -1) {
					stringBuffer.append(new String(bytes, 0, len));
				}
			}
			Client.RegCenterIsLive=true;
		} catch (Exception err) {
			Client.RegCenterIsLive=false;
			if(!string.IsNullOrEmpty(Client.getHost2()))
			{
				MsConfig.set(MsConfig.getClientRegUrl(), Client.getHost2());//切换到备用库
			}
			stringBuffer.append(err.getMessage());
		}
		return stringBuffer.toString();
	}

	/**
	 * 获取当前版本号。
	 * @return
	 */
	private static String getVersion() {
		return Run.class.getPackage().getImplementationVersion();
	}
}
