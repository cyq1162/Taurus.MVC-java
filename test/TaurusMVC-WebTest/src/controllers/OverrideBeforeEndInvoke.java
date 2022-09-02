package controllers;

import java.util.Random;

import taurus.mvc.Controller;
import taurus.mvc.annotation.IgnoreDefaultController;

@IgnoreDefaultController
public class OverrideBeforeEndInvoke extends Controller {
	
	@Override public void Default() {
		write("call @Override Default on 404 Path.<br/>");
	}
	
	@Override
	public Boolean beforeInvoke(String methodName) {
		write("call @Override beforeInvoke：返回随机true or false，可多次刷新访问。<br/>");
		return new Random().nextBoolean();
	}

	@Override
	public void endInvoke(String methodName) {
		write("call @Override endInvoke<br/>");
	}

	public void testBeforeInkoke() {
		write("exe testBeforeInkoke<br />");
	}

	public void testEndInkoke() {
		write("exe testEndInkoke<br />");
	}
}
