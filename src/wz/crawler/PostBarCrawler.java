package wz.crawler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * 贴吧爬虫
 * @author ice
 *
 */
public class PostBarCrawler extends PostBarVisitor{

	public PostBarCrawler(String postBarName) {
		super(postBarName);
	}

	/**
	 * 将贴吧中相关内容的帖子url添加至urlSet中
	 */
	@Override
	public void visit(String[] args) {
		Document doc=null;
		String url=null;
		Integer pageCount=0;
		
		while(urlSet.postBarPageUrl.size()>0){
			url=urlSet.postBarPageUrl.pollFirst();
			if(url==null)
				continue;
			pageCount++;
			System.out.println("正在爬取贴吧第"+pageCount+"页");
			try {
				doc=Jsoup.connect(url).get();
//				Elements posts=doc.select("div.threadlist_lz clearfix");
				Elements posts=doc.getElementsByAttributeValue("class", "threadlist_title pull_left j_th_tit  ");
				System.out.println("共有"+posts.size()+"个帖子");
				for(Element post:posts){
					Elements postUrls=post.select("a[href]");
					for(Element postUrl:postUrls){
//						System.out.println(postUrl.text());
//						System.out.println(postUrl.attr("abs:href"));
						//将帖子的标题和url存入urlSet
						urlSet.posts.put(postUrl.text(), postUrl.attr("abs:href"));
					}
				}
			} catch (IOException e) {
				System.err.println("爬取贴吧第"+pageCount+"页出现异常");
				e.printStackTrace();
			}
			System.out.println();
		}
//		
//		for(Entry<String, String> entry:urlSet.posts.entrySet()){
//			System.out.println(entry.getKey());
//			System.out.println(entry.getValue());
//		}
//		
//		System.out.println("共有"+urlSet.posts.size()+"个帖子");
		
	}
	
	/**
	 * 更新贴吧页面url
	 * @param args
	 */
	public void updatePostBarUrl(String[] args){
		
		Integer pageCount=Integer.parseInt(args[0]);
		Integer count=0;
		
		//贴吧页面url前缀
		String barUrl=new String("http://tieba.baidu.com/f?kw="+this.postBarName+"&ie=utf-8&pn=");
		
		//将贴吧前count页url添加至urlSet
		while(count < pageCount){
			urlSet.postBarPageUrl.add(barUrl+(50*count));
			count++;
		}
	}
	
	public static void main(String []args){
		PostBarCrawler crawler=new PostBarCrawler("山东科技大学");
		String []arguments=new String[1];
		arguments[0]="10";
		System.out.println("start...");
		crawler.updatePostBarUrl(arguments);
		crawler.visit(null);
		PostInfoDownload download=new PostInfoDownload(crawler.urlSet.posts);
		try {
			download.Connect().Download();
		} catch (SQLException e) {
			System.err.println("数据库插入出错");
			e.printStackTrace();
		} finally{
			download.Release();
		}
		
		DbInfoShow.show();
		System.out.println("end...");
	}
	
}
