package com.xy.admx.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xy.admx.core.service.base.BaseServiceImpl;
import com.xy.admx.service.ApiAuthService;
import com.xy.cms.entity.ApiAuthDetail;
import com.xy.cms.entity.AppUser;
@Service
public class ApiAuthServiceImpl extends BaseServiceImpl implements ApiAuthService {

	@Override
	public Boolean isAuth(AppUser user, Long apiAuthId) {
		StringBuilder hql =new StringBuilder("select count(*) from ApiAuthDetail where apiAuth.id=:apiAuthId and ((type="+ApiAuthDetail.Type.account.getCode()+" and value=:userId) or (type="+ApiAuthDetail.Type.role.getCode()+" and value=:roleId))");
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("userId", user.getId());
		params.put("roleId", user.getRoleId());
	
		return Integer.valueOf(this.getUniqueResult(hql.toString(),params).toString())>0;
	}

}
