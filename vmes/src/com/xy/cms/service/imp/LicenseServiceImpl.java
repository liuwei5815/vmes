package com.xy.cms.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.MD5;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Admin;
import com.xy.cms.entity.AppUser;
import com.xy.cms.entity.License;
import com.xy.cms.service.AdminService;
import com.xy.cms.service.LicenseService;

public class LicenseServiceImpl extends BaseDAO implements LicenseService {

	@Override
	public void save(License lic) {
		if(CommonFunction.isNotNull(lic)){
			this.save(lic);
		}
	}

	@Override
	public License getLicById(Long id) {
		if(CommonFunction.isNotNull(id)){
			return (License)this.get(License.class, id);
		}
		return null;
	}

	@Override
	public void update(License lic) {
		if(CommonFunction.isNotNull(lic)){
			super.update(lic);
		}
		
	}
}
