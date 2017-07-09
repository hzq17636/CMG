package com.jz.crm.base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class XmlUtil<T> {

	/**
	 * 反射成对象
	 * 
	 * @param inputStream
	 * @param cls
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	public static Object unmarshal(InputStream inputStream, Class cls)
			throws JAXBException, IOException {
		JAXBContext jaxbContext = JAXBContext.newInstance(cls);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return jaxbUnmarshaller.unmarshal(inputStream);
	}

	/**
	 * 返回xml数据
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	static String inputStream2String(InputStream is) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		return buffer.toString();
	}

	public static Map<?, ?> xmlToMap(String xml) throws DocumentException {
		Document document = DocumentHelper.parseText(xml);
		return domToMap(document);
	}

	/**
	 * 根据dom来生成map
	 * 
	 * @param doc
	 * @return
	 */
	public static Map<String, Object> domToMap(Document doc) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (doc == null)
			return map;
		Element root = doc.getRootElement();
		List list = root.elements();
		if (list.size() > 0) {
			map.put(root.getName(), Dom2Map(root));
		} else {
			map.put(root.getName(), root.getText());
		}
		// for (Iterator iterator = root.elementIterator(); iterator.hasNext();)
		// {
		// Element e = (Element) iterator.next();
		// }
		return map;
	}

	public static Map Dom2Map(Element e) {
		Map map = new HashMap();
		List list = e.elements();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				List mapList = new ArrayList();

				if (iter.elements().size() > 0) {
					Map m = Dom2Map(iter);
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(m);
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), m);
				} else {
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(iter.getText());
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(iter.getText());
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), iter.getText());
				}
			}
		} else
			map.put(e.getName(), e.getText());
		return map;
	}

	public static void main(String[] args) throws DocumentException {
		String xml = "<?xml version='1.0' encoding='UTF-8'?><NewDataSet><Table><个人编号>1000763614</个人编号></Table></NewDataSet>";
		Map<?,?> fieldMap = XmlUtil.xmlToMap(xml);
		String value = MapUtil.getValueByMap(fieldMap, "NewDataSet.Table.个人编号", -1);
		System.out.println(value);
	}

}
