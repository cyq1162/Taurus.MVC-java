package test.taurus.core.config;

import static org.junit.Assert.*;

import org.junit.Test;

import taurus.core.config.Const;
import taurus.core.config.MicroServiceConfig;
import taurus.core.config.MvcConfig;

public class ConfigTest {

	@Test
	public void testMvcConfig() {
		MvcConfig.set(Const.MvcRouteMode, "2");
		assertTrue(2==MvcConfig.getRouteMode());
		MvcConfig.set(Const.MvcIsAllowCORS, "false");
		assertTrue(!MvcConfig.getIsAllowCORS());
		MvcConfig.set(Const.MvcControllerJarNames, "*");
		assertTrue("*".equals(MvcConfig.getControllerJarNames()));
	}
	@Test
	public void testMicroServiceConfig() {
		MicroServiceConfig.set(Const.MicroServiceClientKey, "2");
		String key=MicroServiceConfig.getClientKey();
		assertTrue("2".equals(key));
	}
}
