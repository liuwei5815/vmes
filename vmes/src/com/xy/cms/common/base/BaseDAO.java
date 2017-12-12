package com.xy.cms.common.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.prefs.BackingStoreException;

import javax.persistence.criteria.Order;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger; 
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException; 
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.xy.cms.common.CommonFunction;
import com.xy.cms.service.IDAO;
import com.xy.cms.utils.MyBeanUtils;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.common.hibernate3.Updater;
import com.xy.cms.db.DBUtil;
import com.xy.cms.db.bean.Column;
import com.xy.cms.db.bean.OrderBy;
import com.xy.cms.db.bean.Where;

@Transactional
public class BaseDAO implements IDAO{

	private SessionFactory sessionFactory;
	private DBUtil dbUtil;
	protected static Logger log = Logger.getLogger(BaseDAO.class);
	/*******************************************************************************************************************************************************************************
	 * 删除或更新 用户hql语句
	 ******************************************************************************************************************************************************************************/
	public int updateOrDel(String hql, Map parMap) {
		Query query = this.getQuery(hql, parMap);
		
		return query.executeUpdate();
	}
	/*
	/***************************************************************************
	 * 取得某个类的所有的记录的List集合
	 ************************************************************************
	 ***/
	
	public List getAll(Class clazz) throws DataAccessException {
		return sessionFactory.getCurrentSession().createCriteria(clazz).list();
	}
	 

	/***************************************************************************
	 * 用主键获取某个类（即数据库中的一条记录）
	 **************************************************************************/
	public Object get(Class clazz, Serializable id) throws DataAccessException {
		Object o = sessionFactory.getCurrentSession().get(clazz, id);
		return o;
	}

