package wz.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class DateTest {
	@Test
	public void test1() throws ParseException {
//		Date date = DateFormat.getInstance().parse("07/10/96 4:5 PM, PDT");
//		System.out.println(DateFormat.getInstance().format(new Date()));
//		System.out.println(new Date());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date date = sdf.parse("2015-09-01 22:13");
		System.out.println();
	}
}
