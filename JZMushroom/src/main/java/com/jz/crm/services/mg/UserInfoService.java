package com.jz.crm.services.mg;

import org.springframework.stereotype.Service;

import com.jz.crm.base.service.BaseServiceImpl;
import com.jz.crm.mappers.mg.UserInfoMapper;
import com.jz.crm.models.mg.UserInfo;

@Service("userInfoService")
public class UserInfoService extends BaseServiceImpl<UserInfo, Long, UserInfoMapper>{
	
	/**
	 * 新增或修改用户
	 * @param usersInfo
	 */
	public void addOrupdateUser(UserInfo userInfo){
		if(userInfo.getId()!=null){
			this.getDao().update(userInfo);
		}else{
			this.getDao().insert(userInfo);
		}
	}
}