	/***************************************************************************
	 * lockMode表示锁的模式，如果使用for update 语句， 则LockMode.UPGRADE_NOWAIT
	 **************************************************************************/
	public Object get(Class clazz, Serializable id, LockMode lockMode)
			throws DataAccessException {
		Object o = sessionFactory.getCurrentSession().get(clazz, id, lockMode);
		return o;
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
				value = MyBeanUtils.getSimpleProperty(bean, propName);
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
	 * 删除某个类（即数据库中的一条记录）
	 **************************************************************************/
	public void remove(Object clazz){
		sessionFactory.getCurrentSession().delete(clazz);
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
	 * 执行一条hql（非查询语句）
	 **************************************************************************/
	public void execute(final String exhql){
		sessionFactory.getCurrentSession().createQuery(exhql).executeUpdate();

	}

	/***************************************************************************
	 * 执行一条hql（非查询语句）
	 **************************************************************************/
	public void execute(final String exhql, final Map params){
		Query q = sessionFactory.getCurrentSession().createQuery(exhql);
		if (null != params && params.size() > 0) {
			for (Iterator i = params.entrySet().iterator(); i
					.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				// 调用此方法向query对象中绑定查询参数
				setParameterValue(q, (String) entry.getKey(),
						entry.getValue());
			}
		}
		q.executeUpdate();
	}
	public void executeSQL(final String sql){
		sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

	public void executeSQL(final String sql, final Map params){
		Query q = sessionFactory.getCurrentSession().createSQLQuery(sql);
		if (null != params && params.size() > 0) {
			for (Iterator i = params.entrySet().iterator(); i
					.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				// 调用此方法向query对象中绑定查询参数
				setParameterValue(q, (String) entry.getKey(),
						entry.getValue());
			}
		}
		
		q.executeUpdate();
	}

	/***************************************************************************
	 * 用hql查询返回一个结果集
	 **************************************************************************/
	public List find(String queryString) {
		return sessionFactory.getCurrentSession().createQuery(queryString).list();
	}

	/**
	 * 查询某个范围内的结果集
	 * @param start	开始行
	 * @param count	结束行
	 * @param hql
	 * @return
	 */
	public List find(final int start, final int count, final String hql) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setFirstResult(start);
		query.setMaxResults(count);
		return query.list();
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

	public List findBySQL(final String sql) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		List list = query.list();
		return list;
	}
	public List findBySQLToBean(final String sql,final Class beanClass) {
		List list=  sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(beanClass).list();;
		return list;
	}

	/***************************************************************************
	 * 用于复杂的查询语句 我们在代入查询参数的时候, 建议使用 name里面放的是HQL语句 eg: "from Courses where
	 * name=:name" map里面放的是你想绑定的参数实现集 eg: Map map = new HashMap();
	 * map.put("name", "leon"); 最后得到的结果就是 from Courses where name="leon"
	 **************************************************************************/
	public java.util.List getNamedQuery(final String name, final Map params)
	{
		Query q = sessionFactory.getCurrentSession().createQuery(name);
		if (null != params && params.size() > 0) {
			for (Iterator i = params.entrySet().iterator(); i
					.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				// 调用此方法向query对象中绑定查询参数
				setParameterValue(q, (String) entry.getKey(),
						entry.getValue());
			}
		}
		List list = q.list();
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
		if (null == key || null == value) {
			query.setParameter(key,null);
		} else if (value instanceof Boolean) {
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
		else if(value instanceof Long[]){
			query.setParameterList(key,(Long[])value);
		}
		else{
			query.setParameter(key, value);
		}
		
	}

	/***************************************************************************
	 * 自动匹配参数类型
	 * 
	 * @param query
	 * @param key
	 * @param value
	 **************************************************************************/
	protected void setParameterValues(SQLQuery query, String key, Object value) {
		if (null == key || null == value) {
			return;
		} else if (value instanceof Boolean) {
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
		}else if(value instanceof Long[]){
			query.setParameterList(key,(Long[])value);
		}
	}

	/***************************************************************************
	 * 用于分页查询返回一个结果集 其中map里面装的是参数列表 qEntity是分页类
	 **************************************************************************/
	public List getQueryResult(final String hql, final Map values,
			final BaseQEntity qEntity) {
		this.log.debug("分页显示：["
				+ qEntity.isShowPages()
				+ "] 每页["
				+ qEntity.getPerPageRows()
				+ "]条记录，从第["
				+ ((qEntity.getCurrPage() - 1) * qEntity.getPerPageRows() + 1) + "]条记录开始，显示第["
				+ qEntity.getCurrPage() + "]页...");

		String q = hql;
		Query query = sessionFactory.getCurrentSession().createQuery(q);

		// 调用此方法向query对象中绑定查询参数

		if (values != null && values.size() > 0) {
			for (Iterator i = values.entrySet().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				setParameterValue(query, (String) entry.getKey(), entry.getValue());
			}
		}
		// 分页控制 (根据当前的页数来得到要现在的记录集)
		if (qEntity.isShowPages()) {
			query.setFirstResult((qEntity.getCurrPage() - 1) * qEntity.getPerPageRows());
			query.setMaxResults(qEntity.getPerPageRows());
		}
		List list = query.list();
		return list;


	}
	
	/***************************************************************************
	 * 用于分页查询返回一个结果集,只使用SQL语句，qEntity是分页类
	 **************************************************************************/
	public List getQueryResultBySql(final String sql, final BaseQEntity qEntity) {
		this.log.debug("分页显示：["
				+ qEntity.isShowPages()
				+ "] 每页["
				+ qEntity.getPerPageRows()
				+ "]条记录，从第["
				+ ((qEntity.getCurrPage() - 1) * qEntity.getPerPageRows() + 1) + "]条记录开始，显示第["
				+ qEntity.getCurrPage() + "]页...");

		
		//Query query = sessionFactory.getCurrentSession().createQuery(q);
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		// 分页控制 (根据当前的页数来得到要现在的记录集)
		if (qEntity.isShowPages()) {
			query.setFirstResult((qEntity.getCurrPage() - 1) * qEntity.getPerPageRows());
			query.setMaxResults(qEntity.getPerPageRows());
		}
		List list = query.list();
		return list;


	}
	/***************************************************************************
	 * 用于分页查询返回一个结果集 其中map里面装的是参数列表 qEntity是分页类
	 **************************************************************************/
	public List getQueryResultSQL( final String hql,
			final Map values, final BaseQEntity qEntity){
		this.log
		.info("分页显示：["
				+ qEntity.isShowPages()
				+ "] 每页["
				+ qEntity.getPerPageRows()
				+ "]条记录，从第["
				+ ((qEntity.getCurrPage() - 1)
						* qEntity.getPerPageRows() + 1) + "]条记录开始，显示第["
						+ qEntity.getCurrPage() + "]页...");

		String q = hql;
		Class cl = null;

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(q);

		// 调用此方法向query对象中绑定查询参数

		if (values != null && values.size() > 0) {
			for (Iterator i = values.entrySet().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				setParameterValues(query, (String) entry.getKey(),entry.getValue());
			}
		}
		// 分页控制 (根据当前的页数来得到要现在的记录集)
		if (qEntity.isShowPages()) {
			query.setFirstResult((qEntity.getCurrPage() - 1) * qEntity.getPerPageRows());
			query.setMaxResults(qEntity.getPerPageRows());
		}
		List list = query.list();
		return list;

	}
	
	public List getQueryResultSQLToMap( final String hql,
			final Map values, final BaseQEntity qEntity){
		this.log
		.info("分页显示：["
				+ qEntity.isShowPages()
				+ "] 每页["
				+ qEntity.getPerPageRows()
				+ "]条记录，从第["
				+ ((qEntity.getCurrPage() - 1)
						* qEntity.getPerPageRows() + 1) + "]条记录开始，显示第["
						+ qEntity.getCurrPage() + "]页...");

		String q = hql;
		Class cl = null;

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(q);

		// 调用此方法向query对象中绑定查询参数

		if (values != null && values.size() > 0) {
			for (Iterator i = values.entrySet().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				setParameterValues(query, (String) entry.getKey(),entry.getValue());
			}
		}
		// 分页控制 (根据当前的页数来得到要现在的记录集)
		if (qEntity.isShowPages()) {
			query.setFirstResult((qEntity.getCurrPage() - 1) * qEntity.getPerPageRows());
			query.setMaxResults(qEntity.getPerPageRows());
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		return list;

	}
	
	public List getListResultSQLToMap( final String hql,
			final Map values){
		

		String q = hql;
		Class cl = null;

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(q);

		// 调用此方法向query对象中绑定查询参数

		if (values != null && values.size() > 0) {
			for (Iterator i = values.entrySet().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				setParameterValues(query, (String) entry.getKey(),entry.getValue());
			}
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		return list;

	}
	
	public List getListResultSQLToMap( final String hql,
			final Map values,final BaseQEntity qEntity){
		String q = hql;
		Class cl = null;

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(q);

		// 调用此方法向query对象中绑定查询参数

		if (values != null && values.size() > 0) {
			for (Iterator i = values.entrySet().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				setParameterValues(query, (String) entry.getKey(),entry.getValue());
			}
		}
		// 分页控制 (根据当前的页数来得到要现在的记录集)
				if (qEntity.isShowPages()) {
					query.setFirstResult((qEntity.getCurrPage() - 1) * qEntity.getPerPageRows());
					query.setMaxResults(qEntity.getPerPageRows());
				}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		return list;

	}
	
	/***************************************************************************
	 * 用于分页查询返回一个结果集 其中map里面装的是参数列表 qEntity是分页类
	 **************************************************************************/
	public List getQueryResultSQL(final String classPath, final String hql,
			final Map values, final BaseQEntity qEntity){
		this.log.debug("分页显示：["
				+ qEntity.isShowPages()
				+ "] 每页["
				+ qEntity.getPerPageRows()
				+ "]条记录，从第["
				+ ((qEntity.getCurrPage() - 1)
						* qEntity.getPerPageRows() + 1) + "]条记录开始，显示第["
						+ qEntity.getCurrPage() + "]页...");

		String q = hql;
		Class cl = null;
		try {
			cl = Class.forName(classPath);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(q).addEntity(cl);

		// 调用此方法向query对象中绑定查询参数

		if (values != null && values.size() > 0) {
			for (Iterator i = values.entrySet().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				setParameterValues(query, (String) entry.getKey(),
						entry.getValue());
			}
		}
		// 分页控制 (根据当前的页数来得到要现在的记录集)
		if (qEntity.isShowPages()) {
			query.setFirstResult((qEntity.getCurrPage() - 1)
					* qEntity.getPerPageRows());
			query.setMaxResults(qEntity.getPerPageRows());
		}
		List list = query.list();
		return list;


	}


	/***************************************************************************
	 * 根据你传入的hql语句得到记录集的记录条数
	 **************************************************************************/
	public int getQueryCount(final String hql, final Map values)
	{

		int fromIndex = hql.indexOf("from");
		String hqlSub=hql.substring(fromIndex);

		//当hql里面后子查询且包含from关键字，做如下判断
		String temp = hql.substring(fromIndex+4);

		if(temp.contains("from")){

			int secondFromIndex = temp.indexOf("from");

			hqlSub = temp.substring(secondFromIndex);

		}

		int oderById=hqlSub.indexOf("order by");
		String q;
		if(oderById!=-1){
			q = "select count(*) " + hqlSub.substring(0,oderById);
		}else{
			q = "select count(*) " + hqlSub;
		}
		Query query = sessionFactory.getCurrentSession().createQuery(q);
		// 设置参数
		if (values != null && values.size() > 0) {
			for (Iterator i = values.entrySet().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				setParameterValue(query, (String) entry.getKey(), entry
						.getValue());
			}
		}
		Long queryCountLong = (Long) query.uniqueResult();
		Integer queryCount = queryCountLong.intValue();
		return 	queryCount;
	}
	
	/***************************************************************************
	 * 根据你传入的hql语句得到记录集的记录条数
	 **************************************************************************/
	public int getQueryCountS(final String hql, final Map values)
	{

		int fromIndex = hql.indexOf("from");
		String hqlSub=hql.substring(fromIndex);

		//当hql里面后子查询且包含from关键字，做如下判断
		String temp = hqlSub;

		if(temp.contains("from")){

			int secondFromIndex = temp.indexOf("from");

			hqlSub = temp.substring(secondFromIndex);

		}

		int oderById=hqlSub.indexOf("order by");
		String q;
		if(oderById!=-1){
			q = "select count(*) " + hqlSub.substring(0,oderById);
		}else{
			q = "select count(*) " + hqlSub;
		}
		Query query = sessionFactory.getCurrentSession().createQuery(q);
		// 设置参数
		if (values != null && values.size() > 0) {
			for (Iterator i = values.entrySet().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				setParameterValue(query, (String) entry.getKey(), entry
						.getValue());
			}
		}
		Long queryCountLong = (Long) query.uniqueResult();
		Integer queryCount = queryCountLong.intValue();
		return 	queryCount;
	}

	public QueryResult getPageQueryResultSQL(final String hql, 
			final Map values, final BaseQEntity qEntity){
		QueryResult rs = new QueryResult();
		String rows = CommonFunction.getPageRows();
		if(qEntity!=null){
			if(qEntity.getPerPageRows()>0){
				rows=String.valueOf(qEntity.getPerPageRows());
			}
		}
		Integer totalCount = this.getQueryCountSQL(hql, values);
		Integer totalPage= (totalCount - 1)/ Integer.parseInt(rows) + 1;
		if(qEntity.getCurrPage()>totalPage){
			qEntity.setCurrPage(totalPage);
		}
		List list = this.getQueryResultSQL(hql, values, qEntity);

		if (list != null && list.size() > 0) {
			rs.setCurrentPage(String.valueOf(qEntity.getCurrPage()));
			rs.setTotalCount(String.valueOf(totalCount));
			rs.setTotalPage(String.valueOf(totalPage));
			rs.setList(list);
			return rs;
		}
		return null;
	}
	
	public QueryResult getPageQueryResultSQLToMap(final String hql, 
			final Map values, final BaseQEntity qEntity){
		QueryResult rs = new QueryResult();
		String rows = CommonFunction.getPageRows();
		List list=null;
		
		Integer totalCount = this.getQueryCountSQL(hql, values);
		Integer totalPage= (totalCount - 1)/ Integer.parseInt(rows) + 1;
		if(qEntity.getCurrPage()>totalPage){
			qEntity.setCurrPage(totalPage);
		}
		list = this.getQueryResultSQLToMap(hql, values, qEntity);
		
		

		if (list != null && list.size() > 0) {
			rs.setCurrentPage(String.valueOf(qEntity.getCurrPage()));
			rs.setTotalCount(String.valueOf(totalCount));
			rs.setTotalPage(String.valueOf(totalPage));
			rs.setList(list);
			return rs;
		}
		return null;
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
		if (values != null && values.size() > 0) {
			for (Iterator i = values.entrySet().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				setParameterValues(query, (String) entry.getKey(),entry.getValue());
			}
		}
		Object queryCount = (Object) query.uniqueResult();
		BigInteger big=(BigInteger) queryCount;
		return big.intValue();
	}
	/***************************************************************************
	 * 查询结果集 map里面放入参数 qEntity为分页类
	 **************************************************************************/
	public QueryResult getPageQueryResult(final String hql, final Map values,
			final BaseQEntity qEntity) {

		QueryResult rs = new QueryResult();
		String rows = CommonFunction.getPageRows();
		if(qEntity!=null){
			if(qEntity.getPerPageRows()>0){
				rows=String.valueOf(qEntity.getPerPageRows());
			}
		}
		Integer totalCount=  this.getQueryCountS(hql, values);
		Integer totalPage= (totalCount - 1)/ Integer.parseInt(rows) + 1;
		if(qEntity.getCurrPage()>totalPage){
			qEntity.setCurrPage(totalPage);
		}
		List list = this.getQueryResult(hql, values, qEntity);

		if (list != null && list.size() > 0) {
			rs.setCurrentPage(String.valueOf(qEntity.getCurrPage()));
			rs.setTotalCount(String.valueOf(totalCount));
			rs.setTotalPage(String.valueOf(totalPage));
			rs.setList(list);
			return rs;
		}
		return null;
	}

	
	/***************************************************************************
	 * 查询结果集  使用sql语句查询结果集     qEntity为分页类
	 **************************************************************************/
	public QueryResult getPageQueryResultBySql(final String sql, final BaseQEntity qEntity) {

		QueryResult rs = new QueryResult();
		String rows = CommonFunction.getPageRows();
		if(qEntity!=null){
			if(qEntity.getPerPageRows()>0){
				rows=String.valueOf(qEntity.getPerPageRows());
			}
		}
		Integer totalCount = this.getQueryCountSQL(sql, null);
		Integer totalPage= (totalCount - 1)/ Integer.parseInt(rows) + 1;
		if(qEntity.getCurrPage()>totalPage){
			qEntity.setCurrPage(totalPage);
		}
		List list = this.getQueryResultBySql(sql, qEntity);
		if (list != null && list.size() > 0) {
			rs.setCurrentPage(String.valueOf(qEntity.getCurrPage()));
			rs.setTotalCount(String.valueOf(totalCount));
			rs.setTotalPage(String.valueOf((int) Math.ceil((totalCount - 1)/ Integer.parseInt(rows)) + 1));
			rs.setList(list);
			return rs;
		}
		return null;
	}
	
	


	/***************************************************************************
	 * 查询结果集 map里面放入参数 qEntity为分页类
	 **************************************************************************/
	public QueryResult getPageQueryResultSQL(final String classPath,
			final String hql, final Map values, final BaseQEntity qEntity){

		QueryResult rs = new QueryResult();
		String rows = CommonFunction.getPageRows();
		if(qEntity!=null){
			if(qEntity.getPerPageRows()>0){
				rows=String.valueOf(qEntity.getPerPageRows());
			}
		}
		List list = this.getQueryResultSQL(classPath, hql, values, qEntity);

		String totalCount = String.valueOf(this.getQueryCountSQL(hql, values));

		if (list != null && list.size() > 0) {
			rs.setTotalCount(totalCount);
			rs.setTotalPage(String.valueOf((int) Math.ceil((Integer.parseInt(totalCount) - 1)/ Integer.parseInt(rows)) + 1));
			rs.setList(list);
			return rs;
		}
		return null;

	}

	/***************************************************************************
	 * 
	 * @param hql
	 * @param values
	 * @param qEntity
	 * @return
	 * @throws DataAccessException
	 **************************************************************************/
	public QueryResult getPageQueryResultForSettlement(final String hql,
			final Map values, final BaseQEntity qEntity){
		QueryResult rs = new QueryResult();
		String rows = CommonFunction.getPageRows();
		List list = this.getQueryResult(hql, values, qEntity);

		String totalCount = String.valueOf(this.getQueryCountForSettlement(hql,values));

		if (list != null && list.size() > 0) {
			rs.setTotalCount(totalCount);
			rs.setTotalPage(String.valueOf((int) Math.ceil((Integer.parseInt(totalCount) - 1)/ Integer.parseInt(rows)) + 1));
			rs.setList(list);
			return rs;
		}
		return null;
	}

	/***************************************************************************
	 * 
	 * @param hql
	 * @param values
	 * @return
	 * @throws DataAccessException
	 **************************************************************************/
	public int getQueryCountForSettlement(final String hql, final Map values){
		int fromIndex = hql.indexOf("from");
		String q = "select count(*) " + hql.substring(fromIndex);
		Query query = sessionFactory.getCurrentSession().createQuery(q);
		// 设置参数
		if (values != null && values.size() > 0) {
			for (Iterator i = values.entrySet().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				setParameterValue(query, (String) entry.getKey(), entry.getValue());
			}
		}
		List list = query.list();
		Integer queryCount = new Integer(0);
		if (list != null) {
			Long queryCountLong = new Long(list.size());
			queryCount = queryCountLong.intValue();
		}
		return queryCount;

	}

	/***************************************************************************
	 * 
	 * @param clazz
	 * @param baseQEntity
	 * @param queryFields
	 * @return
	 **************************************************************************/
	public QueryResult getPageQueryResult(Object[] clazz,
			BaseQEntity baseQEntity, String queryFields) {

		int count = clazz.length;
		Map valueMap = new HashMap();
		StringBuffer hql = new StringBuffer("from");
		for (int i = 0; i < clazz.length; i++) {
			int pos = ((String) clazz[i]).indexOf(".");
			String objName = ((String) clazz[i]).substring(pos);
			hql.append(objName + " a" + i + ",");

		}
		for (int i = 0; i < count; i++) {
			Field[] f = clazz[i].getClass().getFields();
			for (int j = 0; j < f.length; j++) {
				f[j].getName();
			}
		}
		hql.replace(hql.length() - 1, hql.length(), " where 1=1 ");
		for (int i = 0; i < queryFields.length(); i++) {
			String[] values = queryFields.split(",");
			for (int j = 0; j < values.length; j++) {

			}
		}
		return null;
	}

	public List getList(String hql, Map parMap) {
		return this.getQuery(hql, parMap).list();
	}
	
	

	public List getListBySQL(String sql, Map parMap) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		if (parMap != null && parMap.size() > 0) {
			for (Iterator i = parMap.entrySet().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				setParameterValue(query, (String) entry.getKey(), entry.getValue());
			}
		}
		
		return query.list();
	}

	/***************************************************************************
	 * 根据hql语句参数赋值
	 * 
	 * @param hql
	 * @param parMap
	 * @return
	 **************************************************************************/
	public Query getQuery(String hql, Map parMap) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if (parMap != null && parMap.size() > 0) {
			for (Iterator i = parMap.entrySet().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				setParameterValue(query, (String) entry.getKey(), entry
						.getValue());
			}
		}
		return query;
	}

	/***************************************************************************
	 * 根据你传入的hql语句得到记录集的记录条数
	 **************************************************************************/

	public int getUniqueResult(String hql) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return (Integer) query.uniqueResult();
	}
	
	public Object getUniqueResultSql(String sql,Map parMap) {
		Session session =sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		if (parMap != null && parMap.size() > 0) {
			for (Iterator i = parMap.entrySet().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				setParameterValue(query, (String) entry.getKey(), entry
						.getValue());
			}
		}
		return query.uniqueResult();
	}
	
	public Object getUniqueResult(String hql,Map parMap) {
		Session session =sessionFactory.getCurrentSession();
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if (parMap != null && parMap.size() > 0) {
			for (Iterator i = parMap.entrySet().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				setParameterValue(query, (String) entry.getKey(), entry
						.getValue());
			}
		}
		return query.uniqueResult();
	}
	public List getListBySQLToMap(String sql, Map parMap) {
		
		
		QueryResult result = null;
		BaseQEntity qEntity = new BaseQEntity();
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		if (parMap != null && parMap.size() > 0) {
			for (Iterator i = parMap.entrySet().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				setParameterValue(query, (String) entry.getKey(), entry.getValue());
			}
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
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
	public void setQueryMap(Query query,Map parMap){
		if (parMap != null && parMap.size() > 0) {
			for (Iterator i = parMap.entrySet().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				setParameterValue(query, (String) entry.getKey(), entry.getValue());
			}
		}
	}
	public <T> List<T> getListBySQLToBean(Query query, Map parMap,Class<T> clazz) {
		if (parMap != null && parMap.size() > 0) {
			for (Iterator i = parMap.entrySet().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				setParameterValue(query, (String) entry.getKey(), entry.getValue());
			}
		}
		query.setResultTransformer(Transformers.aliasToBean(clazz));
		return query.list();
	}
	
	

	
	/**
	 * 根据当前信息查询下一篇以及上一篇相应数据列表信息
	 * @param tableName 表名
	 * @param rows 显示行数
	 * @param page 当前页
	 * @param map 查询条件 key=value
	 * @param orderList 排序字段集合 
	 * @param columns 查询的字段集合
	 * @return 
	 */
	public Map getMapBySqlTableData(String tableName, Map<String, Object> map, 
			List<Map<String,String>> orderList,List<String> columns,String viewsColName){
			List<Column> columnList = formateColumns(columns);
			List<Where> whereList =formateWheres(map);
			List<OrderBy> orderByList = formateOrderBys(orderList);
			List list = this.getListBySQLToMap(this.createSelectSql(tableName, columnList, whereList, orderByList), map);
			for(int i =0;i<whereList.size();i++){
				Where where =whereList.get(i);
				where.setType(Where.WHERE_TYPE.GT.code);
			}
			List resultNext=this.getListBySQLToMap(this.createSelectSql(tableName, columnList, whereList, orderByList), map);
			for(int i =0;i<whereList.size();i++){
				Where where =whereList.get(i);
				where.setType(Where.WHERE_TYPE.LT.code);
			}
			List resultPre=this.getListBySQLToMap(this.createSelectSql(tableName, columnList, whereList, orderByList), map);
				Map mapData=null;
			if(CommonFunction.isNotNull(list)){
				 mapData=(Map) list.get(0);
			}
			Map mapNext=null;
			if(CommonFunction.isNotNull(resultNext)){
				mapNext=(Map) resultNext.get(0);
			}
			Map mapPre=null;
			if(CommonFunction.isNotNull(resultPre)){
				mapPre=(Map) resultPre.get(0);
			}
			Map mapAll=new HashMap();
			mapAll.put("mapData", mapData);
			mapAll.put("mapNext", mapNext);
			mapAll.put("mapPre", mapPre);
		
			if(CommonFunction.isNull(viewsColName)){
				StringBuilder sqlUpdate=new StringBuilder("update "+tableName+" set "+viewsColName+"="+viewsColName+"+1");
				for(Map.Entry<String,Object> entry:map.entrySet()) {
					sqlUpdate.append(" "+entry.getKey()+"=:"+entry.getKey()+" and ");
				
				}
				this.executeSQL(sqlUpdate.toString(),map);
			}
			
			return mapAll;
	
	}
	
	/**
	 * 根据当前信息查询下一篇以及上一篇相应数据列表信息
	 * @param tableName 表名
	 * @param rows 显示行数
	 * @param page 当前页
	 * @param map 查询条件 key=value
	 * 		  map2 查询条件 key=value(不用于查询上一个和下一个)
	 * @param orderList 排序字段集合 
	 * @param columns 查询的字段集合
	 * @return 
	 */
	public Map getMapBySqlTableData(String tableName, Map<String, Object> map, Map<String, Object> map2,
			List<Map<String,String>> orderList,List<String> columns,String viewsColName){
		List<Column> columnList = formateColumns(columns);
		List<Where> whereList =formateWheres(map);
		List<OrderBy> orderByList = formateOrderBys(orderList);
		QueryResult result = this.getPageQueryResultSQLToMap(this.createSelectSql(tableName, columnList, whereList, orderByList), map, null);
		for(int i =0;i<whereList.size();i++){
			Where where =whereList.get(i);
			where.setType(Where.WHERE_TYPE.GT.code);
			
		}
		List<Where> ws = formateWheres(map2);
		map.putAll(map2);
		whereList.addAll(ws);
		QueryResult resultNext=this.getPageQueryResultSQLToMap(this.createSelectSql(tableName, columnList, whereList, orderByList), map, null);
		for(int i =0;i<whereList.size();i++){
			Where where =whereList.get(i);
			where.setType(Where.WHERE_TYPE.LT.code);
		}
		QueryResult resultPre=this.getPageQueryResultSQLToMap(this.createSelectSql(tableName, columnList, whereList, orderByList), map, null);
		
		Map mapData=(Map) result.list.get(0);
		Map mapNext=null;
		if(resultNext!=null){
			mapNext=(Map) resultNext.list.get(0);
		}
		Map mapPre=null;
		if(resultPre!=null){
			mapPre=(Map) resultPre.list.get(0);
		}
		Map mapAll=new HashMap();
		mapAll.put("mapData", mapData);
		mapAll.put("mapNext", mapNext);
		mapAll.put("mapPre", mapPre);
	
		if(CommonFunction.isNull(viewsColName)){
			StringBuilder sqlUpdate=new StringBuilder("update "+tableName+" set "+viewsColName+"="+viewsColName+"+1");
			for(Map.Entry<String,Object> entry:map.entrySet()) {
				sqlUpdate.append(" "+entry.getKey()+"=:"+entry.getKey()+" and ");
			
			}
			this.executeSQL(sqlUpdate.toString(),map);
		}
	
		
		return mapAll;
	}
	public String createSelectSql(String name,List<Column> columns,List<Where> wheres,List<OrderBy> orderbys){
		return dbUtil.createSelectSql(name, columns, wheres, orderbys);
	}
	public String createUpdateSql(String name,List<Column> columns,List<Where> wheres){
		return dbUtil.createUpdateSql(name, columns, wheres);
	}
	public String createSelectSql(String name,Map<String, Object> map,
			List<Map<String,String>> orderList,List<String> columns){
		
		return dbUtil.createSelectSql(name, formateColumns(columns), formateWheres(map),formateOrderBys(orderList));
	}
	public List<Column> formateColumns(List<String> columns){
		List<Column> cs= new ArrayList<Column>();
		if(columns!=null && columns.size()>0){
			cs = new ArrayList<Column>();
			for(String column:columns){
				Column c = new Column(column);
				cs.add(c);
			}
		}
		return cs;
	}
	//格式化前台
	public List<OrderBy> formateOrderBys(List<Map<String,String>> orderList){
		List<OrderBy> orderbys=null;
		if(orderList!=null && orderList.size()>0){
			orderbys = new ArrayList();
			for(int i=0;i<orderList.size();i++){
				Map bean = orderList.get(i);
				OrderBy orderby = new OrderBy(bean.get("name").toString(),Integer.valueOf(bean.get("orderby").toString()));
				orderbys.add(orderby);
			}
		}
		return orderbys;  
	}
	public List<Where> formateWheres(Map<String,Object> map){
		List<Where> wheres = null;
		if(CommonFunction.isNotNull(map)){
			wheres=new ArrayList();
			for(Map.Entry<String,Object> entry:map.entrySet()){
				Where where = new Where(entry.getKey());
				wheres.add(where);
			}
		
		}
		return wheres;
	}
	
	
	/**
	 * 根据相关条件分页查询相应数据列表信息
	 * @param tableName 表名
	 * @param rows 显示行数
	 * @param page 当前页
	 * @param map 查询条件 key=value
	 * @param orderList 排序字段集合 
	 * @param columns 查询的字段集合
	 * @return 
	 */
	public QueryResult getListBySqlTableData(String tableName, int rows, int page, Map<String, Object> map, 
			List<Map<String,String>> orderList,List<String> columns){
	
		QueryResult result = null;
		BaseQEntity qEntity = new BaseQEntity();
		if(CommonFunction.isNull(page)){
			page=1;
		}
		qEntity.setCurrPage(page);
		qEntity.setPerPageRows(rows);
		
		result = this.getPageQueryResultSQLToMap(createSelectSql(tableName, map, orderList, columns), map, qEntity);
		return result;
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
	
	public Object getFirstResult(String hql,Map parMap){
		List list = this.getList(hql, parMap);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
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
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public DBUtil getDbUtil() {
		return dbUtil;
	}

	public void setDbUtil(DBUtil dbUtil) {
		this.dbUtil = dbUtil;
	}
	

}