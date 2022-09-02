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
		MvcConfig.set(MvcConst.MvcRouteMode, "2");
		assertTrue(2==MvcConfig.getRouteMode());
		MvcConfig.set(MvcConst.MvcIsAllowCORS, "false");
		assertTrue(!MvcConfig.getIsAllowCORS());
		MvcConfig.set(MvcConst.MvcControllerJarNames, "*");
		assertTrue("*".equals(MvcConfig.getControllerJarNames()));
	}
	@Test
	public void testMicroServiceConfig() {
		MsConfig.set(MsConst.MicroServiceClientKey, "2");
		String key=MsConfig.getClientKey();
		assertTrue("2".equals(key));
	}
}
