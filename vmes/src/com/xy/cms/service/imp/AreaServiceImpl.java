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
import com.xy.cms.entity.Region;
import com.xy.cms.service.AreaService;
import com.xy.cms.service.ProductService;
import com.xy.cms.service.SequenceService;

public class AreaServiceImpl extends BaseDAO implements  AreaService{

	@Override
	public List<Region> queryAllRegion() {
		return this.getAll(Region.class);
	}

	@Override
	public void saveRegion(Region region) {
		this.save(region);
	}

	@Override
	public Region queryRegionTypeById(Long id) {
		return (Region) this.get(Region.class, id);
	}

	@Override
	public void updateRegion(Region gegion) {
		this.update(gegion);
	}

	@Override
	public void deleteRegion(Region region) {
		this.delete(region);
	}
	
}
