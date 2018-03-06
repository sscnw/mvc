package com.mvc.app.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.management.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.filters.AddDefaultCharsetFilter;

import com.mvc.dao.CriteriaCustomer;
import com.mvc.dao.CustomerDAO;
import com.mvc.dao.impl.CustomerDAOJdbcImpl;
import com.mvc.domain.Customer;


public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomerDAO customerDAO=new CustomerDAOJdbcImpl();
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String servletPath=request.getServletPath();
		String methodName=servletPath.substring(1);
		methodName=methodName.substring(0,methodName.length()-3);
		System.out.println(methodName);
		
		try {
			Method method=getClass().getDeclaredMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			method.invoke(this, request,response);
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			response.sendRedirect("error.jsp");
		}
	}
	private void edit(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String forwardPath="/error.jsp";
		String idString=request.getParameter("id");
		try {
			Customer customer=customerDAO.get(Integer.parseInt(idString));
			if (customer!=null) {
				forwardPath="/updatecustomer.jsp";
				request.setAttribute("customer", customer);
			}
		
		} catch (Exception e) {
			// TODO: handle exception
		}
		request.getRequestDispatcher(forwardPath).forward(request, response);
		
	}
	private void update(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String id=request.getParameter("id");
		String name=request.getParameter("name");
		String phone =request.getParameter("phone");
		String oldName=request.getParameter("oldName");
		String address=request.getParameter("address");
		if(!oldName.equalsIgnoreCase(name)){
			long count=customerDAO.getCountWithName(name);
			if(count>0){
				request.setAttribute("message","新闻名"+name+"已经被占用，请重新选择");
				request.getRequestDispatcher("/updatenews.jsp").forward(request, response);
				return;
			}
		}
		Customer customer =new Customer(name,address,phone);
		customer.setId(Integer.parseInt(id));
		customerDAO.update(customer);
		response.sendRedirect("query.do");
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String idString=request.getParameter("id");
		int id=0;
		try {
			id=Integer.parseInt(idString);
			customerDAO.delete(id);
		} catch (Exception e) {
			// TODO: handle exception
			
		}
		response.sendRedirect("query.do");
		
	}


	private void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("name");
		String phone=request.getParameter("phone");
		String address=request.getParameter("address");
		CriteriaCustomer criteriaCustomer=new CriteriaCustomer(name, address, phone);
		
		//封装为一个对象
		List<Customer> customers=customerDAO.getForListWithCriteriaCustomer(criteriaCustomer);
		//把news对象放在request中
		request.setAttribute("customers", customers);
		//转发到页面
		request.getRequestDispatcher("/index.jsp").forward(request, response);
		
	}


	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name=request.getParameter("name");
		String address=request.getParameter("address");
		String phone=request.getParameter("phone");
		long count=customerDAO.getCountWithName(name); 
		if (count>0) {
			request.setAttribute("message", "用户名"+name+"已经被占用");
			request.getRequestDispatcher("/newCustomer.jsp").forward(request, response);
			return;	
		}
		Customer customer=new Customer(name,address,phone);
		customerDAO.save(customer);
		response.sendRedirect("success.jsp");
		
	}

}
