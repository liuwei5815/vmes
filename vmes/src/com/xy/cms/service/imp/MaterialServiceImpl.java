package com.xy.cms.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.zxing.qrcode.encoder.QRCode;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.QrCodeFactoty;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Company;
import com.xy.cms.entity.Eqiupment;
import com.xy.cms.entity.EqiupmentType;
import com.xy.cms.entity.Material;
import com.xy.cms.entity.MaterialType;
import com.xy.cms.entity.Orders;
import com.xy.cms.service.EquipmentService;
import com.xy.cms.service.MaterialService;
import com.xy.cms.service.SequenceService;

public class MaterialServiceImpl extends BaseDAO implements MaterialService{
	private SequenceService sequenceService;
	@Override
	public QueryResult queryAllMaterial(Map<String, Object> map) throws BusinessException {
		QueryResult result = null;
		Map m = new HashMap();
		BaseQEntity qEntity = (BaseQEntity) map.get("qEntity");
		Orders orders = (Orders) map.get("orders");
		StringBuilder sql = new StringBuilder("select ma,maType from Material ma,MaterialType maType where ma.materialType=maType.id ");
		//物料名称
		if(CommonFunction.isNotNull(map.get("maName"))){
			sql.append(" and  ma.materialName like:maName ");
			m.put("maName", "%"+map.get("maName")+"%");
		}
		//物料编号
		if(CommonFunction.isNotNull(map.get("maCode"))){
			sql.append(" and  ma.materialCode like:maCode ");
			m.put("maCode", "%"+map.get("maCode")+"%");
		}
		//用户物料编号
		if(CommonFunction.isNotNull(map.get("maUserCode"))){
			sql.append(" and  ma.usermaterialCode like:maUserCode ");
			m.put("maUserCode", "%"+map.get("maUserCode")+"%");
		}
		/*if(CommonFunction.isNotNull(code)){
			sql.append(" and (orders.orderCode like :code or orders.orderCodeAuto like :code)");
			m.put("code", code);
		}
		if(CommonFunction.isNotNull(state)){
			sql.append(" and  orders.state=:state ");
			m.put("state", state);
		}*/
		sql.append(" order by ma.id desc");
	
		result=this.getPageQueryResult(sql.toString(), m, qEntity);
		return result;
	}

	@Override
	public void saveMaterial(Material ma) throws BusinessException {
		String maCode=sequenceService.getNewNoByTableColumns(584L);
		ma.setMaterialCode(maCode);
		ma.setMaterialqrCode(maCode);
		this.save(ma);
		QrCodeFactoty.createTodoQrCode(ma.getMaterialCode(), ma.getMaterialqrCode());
	}
	@Override
	public Material getMaterialById(Long id) {
		return (Material) this.get(Material.class, id);
	}
	
	@Override
	public void updateMaterial(Material ma) throws BusinessException {
		if(CommonFunction.isNotNull(ma)){
			Material newMa =getMaterialById(ma.getId());
			newMa.setMaterialName(ma.getMaterialName());
			newMa.setMaterialSpec(ma.getMaterialSpec());
			newMa.setMaterialType(ma.getMaterialType());
			newMa.setMaterialUnit(ma.getMaterialUnit());
			newMa.setUsermaterialCode(ma.getUsermaterialCode());
			this.update(newMa);
		}
		
	}
	public SequenceService getSequenceService() {
		return sequenceService;
	}

	public void setSequenceService(SequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	@Override
	public void delMaterial(Material ma) throws BusinessException {
		if(CommonFunction.isNull(ma)){
			throw new BusinessException("参数错误");
		}
		this.delete(ma);
		
	}

	@Override
	public String queryMaType(Integer maId) {
		String sql="select ma.name from `material_type` as ma where ma.id=:maId";
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("maId", maId);
		return (String)this.getUniqueResultSql(sql, map);
	}

	@Override
	public void importMaterial(List<Material> materialList) throws BusinessException {
		for (Material material : materialList) {
			this.saveMaterial(material);
		}
		
	}

	@Override
	public MaterialType getMaTpyeByName(String name) {
		String hql="from MaterialType mt where mt.name=:name";
		Map<String, Object> m=new HashMap<String, Object>();
		m.put("name", name);
		return (MaterialType)this.getUniqueResult(hql, m);
	}

	@Override
	public Long addMaterialTypeWithId(MaterialType mt) {
		if(CommonFunction.isNotNull(mt)){
			this.save(mt);
		}
		return mt.getId();
	}

	@Override
	public MaterialType getMaTpyeByNameAndPid(String name, Long pid) {
		String hql="from MaterialType mt where mt.name=:name and mt.pid=:pid";
		Map<String, Object> m=new HashMap<String, Object>();
		m.put("name", name);
		m.put("pid", pid);
		return (MaterialType)this.getUniqueResult(hql, m);
	}

	@Override
	public List<Material> queryMaterialByIds(Long[] ids) {
		StringBuilder hql = new StringBuilder("from Material where id in:ids");
		Map<String,Object> values= new HashMap<String, Object>();
		values.put("ids", ids);
		return this.getList(hql.toString(), values);
	}


	

	
	
	

}
