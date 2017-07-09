package com.jz.crm.services.rb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jz.crm.base.service.BaseServiceImpl;
import com.jz.crm.mappers.rb.RbShareRecordMapper;
import com.jz.crm.models.rb.RbShareRecord;
import com.jz.crm.models.rb.dto.ShareDto;

@Service("rbShareRecordService")
public class RbShareRecordService extends BaseServiceImpl<RbShareRecord, Long, RbShareRecordMapper>{
	
	/**
	 * 获取分享列表
	 * @return list
	 */
	public List<ShareDto> getShareCount(ShareDto shareDto){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mobile", shareDto.getMobile());	
		map.put("startTime", shareDto.getStartTime());
		map.put("endTime", shareDto.getEndTime());
		shareDto.setTotalResult(this.getDao().getShareCount(map));
		if(shareDto.getTotalResult()==0){
			map.put("currentPage", shareDto.getCurrentPage()*shareDto.getShowCount());
		}else{
			map.put("currentPage", (shareDto.getCurrentPage()-1)*shareDto.getShowCount());
		}
		map.put("showCount", shareDto.getShowCount());
		return this.getDao().getShareList(map);
	}
		
	/**
	 * 根据用户id查询用户列表
	 * 
	 * @param bhShareRecord
	 * @return List
	 */
	public  List<ShareDto> getShareUserByUserId(ShareDto shareDto){	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", shareDto.getUserId());		
		shareDto.setTotalResult(this.getDao().getShareUserByUserIdCount(map));
		if(shareDto.getTotalResult()==0){
			map.put("currentPage", shareDto.getCurrentPage()*shareDto.getShowCount());
		}else{
			map.put("currentPage", (shareDto.getCurrentPage()-1)*shareDto.getShowCount());
		}
		map.put("showCount", shareDto.getShowCount());
		return this.getDao().getShareUserByUserId(map);
	}
	
	
	/**
	 * 我的邀请
	 * @param userId
	 * @return list
	 */
	public List<RbShareRecord> getRbShareRecordList(long userId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return this.getDao().getRbShareRecordList(map);
	}
}
