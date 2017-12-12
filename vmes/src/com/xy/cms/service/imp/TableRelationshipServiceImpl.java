package com.xy.cms.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.entity.TableRelationship;
import com.xy.cms.service.TableRelationshipService;

public class TableRelationshipServiceImpl extends BaseDAO implements TableRelationshipService {

	@Override
	public List<TableRelationship> getRelationBySourceTableId(Long tableId) {
		String hql = "from TableRelationship ship where ship.sourceTableId=:tableId and ship.fs="+TableRelationship.SELECT_PK;
		Map param = new HashMap();
		param.put("tableId", tableId);
		return this.getList(hql, param);
	}

	/**
	 * tableId作为主表需要添加从表的情况
	 * @param tableId
	 * @return TableRelationship
	 */
	public List<TableRelationship> getRelationByTargetTabelId(Long tableId){
		String hql = "from TableRelationship ship where ship.targetTableId=:tableId and ship.fs="+TableRelationship.ADD_FK;
		Map param = new HashMap();
		param.put("tableId", tableId);
		return this.getList(hql, param);
	}
	@Override
	public TableRelationship getRelationById(Long id) {
		
		return (TableRelationship) this.get(TableRelationship.class, id);
	}

	@Override
	public Map<Long,TableRelationship> getRelationMapByTableId(Long tableId) {
		StringBuilder hql = new StringBuilder("select id,");
		hql.append("(select id from TableRelationship c where c.sourceTableId =t.targetTableId  and c.targetTableId  in(");
		hql.append(" select targetTableId  from TableRelationship ship where ship.sourceTableId =:tableId and ship.id!=t.id)");
		hql.append(") as shipId");
		hql.append(" from TableRelationship t");
		hql.append(" where t.sourceTableId=:tableId");
		Map param = new HashMap();
		param.put("tableId", tableId);
		List list = this.getList(hql.toString(), param);
		if(CommonFunction.isNull(list)){
			return null;
		}
		Map rMap = new HashMap();
		for(int i=0;i<list.size();i++){
			Object[] o = (Object[]) list.get(i);
			if(o[1]!=null){
				rMap.put(o[0],getRelationById((Long)o[1]));
			}
		}
		return rMap;
	}

	@Override
	public TableRelationship getRelation(Long sourceTableId, Long targetTableId) {
		String hql="from TableRelationship where sourceTableId="+sourceTableId+" and targetTableId="+targetTableId;
		List list=this.find(hql);
		if(list.size()>0){
			TableRelationship ship=(TableRelationship) list.get(0);
			return ship;
		}
		return null;
	}

	@Override
	public TableRelationship getTreeRelationship(Long mainTable) {
		String hql="from TableRelationship where sourceTableId=:mainTable and targetTableId=:mainTable";
		Map<String,Object> pageMap=new HashMap<String, Object>();
		pageMap.put("mainTable", mainTable);
		return (TableRelationship) this.getFirstResult(hql, pageMap);
	}

}
