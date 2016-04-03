package wz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import wz.bean.CommentReply;
import wz.dao.CommentReplyDao;
import wz.exception.DaoException;
import wz.util.C3P0Utils;

/**
 * @author ice
 *
 * @date 2015年9月12日
 */
public class CommentReplyDaoImpl implements CommentReplyDao {

	@Override
	public void addCommentReplys(List<CommentReply> replys) {
		//TODO 测试功能
		String sql = "insert ignore into commentReply (id,content,author,date,commentId) values(?,?,?,?,?)";
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		Object[][] objs = new Object[replys.size()][];
		for (int i = 0; i < replys.size(); i++) {
			objs[i] = replys.get(i).toArray(new String[] { "id","content","author","date","commentId" });
		}
		try {
			qr.batch(sql, objs);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

}
