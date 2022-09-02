package controllers;

import taurus.mvc.Controller;
import taurus.mvc.annotation.RegexConst;
import taurus.mvc.annotation.Require;

public class RequireController extends Controller {

	@Require(paraName="id")
	public void id() {
		write("id : "+query("id")+"<br/>");
	}
	@Require(paraName="id",cnParaName="标识ID")
	public void id2(int id) {
		write("id : "+id+"<br/>");
	}
	@Require(paraName="id",isRequire=false,regex=RegexConst.VerifyCode)
	public void id3() {
		write("id 要么不传，要么传4-6位数字: "+query("id")+"<br/>");
	}
	@Require(paraName="id",emptyTip="自定义提示：ID不能为空哟。")
	public void id4() {
		write("id : "+query("id")+"<br/>");
	}
	
	@Require(paraName="id,name")
	public void idName() {
		write("id : "+query("id")+"<br/>");
		write("name : "+query("name")+"<br/>");
	}
	@Require(paraName="id")
	@Require(paraName="name",cnParaName="姓名")
	public void idName2() {
		write("id : "+query("id")+"<br/>");
		write("name : "+query("name")+"<br/>");
	}
}
