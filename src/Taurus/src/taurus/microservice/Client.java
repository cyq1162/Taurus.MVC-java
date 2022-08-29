package taurus.microservice;

import java.util.HashMap;

import taurus.microservice.config.MsConfig;
import taurus.mvc.tool.string;

public class Client {
	
    /**
     * ��ǰ�����Ƿ���Ϊ��������У�΢����Ӧ�ó���
     * @return
     */
	public static Boolean getIsRunAsClient()
    {
		return !string.IsNullOrEmpty(MsConfig.getClientName()) && 
		 !string.IsNullOrEmpty(MsConfig.getClientRegUrl()) && 
		 !MsConfig.getClientRegUrl().equals(MsConfig.getAppRunUrl());
    }
    /// <summary>
    /// ΢����Ӧ�ó��� - ���ע�������Ƿ��ڡ�
    /// </summary>
    public static boolean RegCenterIsLive = false;

    /// <summary>
    /// ��ȡ��ע������ʱ�������±�ʶ.
    /// </summary>
    public static long Tick = 0;//��ע�����Ķ�ȡ�ı�ʶ�š����ڷ������Աȡ�
    /// <summary>
    /// ��ȡ��ע�����ġ��浵������ת�Ʊ������ӡ�
    /// </summary>
    static String _Host2 = null;
    /// <summary>
    /// ��ȡ����ע�����Ķ�ȡ�ı�������
    /// </summary>
	public static String getHost2() {

//		if (_Host2 == null) {
//			_Host2 = IO.Read(Const.ClientHost2Path);// �״ζ�ȡ���Ա��ڻָ���
//		}
		return _Host2;

    }
    static HashMap<string, HostInfo[]> _HostList;
    /// <summary>
    /// ��΢����������˻�ȡ��΢�����б�����΢������ڲ�������ת��
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
    /// ��ȡģ�����ڵĶ�Ӧ������ַ�������ڶ����ÿ�λ�ȡ����ѭ����һ������
    /// </summary>
    /// <param name="name">����ģ������</param>
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
//                if (info.Version < 0)//����5-10��ע��1�Ρ�
//                {
//                    continue;//�Ѿ��Ͽ�����ġ�
//                }
//                firstInfo.CallIndex = callIndex + 1;//ָ����һ����
//                return infoList[callIndex].Host;
//            }
//        }
        return "";
    }
    /// <summary>
    /// ��ȡģ�������Host�б�
    /// </summary>
    /// <param name="name">����ģ������</param>
    /// <returns></returns>
    public static HostInfo[] getHostList(String name)
    {
//        if (!string.IsNullOrEmpty(name))
//        {
//            List<HostInfo> list = new List<HostInfo>();
//            if (HostList.ContainsKey(name))//΢�������
//            {
//                list.AddRange(HostList[name]);
//            }
//            if (name.Contains("."))//����
//            {
//                if (name != "*.*" && HostList.ContainsKey("*.*"))
//                {
//                    List<HostInfo> commList = HostList["*.*"];
//                    if (commList.Count > 0)
//                    {
//                        if (list.Count == 0 || commList[0].Version >= list[0].Version)//�汾�űȽϴ���
//                        {
//                            list.AddRange(commList);//���ӡ�*.*��ģ���ͨ�÷��Ŵ���
//                        }
//                    }
//                }
//            }
//            else //��ͨģ��
//            {
//                if (name != "*" && HostList.ContainsKey("*"))
//                {
//                    List<HostInfo> commList = HostList["*"];
//                    if (commList.Count > 0)
//                    {
//                        if (list.Count == 0 || commList[0].Version >= list[0].Version)//�汾�űȽϴ���
//                        {
//                            list.AddRange(commList);//���ӡ�*��ģ���ͨ�÷��Ŵ���
//                        }
//                    }
//                }
//            }
//            return list;
//        }
        return null;
    }
}
