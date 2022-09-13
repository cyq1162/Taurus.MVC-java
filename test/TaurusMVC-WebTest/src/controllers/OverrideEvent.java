package controllers;

import java.util.Random;

import taurus.mvc.Controller;
import taurus.mvc.annotation.Ack;
import taurus.mvc.annotation.IgnoreDefaultController;
import taurus.mvc.annotation.MicroService;
import taurus.mvc.annotation.Token;

@IgnoreDefaultController
public class OverrideEvent extends Controller {


	@Override
	public Boolean checkToken(String token) {
		write("call @Override checkToken："+token+"，返回随机true or false，可多次刷新访问。<br/>");
		return new Random().nextBoolean();
	}

	@Override
	public Boolean checkAck(String ack) {
		write("call @Override checkAck："+ack+"，返回随机true or false，可多次刷新访问。<br/>");
		return new Random().nextBoolean();

	}

	@Override
	public Boolean checkMicroService(String msKey) {
		write("call @Override checkMicroService："+msKey+"，返回随机true or false，可多次刷新访问。<br/>");
		return new Random().nextBoolean();
	}
	
	@Token
	public void testCheckToken() {
		write("exe testCheckToken<br/>");
//		write("testToken : "+getRequest().getRequestURI()+"<br/>");
//		write("testToken : "+getRequest().getAttribute("oldUrl")+"<br/>");
//		write("testToken : "+getRequest().getAttribute("oldUri")+"<br/>");
//		write("testToken : "+getRequest().getAttribute("oldQuery")+"<br/>");
	}
	
	@Ack
	public void testCheckAck() {
		write("exe testCheckAck<br/>");
	}
	
	@MicroService
	public void testCheckMicroService() {
		write("exe testCheckMicroService<br/>");
	}
}
