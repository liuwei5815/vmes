package com.xy.cms.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xy.cms.common.CacheFun;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.ConfigBean;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.service.IWebPageItemsService;

/**
 * 下拉框
 * 
 * @author 武汉夏宇信息
 */
public class WebPageItemsServiceImpl extends BaseDAO implements IWebPageItemsService {

	public boolean isExistUser(String userName) {
		String hql = "from Admin a where a.account='" + userName + "'";
		List list = this.getList(hql, null);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	public String getCodeName(String typeID, String code) {
		return CacheFun.getCodeText(typeID, code);
	}

	public Map getCodeList(String type) {
		return CacheFun.getCodeList(type);
	}
	
	public Map getTelCity(){
		Map map = new HashMap();
		String[] citys = ConfigBean.getStringValue("telcity").split(",");
		for(String city:citys){
			map.put(city, city);
		}
		return map;
	}

	public int calAgeByIdCard(String idCard){
		int age =0;
		try {
			age = CommonFunction.calAgeByIdCard(idCard);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return age;
	}
	
	
	
}