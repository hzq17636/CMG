package com.jz.crm.base.util;

import java.util.Map;

import com.jz.crm.base.config.ConstantContext;

public class FieldUtil {
	public static final String JS_SCRIPT_SPLIT = "jsScript:";
	public static final String JS_FUNCATION_SPLIT = "jsFuncation:";

	/**
	 * 根据字段配置信息从HTML里获取值
	 * @param parameterMap
	 *       参数
	 * @param html
	 * @param fieldName
	 * @param fieldKey
	 * @return
	 * @throws Exception
	 */
	public static String getFieldValue(Map<String, String> parameterMap,
			String html, String fieldName, String fieldKey) throws Exception {
		String fieldValue = null;
		String jsScript = null;
		String jsFuncation = null;

		// 1.对整个html处理，和分隔出输出字段方式和js脚本
		if ("html".equals(fieldName)) {// 对整个页面执行js脚本
			fieldValue = html;
		}

		if (fieldKey.indexOf(JS_SCRIPT_SPLIT) != -1) {
			String[] field = fieldKey.split(JS_SCRIPT_SPLIT);
			if (!"".equals(field[0])) {
				fieldKey = field[0].substring(0, field[0].length() - 1);
			} else {
				fieldKey = null;
			}
			jsScript = field[1];
		} else if (fieldKey.indexOf(JS_FUNCATION_SPLIT) != -1) {
			String[] field = fieldKey.split(JS_FUNCATION_SPLIT);
			if (!"".equals(field[0])) {
				fieldKey = field[0].substring(0, field[0].length() - 1);
			} else {
				fieldKey = null;
			}
			jsFuncation = field[1];
		}

		// 2.执行字段输出如:json,xpath
		if (fieldKey != null) {
			// 替换输出字段中的变量
			for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
				fieldKey = StringUtil.replaceAll(fieldKey,
						"\\$\\{" + entry.getKey() + "\\}",
						entry.getValue() == null ? "" : entry.getValue());

			}
			if (fieldKey.indexOf("/") != -1) {// 处理xpath
				fieldValue = XpathUtil.getContentInfo(html, fieldKey);
			} else if (fieldKey.indexOf(".") != -1){// 处理json格式
				Map<?, ?> mapHtml = JsonUtil.jsonToMap(html);
				fieldValue = MapUtil.getValueByMap(mapHtml, fieldKey, -1);
			}else{
				fieldValue = fieldKey;
			}
		}

		// 3.对处理后的数据执行js脚本
		if (jsScript != null) {
			if (fieldValue != null) {
				parameterMap.put(fieldName, fieldValue);
			}
			fieldValue = JsUtil.getJsContentInfo(parameterMap, jsScript);
		}

		if (jsFuncation != null) {
			if (fieldValue != null) {
				parameterMap.put(fieldName, fieldValue);
			}
			fieldValue = JsUtil.getJsFunContentInfo(parameterMap, jsFuncation);
		}

		return fieldValue;
	}
}
