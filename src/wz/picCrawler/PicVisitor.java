package wz.picCrawler;

import java.io.IOException;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import wz.crawler.PageVisitor;

public class PicVisitor extends PageVisitor{
	public static LinkedList<String> pageUrls = null;
	public static LinkedList<String> downloadPageUrls = null;
	public static LinkedList<String> imgUrls = null;
	public static LinkedList<String> visitedPageUrls = null;
	
	static{
		pageUrls=new LinkedList<String>();
		downloadPageUrls=new LinkedList<String>();
		imgUrls = new LinkedList<String>();
		visitedPageUrls =new LinkedList<String>();
	}
	
	
	public static void DownloadPicture(){
		DownloadImage.Download();
	}

	@Override
	public void visit(String[] args) {

		String url = null;
		Document doc = null;
		int count;
		/*
		 * 1.将当前页面所有帖子url放入downloadPageUrls队列中
		 * 2.依次爬取每个帖子中的图片
		 * 3.重复步骤1
		 * 4.此例中只爬取前20个页面的帖子
		 */
		while (pageUrls.size() > 0) {
			
			url = pageUrls.pollFirst();
			
			if (visitedPageUrls.contains(url) || url == null)
				continue;
			count=1;
			//将当前页面所有帖子url放入downloadPageUrls队列中
			System.out.println("开始爬取页面url");
			try {
				doc=Jsoup.connect(url).get();
				Elements divs=doc.getElementsByAttributeValue("class", "box list channel");
				
				for(Element div:divs){
					Elements uls=div.getElementsByTag("ul");
					for(Element ul:uls){
						Elements lis=ul.getElementsByTag("li");
						for(Element li:lis){
							Elements links=li.getElementsByTag("a");
							for(Element l:links){
//								System.out.println("The url is : " + l.attr("abs:href"));
//								System.out.println("The article title is : " + l.text() + '\n');
								if( count++ <= 2)
									continue;
								downloadPageUrls.add(l.attr("abs:href"));
							}
						}
					}
				}
			} catch (IOException e) {
				System.err.println("爬取页面中帖子url：");
				e.printStackTrace();
			}
			System.out.println("爬取页面url结束");
			
			//依次爬取每个帖子中的图片
			System.out.println("开始爬取帖子图片url");
			while(downloadPageUrls.size()>0){
				url=downloadPageUrls.pollFirst();
				if (url == null)
					continue;
				
				try {
					doc=Jsoup.connect(url).get();
					Elements divs=doc.getElementsByAttributeValue("class", "pics");
					for(Element div:divs){
						Elements image=div.select("img[src]");
						for(Element img:image){
							imgUrls.add(img.attr("src"));
						}
					}
				} catch (IOException e) {
					System.err.println("爬取帖子中图片url:");
					e.printStackTrace();
				}
			}
			System.out.println("爬取帖子图片url结束\n");
			
			url = null;
		}
	
		
	}
}





