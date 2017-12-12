package com.xy.cms.service;

import java.util.List;

import com.xy.cms.entity.TableColumns;

public interface TableColumnsService {
		/**
		 * 根据主键id得到起TableColumns
		 * @param id
		 * @return TableColumns
		 */
		TableColumns getTableColumnsById(Long id);
		
		
		/**
		 * 根据table和类型查相对应的id
		 * @param id
		 * @return TableColumns
		 */
		List<TableColumns> getTableColumnsByTIdAndType(Long tableId,Long dataType);
		
		
		
		/**
		 * 是否存在创建时间
		 * @param tableId
		 * @return
		 */
		boolean existsAddDate(Long tableId);
		
		/**
		 * 是否存在修改时间
		 * @param tableId
		 * @return
		 */
		boolean existsUpdateDate(Long tableId);
		
		/**
		 * 根据tableId得到字段
		 * @param tableId
		 * @return
		 */
		List<TableColumns> queryTableColumnsByTableId(Long tableId);


		TableColumns gerPriayKeyColumnsByTableId(Long tableId);
}
