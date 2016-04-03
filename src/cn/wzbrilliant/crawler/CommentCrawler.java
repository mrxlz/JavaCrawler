package cn.wzbrilliant.crawler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.wzbrilliant.bean.Comment;
import cn.wzbrilliant.bean.Post;
import cn.wzbrilliant.core.Crawler;
import cn.wzbrilliant.dao.CommentDao;
import cn.wzbrilliant.dao.impl.CommentDaoImpl;
import cn.wzbrilliant.util.Dom4jUtils;

/**
 * 抓取帖子回复
 *
 * @author ice
 *
 * @date 2015年9月12日
 */
public class CommentCrawler extends Crawler {

	private int pageSize = 10;
	private int commentSize = 10;
	private String classId;
//	private String replyNumClassId;
	private String authorId;
	private Crawler postCrawler;
	
	public CommentCrawler(Crawler crawler){
		super();
		this.pageSize = Integer.parseInt(Dom4jUtils.getConfig("comment", "page-size"));
		this.commentSize = Integer.parseInt(Dom4jUtils.getConfig("comment", "comment-size"));
		this.classId = Dom4jUtils.getConfig("comment", "class-id");
//		this.replyNumClassId = Dom4jUtils.getConfig("comment", "reply-number-class-id");
		this.authorId = Dom4jUtils.getConfig("comment", "author-class-id");
		this.postCrawler = crawler;
	}
	
	@Override
	public List parse(List posts) {
		//TODO 解析帖子，抓取帖子回复
		Post post;
		Date date = null;
//		int replyNum;
		int floor = 0;
		String postUrl,content,author;

		Document doc = null;
		Element nextPage = null;
		Elements cmtElmts,dateElmts,authors,replyNums,floors,replyElmts;
		int errCount = 0,pageCount = 0,cmtCount = 0,visitedCount = 0;
		int postSize = Integer.parseInt(Dom4jUtils.getConfig("post", "post-size"));
		Method savePosts = null;
		List<Post> visitedPosts = new LinkedList<Post>();
		List<Comment> comments = new LinkedList<Comment>();
//		CommentReplyCrawler replyCrawler = new CommentReplyCrawler();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm");
		
		try {
			savePosts = postCrawler.getClass().getMethod("save2DB", List.class);
		} catch (NoSuchMethodException | SecurityException e1) {
			System.out.println("反射错误。CommentCrawler：72");
		}
		
//		System.err.println("开始抓取回复");
		
		cmtCount = 0;
		for(Object obj:posts){
			post = (Post) obj;
			postUrl = post.getUrl();
			pageCount = 0;
			
//			System.err.println("\n开始爬\""+post.getTitle()+"\"这个帖子啦");
			
			do{// 遍历帖子的每一页
				if (nextPage != null)
					postUrl = nextPage.attr("abs:href");
				try {
					doc = Jsoup.connect(postUrl).get();
				} catch (IOException e) {
					System.out.println("连接失败。。CommentCrawler:91");
					// TODO 解析失败的页面放入未访问队列
					errCount++;
					if(errCount > 5){
						//访问失败的 todo.......
						errCount = 0;
						nextPage = doc.select(":containsOwn(下一页)").first();
					}                     
					continue;
				}
				
				cmtElmts = doc.select("[class="+classId+"]");
				if(cmtElmts.size() == 0){
					String classIdOld = Dom4jUtils.getConfig("comment", "class-id-old");
					cmtElmts = doc.select("[class="+classIdOld+"]");
				}
				
				dateElmts = doc.select("span:matches(\\d{4}-\\d{2}-\\d{2})");
				authors = doc.select("[class="+authorId+"]");
				floors = doc.select("span:matches(\\d+楼)");
//				replyNums = doc.select("[class="+replyNumClassId+"]");
				
				try {
//					System.out.println(dateElmts.size());
					if(dateElmts.size()!=0){
						post.setDate(sdf.parse(dateElmts.first().text()));
					}
				} catch (ParseException e) {
					e.printStackTrace();
					break;
				}
				
				//抓取回复
				for(int i = 0;i<cmtElmts.size();i++){
					content = cmtElmts.get(i).text();
					try {
						if(dateElmts.size()!=0){
							date = sdf.parse(dateElmts.get(i).text());
						}else{
							date = null;
						}
					} catch (ParseException e) {
						e.printStackTrace();
						continue;
					}
					author = authors.get(i).text();
					if(floors.size()!=0){
						floor =Integer.parseInt(floors.get(i).text().replace("楼", ""));
					}
//					replyNum = Integer.parseInt(replyNums.get(i).text());
					Comment comment = new Comment(UUID.randomUUID().toString(), author, content, floor, postUrl, post.getId(), date);
					comments.add(comment);
					cmtCount++;
					if(cmtCount >= commentSize){
						visitedPosts.add(post);
						try {
							savePosts.invoke(postCrawler, visitedPosts);
							visitedPosts.clear();
							visitedCount = 0;
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
						save2DB(comments);
						comments.clear();
						cmtCount = 0;
					}
					//TODO 抓取回复的评论
					//选择comment的div，查找评论的div
					//if 含有，则抓取评论内容
//					replyCrawler.setComment(comment);
				}
				
				pageCount++;
//				System.out.println("爬完\""+post.getBarName()+"\"中帖子的一页");
				
				if(pageCount >= pageSize){
					break;
				}
				
				nextPage = doc.select(":containsOwn(下一页)").first();
			}while(nextPage != null);
			
			nextPage = null;
			
			visitedPosts.add(post);
			visitedCount++;
			if(visitedCount >= postSize){
//				((PostCrawler)postCrawler).save2DB(visitedPosts);
				try {
					savePosts.invoke(postCrawler, visitedPosts);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
				visitedPosts.clear();
				visitedCount = 0;
			}
			
//			System.err.println("爬完\""+post.getTitle()+"\"这个帖子啦");
			
		}
		try {
			savePosts.invoke(postCrawler, visitedPosts);
			visitedCount = 0;
			visitedPosts.clear();
			save2DB(comments);
			comments.clear();
			cmtCount = 0;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
//		System.err.println("回复抓取完了");
		return null;
	}

	@Override
	protected List visit() {
		return null;
	}

	@Override
	protected void save2DB(List comments) {
		CommentDao cmtDao = new CommentDaoImpl();
		cmtDao.addComments(comments);
	}
	
//	private void showInfo(Elements es){
//		int count = 0;
//		System.out.println(es.size());
//		for(int i=0;i<es.size();i++){
////			Elements els = es.get(i).select("span:contains(-)");
////			System.out.println(els.size());
////			if(els.size()!=0)
//			System.out.println((++count)+":"+es.get(i).text());
////			for(Element el:els)
////				System.out.println(el.text());
//		}
//	}

}
