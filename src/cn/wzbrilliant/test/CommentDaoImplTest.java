package cn.wzbrilliant.test;

import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import cn.wzbrilliant.bean.Comment;
import cn.wzbrilliant.bean.Post;
import cn.wzbrilliant.dao.CommentDao;
import cn.wzbrilliant.dao.impl.CommentDaoImpl;

public class CommentDaoImplTest {

	@Test
	public void testAddComment() {
		CommentDao cmtDao=new CommentDaoImpl();
		Comment cmt=new Comment("789", "扁小篮子的男人", "火钳刘明", 12, "http://tieba.com", "132", new Date());
		cmtDao.addComment(cmt);
	}

	@Test
	public void testAddComments() {
		CommentDao cmtDao=new CommentDaoImpl();
		List<Comment> cmts=new LinkedList<Comment>();
		cmts.add(new Comment("987", "扁小篮子的男人", "火钳刘明", 13, "http://tieba.com", "1325s", new Date()));
		cmts.add(new Comment("879", "扁小篮子的男人", "火钳刘明", 18,"http://tieba.com", "132", new Date()));
		cmtDao.addComments(cmts);
	}

	@Test
	public void testGetComments() {
		CommentDao cmtDao=new CommentDaoImpl();
		List<Comment> cmts=cmtDao.getComments();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm");
		for(Comment cmt:cmts){
			System.out.println(cmt.getAuthor());
			System.out.println(cmt.getContent());
			System.out.println(cmt.getFloor());
			System.out.println(cmt.getId());
			System.out.println(cmt.getPostId());
			System.out.println(cmt.getPostUrl());
			System.out.println(sdf.format(cmt.getDate())+"\n");
		}
	}

}
