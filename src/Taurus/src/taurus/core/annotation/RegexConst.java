package taurus.core.annotation;

public class RegexConst {
	/// <summary>
    /// 账号
    /// </summary>
    public static final String UserName = "^[a-zA-Z]\\w{5,15}$";
    /// <summary>
    /// 手机号
    /// </summary>
    public static final String Mobile = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[124567]|7[0135678]|9[13589])\\d{8}$";
    /// <summary>
    /// 手机号或者手机后4位
    /// </summary>
    public static final String MobileOrLen4 = "(^1([38][0-9]|4[579]|5[0-3,5-9]|6[124567]|7[0135678]|9[13589])\\d{8})|(^[0-9]{4})$";
    public static final String Email = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    /// <summary>
    /// 中文
    /// </summary>
    public static final String Chinese = "^[\u4e00-\u9fa5]{0,}$";
    /// <summary>
    /// 身份证
    /// </summary>
    public static final String IDCard = "^\\d{15}|\\d{18}$";
    /// <summary>
    /// 邮编
    /// </summary>
    public static final String PostalCode = "^\\d{6}$";
    /// <summary>
    /// IP4地址
    /// </summary>
    public static final String IP = "^((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))$";
    /// <summary>
    /// 验证码
    /// </summary>
    public static final String VerifyCode = "^[0-9]{4,6}$";
}
