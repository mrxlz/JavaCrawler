package wz.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import wz.bean.Post;
import wz.crawler.CommentCrawler;
import wz.crawler.PostCrawler;

public class CommentCrawlerTest {

	public static void main(String[] args) throws ParseException {
		PostCrawler postCrawler = new PostCrawler();
		Thread crawler = new Thread(postCrawler);
		crawler.start();
	}

}
