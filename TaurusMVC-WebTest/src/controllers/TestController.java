package controllers;

import java.util.Enumeration;

import taurus.mvc.Controller;
import taurus.mvc.annotation.Ack;
import taurus.mvc.annotation.HttpGet;
import taurus.mvc.annotation.HttpPost;
import taurus.mvc.annotation.RegexConst;
import taurus.mvc.annotation.Require;

public class TestController extends Controller {

	public static void name() {
		
	}
	@Override
	public void Default() {
		Enumeration<String> names=getRequest().getHeaderNames();
		while (names.hasMoreElements()) {
			String string = (String) names.nextElement();
			write(string+" : "+getRequest().getHeader(string)+"<br />");
			
		}
		String aa=getRequest().getParameter("aa");
		String bb=getRequest().getParameter("bb");
		String name=query("bb");
		write("hello Default"+aa+","+bb+","+name);
	}
	

	@Ack
	@HttpPost
	@HttpGet
	public void gogo() {
	Enumeration<String> names=getRequest().getHeaderNames();
	while (names.hasMoreElements()) {
		String string = (String) names.nextElement();
		write(string+" : "+getRequest().getHeader(string)+"<br />");
		
	}
		
		write("gogo",false);
	}
	@Require(paraName="aa",regex=RegexConst.Mobile)
	public void go(int i,String aa,String bb,boolean cc){
		write("go"+aa+","+bb+","+i+","+cc);
	}
	public void gogogo() {
		write("text/html");
	}

}
