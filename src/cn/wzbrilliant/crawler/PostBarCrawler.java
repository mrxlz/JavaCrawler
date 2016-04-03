package cn.wzbrilliant.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.wzbrilliant.bean.PostBar;
import cn.wzbrilliant.core.Crawler;
import cn.wzbrilliant.dao.PostBarDao;
import cn.wzbrilliant.dao.impl.PostBarDaoImpl;
import cn.wzbrilliant.util.Dom4jUtils;

/**
 * 获取所要爬取的贴吧
 *
 * @author ice
 *
 * @date 2015年9月2日
 */
public class PostBarCrawler extends Crawler implements Runnable {

	/**
	 * 每爬取pageSize个页面就向数据库中存一次
	 */
	private int pageSize = 10;

	public PostBarCrawler() {
		super();
		pageSize = Integer.parseInt(Dom4jUtils.getConfig("post-bar", "page-size"));
	}

	public PostBarCrawler(String classId) {
		super(null, classId);
	}

	@Override
	public void run() {
		// 解析时分批存入数据库
		System.out.println("开始爬了\n");
		runCrawler();
		System.out.println("\n爬完了");
	}

	@Override
	public List<PostBar> parse(List list) {

//		System.out.println("开始解析");

		String barUrl;
		String barName;
		int count = 0;
		String url;
		Document doc = null;
		Element nextPage = null;
		List<PostBar> postBars = new LinkedList<PostBar>();
		LinkedList<String> failedParsePage = null;

		// 遍历每一分类
		for (Object categoriedUrl : list) {
			url = (String) categoriedUrl;
			count = 0;
			do { // 遍历分类的每一页
				if (nextPage != null)
					url = nextPage.attr("abs:href");

				try {
					doc = Jsoup.connect((String) url).get();
				} catch (IOException e) {
					System.out.println("连接失败。。PostBarCrawler:76");
					// 解析失败的页面放入未访问队列
					if (failedParsePage == null)
						failedParsePage = new LinkedList<String>();
					failedParsePage.add(url);
					nextPage = doc.select(":containsOwn(下一页)").first();
					continue;
				}
				Elements elements = doc.select("[class=" + regrex + "]");
				// 遍历每页上的贴吧
				for (Element element : elements) {
					barUrl = element.attr("abs:href");
					barName = element.select("[class=ba_name]").text();
					System.err.print(barName + ":");
					System.out.println(barUrl);
					postBars.add(new PostBar(UUID.randomUUID().toString(), barName, barUrl));
				}
				count++;
				if (count == pageSize) {
					save2DB(postBars);
					postBars.clear();
					count = 0;
				}
				nextPage = doc.select(":containsOwn(下一页)").first();

//				System.err.println("爬完一页");

			} while (nextPage != null);
			// if(count != 0){
			// save2Database(postBars);
			// postBars.clear();
			// count = 0;
			// }
		}
		// 解析前面访问失败的页面
		visitFailedParse(failedParsePage);
//		System.out.println("解析完了");
		return postBars;
	}

	@Override
	public List<String> visit() {
		String categoryUrl = Dom4jUtils.getConfig("post-bar", "category-page");
		List<String> urls = null;
		try {
			Document doc = Jsoup.connect(categoryUrl).get();
			String categoryRegrex = "[class=item-list-ul clearfix] a";
			Elements elements = doc.select(categoryRegrex);
			if (elements.size() != 0) {
				urls = new ArrayList<String>();
				for (Element element : elements) {
					urls.add(element.attr("abs:href"));
					System.out.println(element.attr("abs:href"));
					System.out.println(element.text());
				}
			}
		} catch (IOException e) {
			System.out.println("连接失败。。PostBarCrawler:121");
		}
		return urls;
	}

	private void visitFailedParse(LinkedList<String> failedParsePage) {
		String barUrl;
		String barName;
		int count = 0;
		Document doc;
		String url;
		List<PostBar> postBars = new ArrayList<PostBar>();

		while (!failedParsePage.isEmpty()) {
			url = failedParsePage.pollFirst();
			
			try {
				doc = Jsoup.connect((String) url).get();
			} catch (IOException e) {
				System.out.println("连接失败。。PostBarCrawler:150");
				// 解析失败的页面放入未访问队列队尾
				failedParsePage.addLast(url);
				continue;
			}
			Elements elements = doc.select("[class=" + regrex + "]");
			// 遍历每页上的贴吧
			for (Element element : elements) {
				barUrl = element.attr("abs:href");
				barName = element.select("[class=ba_name]").text();
				System.err.print(barName + ":");
				System.out.println(barUrl);
				postBars.add(new PostBar(UUID.randomUUID().toString(), barName, barUrl));
			}
			count++;
			if (count == pageSize) {
				save2DB(postBars);
				postBars.clear();
				count = 0;
			}
		}
		if (count != 0) {
			save2DB(postBars);
			postBars.clear();
			count = 0;
		}
	}

	@Override
	public void save2DB(List postBars) {
		PostBarDao pbDao = new PostBarDaoImpl();
		pbDao.addPostBars(postBars);
	}

}
