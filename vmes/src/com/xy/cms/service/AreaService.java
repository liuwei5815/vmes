package com.xy.cms.service;

import java.util.List;

import com.xy.cms.entity.MaterialType;
import com.xy.cms.entity.ProductUint;
import com.xy.cms.entity.Region;

public interface AreaService {
	
	/**
	 * 查询所有区域
	 */
	public List<Region> queryAllRegion();
	
	/**
	 * 
	 * 添加区域类型
	 * */
	public void saveRegion(Region region);
	
	/**
	 * 
	 * 通过区域类型id查询区域信息
	 * @param id
	 * @return
	 * */
	public Region queryRegionTypeById(Long id);
	
	/**
	 * 编辑区域类型
	 * */
	public void updateRegion(Region gegion);
	
	/**
	 * 删除区域类型
	 * */
	public void deleteRegion(Region region);
}
