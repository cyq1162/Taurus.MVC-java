package taurus.microservice;

import java.util.Date;

public class HostInfo {
	 /// <summary>
    /// ������ַ��http://localhost:8080
    /// </summary>
    public String Host;
    /// <summary>
    /// �汾�ţ����ڰ汾������
    /// </summary>
    public int Version;
    /// <summary>
    /// ע��ʱ�䣨���£�
    /// </summary>
    public Date RegTime;
    /// <summary>
    /// ��¼����ʱ�䣬���ڸ����޷����õķ�����ʱ���á�
    /// </summary>
    public Date CallTime;
    /// <summary>
    /// ��¼����˳�����ڸ��ؾ���
    /// </summary>
    public int CallIndex;
}
