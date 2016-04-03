package cn.wzbrilliant.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import cn.wzbrilliant.util.Dom4jUtils;

public class JsoupTest {
	@Test
	public void test1(){
		String url=Dom4jUtils.getConfig("post-bar", "category-page");
		try {
			url="http://tieba.baidu.com/p/3883192868";
			Document doc=Jsoup.connect(url).get();
			String regrex1="[class=j_th_tit ]";
			String classId = Dom4jUtils.getConfig("comment", "class-id");
			String regrex2="[class=tail-info]";
			String regrex = "[class=threadlist_li_left j_threadlist_li_left]";
			
			//reply number
			String rg="[class=col2_left j_threadlist_li_left]";
			
			//获取评论的date
			String regrex3 = "span:matches(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2})";
			//author
			String regrex4 = "[class=d_name]";
			//floor
			String regrex5 = "span:matches(\\d+楼)";
			//reply
			String regrex6 = "[class=d_post_content_main ]";
			
			String regrex7 = "span:contains(-)";
			String authorId = Dom4jUtils.getConfig("comment", "author-class-id");
			
			classId = "d_post_content j_d_post_content  clearfix";
			
			Elements es = doc.select("[class="+authorId+"]");
			
			int count = 0;
			System.out.println(es.size());
			for(int i=0;i<es.size();i++){
//				Elements els = es.get(i).select("span:contains(-)");
//				System.out.println(els.size());
//				if(els.size()!=0)
				System.out.println((++count)+":"+es.get(i).text());
//				for(Element el:els)
//					System.out.println(el.text());
			}
//			System.out.println(es.get(2).html());
//			System.out.println(es.size());
//			System.out.println(doc.outerHtml());
			
			
//			String html = doc.outerHtml();
////			Pattern pattern =Pattern.compile("<span>");
////			Matcher matcher = pattern.matcher(html);
////			System.out.println(matcher.find());
//			
//			OutputStream out = new FileOutputStream(new File("html.txt"));
//			out.write(html.getBytes());
//			out.close();
			
			
//			for(Element e:es){
//				System.out.println(e.text());
//			}
//			
//			for(Element e:es){
//				
//				Elements posts=doc.select(regrex1);
//				Elements replyNums=doc.select(rg);
//			Assert.assertNull(es.first());
//			System.out.println(es.first());
//			Assert.assertNotNull(es);
//			System.out.println(es.size());
//				System.out.println(posts.size());
//				System.out.println(replyNums.size()+"\n");
//				for(int i=0; i<posts.size();i++){
//					Element p = posts.get(i);
//					Element r = replyNums.get(i);
//					System.out.println(p.text());
//					System.out.println(p.attr("abs:href"));
//					System.out.println(r.text()+"\n");
//				}
//			}
			
			
//			for(Element e:posts){
//				String href=e.attr("abs:href");
//				System.out.println(href);
//				System.out.println(e.text());
//			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	@Test
//	public void test2() throws IOException{
//		String url = "http://tb2.bdstatic.com/tb/static-pb/img/loading_69032b0.gif";
//		Response resp = Jsoup.connect(url).response();
//		System.out.println(resp.statusCode());
//	}
}
