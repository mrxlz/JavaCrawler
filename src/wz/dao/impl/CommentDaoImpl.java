package wz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import wz.bean.Comment;
import wz.dao.CommentDao;
import wz.exception.DaoException;
import wz.util.C3P0Utils;
import wz.util.Dom4jUtils;

/**
 * @author ice
 *
 * @date 2015年8月25日
 */
public class CommentDaoImpl implements CommentDao {

	/**
	 * 每次从数据库取记录时的起始位置
	 */
	private static int startIndex;

	/**
	 * 每次从数据库中取记录的条数
	 */
	private int size = -1;

	/**
	 * 每次从数据库中取记录的条数(默认值)
	 */
	private static int defaultSize;

	static {
		startIndex = 0;
		defaultSize = Integer.parseInt(Dom4jUtils.getConfig("comment","read-size"));
	}

	public static int getStartIndex() {
		return startIndex;
	}

	public static void setStartIndex(int startIndex) {
		CommentDaoImpl.startIndex = startIndex;
	}

	public int getSize() {
		return size;
	}

	/**
	 * 设置每次从数据库中读取记录的条数
	 * @param size 读取记录的条数
	 */
	public void setSize(int size) {
		this.size = size;
	}

	public static int getDefaultSize() {
		return defaultSize;
	}

	@Override
	public void addComment(Comment comment) {
		String sql = "insert ignore into comment (id,author,content,floor,postUrl,postId,date) values(?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		try {
			qr.update(sql, comment.getId(),comment.getAuthor(),comment.getContent(),comment.getFloor(),comment.getPostUrl(),comment.getPostId(),comment.getDate());
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public void addComments(List<Comment> comments) {
		String sql = "insert ignore into comment (id,author,content,floor,postUrl,postId,date) values(?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		Object[][] objs = new Object[comments.size()][];
		for (int i = 0; i < comments.size(); i++) {
			objs[i] = comments.get(i).toArray(new String[] { "id","author","content","floor","postUrl","postId","date" });
		}
		try {
			qr.batch(sql, objs);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public synchronized List<Comment> getComments() {
		if (size < 0)
			this.setSize(CommentDaoImpl.defaultSize);
		
		List<Comment> list = null;
		String sql = "select id,author,content,floor,postUrl,postId,date from comment order by insert_time limit ?,?";
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		try {
			list = qr.query(sql, new BeanListHandler<Comment>(Comment.class), startIndex, size);
			startIndex += size;
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return list;
	}

}
