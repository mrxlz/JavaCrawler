package cn.wzbrilliant.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.wzbrilliant.bean.Post;
import cn.wzbrilliant.dao.PostsDao;
import cn.wzbrilliant.exception.DaoException;
import cn.wzbrilliant.util.C3P0Utils;
import cn.wzbrilliant.util.Dom4jUtils;

/**
 * @author ice
 *
 * @date 2015年8月23日
 */
public class PostsDaoImpl implements PostsDao {

	/**
	 * 每次从数据库取记录时的起始位置
	 */
	private static int startIndex;
	
	/**
	 * 每次从数据库中取记录的条数
	 */
	private int size=-1;
	
	/**
	 * 每次从数据库中取记录的条数(默认值)
	 */
	private static int defaultSize;
	
	
	static{
		startIndex=0;
		defaultSize=Integer.parseInt(Dom4jUtils.getConfig("post","read-size"));
	}
	
	public static int getStartIndex() {
		return startIndex;
	}

	public static void setStartIndex(int startIndex) {
		PostsDaoImpl.startIndex = startIndex;
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
	public void addPost(Post post) {
		String sql = "insert ignore into post (id,title,author,barName,barId,replyNumber,date,url) values(?,?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		try {
			qr.update(sql, post.getId(),post.getTitle(),post.getAuthor(),post.getBarName(),post.getBarId(), post.getReplyNumber(),post.getDate(),post.getUrl());
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public void addPosts(List<Post> posts) {
		String sql = "insert ignore into post (id,title,author,barName,barId,replyNumber,date,url) values(?,?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		Object[][] objs = new Object[posts.size()][];
		for (int i = 0; i < posts.size(); i++) {
			objs[i] = posts.get(i).toArray(new String[] { "id","title","author","barName","barId","replyNumber","date","url"});
		}
		try {
			qr.batch(sql, objs);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public synchronized List<Post> getPosts() {
		if(size<0)
			this.setSize(PostsDaoImpl.defaultSize);

		List<Post> list = null;
		String sql = "select id,title,author,barName,barId,replyNumber,date,url from post order by insert_time limit ?,?";
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		try {
			list = qr.query(sql, new BeanListHandler<Post>(Post.class), startIndex, size);
			startIndex += size;
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return list;
	}

}
