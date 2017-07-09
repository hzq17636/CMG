package com.jz.crm.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jz.crm.base.controller.BaseWebController;
import com.jz.crm.base.exception.ApplicationException;
import com.jz.crm.models.mg.dto.ReportStatisticsDto;
import com.jz.crm.services.mg.CompanyInfoService;

/**
 * 
 * 报表统计
 * 
 */
@Controller
@RequestMapping(value = "/mg/report/")
public class ReportstatisticsController  extends BaseWebController{
	public static final Logger LOG = LoggerFactory.getLogger(ReportstatisticsController.class);

	@Autowired
	@Qualifier("companyInfoService")
	private CompanyInfoService companyInfoService;
	
	/**
	 * 录入统计
	 * @param reportStatisticsDto
	 * @param session
	 * @return
	 * @throws ApplicationException
	 */
	@ResponseBody
	@RequestMapping(value = "/input.action")
	public Map<String, Object> input(@RequestBody ReportStatisticsDto reportStatisticsDto, HttpSession session)
			throws ApplicationException {
		Map<String,Object> map = new HashMap<String,Object>();
		List<ReportStatisticsDto> dtos = companyInfoService.inputStatistics();//录入统计
		map.put("dtos", dtos);
		return getMap(map);
	}

	/**
	 * 行业分类统计
	 * @param reportStatisticsDto
	 * @param session
	 * @return
	 * @throws ApplicationException
	 */
	@ResponseBody
	@RequestMapping(value = "/industry.action")
	public Map<String, Object> Industry(@RequestBody ReportStatisticsDto reportStatisticsDto, HttpSession session)
			throws ApplicationException {
		Map<String,Object> map = new HashMap<String,Object>();
		List<ReportStatisticsDto> dtos = companyInfoService.industryStatistics();//行业统计
		map.put("dtos", dtos);
		return getMap(map);
	}
	
	
	
}
