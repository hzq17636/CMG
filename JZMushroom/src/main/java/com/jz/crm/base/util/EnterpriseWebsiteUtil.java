package com.jz.crm.base.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jz.crm.base.config.JZMushroom;
import com.jz.crm.base.httprequest.HttpRequest;
import com.jz.crm.base.util.EnterpriseWebsiteUtil;
import com.jz.crm.models.dto.EnterpriseInfoDto;

public class EnterpriseWebsiteUtil {
	public static final Logger LOG = LoggerFactory.getLogger(EnterpriseWebsiteUtil.class);

	/**
	 * 通过企企来查找企业信息
	 * 
	 * @param enterpriseName
	 * @return
	 * @throws Exception
	 */
	public static List<EnterpriseInfoDto> getEnterpriseInfo(String enterpriseName) throws Exception {
		List<EnterpriseInfoDto> result = new ArrayList();
		String url = "http://i.yjapi.com/ECISimple/Search?key=" + JZMushroom.getInstance().get("ApiKey") + "&keyword="
				+ URLEncoder.encode(enterpriseName, "utf-8");
		String html = HttpRequest.sendHttpRequestProxy(false, null, "GET", url, null, null, null, null);

		LOG.info("企查查返回数据：" + html);
		if (result != null) {
			Map<?, ?> resultMap = JsonUtil.jsonToMap(html);
			String status = (String) resultMap.get("Status");
			if ("200".equals(status)) {
				Object object = resultMap.get("Result");
				if (object != null && object instanceof List) {
					List<Map<String, String>> list = (List<Map<String, String>>) object;
					for (Map<String, String> map : list) {
						EnterpriseInfoDto dto = new EnterpriseInfoDto();
						dto.setName(map.get("Name"));
						dto.setRequestEnterpriseId(map.get("KeyNo"));
						result.add(dto);
					}
				}
			}
		}

		return result;

	}

	public static void main(String[] args) throws Exception {
		EnterpriseWebsiteUtil.getEnterpriseInfo("华为");
	}
}
