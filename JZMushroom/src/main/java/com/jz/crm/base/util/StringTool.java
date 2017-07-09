package com.jz.crm.base.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class StringTool {
	/**
	 * 获得相对webapp的路径 返回的path末尾不带 \ 或者 /
	 * 
	 * @param context
	 * @return
	 */
	public static String getRealPath(ServletContext context, String path) {
		if (StringUtils.isEmpty(path)) {
			path = "";
		}
		String realPath = context.getRealPath("/");
		realPath = getPathNoEndSeparator(realPath);
		path = getPathNoEndSeparator(path);
		return realPath.concat(File.separator).concat(path);
	}

	/**
	 * 删除路径的首路径分隔符
	 * 
	 * @param context
	 * @param path
	 * @return
	 */
	public static String getPathNoBeginSeparator(String path) {
		if (StringUtils.isEmpty(path)) {
			return path;
		}
		if (path.length() >= 1 && (path.charAt(0) == WINDOWS_SEPARATOR || path.charAt(0) == UNIX_SEPARATOR)) {
			path = path.substring(1, path.length());
		}
		return path;
	}

	/**
	 * 删除路径的尾路径分隔符
	 * 
	 * @param context
	 * @param path
	 * @return
	 */
	public static String getPathNoEndSeparator(String path) {
		if (StringUtils.isEmpty(path)) {
			return path;
		}
		if (path.length() >= 1
				&& (path.charAt(path.length() - 1) == WINDOWS_SEPARATOR || path.charAt(path.length() - 1) == UNIX_SEPARATOR)) {
			path = path.substring(0, path.length() - 1);
		}
		return path;
	}

	/**
	 * 移除路径两端的路径分隔符
	 * 
	 * @param context
	 * @param path
	 * @return
	 */
	public static String getPathNoSeparator(String path) {
		path = getPathNoBeginSeparator(path);
		path = getPathNoEndSeparator(path);
		return path;
	}

	/**
	 * 拼接路径以/分隔 [a/b, c] > a/b/c [a/b, /c] > a/b/c [/a/b, c/] > a/b/c
	 * 
	 * @param context
	 * @param path
	 * @return
	 */
	public static String getPath(String... paths) {
		StringBuilder sb = new StringBuilder(20);
		for (String each : paths) {
			if (StringUtils.isNotEmpty(each)) {
				sb.append(getPathNoSeparator(each)).append("/");
			}
		}
		if (sb.length() >= 1) {
			return sb.delete(sb.length() - 1, sb.length()).toString();
		}
		return null;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String[] splitByBreakingWhitespace(String str) {
		if (str == null)
			return null;
		int strLen = str.length();
		if (strLen == 0)
			return ArrayUtils.EMPTY_STRING_ARRAY;
		StringTokenizer whitespaceStripper = new StringTokenizer(str);
		List<String> answer = new ArrayList<String>();
		while (whitespaceStripper.hasMoreTokens()) {
			answer.add(whitespaceStripper.nextToken());
		}
		if (answer.isEmpty())
			return ArrayUtils.EMPTY_STRING_ARRAY;
		else
			return (String[]) answer.toArray(new String[answer.size()]);

	}

	/**
	 * 把Map所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String convertMaptoUrl(Map<String, String> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = params.get(key);
			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}
		return prestr;
	}

	/**
	 * The Unix separator character.
	 */
	private static final char UNIX_SEPARATOR = '/';
	/**
	 * The Windows separator character.
	 */
	private static final char WINDOWS_SEPARATOR = '\\';

	public static void main(String[] args) {
		System.out.println(StringTool.getPathNoBeginSeparator("/abc/ccc").equals("abc/ccc"));
		System.out.println(StringTool.getPathNoBeginSeparator("c:\\abc\\ccc").equals("c:\\abc\\ccc"));
		System.out.println(StringTool.getPathNoEndSeparator("/abc/ccc/").equals("/abc/ccc"));
		System.out.println(StringTool.getPathNoSeparator("/abc/ccc/").equals("abc/ccc"));
		System.out.println(StringTool.getPath("/abc", "/c", "c", ".", "png"));
	}
}
