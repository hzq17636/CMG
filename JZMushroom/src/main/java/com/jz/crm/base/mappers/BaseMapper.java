package com.jz.crm.base.mappers;

import java.util.List;

import com.jz.crm.base.mappers.common.Criteria;

public interface BaseMapper<T, PK> {
	/* 基本方法：与mapper对应 */
	public List<T> getDataPageBy(Criteria criteria);

	public List<T> getDataBy(Criteria criteria);

	public T getById(PK id, boolean isForUpdate);

	public Long getTotalCountBy(Criteria criteria);

	public double getSumBy(Criteria criteria);

	public List<T> getByIds(List<PK> ids, boolean isForUpdate);

	public int insert(T entity);

	public int update(T entity);

	public int updateBy(T entity, Criteria criteria);

	public int delete(PK id);

	public int deleteByIds(List<PK> ids);

	public int deleteBy(Criteria criteria);

	/* 扩展方法 */
	public void clearCache();

	public List<T> getAll();

	public T getById(PK id);

	public List<T> getByIds(List<PK> ids);

	public Long getTotalCount();

	public Long getTotalCountBy(String field, Object value);

	public T getOneBy(Criteria criteria);

	public T getOneBy(String field, Object value);

	public T getOneBy(String field, Object value, String orderByField, boolean isAsc);

	public List<T> getDataBy(String field, Object value);

	public List<T> getDataBy(String field, Object value, String orderByField, boolean isAsc);

	public List<T> getDataPageBy(String field, Object value);

	public List<T> getDataPageBy(String field, Object value, String orderByField, boolean isAsc);

	public int insert(List<T> entitys);

	public int update(List<T> entitys);
}