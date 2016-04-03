package wz.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * C3P0工具类。
 * DBCP使用动态代理，当Connection对象调用close()方法时，将Connection对象放回连接池中，实际上并不关闭连接
 * 通过c3p0-config.xml文件配置数据库、连接池参数
 *
 * @author ice
 *
 * @date 2015年8月3日
 */
public class C3P0Utils {
	/**
	 * 数据源
	 */
	public static ComboPooledDataSource cpDataDataSource = new ComboPooledDataSource();
	
	/**
	 * 获取数据源
	 * @return 数据源
	 */
	public static DataSource getDataSource() {
		return cpDataDataSource;
	}
	
	/**
	 * 从连接池中获取连接
	 * @return 
	 */
	public static Connection getConnection(){
		try {
			return cpDataDataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 释放资源
	 */
	public static void releaseResources(ResultSet resultSet,
			Statement statement, Connection connection) {

		try {
			if (resultSet != null)
				resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resultSet = null;
			try {
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				statement = null;
				try {
					if (connection != null)
						connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					connection = null;
				}
			}
		}
	}
}
