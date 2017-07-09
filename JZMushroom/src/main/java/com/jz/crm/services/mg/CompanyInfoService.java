package com.jz.crm.services.mg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jz.crm.base.mappers.common.Criteria;
import com.jz.crm.base.service.BaseServiceImpl;
import com.jz.crm.mappers.mg.CompanyInfoMapper;
import com.jz.crm.models.mg.CompanyInfo;
import com.jz.crm.models.mg.dto.ReportStatisticsDto;
import com.jz.crm.models.rb.dto.RedBookMenu;

@Service("companyInfoService")
public class CompanyInfoService extends BaseServiceImpl<CompanyInfo, Long, CompanyInfoMapper>{
	
	/**
	 * 根据行业或者录入人查询企业信息
	 * @param companyInfo
	 * @return list
	 */
	public List<CompanyInfo> queryCompanysBy(CompanyInfo companyInfo){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyType", companyInfo.getCompanyType());
		map.put("createBy", companyInfo.getCreateBy());
		companyInfo.setTotalResult(this.getDao().getCompanyCountBy(map));
		if(companyInfo.getTotalResult()==0){
			map.put("currentPage", companyInfo.getCurrentPage()*companyInfo.getShowCount());
		}else{
			map.put("currentPage", (companyInfo.getCurrentPage()-1)*companyInfo.getShowCount());
		}
		map.put("showCount", companyInfo.getShowCount());
		return this.getDao().getCompanysBy(map);
		
//		Criteria criteria = new Criteria();
//		if(!"".equals(companyInfo.getCompanyType()) && null != companyInfo.getCompanyType()){
//			criteria.addWhere(CompanyInfo.DBStrCompanyType+"='%1$s'", new Object[] {companyInfo.getCompanyType()});
//		}
//		if(!"".equals(companyInfo.getCreateBy()) && null != companyInfo.getCreateBy()){
//			criteria.addWhere(CompanyInfo.DBStrCreateBy+"='%1$s'", new Object[] {companyInfo.getCreateBy()});
//		}
//		criteria.addDescOrder(CompanyInfo.DBStrCreateTime);
//		return this.getDao().getDataBy(criteria);
	}
	
	/**
	 * 行业统计
	 * @return
	 */
	public List<ReportStatisticsDto> industryStatistics(){
		return this.getDao().industryStatistics();
	}
	
	/**
	 * 录入统计
	 * @return
	 */
	public List<ReportStatisticsDto> inputStatistics(){
		return this.getDao().inputStatistics();
	}
	
	/**
	 * hr红包数-菜单栏
	 * @return
	 */
	public List<RedBookMenu> RedBookMenu() {
		return this.getDao().RedBookMenu();
	}
	
	/**
	 * 按公司名查询公司
	 * @param companyInfo
	 * @return list
	 */
	public List<CompanyInfo> queryCompanyList(CompanyInfo companyInfo){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyName", companyInfo.getCompanyName());
		companyInfo.setTotalResult(this.getDao().getCompanyListCount(map));
		if(companyInfo.getTotalResult()==0){
			map.put("currentPage", companyInfo.getCurrentPage()*companyInfo.getShowCount());
		}else{
			map.put("currentPage", (companyInfo.getCurrentPage()-1)*companyInfo.getShowCount());
		}
		map.put("showCount", companyInfo.getShowCount());
		return this.getDao().getCompanyList(map);
	}
}
