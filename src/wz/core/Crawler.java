package wz.core;

import java.util.List;

public abstract class Crawler {

	/**
	 * 待访问的贴吧(postbar)、帖子(post)、回复(comment)
	 */
	protected List unVisited;
	
	/**
	 * 解析规则
	 */
	protected String regrex;
	
//	protected boolean autoFlush = true;
	
	public Crawler(){
		super();
	}
	
	public Crawler(List unVisited, String regrex){
		this.unVisited = unVisited;
		this.regrex = regrex;
	}
	
	public void runCrawler() {
		List content=visit();
		List result=parse(content);
		if(result != null)
			save2DB(result);
	}
	
	/**
	 * 依据regrex解析待访问内容
	 * @param list 待解析内容列表
	 * @return 返回贴吧、帖子或评论等对象的集合
	 */
	protected abstract List parse(List list);

	/**
	 * 根据待访问对象获取要访问的内容
	 * @return 要访问的内容
	 */
	protected abstract List visit();
	
	/**
	 * 保存到数据库
	 * @param list
	 */
	protected abstract void save2DB(List list);

}
