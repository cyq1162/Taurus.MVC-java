package taurus.microservice;

import java.util.HashMap;

import taurus.microservice.config.MsConfig;
import taurus.mvc.tool.string;

public class Client {
	
    /**
     * 当前程序是否作为客务端运行：微服务应用程序
     * @return
     */
	public static Boolean getIsRunAsClient()
    {
		return !string.IsNullOrEmpty(MsConfig.getClientName()) && 
		 !string.IsNullOrEmpty(MsConfig.getClientRegUrl()) && 
		 !MsConfig.getClientRegUrl().equals(MsConfig.getAppRunUrl());
    }
    /// <summary>
    /// 微服务应用程序 - 检测注册中心是否安在。
    /// </summary>
    public static boolean RegCenterIsLive = false;

    /// <summary>
    /// 读取：注册中心时的最后更新标识.
    /// </summary>
    public static long Tick = 0;//从注册中心读取的标识号【用于发送做对比】
    /// <summary>
    /// 读取：注册中心【存档】故障转移备用链接。
    /// </summary>
    static String _Host2 = null;
    /// <summary>
    /// 读取：从注册中心读取的备用链接
    /// </summary>
	public static String getHost2() {

//		if (_Host2 == null) {
//			_Host2 = IO.Read(Const.ClientHost2Path);// 首次读取，以便于恢复。
//		}
		return _Host2;

    }
    static HashMap<string, HostInfo[]> _HostList;
    /// <summary>
    /// 从微服务主程序端获取的微服务列表【用于微服务间内部调用运转】
    /// </summary>
    public static HashMap<string, HostInfo[]> getHostList()
    {
       
            if (_HostList == null)
            {
//                string json = IO.Read(MicroService.Const.ClientHostListJsonPath);
//                if (!string.IsNullOrEmpty(json))
//                {
//                    _HostList = JsonHelper.ToEntity<MDictionary<string, List<HostInfo>>>(json);
//                }
                if (_HostList == null)
                {
                    _HostList = new HashMap<string, HostInfo[]>();
                }
            }
            return _HostList;
      
    }

    /// <summary>
    /// 获取模块所在的对应主机网址【若存在多个：每次获取都会循环下一个】。
    /// </summary>
    /// <param name="name">服务模块名称</param>
    /// <returns></returns>
    public static String getHost(String name)
    {
//        HostInfo[] infoList = getHostList(name);
//        if (infoList != null && infoList.length() > 0)
//        {
//            HostInfo firstInfo = infoList[0];
//            for (int i = 0; i < infoList.Count; i++)
//            {
//                int callIndex = firstInfo.CallIndex + i;
//                if (callIndex >= infoList.size())
//                {
//                    callIndex = 0;
//                }
//                HostInfo info = infoList[callIndex];
//
//                if (info.Version < 0)//正常5-10秒注册1次。
//                {
//                    continue;//已经断开服务的。
//                }
//                firstInfo.CallIndex = callIndex + 1;//指向下一个。
//                return infoList[callIndex].Host;
//            }
//        }
        return "";
    }
    /// <summary>
    /// 获取模块的所有Host列表。
    /// </summary>
    /// <param name="name">服务模块名称</param>
    /// <returns></returns>
    public static HostInfo[] getHostList(String name)
    {
//        if (!string.IsNullOrEmpty(name))
//        {
//            List<HostInfo> list = new List<HostInfo>();
//            if (HostList.ContainsKey(name))//微服务程序。
//            {
//                list.AddRange(HostList[name]);
//            }
//            if (name.Contains("."))//域名
//            {
//                if (name != "*.*" && HostList.ContainsKey("*.*"))
//                {
//                    List<HostInfo> commList = HostList["*.*"];
//                    if (commList.Count > 0)
//                    {
//                        if (list.Count == 0 || commList[0].Version >= list[0].Version)//版本号比较处理
//                        {
//                            list.AddRange(commList);//增加“*.*”模块的通用符号处理。
//                        }
//                    }
//                }
//            }
//            else //普通模块
//            {
//                if (name != "*" && HostList.ContainsKey("*"))
//                {
//                    List<HostInfo> commList = HostList["*"];
//                    if (commList.Count > 0)
//                    {
//                        if (list.Count == 0 || commList[0].Version >= list[0].Version)//版本号比较处理
//                        {
//                            list.AddRange(commList);//增加“*”模块的通用符号处理。
//                        }
//                    }
//                }
//            }
//            return list;
//        }
        return null;
    }
}
