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

/*��װ�˻�����CRUD�����Թ�����̳�ʹ��
 * ��ǰDAOֱ���ڷ����л�ȡ���ݿ�����
 * ����DAO��ȡDBUtil�������
 * @param <T>��ǰDAO�����ʵ����������ʲô
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
 * ����ĳһ���ֶε�ֵ ���緵��ĳһ����¼��newsName�����߷������ݱ����ж�������¼��
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
	 * ����T����Ӧ��List
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
	 * ���ض�Ӧ��T��һ��ʵ�������
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
	 * �÷�����װ��insert delete update����
	 * @sql SOL���
	 * @args ���SQL����ռλ��
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
