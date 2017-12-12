package com.xy.cms.service;

import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Customer;
import com.xy.cms.entity.Product;

public interface CustomerService {
	
	/**
	 * 通过主键id查询用户
	 * */
	public Customer getCustomerById(Long id)throws BusinessException;
	
	/**
	 *分页查询客户 
	 */
	public QueryResult queryCustomerPage(Map<String, Object> map) throws BusinessException;
	/**
	 * 保存客户信息
	 * @param Customer
	 * @throws BusinessException
	 */
	public void saveCustomer(Customer Customer) throws BusinessException;
	
	/**
	 * 删除客户信息
	 * @param userId
	 * @throws BusinessException
	 */
	public void del(Customer cus) throws BusinessException;
	
	 /**
	  * 根据客户名称查询客户
	  */
	public Customer getCusByName(String name);
	/**
	 * 更新客户信息
	 * @param Customer
	 * @throws BusinessException
	 */
	public void upateCustomer(Customer Customer) throws BusinessException;
	/**
	 * 批量导入客户
	 * @param List<Product>
	 * */
	public void importCustomer(List<Customer> customerList) throws BusinessException;
	
	/**
	 * 
	 * 查询所有的客户信息
	 * */
	public List<Customer> queryAllCustomer();
	
}
