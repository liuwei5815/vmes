package com.xy.cms.service;

import java.util.List;

import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.TableColumnType;


public interface TableColumnTypeService {

	List<TableColumnType> getColumnType() throws BusinessException;
}
