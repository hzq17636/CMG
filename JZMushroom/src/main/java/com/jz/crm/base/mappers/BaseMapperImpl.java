package com.jz.crm.base.mappers;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.jz.crm.base.mappers.common.Criteria;

@SuppressWarnings("unchecked")
public class BaseMapperImpl<T, PK> extends SqlSessionDaoSupport implements BaseMapper<T, PK> {
	/* 基本方法：与mapper对应 */
	@Autowired
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		{
			super.setSqlSessionTemplate(sqlSessionTemplate);
		}
	}

	@Override
	public List<T> getDataPageBy(Criteria criteria) {
		Assert.notNull(criteria, "[criteria] is required");
		Map<String, Object> map = getCriteriaMap(criteria);
		return getSqlSession().selectList(getMapperFullName(GetDataPageBy), map);
	}

	@Override
	public List<T> getDataBy(Criteria criteria) {
		Assert.notNull(criteria, "[criteria] is required");
		Map<String, Object> map = getCriteriaMap(criteria);
		return getSqlSession().selectList(getMapperFullName(GetDataBy), map);
	}

	@Override
	public T getById(PK id, boolean isForUpdate) {
		Assert.isTrue(isPKAvailabe, "table doesn't have primary key or more than 1 primary key");
		Assert.notNull(id, "id is required");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key_value", id);
		map.put("for_update", isForUpdate ? " for update " : "");
		return (T) getSqlSession().selectOne(getMapperFullName(GetById), map);
	}

	@Override
	public Long getTotalCountBy(Criteria criteria) {
		Assert.isTrue(isPKAvailabe, "table doesn't have primary key or more than 1 primary key");
		Assert.notNull(criteria, "[criteria] is required");
		Map<String, Object> map = getCriteriaMap(criteria);
		Number answer = (Number) getSqlSession().selectOne(getMapperFullName(GetTotalCountBy), map);
		if (answer == null) {
			return 0L;
		}
		return answer.longValue();
	}

	@Override
	public double getSumBy(Criteria criteria) {
		Assert.notNull(criteria, "[criteria] is required");
		Map<String, Object> map = getCriteriaMap(criteria);
		Double answer = (Double) getSqlSession().selectOne(getMapperFullName(GetSumBy), map);
		clearCache();
		if (answer == null) {
			return 0.0;
		}
		return answer;
	}

	@Override
	public List<T> getByIds(List<PK> ids, boolean isForUpdate) {
		Assert.isTrue(isPKAvailabe, "table doesn't have primary key or more than 1 primary key");
		Assert.notNull(ids, "id is required");
		Assert.notNull(isForUpdate, "isForupdate is required");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", ids);
		map.put("for_update", isForUpdate ? " for update " : "");
		return getSqlSession().selectList(getMapperFullName(GetByIds), map);
	}

	@Override
	public int insert(T entity) {
		Assert.isTrue(isPKAvailabe, "table doesn't have primary key or more than 1 primary key");
		Assert.notNull(entity, "[entity] is required");
		Integer result = (Integer) getSqlSession().insert(getMapperFullName(INSERT), entity);
		clearCache();
		return result;
	}

	@Override
	public int updateBy(T entity, Criteria criteria) {
		Assert.notNull(criteria, "[entity] is required");
		Map<String, Object> criteriaMap = getEntityCriteriaMap(entity, criteria);
		Integer result = getSqlSession().update(getMapperFullName(UPDATEBY), criteriaMap);
		clearCache();
		return result;
	}

	@Override
	public int update(T entity) {
		Assert.isTrue(isPKAvailabe, "table doesn't have primary key or more than 1 primary key");
		Assert.notNull(entity, "[entity] is required");
		Integer result = (Integer) getSqlSession().update(getMapperFullName(UPDATE), entity);
		clearCache();
		return result;
	}

	@Override
	public int deleteByIds(List<PK> ids) {
		Assert.isTrue(isPKAvailabe, "table doesn't have primary key or more than 1 primary key");
		Assert.notNull(ids, "ids is required");
		Integer result = (Integer) getSqlSession().delete(getMapperFullName(DeleteByIds), ids);
		clearCache();
		return result;
	}

	@Override
	public int deleteBy(Criteria criteria) {
		Assert.notNull(criteria, "[criteria] is required");
		Assert.isTrue(criteria.hasAddWhere(), "please add where into criteria");
		Map<String, Object> criteriaMap = getCriteriaMap(criteria);
		Integer result = (Integer) getSqlSession().delete(getMapperFullName(DeleteBy), criteriaMap);
		clearCache();
		return result;
	}

	@Override
	public int delete(PK id) {
		Assert.isTrue(isPKAvailabe, "table doesn't have primary key or more than 1 primary key");
		Integer result = (Integer) getSqlSession().delete(getMapperFullName(DELETE), id);
		clearCache();
		return result;
	}

	/* 扩展方法 */
	@Override
	public void clearCache() {
		getSqlSession().clearCache();
	}

	@Override
	public List<T> getAll() {
		return getDataBy(new Criteria());
	}

	@Override
	public T getById(PK id) {
		return getById(id, false);
	}

	@Override
	public List<T> getByIds(List<PK> ids) {
		return getByIds(ids, false);
	}

	@Override
	public Long getTotalCount() {
		return getTotalCountBy(new Criteria());
	}

	@Override
	public Long getTotalCountBy(String field, Object value) {
		Criteria criteria = new Criteria();
		criteria.addWhere(field + " = '%1$s'", new Object[] { value });
		return getTotalCountBy(criteria);
	}

	@Override
	public T getOneBy(Criteria criteria) {
		Assert.notNull(criteria, "criteria is required");
		criteria.setLimit(1);
		List<T> result = getDataBy(criteria);
		if (result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	@Override
	public T getOneBy(String field, Object value) {
		Criteria criteria = new Criteria();
		criteria.addWhere(field + " = '%1$s'", new Object[] { value });
		return getOneBy(criteria);
	}

	@Override
	public T getOneBy(String field, Object value, String orderByField, boolean isAsc) {
		Criteria criteria = new Criteria();
		criteria.addWhere(field + " = '%1$s'", new Object[] { value });
		setOrderForCriteria(criteria, orderByField, isAsc);
		return getOneBy(criteria);
	}

	@Override
	public List<T> getDataBy(String field, Object value) {
		Criteria criteria = new Criteria();
		criteria.addWhere(field + " = '%1$s'", new Object[] { value });
		return getDataBy(criteria);
	}

	@Override
	public List<T> getDataBy(String field, Object value, String orderByField, boolean isAsc) {
		Criteria criteria = new Criteria();
		criteria.addWhere(field + " = '%1$s'", new Object[] { value });
		setOrderForCriteria(criteria, orderByField, isAsc);
		return getDataBy(criteria);
	}

	@Override
	public List<T> getDataPageBy(String field, Object value) {
		Criteria criteria = new Criteria();
		criteria.addWhere(field + " = '%1$s'", new Object[] { value });
		return getDataPageBy(criteria);
	}

	@Override
	public List<T> getDataPageBy(String field, Object value, String orderByField, boolean isAsc) {
		Criteria criteria = new Criteria();
		criteria.addWhere(field + " = '%1$s'", new Object[] { value });
		setOrderForCriteria(criteria, orderByField, isAsc);
		return getDataBy(criteria);
	}

	@Override
	public int insert(List<T> entitys) {
		Assert.notNull(entitys, "[entitys] is required");
		int answer = 0;
		for (T each : entitys) {
			answer += insert(each);
		}
		clearCache();
		return answer;
	}

	@Override
	public int update(List<T> entitys) {
		Assert.isTrue(isPKAvailabe, "table doesn't have primary key or more than 1 primary key");
		Assert.notNull(entitys, "[entity] is required");
		int answer = 0;
		for (T each : entitys) {
			answer += update(each);
		}
		clearCache();
		return answer;
	}

	/* 将Criteria转成Map */
	protected Map<String, Object> getEntityCriteriaMap(Object obj, Criteria criteria) {
		Map<String, Object> map = new HashMap<String, Object>();
		updateCriteriaMap(criteria, map);
		map.put("entity", obj);
		return map;
	}

	protected Map<String, Object> getCriteriaMap(Criteria criteria) {
		Map<String, Object> map = new HashMap<String, Object>();
		updateCriteriaMap(criteria, map);
		return map;
	}

	private Map<String, Object> updateCriteriaMap(Criteria criteria, Map<String, Object> map) {
		if (criteria == null) {
			criteria = new Criteria();
		}
		map.putAll(criteria.getAnswer());
		return map;
	}

	/* End */

	/* 获得mapperId的命名空间全路径, 如果有Ex结尾的命名空间, 则优先调用 */
	protected String getMapperFullName(String mapperId) {
		String extendMapper = new StringBuilder(daoMappedId).append(".").append(mapperId).append("Ex").toString();
		if (getSqlSession().getConfiguration().hasStatement(extendMapper)) {
			return extendMapper;
		}
		return new StringBuilder(daoMappedId).append(".").append(mapperId).toString();
	}

	private void setOrderForCriteria(Criteria criteria, String orderByField, boolean isAsc) {
		if (isAsc) {
			criteria.addAscOrder(orderByField);
		} else {
			criteria.addDescOrder(orderByField);
		}
	}

	public BaseMapperImpl() {
		String temp = this.getClass().getName();
		daoMappedId = temp.substring(0, temp.lastIndexOf(".")) + "."
				+ temp.substring(temp.lastIndexOf(".") + 1).replace("Base", "");
		Type pktype = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		isPKAvailabe = (pktype != Object.class);
	}

	protected String daoMappedId;
	private boolean isPKAvailabe = true;

	private static final String GetDataPageBy = "getDataPageBy";
	private static final String GetDataBy = "getDataBy";
	private static final String GetTotalCountBy = "getTotalCountBy";
	private static final String GetSumBy = "getSumBy";
	private static final String GetById = "getById";
	private static final String GetByIds = "getByIds";
	private static final String INSERT = "insert";
	private static final String UPDATE = "update";
	private static final String UPDATEBY = "updateBy";
	private static final String DELETE = "delete";
	private static final String DeleteByIds = "deleteByIds";
	private static final String DeleteBy = "deleteBy";

	/*
	 * private String getBondSql(String mapperId, Map<String, Object> map){
	 * return
	 * getSqlSession().getConfiguration().getMappedStatement(getMapperFullName
	 * (mapperId)).getBoundSql(map).getSql(); }
	 */
}
