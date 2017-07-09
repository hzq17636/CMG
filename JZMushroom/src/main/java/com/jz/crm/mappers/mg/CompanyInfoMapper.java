package com.jz.crm.mappers.mg;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jz.crm.base.mappers.BaseMapperImpl;
import com.jz.crm.models.mg.CompanyInfo;
import com.jz.crm.models.mg.dto.ReportStatisticsDto;
import com.jz.crm.models.rb.dto.RedBookMenu;

@Repository("companyInfoDao")
public class CompanyInfoMapper extends BaseMapperImpl<CompanyInfo, Long> {
	/**
	 * 数据统计（行业和录入人）
	 * @param map
	 * @return
	 */
	public int getCompanyCountBy(Map map) {
		return (Integer)this.getSqlSession().selectOne(getMapperFullName("getCompanysCountBy"), map);
	}
	public List<CompanyInfo> getCompanysBy(Map map) {
		return this.getSqlSession().selectList(getMapperFullName("getCompanysBy"), map);
	}
	
	public int getCompanyListCount(Map map) {
		return (Integer)this.getSqlSession().selectOne(getMapperFullName("getDataListByCount"), map);
	}
	public List<CompanyInfo> getCompanyList(Map map) {
		return this.getSqlSession().selectList(getMapperFullName("getDataListBy"), map);
	}
	
	/**
	 * hr红包数-菜单栏
	 * @return
	 */
	public List<RedBookMenu> RedBookMenu() {
		return this.getSqlSession().selectList(getMapperFullName("redBookMenuList"), null);
	}
	
	/**
	 * 行业统计
	 * @return
	 */
	public List<ReportStatisticsDto> industryStatistics(){
		return this.getSqlSession().selectList(getMapperFullName("industryStatistics"), null);
	}
	
	/**
	 * 录入统计
	 * @return
	 */
	public List<ReportStatisticsDto> inputStatistics(){
		return this.getSqlSession().selectList(getMapperFullName("inputStatistics"), null);
	}
}
