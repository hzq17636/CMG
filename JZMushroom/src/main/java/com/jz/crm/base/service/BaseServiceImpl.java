package com.jz.crm.base.service;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.jz.crm.base.mappers.BaseMapper;
import com.jz.crm.base.mappers.common.Criteria;
import com.jz.crm.base.util.SpringTool;

@SuppressWarnings("unchecked")
public abstract class BaseServiceImpl<T, PK, S extends BaseMapper<T, PK>> {
	private S dao;

	public S getDao() {
		if (dao != null) {
			return dao;
		} else {
			dao = (S) SpringTool.getBean(((Class) ((ParameterizedType) this.getClass().getGenericSuperclass())
					.getActualTypeArguments()[2]));
			return dao;
		}
	}

	/* 基本方法：与mapper对应 */
	public List<T> getDataPageBy(Criteria criteria) {
		return getDao().getDataPageBy(criteria);
	}

	public List<T> getDataBy(Criteria criteria) {
		return getDao().getDataBy(criteria);
	}

	public T get(PK id, boolean isForUpdate) {
		return getDao().getById(id, isForUpdate);
	}

	public Long getTotalCountBy(Criteria criteria) {
		return getDao().getTotalCountBy(criteria);
	}

	public double getSumBy(Criteria criteria) {
		return getDao().getSumBy(criteria);
	}

	public List<T> getByIds(List<PK> ids, boolean isForUpdate) {
		return getDao().getByIds(ids, isForUpdate);
	}

	public int insert(T entity) {
		return getDao().insert(entity);
	}

	public int update(T entity) {
		return getDao().update(entity);
	}

	public int updateBy(T entity, Criteria criteria) {
		return getDao().updateBy(entity, criteria);
	}

	public int delete(PK id) {
		return getDao().delete(id);
	}

	public int deleteByIds(List<PK> ids) {
		return getDao().deleteByIds(ids);
	}

	public int deleteBy(Criteria criteria) {
		return getDao().deleteBy(criteria);
	}

	/* 扩展方法 */
	public void clearCache() {
		getDao().clearCache();
	}

	public List<T> getAll() {
		return getDao().getAll();
	}

	public T get(PK id) {
		return getDao().getById(id);
	}

	public List<T> get(List<PK> ids) {
		return getDao().getByIds(ids);
	}

	public Long getTotalCount() {
		return getDao().getTotalCount();
	}

	public Long getTotalCountBy(String field, Object value) {
		return getDao().getTotalCountBy(field, value);
	}

	public T getOneBy(Criteria criteria) {
		return getDao().getOneBy(criteria);
	}

	public T getOneBy(String field, Object value) {
		return getDao().getOneBy(field, value);
	}

	public T getOneBy(String field, Object value, String orderByField, boolean isAsc) {
		return getDao().getOneBy(field, value, orderByField, isAsc);
	}

	public List<T> getDataBy(String field, Object value) {
		return getDao().getDataBy(field, value);
	}

	public List<T> getDataBy(String field, Object value, String orderByField, boolean isAsc) {
		return getDao().getDataBy(field, value, orderByField, isAsc);
	}

	public List<T> getDataPageBy(String field, Object value) {
		return getDao().getDataPageBy(field, value);
	}

	public List<T> getDataPageBy(String field, Object value, String orderByField, boolean isAsc) {
		return getDao().getDataPageBy(field, value, orderByField, isAsc);
	}

	public int insert(List<T> entitys) {
		return getDao().insert(entitys);
	}

	public int update(List<T> entitys) {
		return getDao().update(entitys);
	}
}
