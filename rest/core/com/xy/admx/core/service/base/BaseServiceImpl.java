package com.xy.admx.core.service.base;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.xy.admx.common.base.PageQEntity;
import com.xy.admx.common.hibernate.Updater;
import com.xy.admx.core.common.CommonFunction;
@Service
public class BaseServiceImpl implements BaseService{

	protected static transient Logger logger = Logger.getLogger(BaseServiceImpl.class.getName());

	private SessionFactory sessionFactory;

	protected static Logger log = Logger.getLogger(BaseServiceImpl.class);

	/*
	/***************************************************************************
	 * 取得某个类的所有的记录的List集合
	 ************************************************************************
	 ***/
	public <T> List<T> getAll(Class<T> clazz){
		return sessionFactory.getCurrentSession().createCriteria(clazz).setCacheable(true).list();
	}


	/***************************************************************************
	 * 用主键获取某个类（即数据库中的一条记录）
	 **************************************************************************/
	public <T> T get(Class<T> clazz, Serializable id){
		Object o = sessionFactory.getCurrentSession().get(clazz, id);
		return (T)o;
	}

	/***************************************************************************
	 * lockOptions表示锁的模式，如果使用for update 语句， 则LockOptions.UPGRADE
	 **************************************************************************/
	public <T> T get(Class<T> clazz, Serializable id, LockOptions lockOptions){
		Object o = sessionFactory.getCurrentSession().get(clazz, id, lockOptions);
		return (T) o;
	}


	/***************************************************************************
	 * 保存或更新一条记录
	 **************************************************************************/
	public void saveOrUpdate(Object o) {
		sessionFactory.getCurrentSession().saveOrUpdate(o);

	}
	/**
	 * 将更新对象拷贝至实体对象，并处理many-to-one的更新。
	 * 
	 * @param updater
	 * @param po
	 */
	private void updaterCopyToPersistentObject(Updater updater, Object po,
			ClassMetadata cm) {
		String[] propNames = cm.getPropertyNames();
		String identifierName = cm.getIdentifierPropertyName();
		Object bean = updater.getBean();
		Object value;
		for (String propName : propNames) {
			if (propName.equals(identifierName)) {
				continue;
			}
			try {
				value = PropertyUtils.getSimpleProperty(bean, propName);
				if (!updater.isUpdate(propName, value)) {
					continue;
				}
				cm.setPropertyValue(po, propName, value);
			} catch (Exception e) {
				throw new RuntimeException(
						"copy property to persistent object failed: '"
								+ propName + "'", e);
			}
		}

	}

	/***************************************************************************
	 * 保存某个类（即数据库中的一条记录）
	 **************************************************************************/
	public void save(final Object obj) {
		sessionFactory.getCurrentSession().save(obj);

	}

	/***************************************************************************
	 * 删除某个类（即数据库中的一条记录）
	 **************************************************************************/
	public void delete(final Object obj){
		sessionFactory.getCurrentSession().delete(obj);

	}

	/***************************************************************************
	 * 更新某个类（即数据库中的一条记录）
	 **************************************************************************/
	public void update(final Object obj){
		sessionFactory.getCurrentSession().update(obj);
	}
	/***************************************************************************
	 * 更新某个类（即数据库中的一条记录），在缓存池中有则更新，没有就保存
	 **************************************************************************/
	@Override
	public Object merge(Object object){
		return sessionFactory.getCurrentSession().merge(object);
	}
	/***************************************************************************
	 * 执行一条hql（非查询语句）
	 **************************************************************************/
	public int execute(final String exhql){
		return sessionFactory.getCurrentSession().createQuery(exhql).executeUpdate();
	}

