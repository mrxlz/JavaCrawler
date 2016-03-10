package com.cream.ice.crawler;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

public class PostInfoDownload extends InfoDownload {

	HashMap<String, String> posts;

	public PostInfoDownload(HashMap<String, String> posts) {
		super();
		this.posts = posts;
	}


	@Override
	public InfoDownload Download() throws SQLException {
		
		String sql = new String(
				"insert into post (id,post_title,post_url) values (?,?,?)");
		
		preStatement=connection.prepareStatement(sql);
		
		int i=1;
		for(Entry<String, String> entry:posts.entrySet()){
			preStatement.setInt(1, i++);
			preStatement.setString(2, entry.getKey());
			preStatement.setString(3, entry.getValue());
			preStatement.addBatch();
//			System.out.println(entry.getKey());
//			System.out.println(entry.getValue());
		}
		
		int num=preStatement.executeBatch().length;
		
		System.out.println("成功插入"+num+"条记录...");
		return this;
	}

}
