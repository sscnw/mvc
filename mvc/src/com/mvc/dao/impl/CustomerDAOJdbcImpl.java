package com.mvc.dao.impl;

import java.util.List;

import com.mvc.dao.*;
import com.mvc.domain.*;
import com.mvc.utils.*;
import com.mvc.domain.Customer;

public class CustomerDAOJdbcImpl extends DAO<Customer> implements CustomerDAO{
	
	public List<Customer> getAll() {
		String sql="select id,name,address,phone from customers";
		// TODO Auto-generated method stub
		return getForList(sql);
	}

	@Override
	public void save(Customer customer) {
		// TODO Auto-generated method stub
		String sql="insert into customers(name,address,phone) values(?,?,?)";
		update(sql, customer.getName(),customer.getAddress(),customer.getPhone());
	}

	@Override
	public Customer get(Integer id) {
		String sql="select id,name,address,phone from customers where id=?";
		return get(sql,id);
	}

	@Override
	public void delete(Integer id) {
		String sql="delete from customers where id=?";
		update(sql, id);
	}

	@Override
	public long getCountWithName(String name) {
		String sql="select count(id) from customers where name=?";
		return getForValue(sql, name);
	}

	@Override
	public List<Customer> getForListWithCriteriaCustomer(CriteriaCustomer criteriaCustomer) {
		String sql="select id,name,address,phone from customers where name like ? and address like ? and phone like ?";
		return getForList(sql,criteriaCustomer.getName(),criteriaCustomer.getAddress(),criteriaCustomer.getPhone());
	}

	@Override
	public void update(Customer customer) {
		// TODO Auto-generated method stub
		String sql="update customers set name=?,address=?,phone=?where id=?";
		update(sql,customer.getName(),customer.getAddress(),customer.getPhone(),customer.getId());
	
	}

}
