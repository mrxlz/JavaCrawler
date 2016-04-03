package cn.wzbrilliant.crawler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.wzbrilliant.bean.Post;
import cn.wzbrilliant.bean.PostBar;
import cn.wzbrilliant.core.Crawler;
import cn.wzbrilliant.dao.PostBarDao;
import cn.wzbrilliant.dao.PostsDao;
import cn.wzbrilliant.dao.impl.PostBarDaoImpl;
import cn.wzbrilliant.dao.impl.PostsDaoImpl;
import cn.wzbrilliant.util.Dom4jUtils;

/**
 * 抓取帖子
 * 
 * @author ice
 *
 * @date 2015年9月10日
 */
public class PostCrawler extends Crawler implements Runnable {

	private int pageSize = 10;
	private int postSize = 50;
	private String classId;
	private String replyNumClassId;
	private String authorClassId;
	private String replyNumClassIdOld;
	private String authorClassIdOld;
	private static int count = 0;
	
	public PostCrawler() {
		super();
		pageSize = Integer.parseInt(Dom4jUtils.getConfig("post", "page-size"));
		classId = Dom4jUtils.getConfig("post", "class-id");
		replyNumClassId = Dom4jUtils.getConfig("post", "reply-number-class-id");
		authorClassId = Dom4jUtils.getConfig("post", "author-class-id");
		replyNumClassIdOld = Dom4jUtils.getConfig("post", "reply-number-class-id-old");
		authorClassIdOld = Dom4jUtils.getConfig("post", "author-class-id-old");
	}
	
	/**
	 * 从抓取的Post对象中抓取帖子内容
	 */
	@Override
	protected List parse(List posts) {
		int count = 0;
		//抓取帖子的回复
		CommentCrawler cmtCrawler = new CommentCrawler(this);
		//解析帖子时将post存入数据库
		cmtCrawler.parse(posts);
		return null;
	}

	/**
	 * 从数据库获取贴吧，返回抓取到的Post对象列表
	 */
	@Override
	protected List<Post> visit() {
		String barId,barName,barUrl;
		
		Document doc = null;
		Element nextPage = null;
		Elements postElmts,replyNums,authors;
		
		int replyNumber;
		String title,postHref,author;
		int errCount = 0,pageCount = 0,postCount = 0;
		List<PostBar> postBars;
		List<Post> posts = new LinkedList<Post>();
//		LinkedList<String> failedParsePage = null;
		
		while(true){
			
			synchronized (PostCrawler.class) {
				if(PostBarDaoImpl.getStartIndex() > PostBarDaoImpl.getBarTotalSize()){
					break;
				}
				postBars = getPostBarFromDB();
			}
			
			//遍历获取到的贴吧
			for(PostBar postBar:postBars){
				//贴吧信息
				barId = postBar.getId();
				barName = postBar.getName();
				barUrl = postBar.getUrl();
				pageCount = 0;
				do{// 遍历贴吧的每一页
					if (nextPage != null){
						barUrl = nextPage.attr("abs:href");
					}
					try {
						doc = Jsoup.connect(barUrl).get();
					} catch (IOException e) {
						System.out.println("连接失败。。PostCrawler:102");
						// TODO 解析失败的页面放入未访问队列
						errCount++;
						if(errCount > 5){
							//访问失败的怎么处理?.......
							errCount = 0;
							nextPage = doc.select(":containsOwn(下一页)").first();
						}                     
						continue;
					}
					//遍历获取post对象
					postElmts = doc.select("[class=" + classId + "]");
					authors = doc.select("[class=" + authorClassId + "]");
					replyNums = doc.select("[class=" + replyNumClassId + "]");
					if(replyNums.size() == 0){
//					break;
						replyNums = doc.select("[class=" + replyNumClassIdOld + "]");
					}
					if(authors.size() == 0){
//					break;
						authors = doc.select("[class=" + authorClassIdOld + "]");
					}
					
					for(int i=0;i<postElmts.size();i++){
						title = postElmts.get(i).text();
						postHref = postElmts.get(i).attr("abs:href");
						
//					System.out.println(replyNumClassId);
						
						replyNumber = Integer.parseInt(replyNums.get(i).text());
						author = authors.get(i).text();
						Post p = new Post(UUID.randomUUID().toString(), title, author, barName, barId, replyNumber, null, postHref);
						posts.add(p);
						postCount++;
					}
					
					nextPage = doc.select(":containsOwn(下一页)").first();
//					System.err.println("爬完一页");
					pageCount++;
					
//					System.out.println("获取到"+postCount+"个帖子了。。");
					
					if(pageCount >= pageSize){
						break;
					}
					if(postCount >= postSize){
						parse(posts);
						posts.clear();
						postCount = 0;
					}
					
				}while(nextPage != null);
				nextPage = null;
			}
		}
		//剩余的post交给parse方法解析
		return posts;
	}

	@Override
	public void save2DB(List posts) {
		PostsDao pDao=new PostsDaoImpl();
		pDao.addPosts(posts);
	}
	
	protected List<PostBar> getPostBarFromDB(){
		PostBarDao pbDao=new PostBarDaoImpl();
		return pbDao.getPostBars();
	}

	//TODO 测试功能
	@Override
	public void run() {
//		System.out.println("开始爬了\n");
		runCrawler();
		System.out.println("\n第"+(++count)+"个线程爬完了");
	}

}
