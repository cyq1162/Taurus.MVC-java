package test.taurus.core.tool;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import taurus.mvc.json.JsonHelper;

public class JsonToolTest {

	@Test
	public void test() {
		//fail("Not yet implemented");
	}
	@Test
	public void testJsonSplit()
	{
		String err="{adfdfdf:eeeeee,success:true}";
		//boolean isJson=JsonTool.IsJson(err);
		assertTrue(JsonHelper.isJson(err));
		assertTrue(!JsonHelper.isJson(err,true));
		String json="{\"aa\":1}";
		assertTrue(JsonHelper.isJson(json));
		
		assertTrue(!JsonHelper.isSuccess(json));
		assertTrue(JsonHelper.isSuccess(err));
		String aa=JsonHelper.getValue(json, "aa");
		assertTrue(aa.equals("1"));
		
		json="{\"success\":true,\"msg\":{\"*\":[{\"Host\":\"http://localhost:8090\",\"Version\":0,\"RegTime\":\"2022-09-29 18:05:22\",\"CallTime\":\"0001-01-01 00:00:00\",\"CallIndex\":0}]},\"tick\":638000715264341582,\"host2\":\"\",\"host\":1}";
		String json2="{\"s\":true,\"msg\":{\"*\":1},\"tick\":638000715264341582,\"host2\":\"\",\"host\":1}";
		
		assertTrue(JsonHelper.getValue(json, "msg").endsWith("}"));
		assertTrue(JsonHelper.getValue(json2, "msg").endsWith("}"));
	}
}
