package taurus.microservice;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import taurus.microservice.config.MsConfig;
import taurus.microservice.config.MsConst;
import taurus.mvc.http.HttpContext;
import taurus.mvc.json.JsonHelper;
import taurus.mvc.tool.ConvertTool;
import taurus.mvc.tool.Debug;
import taurus.mvc.tool.string;

/**
 * 微服务 客户端 启动【由系统内部调用】
 * @author 路过秋天
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
                	 MsConfig.set(MsConst.AppRunUrl, host.toLowerCase());//设置当前程序运行的请求网址。
                 }
                 new Thread(new Runnable() {
					public void run() {
						Random random=new Random();
						Debug.log("MicroService.Run.start.Thread.Runnable : Start");
						 while (true)
			                {
			                    try
			                    {
			                    	if(HttpContext.IsDestroyed)
			                    	{
			                    		Debug.log("MicroService.Run.start.Thread.Runnable : Stoped");
			                    		break;
			                    	}
			                    	afterRegHost(regHost());
			                        Thread.sleep(5000+random.nextInt(5000));//5-10秒循环1次。
			                    }
			                    catch (Exception err)
			                    {
			                    	Debug.log(err,"Run.start.Thread.Runnable");
			                    	try
			                    	{
			                    		Thread.sleep(5000+random.nextInt(5000));//5-10秒循环1次。
			                    	}catch (Exception e)
				                    {}
			                    }
			                }
					}
				}).start();
             }
         }
	}
	private static void afterRegHost(String result) {
		if (!string.IsNullOrEmpty(result) && JsonHelper.isSuccess(result))
        {
            long tick = JsonHelper.getValue(result, "tick",long.class);
            Client.setHost2(JsonHelper.getValue(result, "host2"));
//            if (!string.IsNullOrEmpty(Client.getHost2()))
//            {
//                IO.Write(MSConst.ClientHost2Path, Client.Host2);
//            }
//            else
//            {
//                IO.Delete(MSConst.ClientHost2Path);
//            }
            String host = JsonHelper.getValue(result, "host");
            if (!string.IsNullOrEmpty(host) && !host.equals(MsConfig.getClientRegUrl()))
            {
            	MsConfig.set(MsConst.ClientRegUrl, host);//从备份请求切回主程序
            }
            if (tick > Client.Tick)
            {
                afterGetList(getHostList());
            }
        }
	}
	 private static void afterGetList(String result)
     {
		 if (!string.IsNullOrEmpty(result) && JsonHelper.isSuccess(result))
		 {
			 String host2 = JsonHelper.getValue(result, "host2");
			 String host = JsonHelper.getValue(result, "host");
			 if (!string.IsNullOrEmpty(host) && !host.equals(MsConfig.getClientRegUrl()))
			 {
				 MsConfig.set(MsConst.ClientRegUrl, host);//从备份请求切回主程序
			 }
			 long tick = JsonHelper.getValue(result, "tick",long.class);

			 if (Client.Tick > tick) { return; }
			 Client.Tick = tick;
			 Client.setHost2(host2);


			 String json = JsonHelper.getValue(result, "msg");
			 if (!string.IsNullOrEmpty(json))
			 {
				
				Client._HostList = toEntity(json);
				 //                     IO.Write(MSConst.ClientHostListJsonPath, json);
//				 Debug.log(Rpc.getHost("ms"));
//				 Debug.log(Rpc.getHost("ms"));
			 }
		 }
     }
	/// <summary>
    /// 微服务应用中心调用：服务注册。
    /// </summary>
    /// <returns></returns>
	private static String regHost() {
		String regUrl = MsConfig.getClientRegUrl() + "/microservice/reg";
		StringBuffer stringBuffer = new StringBuffer();
		HttpURLConnection conn=null;
		try {
			URL url = new URL(regUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setReadTimeout(5 * 1000);
			conn.setRequestProperty(MsConst.HeaderKey, MsConfig.getClientKey());
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
				byte[] bytes = new byte[inputStream.available()];
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
				MsConfig.set(MsConst.ClientRegUrl, Client.getHost2());//切换到备用库
			}
			stringBuffer.append(err.getMessage());
		}
		finally {
			if(conn!=null)
			{
				conn.disconnect();
			}
			conn=null;
		}
		return stringBuffer.toString();
	}
	private static String getHostList() {
		String regUrl = MsConfig.getClientRegUrl() + "/microservice/getlist?tick="+Client.Tick;
		StringBuffer stringBuffer = new StringBuffer();
		HttpURLConnection conn=null;
		try {
			URL url = new URL(regUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setReadTimeout(5 * 1000);
			conn.setRequestProperty(MsConst.HeaderKey, MsConfig.getClientKey());
			conn.setRequestProperty("Referer", MsConfig.getAppRunUrl());
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// 设置允许输出
			conn.setDoOutput(true);// 允许向服务器提交数据、
//			// 获得输出流写数据 "&page=1"
//			String data = "name={0}&host={1}&version={2}";
//			data = string.Format(data, MsConfig.getClientName(), MsConfig.getAppRunUrl(),
//					MsConfig.getClientVersion().toString());
//			conn.getOutputStream().write(data.getBytes());// 请求参数放到请求体
			if (conn.getResponseCode() == 200) {
				InputStream inputStream = conn.getInputStream();
				byte[] bytes = new byte[inputStream.available()];
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
				MsConfig.set(MsConst.ClientRegUrl, Client.getHost2());//切换到备用库
			}
			stringBuffer.append(err.getMessage());
		}
		finally {
			if(conn!=null)
			{
				conn.disconnect();
			}
			conn=null;
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
	private static HashMap<String, HostInfo[]> toEntity(String json) {
		HashMap<String, String> map=JsonHelper.split(json);
		if(map!=null && map.size()>0)
		{
			HashMap<String, HostInfo[]> hMap=new HashMap<String, HostInfo[]>();
			for (Map.Entry<String,String> kv : map.entrySet()) {
				List<LinkedHashMap<String, String>> list=JsonHelper.splitArray(kv.getValue());
				HostInfo[] hInfos=null;
				if(list!=null && list.size()>0)
				{
					hInfos=new HostInfo[list.size()];
					for (int i = 0; i < list.size(); i++) {
						LinkedHashMap<String, String> map2 = list.get(i);
						HostInfo hInfo=new HostInfo();
						hInfo.Host=map2.get("Host");
						hInfo.Version=ConvertTool.tryChangeType(map2.get("Version"), int.class);
						hInfo.RegTime=ConvertTool.tryChangeType(map2.get("RegTime"), Date.class);
						hInfos[i]=hInfo;
						
					}
				}
				hMap.put(kv.getKey(), hInfos);
			}
			return hMap;
		}
		return null;
	}
}
