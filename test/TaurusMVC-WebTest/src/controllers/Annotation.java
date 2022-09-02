package controllers;

import taurus.mvc.Controller;
import taurus.mvc.annotation.Ack;
import taurus.mvc.annotation.HttpDelete;
import taurus.mvc.annotation.HttpGet;
import taurus.mvc.annotation.HttpHead;
import taurus.mvc.annotation.HttpPost;
import taurus.mvc.annotation.HttpPut;
import taurus.mvc.annotation.IgnoreDefaultController;
import taurus.mvc.annotation.MicroService;
import taurus.mvc.annotation.Token;

/**
 * ע�����
 * @author ·������
 *
 */
@IgnoreDefaultController
public class Annotation extends Controller {

	@Token
	public void testCheckToken() {
		write("exe testCheckToken��û��@Override checkToken ����������ֱ�ӽ����ˡ�<br/>");
	}
	
	@Ack
	public void testCheckAck() {
		write("exe testCheckAck��û��@Override checkAck ����������ֱ�ӽ����ˡ�<br/>");
	}
	
	@MicroService
	public void testCheckMicroService() {
		write("exe testCheckMicroService��û��@Override checkMicroService ����������ֱ�ӽ����ˡ�<br/>");
	}

	public void testIgnoreDefaultController() {
		write("exe testIgnoreDefaultController @IgnoreDefaultController on the class<br/>");
	}
	
	@HttpGet
	public void testHttpGet() {
		write("exe testHttpGet<br/>");
	}
	@HttpPost
	public void testHttpPost() {
		write("exe testHttpPost<br/>");
	}
	@HttpHead
	public void testHttpHead() {
		write("exe testHttpHead<br/>");
	}
	@HttpPut
	public void testHttpPut() {
		write("exe testHttpPut<br/>");
	}
	@HttpDelete
	public void testHttpDelete() {
		write("exe testHttpDelete<br/>");
	}
}
