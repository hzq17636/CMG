package com.jz.crm.mappers.mg;
import org.springframework.stereotype.Repository;

import com.jz.crm.base.mappers.BaseMapperImpl;
import com.jz.crm.models.mg.UserInfo;

@Repository("userInfoDao")
public class UserInfoMapper extends BaseMapperImpl<UserInfo, Long> {
}
