package com.cream.ice.picCrawler;

public class Crawler {

	public static void main(String[] args) {
		
//		将待抓取页面放入url队列中
		for (int i = 13; i <= 15; i++)
			PicVisitor.pageUrls.add("http://www.857x.com/htm/piclist9/" + i
					+ ".htm");
		
		new PicVisitor().visit(null);
//		VisitUtil.imgUrls.add("http://imges.501aa.com/d2/2417/241786-2.jpg");
		PicVisitor.DownloadPicture();
	}

}
