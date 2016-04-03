package wz.crawler;

import java.util.List;

import wz.bean.Comment;
import wz.core.Crawler;
import wz.dao.CommentReplyDao;
import wz.dao.impl.CommentReplyDaoImpl;

/**
 * 抓取回复的评论
 *
 * @author ice
 *
 * @date 2015年9月12日
 */
public class CommentReplyCrawler extends Crawler {

	private Comment comment;
	
	public CommentReplyCrawler() {
		super();
	}
	
	public void setComment(Comment comment) {
		this.comment = comment;
	}

	@Override
	protected List parse(List list) {
		// TODO 解析回复，抓取回复的评论
		return null;
	}

	@Override
	protected List visit() {

		return null;
	}

	@Override
	protected void save2DB(List replys) {
		CommentReplyDao replyDao = new CommentReplyDaoImpl();
		replyDao.addCommentReplys(replys);
	}

}
