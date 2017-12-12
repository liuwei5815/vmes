package com.xy.cms.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.entity.EqiupmentType;
import com.xy.cms.entity.MaterialType;
import com.xy.cms.service.EquipmentTypeService;

public class EquipmentTypeServiceImpl extends BaseDAO implements EquipmentTypeService{

	@Override
	public List<EqiupmentType> quertEqiupmentTypeAll() {
		return this.getAll(EqiupmentType.class);
	}

	@Override
	public boolean validateRepeatName(String type, Long pid) {
		String hql = " from EqiupmentType where pid=:pid and type=:type";
		Map params = new HashMap();
		params.put("type", type);
		params.put("pid", pid);
		if(this.getList(hql, params).size()==0){
			return false;
		}
		return true;
	}

	@Override
	public void saveEqiupmentType(EqiupmentType eqiupmentType) {
		this.save(eqiupmentType);
	}

	@Override
	public EqiupmentType queryEqiupmentTypeById(Long id) {
		return (EqiupmentType) this.get(EqiupmentType.class, id);
	}

	@Override
	public void updateEqiupmentType(EqiupmentType eqiupmentType) {
		this.update(eqiupmentType);
	}

	@Override
	public void deleteEqiupmentType(EqiupmentType eqiupmentType) {
		Map params = new HashMap();
		params.put("id", eqiupmentType.getId());
		this.execute("delete from EqiupmentType where id=:id or pid=:id", params);
	}

	@Override
	public EqiupmentType queryEqiupmentType(String type, Long pid) {
		String hql="from EqiupmentType mt where mt.type=:type and mt.pid=:pid";
		Map<String, Object> m=new HashMap<String, Object>();
		m.put("type", type);
		m.put("pid", pid);
		return (EqiupmentType)this.getUniqueResult(hql, m);
	}
	
	
	
	

}
