package com.mvc.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sun.xml.internal.bind.v2.util.DataSourceSource;
/*
 * JDBC�����Ĺ�����
 * ʹ��C3P0
 */
public class JdbcUtils {
	/*e
	 * �ͷ�connection
	 */
	public static void releaseConnection(Connection connection){
		try {
			if (connection!=null) {
				connection.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static DataSource dataSource=null;
	static {
		dataSource=new ComboPooledDataSource("mvcApp");
	}
	/*��ȡ����
	 * ��������Դ��CONNECTION����
	 */
	public static Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	}
}

