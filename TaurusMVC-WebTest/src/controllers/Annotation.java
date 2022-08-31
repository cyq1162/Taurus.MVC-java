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
 * 注解测试
 * @author 路过秋天
 *
 */
@IgnoreDefaultController
public class Annotation extends Controller {

	@Token
	public void testCheckToken() {
		write("exe testCheckToken：没有@Override checkToken 方法，所以直接进来了。<br/>");
	}
	
	@Ack
	public void testCheckAck() {
		write("exe testCheckAck：没有@Override checkAck 方法，所以直接进来了。<br/>");
	}
	
	@MicroService
	public void testCheckMicroService() {
		write("exe testCheckMicroService：没有@Override checkMicroService 方法，所以直接进来了。<br/>");
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