	/***************************************************************************
	 * 执行一条hql（非查询语句）
	 **************************************************************************/
	public int execute(final String exhql, final Map params){
		Query query = sessionFactory.getCurrentSession().createQuery(exhql);
		setQueryMap(query, params);
		return query.executeUpdate();
	}
	public void executeSQL(final String sql){
		sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

	public int executeSQL(final String sql, final Map params){
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		setQueryMap(query, params);
		return query.executeUpdate();
	}

	/**
	 * 查询某个范围内的结果集
	 * @param start	开始行
	 * @param count	结束行
	 * @param hql
	 * @return
	 */
	public List getList(final int start, final int count, final String hql) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql).setCacheable(true);
		query.setFirstResult(start);
		query.setMaxResults(count);
		return query.list();
	}
	
	/**
	 * 取满足查询条件的第一条记录
	 * @param hql
	 * @param params
	 * @return
	 */
	public Object getFirstResult(String hql , Map params){

		Query query = sessionFactory.getCurrentSession().createQuery(hql).setCacheable(true);
		setQueryMap(query, params);
		query.setMaxResults(1);
		List list = query.list();
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	public Map<String,Object> getFirstResultBySqlToMap(String hql , Map params){

		Query query = sessionFactory.getCurrentSession().createSQLQuery(hql).setCacheable(true);
		setQueryMap(query, params);
		query.setMaxResults(1);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		if(list != null && list.size() > 0){
			return (Map<String, Object>) list.get(0);
		}
		return null;
	}
	
	
	/*
	public int[] batchUpdate(String[] sql) throws Exception {
		SessionFactory sessionFactory = 
		Session session = sessionFactory.openSession();
		Connection conn = session.connection();
		Statement stmt = null;
		int[] count = null;
		try {
			stmt = conn.createStatement();
			if (sql != null) {
				for (int i = 0; i < sql.length; i++) {
					if (sql[i].length() > 1) {
						stmt.addBatch(sql[i]);
					}
				}
				count = stmt.executeBatch();
			}
		} catch (Exception e) {
			conn.rollback();
			throw new Exception(e.getMessage());
		} finally {
			stmt.close();
			conn.close();
		}

		return count;
	}
	 */

	
	public List findBySQLToBean(final String sql,final Class beanClass) {
		List list=  sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(beanClass).list();;
		return list;
	}

	/***************************************************************************
	 * 自动匹配参数类型
	 * 
	 * @param query
	 * @param key
	 * @param value
	 **************************************************************************/
	protected void setParameterValue(Query query, String key, Object value) {
		if (null == key) {
			return;
		} if(value == null){
			query.setParameter(key,null);
		}else if (value instanceof Boolean) {
			query.setBoolean(key, ((Boolean) value).booleanValue());
		} else if (value instanceof String) {
			query.setString(key, (String) value);
		} else if (value instanceof Integer) {
			query.setInteger(key, ((Integer) value).intValue());
		} else if (value instanceof Long) {
			query.setLong(key, ((Long) value).longValue());
		} else if (value instanceof Float) {
			query.setFloat(key, ((Float) value).floatValue());
		} else if (value instanceof Double) {
			query.setDouble(key, ((Double) value).doubleValue());
		} else if (value instanceof BigDecimal) {
			query.setBigDecimal(key, (BigDecimal) value);
		} else if (value instanceof Byte) {
			query.setByte(key, ((Byte) value).byteValue());
		} else if (value instanceof Calendar) {
			query.setCalendar(key, (Calendar) value);
		} else if (value instanceof Character) {
			query.setCharacter(key, ((Character) value).charValue());
		} else if (value instanceof Timestamp) {
			query.setTimestamp(key, (Timestamp) value);
		} else if (value instanceof Date) {
			query.setDate(key, (Date) value);
		} else if (value instanceof Short) {
			query.setShort(key, ((Short) value).shortValue());
		} else if (value instanceof String[]) {
			query.setParameterList(key,(String[])value);
		}
		else{
			query.setParameter(key, value);
		}
	}

	

	/***************************************************************************
	 * 用于分页查询返回一个结果集 其中map里面装的是参数列表 qEntity是分页类
	 **************************************************************************/
	public List getQueryResult(final String hql, final Map values,
			final PageQEntity qEntity) {
		log.debug(qEntity);
		Query query = sessionFactory.getCurrentSession().createQuery(hql).setCacheable(true);
		// 调用此方法向query对象中绑定查询参数
		setQueryMap(query, values);
		// 分页控制 (根据当前的页数来得到要现在的记录集)
		query.setFirstResult((qEntity.getCurrentPage() - 1) * qEntity.getPerPageRows());
		query.setMaxResults(qEntity.getPerPageRows());
		List list = query.list();
		return list;


	}
	/***************************************************************************
	 * 用于分页查询返回一个结果集 其中map里面装的是参数列表 qEntity是分页类
	 **************************************************************************/
	public List getQueryResultMap(final String hql, final Map values,
			final PageQEntity qEntity) {
		log.debug(qEntity);
		Query query = sessionFactory.getCurrentSession().createQuery(hql).setCacheable(true);
		// 调用此方法向query对象中绑定查询参数
		setQueryMap(query, values);
		// 分页控制 (根据当前的页数来得到要现在的记录集)
		query.setFirstResult((qEntity.getCurrentPage() - 1) * qEntity.getPerPageRows());
		query.setMaxResults(qEntity.getPerPageRows());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		return list;

	}
	/***************************************************************************
	 * 用于分页查询返回一个结果集 其中map里面装的是参数列表 qEntity是分页类
	 **************************************************************************/
	public List getQueryResultSQL( final String hql,
			final Map values, final PageQEntity qEntity){
		log.info(qEntity);
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(hql);

		// 调用此方法向query对象中绑定查询参数
		setQueryMap(query, values);
		// 分页控制 (根据当前的页数来得到要现在的记录集)
		query.setFirstResult((qEntity.getCurrentPage() - 1) * qEntity.getPerPageRows());
		query.setMaxResults(qEntity.getPerPageRows());
		List list = query.list();
		return list;
	}

	public List getQueryResultSQLToMap( final String hql,
			final Map values, final PageQEntity qEntity){
		log.info(qEntity);
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(hql);
		// 调用此方法向query对象中绑定查询参数
		setQueryMap(query, values);
		// 分页控制 (根据当前的页数来得到要现在的记录集)
		query.setFirstResult((qEntity.getCurrentPage() - 1) * qEntity.getPerPageRows());
		query.setMaxResults(qEntity.getPerPageRows());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		return list;

	}
	
	/***************************************************************************
	 * 用于分页查询返回一个结果集 其中map里面装的是参数列表 qEntity是分页类
	 **************************************************************************/
	public List getQueryResultSQL(final String classPath, final String hql,
			final Map values, final PageQEntity qEntity){
		log.info(qEntity);
		Class cl = null;
		try {
			cl = Class.forName(classPath);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(hql).addEntity(cl);

		// 调用此方法向query对象中绑定查询参数
		setQueryMap(query, values);
		// 分页控制 (根据当前的页数来得到要现在的记录集)
		query.setFirstResult((qEntity.getCurrentPage() - 1) * qEntity.getPerPageRows());
		query.setMaxResults(qEntity.getPerPageRows());
		List list = query.list();
		return list;
	}


	/***************************************************************************
	 * 根据你传入的hql语句得到记录集的记录条数
	 **************************************************************************/
	public int getQueryCount(final String hql, final Map values){
		int fromIndex = hql.indexOf("from");
		String hqlSub=hql.substring(fromIndex);
		int oderById=hqlSub.indexOf("order by");
		String q;
		if(oderById!=-1){
			q = "select count(*) " + hqlSub.substring(0,oderById);
		}else{
			q = "select count(*) " + hqlSub;
		}
		Query query = sessionFactory.getCurrentSession().createQuery(q);
		// 设置参数
		setQueryMap(query, values);
		Long queryCountLong = (Long) query.uniqueResult();
		Integer queryCount = queryCountLong.intValue();
		return 	queryCount;
	}

	public void getPageQueryResultSQL(final String hql, 
			final Map values, final PageQEntity qEntity){
		List list = this.getQueryResultSQL(hql, values, qEntity);
		if (list != null && list.size() > 0) {
			int totalCount = this.getQueryCountSQL(hql, values);
			qEntity.setTotalCount(totalCount);
			qEntity.setTotalPage();
			qEntity.setList(list);
		}
	}

	public void getPageQueryResultSQLToMap(final String hql, 
			final Map values, final PageQEntity qEntity){
		List list = this.getQueryResultSQLToMap(hql, values, qEntity);
		if (list != null && list.size() > 0) {
			int totalCount = this.getQueryCountSQL(hql, values);
			qEntity.setTotalCount(totalCount);
			qEntity.setTotalPage();
			qEntity.setList(list);
		}
	}

	public int getQueryCountSQL(final String sql, final Map values){
		int oderById = sql.indexOf("order by");
		String q;
		if (oderById != -1) {
			q = "select count(*) from ("+ sql.substring(0, oderById) + " ) t ";
		} else {
			q = "select count(*) from (" + sql + " ) t";
		}
		SQLQuery query =sessionFactory.getCurrentSession().createSQLQuery(q);
		// 设置参数
		setQueryMap(query, values);
		Object queryCount = (Object) query.uniqueResult();
		BigInteger big=(BigInteger) queryCount;
		return big.intValue();
	}
	
	/***************************************************************************
	 * 查询结果集 map里面放入参数 qEntity为分页类
	 **************************************************************************/
	public void getPageQueryResult(final String hql, final Map values, PageQEntity qEntity) {
		List list = this.getQueryResult(hql, values, qEntity);
		if (list != null && list.size() > 0) {
			int totalCount = this.getQueryCount(hql, values);
			qEntity.setTotalCount(totalCount);
			qEntity.setTotalPage();
			qEntity.setList(list);
		}
	}
	/***************************************************************************
	 * 查询结果集返回map map里面放入参数 qEntity为分页类
	 **************************************************************************/
	public void getPageQueryResultMap(final String hql, final Map values,
			final PageQEntity qEntity) {
		List list = this.getQueryResultMap(hql, values, qEntity);
		int totalCount = this.getQueryCount(hql, values);
		if (list != null && list.size() > 0) {
			qEntity.setTotalCount(totalCount);
			qEntity.setTotalPage();
			qEntity.setList(list);
		}
	}
	/***************************************************************************
	 * 查询结果集 map里面放入参数 qEntity为分页类
	 **************************************************************************/
	public void getPageQueryResultSQL(final String classPath,
			final String hql, final Map values, final PageQEntity qEntity){
		List list = this.getQueryResultSQL(classPath, hql, values, qEntity);
		if (list != null && list.size() > 0) {
			int totalCount = this.getQueryCountSQL(hql, values);
			qEntity.setTotalCount(totalCount);
			qEntity.setTotalPage();
			qEntity.setList(list);
		}
	}

	public List getList(String hql, Map parMap) {
		return getList(hql, parMap,true);
	}
	
	public List getListWithoutCache(String hql, Map parMap) {
		return getList(hql, parMap,false);
	}
	
	/**
	 * 
	 * @param hql
	 * @param parMap
	 * @param cacheable	是否使用缓存
	 * @return
	 */
	public List getList(String hql, Map parMap,boolean cacheable) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql).setCacheable(cacheable);
		setQueryMap(query, parMap);
		return query.list();
	}

	public List getList(String queryString) {
		return sessionFactory.getCurrentSession().createQuery(queryString).setCacheable(true).list();
	}
	
	public List getListWithoutCache(String queryString) {
		return sessionFactory.getCurrentSession().createQuery(queryString).setCacheable(false).list();
	}

	/**
	 * 将查询结果转为map
	 * @param hql
	 * @param parMap
	 * @return
	 */
	public List getListToMap(String hql,Map parMap){
		Query query = 	sessionFactory.getCurrentSession().createQuery(hql).setCacheable(true);
		setQueryMap(query, parMap);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	public void setQueryMap(Query query,Map parMap){
		if (parMap != null && parMap.size() > 0) {
			for (Iterator i = parMap.entrySet().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				setParameterValue(query, (String) entry.getKey(), entry.getValue());
			}
		}
	}
	
	public List getListBySQL(String sql, Map parMap) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		setQueryMap(query, parMap);
		return query.list();
	}
	
	public List getListBySQL(final String sql) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		List list = query.list();
		return list;
	}
	public <T> List<T> getClassListBySQL(final String sql,Class<T> clazz) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		List list = query.list();
		return list;
	}
	/***
	 * 结果集返回为实体bean，传入的class必须是hibernate的实体映射对象
	 * 编写方式：如果结果集只有一个实体则 select * from 即可
	 * 如果有多个实体 则 select {别名.*},{别名.*} 
	 * 别名及时实体类的类名  如查询员工（Emp）和部门（Dept） 则传入 "select {Emp.*},{Dept.*} from emp,dept",Emp.class,Dept.class
	 * @param sql
	 * @param parMap
	 * @param entitys
	 * @return
	 */
	public <T> List<T> getEnitListBySql(String sql,Class<T> ... entitys){
		return getEntityListBySQL(sql,null,entitys);
	}
	public <T> List<T>  getEntityListBySQL(String sql,Map parMap,Class<T> ... entitys){
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		setQueryMap(query, parMap);
		if(entitys.length>0){
			for (Class<T> entity : entitys) {
				query.addEntity(entity.getSimpleName(), entity);
			}
		}
		return query.list();
	}

	/***************************************************************************
	 * 根据你传入的hql语句得到记录集的记录条数
	 **************************************************************************/

	public int getUniqueResult(String hql) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return (Integer) query.uniqueResult();
	}
	public Object getUniqueResultSql(String sql) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		return query.uniqueResult();
	}
	public int getUniqueResultSql(String sql,Map parMap) {
		Session session =sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		if (parMap != null && parMap.size() > 0) {
			for (Iterator i = parMap.entrySet().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				setParameterValue(query, (String) entry.getKey(), entry
						.getValue());
			}
		}
		return Integer.valueOf(query.uniqueResult().toString());
	}

	
	public Object getUniqueResult(String hql,Map parMap) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		setQueryMap(query, parMap);
		return query.uniqueResult();
	}
	
	public List getListBySQLToMap(String sql, Map parMap) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		setQueryMap(query, parMap);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	/**
	 * 随机选取相关数据信息列表
	 * @param tableName 表名
	 * @param random 随机几条数据
	 * @param columns 查询字段名集合
	 * @param primaryKey 该表的主键字段
	 * @return
	 */
	public List getListBySqlRandomData(String tableName, int random,String primaryKey,List<String> columns,String id){
		//先查询总条数
		String sqlCount = " select count(*) from "+tableName;
		int count = this.getUniqueResultSql(sqlCount, null);

		StringBuilder sql = new StringBuilder("select ");
		if(columns!=null && columns.size()>0){
			StringBuffer cols = new StringBuffer();
			for(String column:columns){
				cols.append(" "+ column+" ,");
			}
			if(cols!=null){
				sql.append(cols.substring(0, cols.length()-1));
			}
		}else{
			sql.append(" * ");
		}
		sql.append(" from "+tableName+" ");
		if(CommonFunction.isNotNull(primaryKey)){
			sql.append(" where "+primaryKey+" >= ");
			sql.append(" (SELECT FLOOR( RAND() * "+(count/2)+" )) ");
			sql.append(" and "+primaryKey+" != "+Long.parseLong(id));
			sql.append(" order by "+primaryKey);
		}
		sql.append(" limit "+random);
		return getListBySQLToMap(sql.toString(),null);
	}

	public Map getListBySqlTableData(String tableName, int rows,String primaryKeyField,String parentIdField,
			List<String> columns){
		StringBuffer sql = new StringBuffer("select ");
		if(columns!=null && columns.size()>0){
			sql.append(primaryKeyField+","+parentIdField+",");
			StringBuffer cols = new StringBuffer();
			for(String column:columns){
				cols.append(" "+ column+" ,");
			}
			if(cols!=null){
				sql.append(cols.substring(0, cols.length()-1));
			}
		}else{
			sql.append(" * ");
		}
		sql.append(" from "+tableName+" ");
		sql.append(" where "+parentIdField+" != 0");
		List list=this.getListBySQLToMap(sql.toString(), null);
		Map map=new HashMap();
		if(list != null && list.size() > 0){
			List resList=null;
			for (int i = 0; i < list.size(); i++) {
				Map objmap=(Map) list.get(i);
				resList=(List) map.get(objmap.get(parentIdField));
				if(resList == null){
					resList=new ArrayList();
					map.put(objmap.get(parentIdField), resList);
				}
				resList.add(objmap);
			}
		}

		return map;
	}

	@Override
	public void updateProperties(Class clazz,Map<String, Object> properties,Serializable id) {
		StringBuffer hql = new StringBuffer("update ").append(clazz.getName()).append(" set ");
		if(properties == null || properties.size() == 0){
			throw new IllegalArgumentException("properties can't be null");
		}
		Iterator<String> iterator = properties.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			if(key.indexOf(".") > -1){//有的属性是映射的实体类，如emp.id
				hql.append(key).append("=").append(":").append(key.replace(".", "_")).append(",");
				properties.put(key.replace(".", "_"), properties.get(key));
				properties.remove(key);
			}else{
				hql.append(key).append("=").append(":").append(key).append(",");
			}
		}
		hql = new StringBuffer(hql.substring(0, hql.length()-1));

		hql.append(" where ").append(getIdentifierPropertyName(clazz)).append("=").append(":id");
		properties.put("id", id);
		this.execute(hql.toString(), properties);
	}
	
	public <T extends Serializable>T updateProperties(T t,Set<String> properties){
		try {
			ClassMetadata metadata = getSessionFactory().getClassMetadata(t.getClass());
			T old = (T) this.get(t.getClass(),(Serializable)PropertyUtils.getProperty(t, metadata.getIdentifierPropertyName()));
			String[] proNames = metadata.getPropertyNames();
			for (String propertyName : proNames) {
				if(properties.contains(propertyName)){
					PropertyUtils.setProperty(old, propertyName, PropertyUtils.getProperty(t, propertyName));
				}
			}
			return old;
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			throw new RuntimeException(e);
		}

	}

	private String getIdentifierPropertyName(Class clazz){
		return getSessionFactory().getClassMetadata(clazz).getIdentifierPropertyName();
	}
	

	/**
	 * 批量删除
	 * @param collection
	 */
	public void removeAll(Collection<? extends Serializable> collection){
		for (Object object: collection) {
			this.delete(object);
		}
	}
	
	@Override
	public <T> void delById(Class<T> clazz, Serializable id) {
		T t = this.get(clazz, id);
		this.delete(t);
	}

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}



	@Override
	public void clear() {
		this.sessionFactory.getCurrentSession().clear();
	}


}