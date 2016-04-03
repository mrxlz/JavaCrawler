package cn.wzbrilliant.test;

import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import cn.wzbrilliant.bean.Post;
import cn.wzbrilliant.dao.PostsDao;
import cn.wzbrilliant.dao.impl.PostsDaoImpl;

public class PostsDaoImplTest {

	@Test
	public void testAddPost() throws ParseException {
		PostsDao pDao=new PostsDaoImpl();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date date = sdf.parse("2015-09-01 22:13");
		Post p=new Post("123", "标题什么的", "欠扁的小篮子", "山东科技大学吧", "0000003", 500, date, "http://csdn.blog.com");
		pDao.addPost(p);
	}

	@Test
	public void testAddPosts() {
		PostsDao pDao=new PostsDaoImpl();
		List<Post> posts=new LinkedList<Post>();
		posts.add(new Post("132", "标题什么哒", "欠扁的大篮子", "山东科技大学吧", "0000003", 20, new Date(), "http://csdn.blog.com"));
		posts.add(new Post("1325s", "标题什么哒", "欠扁的大篮子", "李毅吧", "0000001", 20, new Date(), "http://csdn.blog.com"));
		pDao.addPosts(posts);
	}

	@Test
	public void testGetPosts() {
		PostsDao pDao=new PostsDaoImpl();
		List<Post> posts=pDao.getPosts();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm");
		for(Post post:posts){
			System.out.println(post.getAuthor());
			System.out.println(post.getBarId());
			System.out.println(post.getBarName());
			System.out.println(post.getId());
			System.out.println(post.getReplyNumber());
			System.out.println(post.getTitle());
			System.out.println(post.getUrl());
			System.out.println(sdf.format(post.getDate())+"\n");
		}
	}

}
