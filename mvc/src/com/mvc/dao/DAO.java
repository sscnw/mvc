package com.mvc.dao;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.mvc.utils.JdbcUtils;
import com.sun.glass.ui.CommonDialogs.Type;
import com.sun.org.apache.bcel.internal.generic.NEW;

/*封装了基本的CRUD方法以供子类继承使用
 * 当前DAO直接在方法中获取数据库连接
 * 整个DAO采取DBUtil解决方案
 * @param <T>当前DAO处理的实体类类型是什么
 */
public class DAO<T>{
	private QueryRunner queryRunner= new QueryRunner(); 
	private Class<T> clazz;
	public DAO(){
	java.lang.reflect.Type superClass=getClass().getGenericSuperclass();
	if(superClass instanceof ParameterizedType ){
		ParameterizedType parameterizedType=(ParameterizedType) superClass;
		java.lang.reflect.Type[] typeArgs=parameterizedType.getActualTypeArguments();
		if(typeArgs[0] !=null&&typeArgs.length>0){
			if (typeArgs[0] instanceof Class) {
				clazz=(Class<T>) typeArgs[0];
			}
		}
	}
	
	}
/*
 * 返回某一个字段的值 例如返回某一条记录的newsName，或者返回数据表中有多少条记录等
 */
	public <E> E getForValue(String sql,Object ...args){
		Connection connection=null;
		try {
			connection =JdbcUtils.getConnection();
			return (E) queryRunner.query(connection, sql,new ScalarHandler(),args);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally {
			JdbcUtils.releaseConnection(connection);
		}
		return null;
	} 
	/*
	 * 返回T所对应的List
	 */
	public List<T> getForList(String sql,Object ...args){
		
		Connection connection=null;
		try {
			connection =JdbcUtils.getConnection();
			return queryRunner.query(connection, sql,new BeanListHandler<>(clazz),args);
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally {
			JdbcUtils.releaseConnection(connection);
		}
		return null;
	}
	/*
	 * 返回对应的T的一个实例类对象
	 */
	public T get(String sql,Object ...args){
		Connection connection =null;
		try {
			connection=JdbcUtils.getConnection();
			return queryRunner.query(connection, sql,new BeanHandler<>(clazz),args);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally {
			JdbcUtils.releaseConnection(connection);
		}
		return null;
	}
	/*
	 * 该方法封装了insert delete update操作
	 * @sql SOL语句
	 * @args 填充SQL语句的占位符
	 */
	public void update(String sql,Object ...args){
		Connection connection=null;
		try {
			connection=JdbcUtils.getConnection();
			queryRunner.update(connection,sql,args);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally {
			JdbcUtils.releaseConnection(connection);
		}
	}
}
