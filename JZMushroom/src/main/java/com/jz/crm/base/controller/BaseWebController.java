package com.jz.crm.base.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.jz.crm.base.config.ConstantContext;
import com.jz.crm.base.config.SystemCode;

@Controller
public class BaseWebController {
	
	public Map<String, Object> getMap(Object body) {
		Map<String, Object> map = new HashMap();
		map.put(ConstantContext.RESPONSE_CODE, SystemCode.SUCCESS);
		if (body != null) {
			map.put(ConstantContext.RESPONSE_Body, body);
		}
		return map;
	}

	public Map<String, Object> getMap() {
		return getMap(null);
	}

}
