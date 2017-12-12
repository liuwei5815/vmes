package com.xy.admx.core.service.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.LockOptions;

public interface BaseService {
	
	/**
	 * 根据主健删除对象
	 * @param clazz
	 * @param id
	 */
	public <T> void delById(Class<T> clazz, Serializable id);
	
	/**
	 * 根据传入的要更新的属性及值动态组成hql
	 * @param clazz 实体对象类名
	 * @param properties	要更新的属性及值,key为实体类的属性名,value为更新的值
	 * @param id	主键id
	 */
	public void updateProperties(Class clazz,Map<String,Object> properties,Serializable id);
	/**
	 * 根据传入的要更新的实体和属性动作更新实体类
	 * @param clazz 实体对象类名
	 * @param properties	要更新的属性
	 */
	public <T extends Serializable> T updateProperties(T t,Set<String> properties);
	
	/**
	 * 保存某个类（即数据库中的一条记录）
	 * @param object
	 */
	public void save(Object object);

	/**
	 * 保存某个类（即数据库中的一条记录）
	 * @param object
	 */
	public void update(Object object);
	
	public Object merge(Object object) throws Exception;

	/**
	 * 删除某个类（即数据库中的一条记录）
	 * @param object
	 */
	public void delete(Object object);

	/**
	 * 用主键获取某个类（即数据库中的一条记录）
	 * @param clazz
	 * @param id
	 * @return
	 */
	public <T> T get(Class<T> clazz, Serializable id);
	
	/**
	 * lockOptions表示锁的模式，如果使用for update 语句， 则LockOptions.UPGRADE
	 * @param clazz
	 * @param id	主健
	 * @param lockOptions	锁模式
	 * @return
	 */
	public <T> T get(Class<T> clazz, Serializable id, LockOptions lockOptions);
	
	/**
	 * 取得某个类的所有的记录的List集合
	 * @param clazz
	 * @return
	 */
	public <T> List<T> getAll(Class<T> clazz);
	
	
	/***
	 * 
	 */
	public void clear();

}
