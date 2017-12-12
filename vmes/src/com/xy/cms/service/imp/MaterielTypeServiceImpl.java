package com.xy.cms.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.entity.MaterialType;
import com.xy.cms.entity.ProductUint;
import com.xy.cms.service.MaterielTypeService;

public class MaterielTypeServiceImpl extends BaseDAO implements MaterielTypeService{

	@Override
	public List<MaterialType> quertMaterielTypeAll() {
		return this.getAll(MaterialType.class);
	}

	@Override
	public void saveMaterialType(MaterialType materialType) {
		this.save(materialType);
	}

	@Override
	public MaterialType queryMaterialTypeById(Long id) {
		return (MaterialType) this.get(MaterialType.class, id);
	}

	@Override
	public void updateMaterialType(MaterialType materialType) {
		this.update(materialType);
	}

	@Override
	public void deleteMaterialType(MaterialType materialType) {
		Map params = new HashMap();
		params.put("id", materialType.getId());
		this.execute("delete from MaterialType where id=:id or pid=:id", params);
	}

	@Override
	public boolean validateRepeatName(String name, Long pid) {
		String hql = " from MaterialType where pid=:pid and name=:name";
		Map params = new HashMap();
		params.put("name", name);
		params.put("pid", pid);
		if(this.getList(hql, params).size()==0){
			return false;
		}
		return true;
	}

	@Override
	public MaterialType queryMaterialType(String name, Long pid) {
		String hql="from MaterialType mt where mt.name=:name and mt.pid=:pid";
		Map<String, Object> m=new HashMap<String, Object>();
		m.put("name", name);
		m.put("pid", pid);
		return (MaterialType)this.getUniqueResult(hql, m);
	}

}
