package wz.dao.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import wz.dao.PostBarDao;

public class PostBarDaoImplHandler implements InvocationHandler {

	private PostBarDao pbDaoImpl;
	
	public PostBarDaoImplHandler(PostBarDao pbDaoImpl) {
		this.pbDaoImpl = pbDaoImpl;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		return null;
	}

}
