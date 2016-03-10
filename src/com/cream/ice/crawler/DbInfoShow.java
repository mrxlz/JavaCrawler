package com.cream.ice.crawler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.cream.ice.jdbc.JdbcUtils;

public class DbInfoShow {
	public static void show(){
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection=JdbcUtils.getConnection();
			statement =connection.createStatement();
			String sql="select * from post";
			
			resultSet=statement.executeQuery(sql);
			
			while(resultSet.next()){
				System.out.println(resultSet.getString("post_title"));
				System.out.println(resultSet.getString("post_url")+'\n');
			}
			
		} catch (SQLException e) {
			System.err.println("显示数据库信息出错...");
			e.printStackTrace();
		} finally{
			JdbcUtils.releaseResources(resultSet, statement, connection);
		}
	}
}
