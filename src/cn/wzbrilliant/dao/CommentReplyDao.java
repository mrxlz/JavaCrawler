package cn.wzbrilliant.dao;

import java.util.List;

import cn.wzbrilliant.bean.CommentReply;

public interface CommentReplyDao {

	/**
	 * 添加多条回复的评论至数据库
	 * @param replys 回复的评论
	 */
	public void addCommentReplys(List<CommentReply> replys);
	
}
