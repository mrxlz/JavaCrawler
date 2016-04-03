package cn.wzbrilliant.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import cn.wzbrilliant.crawler.PostBarCrawler;
import cn.wzbrilliant.crawler.PostCrawler;
import cn.wzbrilliant.util.Dom4jUtils;

public class MainProcess {

	public static void main(String[] args) throws IOException {
		
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("是否抓取贴吧数据？y/n");
		String option1 = in.readLine();
		System.out.println("是否抓取贴子数据？y/n");
		String option2 = in.readLine();

		//爬贴吧
		if(option1.equalsIgnoreCase("y")){
			PostBarCrawler barCrawler=new PostBarCrawler(Dom4jUtils.getConfig("class-id"));
			new Thread(barCrawler).start();
		}
		
		//爬帖子
		if(option2.equalsIgnoreCase("y")){
			int threadSize = Integer.parseInt(Dom4jUtils.getConfig("thread-number"));
			PostCrawler postCrawler = new PostCrawler();
			startThreads(threadSize, postCrawler);
		}
	}

	private static void startThreads(int threadSize, PostCrawler postCrawler) {
		for (int i = 0; i <= threadSize; i++) {
			new Thread(postCrawler).start();
		}
	}

}
