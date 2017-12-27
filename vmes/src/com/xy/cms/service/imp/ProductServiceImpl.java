package com.xy.cms.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Admin;
import com.xy.cms.entity.Eqiupment;
import com.xy.cms.entity.Material;
import com.xy.cms.entity.MaterialType;
import com.xy.cms.entity.Product;
import com.xy.cms.entity.ProductUint;
import com.xy.cms.service.ProductService;
import com.xy.cms.service.SequenceService;

public class ProductServiceImpl extends BaseDAO implements  ProductService{
	private SequenceService sequenceService;
	@Override
	public QueryResult queryProductPage(Map pageMap) throws BusinessException {
		Map<String, Object> param = new HashMap<String, Object>();
		QueryResult result = null;
		BaseQEntity qEntity = (BaseQEntity) pageMap.get("qEntity");
		StringBuilder sql=new StringBuilder();
		sql.append("SELECT * FROM (SELECT mt.name AS mtName,p.id,p.product_code productCode,p.Name AS productName,p.dsc, ");
		sql.append("p.type,p.typespec,p.unit,p.userproduct_code AS userProductCode FROM `product` p LEFT JOIN `material_type` mt ON p.type=mt.id ) source where 1=1");
		//产品名称
		if(CommonFunction.isNotNull(pageMap.get("productName"))){
			sql.append(" and  source.productName like:productName ");
			param.put("productName", "%"+pageMap.get("productName")+"%");
		}
		//产品编号
		if(CommonFunction.isNotNull(pageMap.get("productCode"))){
			sql.append(" and  source.productCode like:productCode ");
			param.put("productCode", "%"+pageMap.get("productCode")+"%");
		}
		//用户产品编号
		if(CommonFunction.isNotNull(pageMap.get("userProductCode"))){
			sql.append(" and  source.userProductCode like:userProductCode ");
			param.put("userProductCode", "%"+pageMap.get("userProductCode")+"%");
		}
		result = this.getPageQueryResultSQLToMap(sql.toString(), param,qEntity);
		return result;
	}

	@Override
	public Product getProductById(Long id) throws BusinessException {
		if(CommonFunction.isNull(id)){
			throw new BusinessException("参数错误");
		}
		return (Product) this.get(Product.class, id);
	}
	
	@Override
	public List<Product> queryProduct() {
		return this.getAll(Product.class);
	}

	@Override
	public List<Product> getProductByOrderId(Long orderId) throws BusinessException {
		String hql="from Product p where p.id in (SELECT d.productId FROM OrdersDetail d WHERE d.orderId=:orderId) ";
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("orderId", orderId);
		return this.getList(hql, map);
	}

	@Override
	public void saveProduct(Product product) throws BusinessException {
		String productCode=sequenceService.getNewNoByTableColumns(421L);
		product.setProductCode(productCode);
		this.save(product);
		
	}

	@Override
	public void updateProduct(Product product) throws BusinessException {
		if(CommonFunction.isNotNull(product)){
			Product newProduct =getProductById(product.getId());
			newProduct.setName(product.getName());
			newProduct.setDsc(product.getDsc());
			newProduct.setType(product.getType());
			newProduct.setStatus(product.getStatus());
			newProduct.setTypespec(product.getTypespec());
			newProduct.setUnit(product.getUnit());
			this.update(newProduct);
		}
		
	}

	@Override
	public void delProduct(Product product) throws BusinessException {
		if(CommonFunction.isNull(product)){
			throw new BusinessException("参数错误");
		}
		this.delete(product);
		
	}
	public SequenceService getSequenceService() {
		return sequenceService;
	}

	public void setSequenceService(SequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	@Override
	public void importProduct(List<Product> productList) throws BusinessException {
		for (Product product : productList) {
			this.saveProduct(product);
		}
		
	}

	@Override
	public List<Object> queryProducAndUnit() {
		String hql="from Product product,ProductUint pu where product.unit=pu.id";
		return this.getList(hql, null);
	}

	@Override
	public List<ProductUint> queryAllUnit() {
		return this.getAll(ProductUint.class);
	}
	public ProductUint getPuById(Long id){
		return (ProductUint)this.get(ProductUint.class, id);
	}
	@Override
	public void editUnit(ProductUint pu) {
		if(CommonFunction.isNotNull(pu)){
			ProductUint newPu=getPuById(pu.getId());
			newPu.setName(pu.getName());
			newPu.setPid(pu.getPid());
			this.update(newPu);
		}
		
	}

	@Override
	public String queryMaType(Integer maId) {
		String sql="select ma.name from `material_type` as ma where ma.id=:maId";
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("maId", maId);
		return (String)this.getUniqueResultSql(sql, map);
	}

	@Override
	public void saveUnit(ProductUint pu) {
		this.save(pu);
	}
	public List<ProductUint> getUnitByPid(Long id){
		String hql="from ProductUint pu where pu.pid=:id";
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("id", id);
		return this.getList(hql, param); 
	}
	@Override
	public void delUnit(ProductUint pu) {
		if(CommonFunction.isNotNull(pu)){
			this.delete(pu);
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
	public MaterialType getMaTpyeByNameAndPid(String name, Long pid) {
		String hql="from MaterialType mt where mt.name=:name and mt.pid=:pid";
		Map<String, Object> m=new HashMap<String, Object>();
		m.put("name", name);
		m.put("pid", pid);
		return (MaterialType)this.getUniqueResult(hql, m);
	}

	@Override
	public Long addMaterialTypeWithId(MaterialType mt) {
		if(CommonFunction.isNotNull(mt)){
			this.save(mt);
		}
		return mt.getId();
	}

}
