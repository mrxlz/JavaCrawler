package wz.crawler;

import java.util.ArrayList;


/**
 * 
 * 页面访问抽象类
 * @author ice
 *
 */
abstract public class PageVisitor {
	
	/**
	 * 正则规则
	 */
	protected ArrayList<String> regexRule;

	/**
	 * 自定义访问页面方式
	 * @param args 访问参数
	 */
	abstract public void visit(String []args);
	
	public PageVisitor() {
		super();
		regexRule=new ArrayList<String>();
	}

	public void addRegex(String regex){
		regexRule.add(regex);
	}
	
	public void removeRegex(String regex){
		regexRule.remove(regex);
	}
	
	public void clearRegex(){
		regexRule.clear();
	}
	
}










