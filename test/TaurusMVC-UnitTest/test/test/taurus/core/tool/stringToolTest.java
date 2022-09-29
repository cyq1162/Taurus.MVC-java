package test.taurus.core.tool;

import static org.junit.Assert.*;

import org.junit.Test;

import taurus.mvc.tool.string;

public class stringToolTest {

	@Test
	public void test() {
		String aaa="Not";
		String bbb= string.TrimStart(aaa, 'a','N');
		assertTrue(bbb.equals("ot"));
		
		String ccc= string.TrimEnd(aaa, 't','N','O');
		assertTrue(ccc.equals("N"));
		
		String ddd= string.TrimEnd(aaa, '6','r',' ');
		assertTrue(ddd.equals("Not"));
	}

}
