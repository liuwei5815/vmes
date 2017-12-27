package com.xy.cms.service;

import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Admin;
import com.xy.cms.entity.Company;
import com.xy.cms.entity.Region;

public interface CompanyService {
	/**
	 * 根据主键id获取公司信息
	 * @param id 主键id
	 * @return
	 * */
	public Company getCompanyById(Long id);
	
	/**
	 * 保存公司信息
	 * */
	public void updateCompany(Company company,Admin admin)throws BusinessException;
	
	/**
	 * 查询省
	 * */
	public QueryResult getRegion(Map pageMap,Long Parentid);
	
	/**
	 * 通过主键id得到省市
	 * @param id 主键id
	 * @return 
	 * */
	public Region getRegionById(Long id)throws BusinessException;
	
	/**
	 * 添加一个指定的公司信息
	 * */
	public void saveCompany(Company company,Admin admin);
	/**
	 * 根据主键获取行政区域
	 */
	public Region findRegionById(Long regionId);
	
	/**
	 * 查询符合条件的第一条记录
	 */
	public Company getCompanyOne();
}
