package cn.wzbrilliant.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import cn.wzbrilliant.bean.Post;
import cn.wzbrilliant.crawler.CommentCrawler;
import cn.wzbrilliant.crawler.PostCrawler;

public class CommentCrawlerTest {

	public static void main(String[] args) throws ParseException {
		PostCrawler postCrawler = new PostCrawler();
		Thread crawler = new Thread(postCrawler);
		crawler.start();
	}

}
