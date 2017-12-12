package com.xy.cms.service.imp;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.xy.cms.common.DateUtil;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.opt.AutoNumberOpt;
import com.xy.cms.entity.Sequence;
import com.xy.cms.entity.Sequence.PeriodType;
import com.xy.cms.entity.TableColumns;
import com.xy.cms.service.SequenceService;

public class SequenceServiceImpl extends BaseDAO implements SequenceService{

	@Override
	public Sequence getLatestAndIncrement(Long colId) {
		String hql = "from Sequence a where a.tableColumnsId=:id order by a.lastResetTime desc,a.currentNumber desc";
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", colId);
		List<Sequence> list =  this.getList(hql,paramMap);
		if(list != null && list.size()!=0){
			Sequence s = list.get(0);
			s.setCurrentNumber(isPeriod(s)?s.getStart():s.getCurrentNumber()+s.getStep());
			s.setLastResetTime(System.currentTimeMillis());
			this.update(s);
			return s;
		}else{
			return null;
		}
	}
	private boolean isPeriod(Sequence sequence){
		if(sequence.getLastResetTime()==null){
			return true;
		}
		if(sequence.getPeriodType()==null){
			return false;
		}
		Calendar lastResetCalendar = Calendar.getInstance();
		lastResetCalendar.setTimeInMillis(sequence.getLastResetTime());
		Calendar now = Calendar.getInstance();
		Integer lastResetYear = lastResetCalendar.get(Calendar.YEAR);
		Integer year =now.get(Calendar.YEAR);
		if(year>lastResetYear){
			return true;
		}
		if(sequence.getPeriodType().equals(PeriodType.DAY.getCode())){
			return now.get(Calendar.DAY_OF_YEAR)>lastResetCalendar.get(Calendar.DAY_OF_YEAR);
		}
		else if (sequence.getPeriodType().equals(PeriodType.WEEK.getCode())){
			return now.get(Calendar.WEEK_OF_YEAR)>lastResetCalendar.get(Calendar.WEEK_OF_YEAR);
		}
		
		return false;
		
	}
	@Override
	public String getNewNoByTableColumns(Long tableColumnsId) {
		TableColumns tableColumns = (TableColumns) this.get(TableColumns.class, tableColumnsId);
		if(!tableColumns.getDataType().equals(TableColumns.DataType.AUTONUMBER.getCode())){
			throw new IllegalArgumentException(" is not autoNumber dataType");
		}
		AutoNumberOpt autoNumberOpt = JSONObject.parseObject(tableColumns.getOptvalue(),AutoNumberOpt.class);
		Sequence newSequence = this.getLatestAndIncrement(tableColumnsId);
		StringBuilder newNo= new StringBuilder();
		newNo.append(autoNumberOpt.getPrefix()==null?"":autoNumberOpt.getPrefix());
		newNo.append(autoNumberOpt.getTimeStr()==null?"":DateUtil.format(autoNumberOpt.getTimeStr(), new Date()));
		newNo.append(String.format("%0"+newSequence.getPosition()+"d",newSequence.getCurrentNumber()));
		newNo.append(autoNumberOpt.getSuffix()==null?"":autoNumberOpt.getTimeStr());
		return newNo.toString();
		
	}


}
