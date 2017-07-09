package com.jz.crm.base.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jz.crm.base.httprequest.HttpRequest;

public class XpathUtil {
	public static final Logger LOG = LoggerFactory.getLogger(XpathUtil.class);

	public static final String XPATH_VALUE_SPLIT = "#";

	/**
	 * 根据url,xpth获取网页某个节点信息
	 * 
	 * @param url
	 * @param xpath
	 * @return
	 * @throws ParserConfigurationException
	 * @throws XPathExpressionException
	 * @throws IOException
	 */
	public static List<String> getContentList(String html, List<String> xpaths) throws ParserConfigurationException,
			XPathExpressionException, IOException {
		List<String> output = new ArrayList();
		String content = null;
		HtmlCleaner hc = new HtmlCleaner();
		TagNode tn = hc.clean(html.replaceAll("&nbsp;", ""));
		Document dom = new DomSerializer(new CleanerProperties()).createDOM(tn);
		for (String xpath : xpaths) {
			XPath xPath = XPathFactory.newInstance().newXPath();
			Object result = xPath.evaluate(xpath, dom, XPathConstants.NODESET);
			if (result instanceof NodeList) {
				NodeList nodeList = (NodeList) result;
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node node = nodeList.item(i);
					content = node.getNodeValue() == null ? node.getTextContent() : node.getNodeValue();
					if (content != null && !"".equals(content)) {
						output.add(content.trim());
					}
				}
			}
		}
		return output;
	}

	/*
	 * 把dom文件转换为xml字符串
	 */
	public static String toStringFromDoc(Document document) {
		String result = null;

		if (document != null) {
			StringWriter strWtr = new StringWriter();
			StreamResult strResult = new StreamResult(strWtr);
			TransformerFactory tfac = TransformerFactory.newInstance();
			try {
				javax.xml.transform.Transformer t = tfac.newTransformer();
				t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				t.setOutputProperty(OutputKeys.INDENT, "yes");
				t.setOutputProperty(OutputKeys.METHOD, "xml"); // xml, html,
				// text
				t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
				t.transform(new DOMSource(document.getDocumentElement()), strResult);
			} catch (Exception e) {
				System.err.println("XML.toString(Document): " + e);
			}
			result = strResult.getWriter().toString();
			try {
				strWtr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * 根据url,xpth获取网页某个节点信息 如果出现+，说明以+分隔后的多个xpath拼接而成的字段值
	 * 
	 * @param url
	 * @param xpath
	 * @return
	 * @throws ParserConfigurationException
	 * @throws XPathExpressionException
	 * @throws IOException
	 */
	public static String getContentInfo(String html, String xpath) throws ParserConfigurationException,
			XPathExpressionException, IOException {
		List<String> xpaths = new ArrayList();
		xpaths.add(xpath);
		return getContentInfo(html, xpaths);
	}

	/**
	 * 根据url,xpth获取网页某个节点信息 如果出现+，说明以+分隔后的多个xpath拼接而成的字段值
	 * 
	 * @param url
	 * @param xpath
	 * @return
	 * @throws ParserConfigurationException
	 * @throws XPathExpressionException
	 * @throws IOException
	 */
	public static String getContentInfo(String html, List<String> xpaths) throws ParserConfigurationException,
			XPathExpressionException, IOException {
		if (html == null || xpaths == null || xpaths.size() == 0) {
			return null;
		}
		String content = null;
		Object result = null;
		HtmlCleaner hc = new HtmlCleaner();
		TagNode tn = hc.clean(html.replaceAll("&nbsp;", ""));
		Document dom = new DomSerializer(new CleanerProperties()).createDOM(tn);
		// LOG.info("===========================================================");
		// LOG.info(toStringFromDoc(dom));
		// LOG.info("=========================================================");
		for (String xpath : xpaths) {
			if (xpath != null && !"".equals(xpath)) {
				// 如果xpath配置html就返回html内容
				if ("html".equals(xpath)) {
					return html;
				}
				StringBuffer output = new StringBuffer();
				String[] toXpaths = xpath.split("\\+");
				for (String toXpath : toXpaths) {
					XPath xPath = XPathFactory.newInstance().newXPath();
					try {
						result = xPath.evaluate(toXpath, dom, XPathConstants.NODESET);
					} catch (Exception e) {
						LOG.error("xPath[" + xpath + "] evaluate error", e);
					}
					if (result instanceof NodeList) {
						NodeList nodeList = (NodeList) result;
						int index = 0;
						for (int i = 0; i < nodeList.getLength(); i++) {
							Node node = nodeList.item(i);
							content = node.getNodeValue() == null ? node.getTextContent() : node.getNodeValue();
							if (content != null && !"".equals(content.trim())) {
								if (index > 0) {
									output.append(XPATH_VALUE_SPLIT);
								}
								output.append(content.trim());
								index++;
							}
						}
					}
				}
				if (output.length() > 0) {
					return output.toString();
				}
			}
		}
		return null;
	}

	/**
	 * 通过正则式过滤出字段值
	 * 
	 * @param fieldValue
	 * @param regex
	 * @return
	 */
	public static String filterField(String fieldValue, String regex) {
		if (fieldValue == null) {
			return null;
		}
		if (regex == null || "".equals(regex)) {
			return fieldValue;
		}
		StringBuffer result = new StringBuffer();
		int index = -1;
		int pos = 0;
		String replace = "";
		index = regex.indexOf("($["); // 表示取第几个匹配到的字符串
		if (index != -1) {
			int end = regex.indexOf(")", index);
			String temp = regex.substring(index + 3, end - 1);
			String posreplace = regex.substring(index, end + 1);
			regex = regex.replace(posreplace, "");
			pos = Integer.parseInt(temp);
		}

		index = regex.indexOf("(replace["); // 表示每个匹配到的字符串都需要进行替换
		if (index != -1) {
			int end = regex.indexOf(")", index);
			replace = regex.substring(index + 9, end - 1);
			String replaceReg = regex.substring(index, end + 1);
			regex = regex.replace(replaceReg, "");

		}
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(fieldValue);
		int res = 1;
		while (matcher.find()) {
			String tempReplace[] = replace.split("##");
			String temp = "";
			String src = matcher.group();
			for (int j = 0; j < tempReplace.length; j++) {
				temp = src = src.replace(tempReplace[j], "");
			}
			result.append(temp);
			if (pos != 0 && pos == res) {
				return temp;
			}
			res++;
		}
		if (result.length() == 0) {
			return null;
		}
		return result.toString();
	}

	public static String readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		StringBuffer src = new StringBuffer();
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				src.append(tempString);
				line++;
			}
			reader.close();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
			}
		}
		return src.toString();
	}

	public static void main(String[] args) {
		XpathUtil test = new XpathUtil();
		// String regContent = test
		// .readFileByLines("C:/Users/Administrator/Desktop/credit_assist.log");

		// String regValue = test.filterField(regContent, "用户(.*?)提交银联认证码成功");
		// System.out.println(regValue);
		// System.exit(0);
		// String value = test
		// .filterField(
		// "if (!window.unieap) {  unieap = {}; } unieap.WEB_APP_NAME = \"/siweb\"; unieap.appPath = \"/siweb/si\"; var roleid = \"402802dc36f160ed0136f160ede90000\"; dojo.require(\"unieap.rpc\"); dojo.require(\"unieap.tree.Tree\");",
		// "roleid = (.*?);(replace[roleid = \"##\";])");
		// System.out.println("value:" + value);
		//
		// System.exit(0);
		Map<String, String> requestProperty = new HashMap();
		requestProperty.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
		requestProperty.put("Accept", "application/json, text/plain, */*");
		requestProperty.put("Accept-Language", "zh-CN,zh;q=0.8");
		requestProperty.put("Accept-Encoding", "gzip, deflate, sdch");
		requestProperty.put("Tyc-From", "normal");
		requestProperty.put("CheckError", "check");
		requestProperty.put("Tyc-Click", "rank=1&keyword=广东同缘堂保健科技有限公司&cname=<em>广东同缘堂保健科技有限公司</em>");
		requestProperty.put("Referer", "http://www.tianyancha.com/company/2334645656");
		String html = HttpRequest.sendHttpRequestProxy(true, "11", "GET", "http://www.11467.com/shenzhen/dir/a.htm",
				null, null, "gb2312", null);

		System.out.println(html);

		try {

			String value = test.getContentInfo(html, ".//*[@id='il']/div[*]/div/div[*]/a");
			String[] outputs = value.split(XpathUtil.XPATH_VALUE_SPLIT);
			for (String output : outputs) {
				// html = HttpRequest.sendHttpRequestProxy(true, "11", "GET",
				// output, null, null, null, requestProperty);
				// String pageUrl = test.getContentInfo(html,
				// ".//*[@class='page_tag Baidu_paging_indicator']/a/@href");
				System.out.println(output);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
