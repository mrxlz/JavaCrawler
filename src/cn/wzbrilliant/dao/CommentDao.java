package cn.wzbrilliant.dao;

import java.util.List;

import cn.wzbrilliant.bean.Comment;

public interface CommentDao {

	/**
	 * 添加一条留言至数据库
	 * @param comment
	 */
	public void addComment(Comment comment);
	
	/**
	 * 添加多条留言至数据库
	 * @param comment
	 */
	public void addComments(List<Comment> comment);
	
	/**
	 * 获取多条留言
	 * @return 多条留言
	 */
	public List<Comment> getComments();

}
