package com.jz.crm.base.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jz.crm.base.config.JZMushroom;

public class JsUtil {
	public static final Logger LOG = LoggerFactory.getLogger(JsUtil.class);
	public static final String JS_CONTENT = "jsContent";

	/**
	 * 替换jsScript变量，执行脚本
	 * 
	 * @param jsScript
	 * @param parameterMap
	 * @return value
	 * @throws Exception
	 */
	public static String getJsContentInfo(Map<?, ?> parameterMap, String jsScript) throws Exception {
		if (parameterMap != null) {
			Set s = parameterMap.entrySet();
			Iterator iterator = s.iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
				String replaceValue = "\\$\\{" + entry.getKey() + "\\}";
				try {
					jsScript = StringUtil.replaceAll(jsScript, replaceValue, (String) entry.getValue());
				} catch (Exception e) {
					LOG.warn("Replace All var failure : " + e.getMessage());
				}
			}
		}

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");
		if (LOG.isDebugEnabled()) {
			LOG.debug("运行js内容 : " + jsScript);
		}
		Object result = engine.eval(jsScript);
		if (result == null) {
			return null;
		}
		return result.toString();
	}

	/**
	 * 通过field中的js 函授 来获取数据
	 * 
	 * @param fieldMap
	 *            ，field
	 * @return value
	 * @throws Exception
	 */
	public static String getJsFunContentInfo(Map<?, ?> parameterMap, String jsFuncation) throws Exception {

		String[] item = jsFuncation.split(";");
		if (item.length < 3) {
			return "";
		}

		String funcationName = item[1].trim();
		String parmentContent = item[2].trim();
		String[] parameterOfJS = parmentContent.split(",");
		for (int i = 0; i < parameterOfJS.length; i++) {
			if (parameterMap != null) {
				Set s = parameterMap.entrySet();
				Iterator iterator = s.iterator();
				while (iterator.hasNext()) {
					Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
					try {
						if (entry.getValue() == null) {
							continue;
						}
						String replaceValue = "\\$\\{" + entry.getKey() + "\\}";
						parameterOfJS[i] = StringUtil.replaceAll(parameterOfJS[i], replaceValue,
								(String) entry.getValue());
					} catch (Exception e) {
						String replaceValue = "${" + entry.getKey() + "}";
						if (parameterOfJS[i] != null && replaceValue.equals(parameterOfJS[i].trim())) {
							parameterOfJS[i] = (String) entry.getValue();
						}
						LOG.warn("Replace All var failure : " + e.getMessage());
					}
				}
			}
		}

		// 通过反射技术来实现多个参数传入执行js方法
		Class[] nameOfParameter = new Class[2];
		nameOfParameter[0] = String.class;
		nameOfParameter[1] = Object[].class;
		Object[] objectOfParameter = new Object[2];
		objectOfParameter[0] = funcationName;
		objectOfParameter[1] = parameterOfJS;
		ScriptEngineManager manager = new ScriptEngineManager();
		Object object = manager.getEngineByName("javascript");
		if (object instanceof ScriptEngine) {
			ScriptEngine engine = (ScriptEngine) object;
			String jsContent = "";
			// 处理从html获取的js脚本内容要和js文件内容合并为需要执行的 js脚本
			// 如果在参数map中有脚本内容就需要从参数map里获取脚本然后拼接在一起。
			if (parameterMap != null && parameterMap.get(JS_CONTENT) != null) {
				jsContent = (String) parameterMap.get(JS_CONTENT);
			}
			String[] jsFiles = item[0].trim().split("&");
			for (String jsFile : jsFiles) {
				String fileContent = JZMushroom.getJsContent(jsFile);
				if (fileContent != null) {
					jsContent += fileContent;
				}
			}
			if ("".equals(jsContent)) {
				return null;
			}
			engine.eval(jsContent);
		}
		Class<?> classType = Class.forName("javax.script.Invocable");
		Method addMethod = classType.getMethod("invokeFunction", nameOfParameter);
		if (LOG.isDebugEnabled()) {
			LOG.info("运行js脚本 : " + JsonUtil.objectToJson(objectOfParameter));
		}

		Object result = addMethod.invoke(object, objectOfParameter);
		if (result == null) {
			return null;
		}
		return result.toString();
	}

	public static String operatorExpression(String expression) throws Exception {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");
		return engine.eval(expression).toString();
	}
}
