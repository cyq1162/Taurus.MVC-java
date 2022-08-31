package controllers;

import taurus.mvc.Controller;
import taurus.mvc.annotation.RegexConst;
import taurus.mvc.annotation.Require;

public class RequireController extends Controller {

	@Require(paraName="id")
	public void id() {
		write("id : "+query("id")+"<br/>");
	}
	@Require(paraName="id",cnParaName="��ʶID")
	public void id2(int id) {
		write("id : "+id+"<br/>");
	}
	@Require(paraName="id",isRequire=false,regex=RegexConst.VerifyCode)
	public void id3() {
		write("id Ҫô������Ҫô��4-6λ����: "+query("id")+"<br/>");
	}
	@Require(paraName="id",emptyTip="�Զ�����ʾ��ID����Ϊ��Ӵ��")
	public void id4() {
		write("id : "+query("id")+"<br/>");
	}
	
	@Require(paraName="id,name")
	public void idName() {
		write("id : "+query("id")+"<br/>");
		write("name : "+query("name")+"<br/>");
	}
	@Require(paraName="id")
	@Require(paraName="name",cnParaName="����")
	public void idName2() {
		write("id : "+query("id")+"<br/>");
		write("name : "+query("name")+"<br/>");
	}
}
