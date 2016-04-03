package cn.wzbrilliant.test;

import static org.junit.Assert.*;

import org.junit.Test;

import cn.wzbrilliant.util.Dom4jUtils;

public class Dom4jUtilsTest {

	@Test
	public void testGetConfig() {
		String result=Dom4jUtils.getConfig("thread-number");
		assertEquals(20, Integer.parseInt(result));
	}

}
