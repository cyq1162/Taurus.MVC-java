package taurus.mvc.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import taurus.mvc.annotation.Require;
import taurus.mvc.tool.string;

public class RequireInfo
{
	
    public String paraName, regex, emptyTip, regexTip;
    public boolean isRequire, isValidated;
    public RequireInfo(Require require)
    {
    	
    	init(require.paraName(),require.cnParaName(), require.isRequire(), require.regex(), require.emptyTip(), require.regexTip());        
    }
	private void init(String paraName, String cnParaName, Boolean isRequire, String regex, String emptyTip,
			String regexTip) {
		this.paraName = paraName;
		this.isRequire = isRequire;
		this.regex = regex;
		this.emptyTip=emptyTip;
		this.regexTip=regexTip;
		if(paraName.contains(","))
		{
			setEmptyTip("{0} is required.");
		}
		if (!cnParaName.equals(paraName) && !string.IsNullOrEmpty(cnParaName)) {
			Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
	        Matcher m = p.matcher(cnParaName);

			if (m.find())// ��������
			{
				setEmptyTip(cnParaName + "����Ϊ�ա�");
				setRegexTip(cnParaName + "��ʽ����");
			} else {
				setEmptyTip(cnParaName + " is required.");
				setRegexTip(cnParaName + " is invalid.");
			}
		}

		setEmptyTip(paraName + " is required.");
		setRegexTip(paraName + " is invalid.");

	}

	private void setEmptyTip(String tip) {
		if (string.IsNullOrEmpty(this.emptyTip)) {
			this.emptyTip = tip;
		}
	}
	private void setRegexTip(String tip) {
		if (string.IsNullOrEmpty(this.regexTip)) {
			this.regexTip = tip;
		}
	}
}
