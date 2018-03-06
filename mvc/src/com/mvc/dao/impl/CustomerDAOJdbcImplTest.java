package com.mvc.dao.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.mvc.dao.CriteriaCustomer;
import com.mvc.dao.CustomerDAO;
import com.mvc.domain.Customer;

public class CustomerDAOJdbcImplTest {
	private CustomerDAO customerDAO=new CustomerDAOJdbcImpl();
		
//		@Override
//		public void save(Customer customer) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//		@Override
//		public long getCountWithName(String name) {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//		
//		@Override
//		public List<Customer> getAll() {
//			// TODO Auto-generated method stub
//			return null;
//		}
//		
//		@Override
//		public Customer get(Integer id) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//		
//		@Override
//		public void delete(Integer id) {
//			// TODO Auto-generated method stub
//			
//		}
	@Test
	public void testGetAll() {
		List<Customer> customers=customerDAO.getAll();
		System.out.println(customers);
	}

	@Test
	public void testSave() {
		Customer customer=new Customer();
		customer.setAddress("shanghai");
		customer.setName("jerry");
		customer.setPhone("1496904");
		customerDAO.save(customer);
	}

	@Test
	public void testGetInteger() {
		Customer customer=customerDAO.get(1);
		System.out.println(customer);
	}

	@Test
	public void testDelete() {
		customerDAO.delete(1);
	}

	@Test
	public void testGetCountWithName() {
		long count=customerDAO.getCountWithName("mike");
		System.out.println(count);
	}
	public void testGetForListWithCriteriaCustomer(){
		CriteriaCustomer criteriaCustomer=new CriteriaCustomer("mike",null,null);
		List<Customer> customers=customerDAO.getForListWithCriteriaCustomer(criteriaCustomer);
		System.out.println(customers);
	}
}
