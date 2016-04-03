package wz.test;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import wz.bean.PostBar;
import wz.dao.PostBarDao;
import wz.dao.impl.PostBarDaoImpl;

public class PostBarDaoImplTest {

	@Test
	public void testAddPostBar() {
		PostBarDao pbDao=new PostBarDaoImpl();
		PostBar postBar=new PostBar("0000001","李毅吧","http://tieba.baidu.com");
		pbDao.addPostBar(postBar);
	}

	@Test
	public void testAddPostBars() {
		PostBarDao pbDao=new PostBarDaoImpl();
		List<PostBar> postBars=new LinkedList<PostBar>();
		postBars.add(new PostBar("0000004","弱智吧","http://tieba2.baidu.com"));
		postBars.add(new PostBar("0000005","山东科技大学吧","http://tieba3.baidu.com"));
		pbDao.addPostBars(postBars);
	}

	@Test
	public void testGetPostBar() {
		PostBarDao pbDao=new PostBarDaoImpl();
		PostBar pb1=pbDao.getPostBar("李毅吧");
		PostBar pb2=pbDao.getPostBar("山科吧");
		Assert.assertNotNull(pb1);
		Assert.assertNull(pb2);
		System.out.println(pb1.getId()+pb1.getName()+pb1.getUrl());
	}

	@Test
	public void testGetPostBars() {
		PostBarDao pbDao=new PostBarDaoImpl();
		List<PostBar> pbs=pbDao.getPostBars();
		Assert.assertNotNull(pbs);
		for(PostBar pb:pbs){
			System.out.println(pb.getId()+pb.getName()+pb.getUrl());
		}
	}

}
