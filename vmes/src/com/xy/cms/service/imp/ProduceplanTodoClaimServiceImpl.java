package com.xy.cms.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.entity.ProduceplanTodoClaim;
import com.xy.cms.entity.TodoClaimEquip;
import com.xy.cms.entity.TodoClaimMaterial;
import com.xy.cms.service.ProduceplanTodoClaimService;

public class ProduceplanTodoClaimServiceImpl extends BaseDAO implements ProduceplanTodoClaimService {

	@Override
	public List<ProduceplanTodoClaim> queryTodoClaimByTodoId(Long id) {
		String hql = "select claim,e.name from ProduceplanTodoClaim claim,AppUser u,Employee e where e.id= u.empId and u.id =claim.executor  and  claim.todoId=:todoId";
		Map<String,Object> param = new HashMap();
		param.put("todoId", id);
		return this.getList(hql, param);
	}

	@Override
	public List<Object[]> queryTodoClaimByTodoIds(Long[] todoId) {
		String hql = "select claim,e.name from ProduceplanTodoClaim claim,AppUser u,Employee e where e.id= u.empId and u.id =claim.executor  and  claim.todoId in (:todoId)";
		Map<String,Object> param = new HashMap();
		param.put("todoId", todoId);
		return this.getList(hql, param);
	}

	@Override
	public List<TodoClaimEquip> queryEquipByToId(Long claimId) {
		String hql="select r.equipId as equipid,r.equipCode as equipcode,r.equipName as equipname,r.equipModel as model from TodoClaimEquip r where r.claimId=:claimId";
		Map<String,Object> map=new HashMap();
		map.put("claimId", claimId);
		System.out.println("sssss:"+hql);
		return this.getList(hql, map);
	}

	@Override
	public List<TodoClaimMaterial> queryMaByToId(Long claimId) {
		String hql="select r.materialId as id,r.materialCode as code,r.materialName as name,r.batchNumber as batchno , r.materialModel as model from TodoClaimMaterial r where r.claimId=:claimId";
		Map<String,Object> map=new HashMap();
		map.put("claimId", claimId);
		return this.getList(hql, map);
	}

}
