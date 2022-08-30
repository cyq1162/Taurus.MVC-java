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
 * ΢���� �ͻ��� ����
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
                	 MsConfig.set(MsConst.MicroServiceAppRunUrl, host.toLowerCase());//���õ�ǰ�������е�������ַ��
                 }
                 new Thread(new Runnable() {
					public void run() {
						Random random=new Random();
						 while (true)
			                {
			                    try
			                    {
			                        AfterRegHost(RegHost());
			                        Thread.sleep(5000+random.nextInt(5000));//5-10��ѭ��1�Ρ�
			                    }
			                    catch (Exception err)
			                    {
			                    	try
			                    	{
			                    		Thread.sleep(5000+random.nextInt(5000));//5-10��ѭ��1�Ρ�
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
    /// ΢����Ӧ�����ĵ��ã�����ע�ᡣ
    /// </summary>
    /// <returns></returns>
	private static String RegHost() {
		String regUrl = MsConfig.getClientRegUrl() + "/MicroService/Reg";
		StringBuffer stringBuffer = new StringBuffer();
		HttpURLConnection conn=null;
		try {
			URL url = new URL(regUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setReadTimeout(5 * 1000);
			conn.setRequestProperty(MsConst.MicroServiceHeaderKey, MsConfig.getClientKey());
			conn.setRequestProperty("Referer", MsConfig.getAppRunUrl());
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// ��������ʽ��Ĭ����get
			conn.setRequestMethod("POST");// ��д��POST
			// �����������
			conn.setDoOutput(true);// ������������ύ���ݡ�
			// ��������д���� "&page=1"
			String data = "name={0}&host={1}&version={2}";
			data = string.Format(data, MsConfig.getClientName(), MsConfig.getAppRunUrl(),
					MsConfig.getClientVersion().toString());
			conn.getOutputStream().write(data.getBytes());// ��������ŵ�������
			if (conn.getResponseCode() == 200) {
				InputStream inputStream = conn.getInputStream();
				byte[] bytes = new byte[1024];
				int len = 0;
				while ((len = inputStream.read(bytes)) != -1) {
					stringBuffer.append(new String(bytes, 0, len));
				}
			}
			conn.disconnect();
			Client.RegCenterIsLive=true;
		} catch (Exception err) {
			Client.RegCenterIsLive=false;
			if(!string.IsNullOrEmpty(Client.getHost2()))
			{
				MsConfig.set(MsConfig.getClientRegUrl(), Client.getHost2());//�л������ÿ�
			}
			stringBuffer.append(err.getMessage());
		}
		finally {
			if(conn!=null)
			{
				conn.disconnect();
			}
		}
		return stringBuffer.toString();
	}

	/**
	 * ��ȡ��ǰ�汾�š�
	 * @return
	 */
	private static String getVersion() {
		return Run.class.getPackage().getImplementationVersion();
	}
}
