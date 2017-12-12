package com.xy.cms.service;

import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Eqiupment;
import com.xy.cms.entity.Material;
import com.xy.cms.entity.Product;
import com.xy.cms.entity.ProductUint;

public interface ProductService {
	
	/**
	 * 分页查询产品信息
	 * @param param
	 * */
	QueryResult queryProductPage(Map pageMap) throws BusinessException;
	
	/**
	 * 通过主键id得到Product
	 * @param id 产品主键id
	 * @return
	 * */
	public Product getProductById(Long id)throws BusinessException;
	
	/**
	 * 通过主键id得到Product列表
	 * @param id 产品主键id
	 * @return
	 * */
	public List<Product> getProductByOrderId(Long orderId)throws BusinessException;
	
	/**
	 * 查询产品信息
	 */
	public List<Product> queryProduct();
	/**
	 * 保存产品
	 * @return 
	 */
	void saveProduct(Product product) throws BusinessException;
	/**
	 * 更新产品信息
	 */
	public void updateProduct(Product product)throws BusinessException;
	/**
	 * 删除产品信息
	 */
	public void delProduct(Product product)throws BusinessException;
	/**
	 * 批量导入物料
	 * @param List<Product>
	 * */
	public void importProduct(List<Product> productList) throws BusinessException;
	//查询产品及单位
	public List<Object> queryProducAndUnit();
	/**
	 * 查询所有单位
	 */
	public List<ProductUint> queryAllUnit();
	/**
	 * 编辑单位
	 */
	public void editUnit(ProductUint pu);
	/**
	 * 添加一个单位
	 */
	public void saveUnit(ProductUint pu);
	/**
	 * 删除一个单位
	 */
	public void delUnit(ProductUint pu);
	/**
	 * 根据id查询一个单位
	 */
	public ProductUint getPuById(Long id);
	
	public List<ProductUint> getUnitByPid(Long id);
}
