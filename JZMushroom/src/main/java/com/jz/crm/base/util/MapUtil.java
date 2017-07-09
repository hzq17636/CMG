package com.jz.crm.base.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUtil {

	public static String getValueByMap(Map<?, ?> map, String key, int index1,
			int index2) {
		if (key == null || key.length() < 0) {
			return null;
		}
		String[] keys = key.split("\\.");
		Map<?, ?> outputMap = map;
		boolean isIndex1 = true;
		for (int i = 0; i < keys.length - 1; i++) {
			if ("${blank}".equals(keys[i])) {
				keys[i] = "";
			}
			Object output = outputMap.get(keys[i].trim());
			if (output == null) {
				return null;
			}
			if (output instanceof Map<?, ?>) {
				outputMap = (Map<?, ?>) output;
			}
			if (index1 != -1 && isIndex1) {
				if (output instanceof List) {
					List<?> list = (List<?>) output;
					if (index1 < list.size()) {
						isIndex1 = false;
						output = list.get(index1);
						if (output instanceof Map<?, ?>) {
							outputMap = (Map<?, ?>) output;
						}
					}
				}
			}
			if (index2 != -1 && !isIndex1) {
				if (output instanceof List) {
					List<?> list = (List<?>) output;
					if (index2 < list.size()) {
						output = list.get(index2);
						if (output instanceof Map<?, ?>) {
							outputMap = (Map<?, ?>) output;
						}
					}
				}
			}
		}
		Object result = outputMap.get(keys[keys.length - 1]);
		if (result != null) {
			return String.valueOf(result);
		}
		return null;
	}

	/**
	 * 处理json转成map后的key值 ，如果有层字段，那每个字段为.来连接，如a.b
	 * 
	 * @param map
	 *            json 转成map
	 * @param jsonKey
	 *            json key 如 a.b
	 * @param index
	 *            如果是list就设置此值 ，否则为-1
	 * @return
	 */
	public static String getValueByMap(Map<?, ?> map, String key, int index) {
		if (key == null || key.length() < 0) {
			return null;
		}
		String[] keys = key.split("\\.");
		Map<?, ?> outputMap = map;
		for (int i = 0; i < keys.length - 1; i++) {
			if ("${blank}".equals(keys[i])) {
				keys[i] = "";
			}
			Object output = outputMap.get(keys[i].trim());
			if (output == null) {
				return null;
			}
			if (output instanceof Map<?, ?>) {
				outputMap = (Map<?, ?>) output;
			}
			if (index != -1) {
				if (output instanceof List) {
					List<?> list = (List<?>) output;
					if (index < list.size()) {
						output = list.get(index);
						if (output instanceof Map<?, ?>) {
							outputMap = (Map<?, ?>) output;
						}
					}
				}
			}
		}
		Object result = outputMap.get(keys[keys.length - 1]);
		if (result != null) {
			return String.valueOf(result);
		}
		return null;
	}

	/**
	 * 多个map 合并到一个map中
	 * @param list
	 * @return
	 */
	public static Map<String, String> listToMap(List<Map<String, String>> list) {
		Map<String, String> result = new HashMap();
		if (list != null && list.size() > 0) {
			for (Map<String, String> map : list) {
				for (Map.Entry<String, String> entry : map.entrySet()) {
					if (entry.getValue() != null) {
						result.put(entry.getKey(), entry.getValue());
					}
				}
			}
		}
		return result;
	}
}
