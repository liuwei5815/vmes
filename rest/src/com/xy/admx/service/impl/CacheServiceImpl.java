package com.xy.admx.service.impl;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import com.xy.admx.common.CachePool;
import com.xy.admx.core.service.base.BaseServiceImpl;
import com.xy.admx.service.ICacheService;
import com.xy.cms.entity.CodeList;
import com.xy.cms.entity.Config;


@Service
public class CacheServiceImpl extends BaseServiceImpl implements ICacheService {

	private Logger logger = Logger.getLogger(CacheServiceImpl.class);
	/**
	 * 初始化
	 */
	@Override
	public void init() throws Exception {
		initCodeList();
		
	
		initConfig();
		logger.debug("===============================" + " 配置文件加载成功");
	}


	/**
	 * 初始化codelist
	 */
	public void initCodeList(){
		String hql = "from CodeList order by typeId";
		List list = this.getList(hql);
		if(list!=null && !list.isEmpty()){
			Map tCodeListCache1 = Collections.synchronizedMap(new LinkedHashMap());
			Map tCodeListCache2 = Collections.synchronizedMap(new LinkedHashMap());

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


	/**
	 * 系统关闭清除缓存
	 */
	@Override
	public void destroy() {
		 CachePool.destroy();
	}
}
