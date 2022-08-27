package taurus.core.annotation;

public class RegexConst {
	/// <summary>
    /// �˺�
    /// </summary>
    public static final String UserName = "^[a-zA-Z]\\w{5,15}$";
    /// <summary>
    /// �ֻ���
    /// </summary>
    public static final String Mobile = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[124567]|7[0135678]|9[13589])\\d{8}$";
    /// <summary>
    /// �ֻ��Ż����ֻ���4λ
    /// </summary>
    public static final String MobileOrLen4 = "(^1([38][0-9]|4[579]|5[0-3,5-9]|6[124567]|7[0135678]|9[13589])\\d{8})|(^[0-9]{4})$";
    public static final String Email = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    /// <summary>
    /// ����
    /// </summary>
    public static final String Chinese = "^[\u4e00-\u9fa5]{0,}$";
    /// <summary>
    /// ���֤
    /// </summary>
    public static final String IDCard = "^\\d{15}|\\d{18}$";
    /// <summary>
    /// �ʱ�
    /// </summary>
    public static final String PostalCode = "^\\d{6}$";
    /// <summary>
    /// IP4��ַ
    /// </summary>
    public static final String IP = "^((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))$";
    /// <summary>
    /// ��֤��
    /// </summary>
    public static final String VerifyCode = "^[0-9]{4,6}$";
}
