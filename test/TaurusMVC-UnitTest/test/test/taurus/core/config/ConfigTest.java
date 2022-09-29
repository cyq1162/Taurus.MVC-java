package test.taurus.core.config;

import static org.junit.Assert.*;

import org.junit.Test;

import taurus.microservice.config.MsConfig;
import taurus.microservice.config.MsConst;
import taurus.mvc.config.MvcConfig;
import taurus.mvc.config.MvcConst;

public class ConfigTest {

	@Test
	public void testMvcConfig() {
		MvcConfig.set(MvcConst.RouteMode, "2");
		assertTrue(2==MvcConfig.getRouteMode());
		MvcConfig.set(MvcConst.IsAllowCORS, "false");
		assertTrue(!MvcConfig.getIsAllowCORS());
		MvcConfig.set(MvcConst.ControllerJarNames, "*");
		assertTrue("*".equals(MvcConfig.getControllerJarNames()));
	}
	@Test
	public void testMicroServiceConfig() {
		MsConfig.set(MsConst.ClientKey, "2");
		String key=MsConfig.getClientKey();
		assertTrue("2".equals(key));
	}
}
