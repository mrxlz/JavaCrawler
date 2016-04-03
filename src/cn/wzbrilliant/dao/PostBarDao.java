package cn.wzbrilliant.dao;

import java.util.List;

import cn.wzbrilliant.bean.PostBar;

public interface PostBarDao {
	
	/**
	 * 添加贴吧至数据库
	 * @param postBar 贴吧对象
	 */
	public void addPostBar(PostBar postBar);
	
	/**
	 * 添加多个贴吧至数据库
	 * @param postBars 多个贴吧
	 */
	public void addPostBars(List<PostBar> postBars);
	
	/**
	 * 通过贴吧名获取贴吧
	 * @param barName 贴吧名
	 * @return 贴吧对象
	 */
	public PostBar getPostBar(String barName);
	
	/**
	 * 获取多个贴吧
	 * @return 多个贴吧对象
	 */
	public List<PostBar> getPostBars();
	
}
