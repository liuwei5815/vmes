package com.xy.cms.service.imp;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.DateUtil;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Customer;
import com.xy.cms.entity.Product;
import com.xy.cms.entity.Role;
import com.xy.cms.service.CustomerService;
import com.xy.cms.service.SequenceService;

public class CustomerServiceImpl extends BaseDAO implements CustomerService {
	private SequenceService sequenceService;
	@Override
	public Customer getCustomerById(Long id) throws BusinessException {
		return (Customer) this.get(Customer.class, id);
	}

	@Override
	public QueryResult queryCustomerPage(Map<String, Object> map) throws BusinessException {
		try {
			QueryResult result = null;
			Map<String, Object> param = new HashMap<String, Object>();
			BaseQEntity qEntity = (BaseQEntity) map.get("qEntity");
			StringBuffer hql = new StringBuffer();
			hql.append("from Customer c where 1=1");
			//用户设备编号
			if(CommonFunction.isNotNull(map.get("cusName"))){
				hql.append(" and  c.name like:cusName ");
				param.put("cusName", "%"+map.get("cusName")+"%");
			}
			result = this.getPageQueryResult(hql.toString(), param, qEntity);
			return result;
		} catch (Exception e) {
			throw new BusinessException("分页查询客户发生异常,Error:" + e.getMessage());
		}	}

	@Override
	public void saveCustomer(Customer customer) throws BusinessException {
		try {
			customer.setAddDate(new Timestamp(new Date().getTime()));
			String cusCode=sequenceService.getNewNoByTableColumns(590L);
			customer.setCustomerCode(cusCode);
			this.save(customer);
		} catch (Exception e) {
			throw new BusinessException("添加客户信息异常:"+e.getMessage());
		}
		
	}

	@Override
	public void del(Customer cus) throws BusinessException {
		if(CommonFunction.isNull(cus)){
			throw new BusinessException("参数错误");
		}
		this.delete(cus);
	}

	@Override
	public void upateCustomer(Customer customer) throws BusinessException {
		if(CommonFunction.isNotNull(customer)){
			Customer newCus =getCustomerById(customer.getId());
			newCus.setName(customer.getName());
			newCus.setContact(customer.getContact());
			newCus.setTel(customer.getTel());
			newCus.setUpdateDate(new Timestamp(new Date().getTime()));
			this.update(newCus);
		}
		
	}

	public SequenceService getSequenceService() {
		return sequenceService;
	}

	public void setSequenceService(SequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	@Override
	public Customer getCusByName(String name) {
		String hql="from Customer c where c.name=:name";
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("name", name);
		return (Customer)this.getUniqueResult(hql, map);
	}

	@Override
	public void importCustomer(List<Customer> customerList) throws BusinessException {
		for (Customer customer : customerList) {
			this.saveCustomer(customer);
		}
		
	}

	@Override
	public List<Customer> queryAllCustomer() {
		return this.getAll(Customer.class);
	}

}
