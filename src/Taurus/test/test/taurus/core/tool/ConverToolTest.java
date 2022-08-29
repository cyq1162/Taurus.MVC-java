package test.taurus.core.tool;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.junit.Test;

import taurus.core.tool.ConvertTool;



public class ConverToolTest {

	
	public enum ABC{
		
		A,
		B,
		C
	}
	@Test
	public void testToNum() {
		int i=0;
		float f=0;
		double d=0;
		try {
			//int
			i=(int)ConvertTool.changeType("dfdf", int.class);
			assertEquals(0, i);
			i=(Integer)ConvertTool.changeType("13.3.3", int.class);
			assertEquals(0, i);
			
			i=(Integer)ConvertTool.changeType("13", int.class);
			assertEquals(13, i);

			i=(int)ConvertTool.changeType("13.333", Integer.class);
			assertEquals(13, i);
			i=(Integer)ConvertTool.changeType("+13.333", int.class);
			assertEquals(13, i);
			i=(Integer)ConvertTool.changeType("-13.333", Integer.class);
			assertEquals(-13, i);
			
			//float
			f=(Float)ConvertTool.changeType("x13.3.33", Float.class);
			assertTrue(0f== f);
			f=(Float)ConvertTool.changeType("13.3.33", float.class);
			assertTrue(0f== f);

			f=(Float)ConvertTool.changeType("13", float.class);
			assertTrue(13f== f);
			f=(Float)ConvertTool.changeType("13.223", float.class);
			assertTrue(13.223f==f);
			f=(Float)ConvertTool.changeType("-13.229", float.class);
			assertTrue(-13.229f==f);
			f=(Float)ConvertTool.changeType("13.2", float.class);
			assertTrue(13.2f==f);
			f=(Float)ConvertTool.changeType("+13", float.class);
			assertTrue(13f==f);
			
			//double
			d=(double)ConvertTool.changeType("x13.3.33", double.class);
			assertTrue(0== d);
			d=(double)ConvertTool.changeType("13.3.33", Double.class);
			assertTrue(0== d);

			d=(double)ConvertTool.changeType("13", Double.class);
			assertTrue(13== d);
			d=(double)ConvertTool.changeType("13.223", double.class);
			assertTrue(13.223==d);
			d=(double)ConvertTool.changeType("-13.229", Double.class);
			assertTrue(-13.229==d);
			d=(double)ConvertTool.changeType("13.2", double.class);
			assertTrue(13.2==d);
			d=(double)ConvertTool.changeType("+13", Double.class);
			assertTrue(13==d);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertEquals(1, 2);
		}
		
	}
	@Test
	public void TestToEnum()
	{
		ABC actual=ABC.B;

		try {
			int i=(Integer)ConvertTool.changeType(actual, int.class);
			assertEquals(1, i);
			String s=(String)ConvertTool.changeType(actual, String.class);
			assertEquals("B", s);
			
			actual = (ABC)ConvertTool.changeType("C", ABC.class);
			assertEquals(ABC.C, actual);
			actual = (ABC)ConvertTool.changeType(2, ABC.class);
			assertEquals(ABC.C, actual);
			actual = (ABC)ConvertTool.changeType("errorToDefault", ABC.class);
			assertEquals(ABC.A, actual);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertEquals(1, 2);
		}
	}

	@Test
	public void TestToUUID() {
		try {
			UUID aa;
			aa = (UUID) ConvertTool.changeType("aa", UUID.class);
			assertTrue(aa.equals(UUID.fromString("00000000-0000-0000-0000-000000000000")));
			aa = (UUID) ConvertTool.changeType("newID()", UUID.class);
			assertTrue(!aa.equals(UUID.fromString("00000000-0000-0000-0000-000000000000")));
			aa = (UUID) ConvertTool.changeType("00000000-0000-0000-0000-000000000002", UUID.class);
			assertTrue(aa.equals(UUID.fromString("00000000-0000-0000-0000-000000000002")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertEquals(1, 2);
		}
	}
	@Test
	public void TestToDate() {
		try {
			Date date;
			date = (Date) ConvertTool.changeType("1988-09-12", Date.class);
			assertNotEquals(date, getMinDate());
			date = (Date) ConvertTool.changeType("1988/09-12 22:22:22.555", Date.class);
			assertNotEquals(date, getMinDate());
			date = (Date) ConvertTool.changeType("00000000-0000-0000-0000-000000000002", Date.class);
			assertEquals(date, getMinDate());
			date = (Date) ConvertTool.changeType("00000000-0xxx", Date.class);
			assertEquals(date, getMinDate());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertEquals(1, 2);
		}
	}
	@Test
	public void TestToBoolean() {
		try {
			boolean boo;
			boo = (boolean) ConvertTool.changeType("yEs", boolean.class);
			assertTrue(boo);
			boo = (boolean) ConvertTool.changeType("1", boolean.class);
			assertTrue(boo);
			boo = (boolean) ConvertTool.changeType("00000000-0000-0000-0000-000000000002", boolean.class);
			assertTrue(!boo);
			boo = (boolean) ConvertTool.changeType("00000000-0xxx", boolean.class);
			assertTrue(!boo);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertEquals(1, 2);
		}
	}
    private Date getMinDate() {
    	Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
}
