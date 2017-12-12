package com.xy.cms.service.imp;

import java.util.List;
 
import com.xy.cms.common.base.BaseDAO; 
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.TableColumnType; 
import com.xy.cms.service.TableColumnTypeService; 

public class TableColumnTypeServiceImpl extends BaseDAO implements TableColumnTypeService {

	@Override
	public List<TableColumnType> getColumnType() throws BusinessException {
		String hql =" from TableColumnType";
		return this.find(hql);
	}
}
