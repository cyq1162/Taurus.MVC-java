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
	public Boolean checkToken() {
		write("call @Override checkToken���������true or false���ɶ��ˢ�·��ʡ�<br/>");
		return new Random().nextBoolean();
	}

	@Override
	public Boolean checkAck() {
		write("call @Override checkAck���������true or false���ɶ��ˢ�·��ʡ�<br/>");
		return new Random().nextBoolean();

	}

	@Override
	public Boolean checkMicroService() {
		write("call @Override checkMicroService���������true or false���ɶ��ˢ�·��ʡ�<br/>");
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
