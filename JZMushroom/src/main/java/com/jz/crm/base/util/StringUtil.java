package com.jz.crm.base.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jz.crm.models.dto.Property;

public class StringUtil {

	public static String joinString(List<String> array, String symbol) {
		String result = "";
		if (array != null) {
			for (int i = 0; i < array.size(); i++) {
				String temp = array.get(i).toString();
				if (temp != null && temp.trim().length() > 0)
					result += (temp + symbol);
			}
			if (result.length() > 1)
				result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	public static String joinString(String[] array, String symbol) {
		String result = "";
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				String temp = array[i];
				if (temp != null && temp.trim().length() > 0)
					result += (temp + symbol);
			}
			if (result.length() > 1)
				result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	public static List<String> mkList(String str, String regx) {
		List<String> list = new ArrayList<String>();
		String[] array = str.split(regx);
		for (String value : array) {
			list.add(value);
		}
		return list;
	}

	public static String getKeyValue(String str, String regx, String key) {

		String[] params = str.split(regx);
		for (String param : params) {
			String[] values = param.split("=");
			if (key.equals(values[0].trim())) {
				return values[1].trim();
			}
		}
		return str;
	}

	public static String areaText(String ptext, String etext, String searchWord, int wlength, boolean merge) {
		if (!etext.contains(searchWord))
			return null;
		String wordText = etext;
		int start = etext.indexOf(searchWord);
		int end = start;
		if (merge) // true 不合并多个关键字
			end = etext.lastIndexOf(searchWord);
		String startStr = etext.substring(0, start);
		String endStr = etext.substring(end + searchWord.length());
		if (ptext != null && !ptext.trim().equals("")) { // 父文本不为空时
			if (startStr.length() < wlength) { // 从父文本中剪切响应补充关键字左边文本
				int diff = wlength - startStr.length();
				int newStart = ptext.indexOf(etext) - diff;
				if (newStart < 0)
					newStart = 0;
				startStr = ptext.substring(newStart, (newStart + wlength) > ptext.length() ? ptext.length()
						: (newStart + wlength));
			} else {
				startStr = startStr.substring(startStr.length() - wlength);
			}
			if (endStr.length() < wlength) { // 从父文本中剪切响应补充关键字右边文本
				int subIndex = ptext.indexOf(etext);
				int diff = wlength - endStr.length();
				int newEnd = subIndex + etext.length() + diff;
				if (newEnd > ptext.length())
					newEnd = ptext.length();
				endStr = ptext.substring(subIndex + etext.length() - endStr.length(), newEnd);
			} else {
				endStr = endStr.substring(0, wlength);
			}
			wordText = startStr + etext.substring(start, end + searchWord.length()) + endStr;
		} else {
			if (startStr.length() > wlength) // 剪切 多余的长度文本
				startStr = startStr.substring(startStr.length() - wlength);
			if (endStr.length() > wlength)
				endStr = endStr.substring(0, wlength);
			wordText = startStr + etext.substring(start, end + searchWord.length()) + endStr;
		}
		return wordText;
	}

	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	public static String nvl(Object obj, String other) {
		if (obj == null)
			return other;
		else
			return obj.toString();
	}

	/**
	 * 替换所有变量值
	 * 
	 * @param str
	 *            输入的整个String
	 * @param src
	 *            被替换的string
	 * @param replacement
	 *            替换后的值
	 * @return
	 */
	public static String replaceAll(String str, String src, String replacement) {
		Pattern p = Pattern.compile(src);
		Matcher m = p.matcher(str);
		String result = m.replaceAll(Matcher.quoteReplacement(replacement));
		return result;
	}

	/**
	 * 处理数据库自定义字段转map. 1.每行用\r\n分隔 2.每列用=分隔
	 * 
	 * @param str
	 * @return
	 */
	public static List<Property> toList(String str) {
		List<Property> list = new ArrayList();
		if (str != null) {
			String[] datas = str.split("\r\n");
			for (String data : datas) {
				int index = data.indexOf("=");
				if (index != -1) {
					String fieldKey = data.substring(0, index).trim();
					String fieldValue = data.substring(index + 1).trim();
					Property property = new Property();
					property.setKey(fieldKey);
					property.setValue(fieldValue);
					list.add(property);
				}
			}
		}
		return list;
	}

	public static String generateOrderNumber() {
		return "BB" +System.currentTimeMillis() + (long) (Math.random() * 10000000L);
	}

	public static String[] toArray(String str) {
		if (str != null) {
			String[] datas = str.split("\r\n");
			return datas;
		}
		return null;
	}

	public static String get(List<Property> list, String key) {
		for (Property property : list) {
			if (property.getKey().equals(key)) {
				return property.getValue();
			}
		}
		return null;
	}
}
