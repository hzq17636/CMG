package com.jz.crm.mappers.rb;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jz.crm.base.mappers.BaseMapperImpl;
import com.jz.crm.models.rb.RbShareRecord;
import com.jz.crm.models.rb.dto.ShareDto;

@Repository("rbShareRecordDao")
public class RbShareRecordMapper extends BaseMapperImpl<RbShareRecord, Long> {
	//获取分享列表
	public List<ShareDto> getShareList(Map map){
		return this.getSqlSession().selectList(getMapperFullName("getShareNum"), map);
	}
	public int getShareCount(Map map){
		return this.getSqlSession().selectOne(getMapperFullName("getShareCount"), map);
	}
	
	//通过用户id获取用户信息
	public List<ShareDto> getShareUserByUserId(Map map){
		return this.getSqlSession().selectList(getMapperFullName("getDataByUserIds"), map);
	}
	public int getShareUserByUserIdCount(Map map){
		return this.getSqlSession().selectOne(getMapperFullName("getDataByUserIdCount"), map);
	}
	
	/**
	 * 我的邀请列表
	 * @param map
	 * @return list
	 */
	public List<RbShareRecord> getRbShareRecordList(Map map){
		return this.getSqlSession().selectList(getMapperFullName("getShareData"), map);
	}
}
