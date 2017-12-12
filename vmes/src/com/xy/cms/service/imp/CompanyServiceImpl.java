package com.xy.cms.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.MD5;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Admin;
import com.xy.cms.entity.Company;
import com.xy.cms.entity.Region;
import com.xy.cms.service.AdminService;
import com.xy.cms.service.CompanyService;

public class CompanyServiceImpl extends BaseDAO implements CompanyService{
	private AdminService adminService;
	@Override
	public Company getCompanyById(Long id) {
		return (Company) this.get(Company.class, id);
	}

	@Override
	public void updateCompany(Company company,Admin admin) throws BusinessException{
		if(CommonFunction.isNotNull(company)){
			Company newCompany =getCompanyById(company.getId());
			newCompany.setName(company.getName());
			newCompany.setAddress(company.getAddress());
			/*newCompany.setCity(company.getCity());*/
			newCompany.setContact(company.getContact());
			/*newCompany.setProvince(company.getProvince());*/
			newCompany.setArea(company.getArea());
			newCompany.setTel(company.getTel());
			this.update(newCompany);
			
			admin.setAddDate(new Date());
			if(CommonFunction.isNotNull(admin.getPwd())){
				System.out.println(admin.getId());
				Admin newAdmin=adminService.getAdmin(admin.getId());
				newAdmin.setPwd(MD5.MD5( admin.getPwd()));
				this.update(newAdmin);
			}
			
		}
	}

	@Override
	public QueryResult getRegion(Map pageMap,Long Parentid) {
		String hql = " from Region where Parentid=:Parentid order by id ";
		Map par=new HashMap();
		par.put("Parentid", Parentid);
		QueryResult result = null;
		BaseQEntity qEntity = (BaseQEntity) pageMap.get("qEntity");
		result = this.getPageQueryResult(hql.toString(), par, qEntity);
		return result;
	}

	@Override
	public Region getRegionById(Long id) throws BusinessException {
		return (Region) this.get(Region.class, id);
	}

	@Override
	public void saveCompany(Company company,Admin admin) {
		if(CommonFunction.isNotNull(company)){
			company.setId(1L);
			company.setAddDate(new Date());
			this.save(company);
			
			admin.setAddDate(new Date());
			admin.setPwd(MD5.MD5( admin.getPwd()));
			admin.setRoleId(2L);
			this.save(admin);
		}
	}

	@Override
	public Region findRegionById(Long regionId) {
		 return (Region)this.get(Region.class, regionId);
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}
	
}
