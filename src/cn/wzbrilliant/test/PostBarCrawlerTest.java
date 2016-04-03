package cn.wzbrilliant.test;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import cn.wzbrilliant.crawler.PostBarCrawler;
import junit.framework.Assert;

public class PostBarCrawlerTest {

	@Test
	public void testSave2Database() {
		fail("Not yet implemented");
	}
	
	public static void main(String[] args) {
		PostBarCrawler barCrawler=new PostBarCrawler("ba_href clearfix");
		Thread crawler=new Thread(barCrawler);
		crawler.start();
//		List content=barCrawler.visit();
//		List result=barCrawler.parse(content);
	}

	@Test
	public void testRun() {
		PostBarCrawler barCrawler=new PostBarCrawler("ba_href clearfix");
		Thread crawler=new Thread(barCrawler);
		crawler.start();
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testVisit() {
		PostBarCrawler barCrawler=new PostBarCrawler("ba_href clearfix");
		List<String> urls = barCrawler.visit();
		Assert.assertNotNull(urls);
	}

	@Test
	public void testParseList() {
		fail("Not yet implemented");
	}


}
