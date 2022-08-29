package taurus.microservice;

import java.util.Date;

public class HostInfo {
	 /// <summary>
    /// 主机地址：http://localhost:8080
    /// </summary>
    public String Host;
    /// <summary>
    /// 版本号：用于版本升级。
    /// </summary>
    public int Version;
    /// <summary>
    /// 注册时间（最新）
    /// </summary>
    public Date RegTime;
    /// <summary>
    /// 记录调用时间，用于隔离无法调用的服务，延时调用。
    /// </summary>
    public Date CallTime;
    /// <summary>
    /// 记录调用顺序，用于负载均衡
    /// </summary>
    public int CallIndex;
}
