package com.cream.ice.crawler;

import java.util.HashMap;
import java.util.LinkedList;


/**
 * 
 * 访问某贴吧页面，将具有相关的内容的帖子放入队列中
 * 内部类UrlSet存放该贴吧内待访问url
 * @author ice
 *
 */
public abstract class PostBarVisitor extends PageVisitor {
	/**
	 * 贴吧内所爬取相关内容的url
	 * @author ice
	 */
	class UrlSet{
		/**
		 * 贴吧页面url
		 */
		public LinkedList<String> postBarPageUrl;
		/**
		 * 帖子集合， title——url
		 */
		public HashMap<String,String> posts;
		
		public UrlSet() {
			super();
			postBarPageUrl=new LinkedList<String>();
			posts=new HashMap<String, String>();
		}
		
	}
	
	/**
	 * url集合
	 */
	protected UrlSet urlSet;
	
	/**
	 * 贴吧名称
	 */
	protected String postBarName;
	
	public PostBarVisitor(String postBarName) {
		super();
		urlSet=new UrlSet();
		this.postBarName=postBarName;
	}

}
