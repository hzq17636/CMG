package com.jz.crm.mappers.rb;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jz.crm.base.mappers.BaseMapperImpl;
import com.jz.crm.models.rb.RbCollectRecord;
import com.jz.crm.models.rb.dto.CollectCompany;

@Repository("rbCollectRecordDao")
public class RbCollectRecordMapper extends BaseMapperImpl<RbCollectRecord, Long> {
	
	/**
	 * hr红宝书列表
	 * @param map
	 * @return
	 */
	public List<CollectCompany> queryRedBookList(Map map){
		return this.getSqlSession().selectList(getMapperFullName("getRedBookList"), map);
	}
	
	/**
	 * 我的收藏
	 * @param map
	 * @return list
	 */
	public List<CollectCompany> queryCollectCompany(Map map){
		return this.getSqlSession().selectList(getMapperFullName("getCollectCompanyList"), map);
	}
}
