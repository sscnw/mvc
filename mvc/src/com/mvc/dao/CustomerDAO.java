package com.mvc.dao;

import java.util.List;

import com.mvc.domain.Customer;

public interface CustomerDAO {
	public List<Customer> getForListWithCriteriaCustomer(CriteriaCustomer criteriaCustomer);
	public List<Customer> getAll();
	public void save(Customer customer);
	public Customer get(Integer id);
	public void delete(Integer id);
	public void update(Customer customer);
	/*
	 * 返回和Name相等的记录数
	 */
	public long getCountWithName(String name);
}
