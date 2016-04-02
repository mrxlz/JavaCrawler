package wz.crawler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import wz.jdbc.JdbcUtils;

/**
 * 
 * 将信息存入数据库,抽象类
 * 
 * @author ice
 *
 */
public abstract class InfoDownload {

	protected Connection connection;
	protected Statement statement;
	protected PreparedStatement preStatement;
	protected ResultSet resultSet;
	
	//连接数据库
	public InfoDownload Connect() throws SQLException{
		connection=JdbcUtils.getConnection();
		return this;
	}
	
	//存储数据到数据库
	abstract public InfoDownload Download() throws SQLException;
	
	//释放数据库资源
	public InfoDownload Release(){
		JdbcUtils.releaseResources(resultSet, preStatement, connection);
		return this;
	}
	

}
