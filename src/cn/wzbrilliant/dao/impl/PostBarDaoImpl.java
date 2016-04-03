package cn.wzbrilliant.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.wzbrilliant.bean.PostBar;
import cn.wzbrilliant.dao.PostBarDao;
import cn.wzbrilliant.exception.DaoException;
import cn.wzbrilliant.util.C3P0Utils;
import cn.wzbrilliant.util.Dom4jUtils;


/**
 * @author ice
 *
 * @date 2015骞�8鏈�25鏃�
 */
public class PostBarDaoImpl implements PostBarDao {

	/**
	 * 姣忔浠庢暟鎹簱鍙栬褰曟椂鐨勮捣濮嬩綅缃�
	 */
	private static int startIndex;

	/**
	 * 姣忔浠庢暟鎹簱涓彇璁板綍鐨勬潯鏁�
	 */
	private int size = -1;

	/**
	 * 姣忔浠庢暟鎹簱涓彇璁板綍鐨勬潯鏁�(榛樿鍊�)
	 */
	private static int defaultSize;
	
	private static int barTotalSize;

	static {
		startIndex = 0;
		defaultSize = Integer.parseInt(Dom4jUtils.getConfig("post-bar", "read-size"));
		barTotalSize = Integer.parseInt(Dom4jUtils.getConfig("post-bar", "total-size"));
	}

	public static int getStartIndex() {
		return startIndex;
	}

	public static void setStartIndex(int startIndex) {
		PostBarDaoImpl.startIndex = startIndex;
	}

	public int getSize() {
		return size;
	}

	/**
	 * 璁剧疆姣忔浠庢暟鎹簱涓鍙栬褰曠殑鏉℃暟
	 * @param size 璇诲彇璁板綍鐨勬潯鏁�
	 */
	public void setSize(int size) {
		this.size = size;
	}

	public static int getDefaultSize() {
		return defaultSize;
	}
	
	public static int getBarTotalSize() {
		return barTotalSize;
	}

	@Override
	public void addPostBar(PostBar postBar) {
		String sql = "insert ignore into postbar (id,name,url) values(?,?,?)";
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		try {
			qr.update(sql, postBar.getId(), postBar.getName(), postBar.getUrl());
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public void addPostBars(List<PostBar> postBars) {
		String sql = "insert ignore into postbar (id,name,url) values(?,?,?)";
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		Object[][] objs = new Object[postBars.size()][];
		for (int i = 0; i < postBars.size(); i++) {
			objs[i] = postBars.get(i).toArray(new String[] { "id", "name", "url"});
		}
		try {
			qr.batch(sql, objs);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public PostBar getPostBar(String barName) {
		PostBar postBar = null;
		String sql = "select id,name,url from postbar where name=?";
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		try {
			postBar = qr.query(sql, new BeanHandler<PostBar>(PostBar.class), barName);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return postBar;
	}

	@Override
	public List<PostBar> getPostBars() {
		if (size < 0)
			this.setSize(PostBarDaoImpl.defaultSize);
		
		List<PostBar> list = null;
		String sql = "select id,name,url from postbar order by insert_time desc limit ?,?";
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		try {
			list = qr.query(sql, new BeanListHandler<PostBar>(PostBar.class), startIndex, size);
			startIndex += size;
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return list;
	}

}
