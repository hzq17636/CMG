package com.jz.crm.services.rb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jz.crm.base.mappers.common.Criteria;
import com.jz.crm.base.service.BaseServiceImpl;
import com.jz.crm.mappers.rb.RbCollectRecordMapper;
import com.jz.crm.models.rb.RbCollectRecord;
import com.jz.crm.models.rb.RbuserInfo;
import com.jz.crm.models.rb.dto.CollectCompany;

@Service("rbCollectRecordService")
public class RbCollectRecordService extends BaseServiceImpl<RbCollectRecord, Long, RbCollectRecordMapper>{
	public static final Logger LOG = LoggerFactory.getLogger(RbCollectRecordService.class);
	
	@Autowired
	@Qualifier("rbuserInfoService")
	private RbuserInfoService rbuserInfoService;
	
	/**
	 * 根据companyId和userId 删除收藏记录
	 * @param companyId
	 * @param userId
	 * @return 
	 */
	@Transactional
	public int deleteCollectRecord(long companyId,long userId){
		Criteria criteria = new Criteria();
		criteria.addWhere(RbCollectRecord.DBStrCompanyId + "='%1$s'", new Object[] {companyId});
		criteria.addWhere(RbCollectRecord.DBStrUserId + "='%1$s'", new Object[] {userId});
		return this.getDao().deleteBy(criteria);
	}
	
	
	/**
	 * 通过companyId、userId 查询收藏信息
	 * @param companyId
	 * @param userId
	 * @return RbCollectRecord
	 */
	public RbCollectRecord queryRbCollectRecord(long companyId,long userId){
		Criteria criteria = new Criteria();
		criteria.addWhere(RbCollectRecord.DBStrCompanyId + "='%1$s'", new Object[] {companyId});
		criteria.addWhere(RbCollectRecord.DBStrUserId + "='%1$s'", new Object[] {userId});
		return this.getDao().getOneBy(criteria);
	}
	
	
	/**
	 * hr 红宝书列表
	 * @param collectRecord
	 * @return list
	 */
	public List<CollectCompany> queryRedBookList(RbCollectRecord collectRecord){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyType", collectRecord.getCompanyType());
		map.put("currentPageNo", (collectRecord.getPageComm().getCurrentPageNo()-1)*collectRecord.getPageComm().getPageSize());
		map.put("pageSize", collectRecord.getPageComm().getPageSize());
		return this.getDao().queryRedBookList(map);
	}
	
	
	/**
	 * 我的收藏
	 * @param collectRecord
	 * @return list
	 */
	public List<CollectCompany> queryCollectCompany(RbCollectRecord collectRecord,RbuserInfo rbuserInfo){
		RbuserInfo rbuserInfo2 = rbuserInfoService.getOneBy(RbuserInfo.DBStrUserName, rbuserInfo.getUserName());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", rbuserInfo2.getId());
		map.put("companyType", collectRecord.getCompanyType());
		map.put("companyName", collectRecord.getCompanyName());
		map.put("currentPageNo", (collectRecord.getPageComm().getCurrentPageNo()-1)*collectRecord.getPageComm().getPageSize());
		map.put("pageSize", collectRecord.getPageComm().getPageSize());
		return this.getDao().queryCollectCompany(map);
	}
	
}
