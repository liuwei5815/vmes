package com.xy.cms.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.formula.functions.Odd;

import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Employee;
import com.xy.cms.entity.Orders;
import com.xy.cms.entity.OrdersDetail;
import com.xy.cms.entity.Produceplan;
import com.xy.cms.entity.ProduceplanTodo;


public interface OrdersService {
	QueryResult queryAllOrders(Map<String, Object> map) throws BusinessException;
	/**
	 * 获取客户编号
	 * @return
	 * @throws BusinessException
	 */
	List<String> queryCusCode() throws BusinessException;

	/**
	 * 保存一个订单
	 * @param orders
	 * @param list
	 * @throws BusinessException
	 */
	void saveOrders(Orders orders,List<OrdersDetail> list) throws BusinessException;
	/**
	 * 保存订单详情
	 * @param detail
	 * @throws BusinessException
	 */
	void saveDetails(OrdersDetail detail) throws BusinessException;

	/**
	 * 查询所有的订单编号
	 * 
	 * */
	public List<String> getAllOrdersCode()throws BusinessException;
	
	/**
	 * 通过订单编号查询订单
	 * @param orderCode 订单编号
	 * */
	public Orders getOrderByOrderCode(String orderCode)throws BusinessException;
	
	/**
	 * 查询所有的订单
	 * */
	public List<Orders> getAllOrders()throws BusinessException;
	
	/**
	 * 通过主键id获得Orders
	 * @param id 主键id
	 * */
	public Orders getOrderById(Long id);
	
	/**
	 * 通过主键id获得OrdersDetails
	 * @param id 主键id
	 * */
	public OrdersDetail getOrdersDetailById(Long id)throws BusinessException;
	
	
	List<OrdersDetail> queryOrdersDetailByOrdersIds(Long[] ordersIds);
	/**
	 * 调整订单详情
	 */
	public void editOrderDetailNum(Long detailId,Long num,Long oldNum)throws BusinessException;
	/**
	 * 取消销售订单
	 * */
	public void cancelOrders(Orders orders)throws BusinessException;
	/**
	 * 取消销售订单详情
	 * */
	public void cancelOrderDetail(OrdersDetail ordersDetail)throws BusinessException;
	/**
	 * 根据orderId查询详情
	 */
	public List<OrdersDetail> getDetailListByOrderId(Long orderId);
	/**
	 * 编辑详情
	 */
	public  void editDetails(OrdersDetail ordersDetail);
	/**
	 * 获取对应的员工
	 */
	public List<Employee> getEmp();
	
	
}
