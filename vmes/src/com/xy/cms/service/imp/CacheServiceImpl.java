package com.xy.cms.service.imp;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.logging.Logger;

import com.xy.cms.common.Auth_Authaction;
import com.xy.cms.common.CacheFun;
import com.xy.cms.common.CachePool;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.entity.Authaction;
import com.xy.cms.entity.CodeList;
import com.xy.cms.entity.Config;
import com.xy.cms.entity.TableColumnType;
import com.xy.cms.service.ICacheService;

public class CacheServiceImpl extends BaseDAO implements ICacheService {

	private Logger logger = Logger.getLogger(CacheServiceImpl.class);
	/**
	 * 初始化
	 */
	public void init() throws Exception {
		initCodeList();		 
		initAuthaction();
		initColumnType();
		initConfig();
		initAppModular();
		logger.debug("===============================" + " 配置文件加载成功");
	}
	
	
	private void initAppModular(){
		String hql = " from AppModular ";
		CachePool.appModularList = this.getList(hql, null);
		
	}
	
	
	
	public void initColumnType(){
		String hql ="from TableColumnType ";
		Map m = new ConcurrentHashMap<Long,TableColumnType>();
		List<TableColumnType> types = this.find(hql);
		for (TableColumnType tableColumnType : types) {
			m.put(tableColumnType.getCode(),tableColumnType);
		}
		CachePool.columnTypeCache=m;
	}
	/**
	 * 初始化codelist
	 */
	public void initCodeList(){
		String hql = "from CodeList order by typeId";
		List list = this.getList(hql, null);
		if(list!=null && !list.isEmpty()){
			Map tCodeListCache1 = Collections.synchronizedMap(new HashMap());
			Map tCodeListCache2 = Collections.synchronizedMap(new HashMap());
			
			int listSize = list.size();
			CodeList bean = null;
			for(int i=0;i<listSize;i++){
				bean = (CodeList)list.get(i);
				Map tm1 = (Map)tCodeListCache1.get(bean.getTypeId());
				Map tm2 = (Map)tCodeListCache2.get(bean.getTypeId());
				if(tm1==null){
					tm1 = Collections.synchronizedMap(new TreeMap());
					tCodeListCache1.put(bean.getTypeId(),tm1);
				}
				if(tm2==null){
					tm2 = Collections.synchronizedMap(new TreeMap());
					tCodeListCache2.put(bean.getTypeId(),tm2);
				}
				tm1.put(bean.getCode(),bean.getContents());
				tm2.put(bean.getContents(),bean.getCode());
			}
			CachePool.codeListCache = tCodeListCache1;
			CachePool.CodeListCache2 = tCodeListCache2;
			logger.debug("codeList缓存加载完成");
		}
	}
	
	/**
	 * 初始化系统配置
	 */
	private void initConfig(){
		List list = this.getAll(Config.class);
		for(int i = 0 ; i < list.size() ;i++){
			Config config = (Config)list.get(i);
			CachePool.config.put(config.getConfname(),config.getConfvalue());
		}
	}
	
	private void initAuthaction(){
		String hql = "from Authaction t where t.state='1' ";
		List<Authaction> list = getList(hql,null);
		for(Authaction action : list){
			Auth_Authaction.actionMap.put(action.getAction(), action);
		}
		logger.info("Authaction 缓存成功!");
	}
	/**
	 * 系统关闭清除缓存
	 */
	public void destroy() {
		 CachePool.destroy();
	}
}
