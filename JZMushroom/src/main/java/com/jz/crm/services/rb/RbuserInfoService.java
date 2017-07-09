package com.jz.crm.services.rb;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jz.crm.base.service.BaseServiceImpl;
import com.jz.crm.mappers.rb.RbShareRecordMapper;
import com.jz.crm.mappers.rb.RbuserInfoMapper;
import com.jz.crm.models.rb.RbShareRecord;
import com.jz.crm.models.rb.RbuserInfo;

@Service("rbuserInfoService")
public class RbuserInfoService extends BaseServiceImpl<RbuserInfo, Long, RbuserInfoMapper>{
	@Autowired
	@Qualifier("rbShareRecordDao")
	private RbShareRecordMapper rbShareRecordDao;
	
	/**
	 * 如果fromUserId不为空且存在用户表中，而且此用户没有被分享过就增加一条分享记录
	 * 
	 * @param userInfo
	 */
	public void insertAndUpdateObj(RbuserInfo rbuserInfo) {
		boolean isNewUser = false;
		rbuserInfo.setUpdateTime(new Date());
		if (rbuserInfo.getId() == null) {
			isNewUser = true;
			rbuserInfo.setCreateTime(new Date());
			this.getDao().insert(rbuserInfo);
		} else {
			this.getDao().update(rbuserInfo);
		}
		// 新用户才会判断是否需要统计分享
		RbShareRecord  shareRecord  = rbShareRecordDao.getOneBy(RbShareRecord.DBStrShareUserId, rbuserInfo.getId());
		if (rbuserInfo.getFromUserId()!=0 && isNewUser && shareRecord == null) {
			shareRecord = new RbShareRecord();
			shareRecord.setUserId(rbuserInfo.getFromUserId());
			shareRecord.setShareUserId(rbuserInfo.getId());
			shareRecord.setStatus(RbShareRecord.STATUS_NO);
			shareRecord.setCreateTime(new Date());
			shareRecord.setUpdateTime(new Date());
			rbShareRecordDao.insert(shareRecord);
		}
		if (!isNewUser && shareRecord != null && shareRecord.getStatus().intValue() == RbShareRecord.STATUS_NO) {
			//RbuserInfo fromUser = this.getDao().getById(shareRecord.getUserId());
			shareRecord.setUpdateTime(new Date());
			shareRecord.setStatus(RbShareRecord.STATUS_YES);
			rbShareRecordDao.update(shareRecord);
		}
	}
}
