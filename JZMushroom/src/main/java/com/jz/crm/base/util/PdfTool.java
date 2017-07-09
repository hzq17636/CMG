package com.jz.crm.base.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jz.crm.base.config.JZMushroom;

/**
 * 生成PDF，通过freemarker模板
 * 
 * @author zf
 */
public class PdfTool {
	public static final Logger LOG = LoggerFactory.getLogger(PdfTool.class);

	/**
	 * 使用第三方工具wkhtmltopdf 生成pdf
	 * 
	 * @param url
	 *            html的url地址
	 * @param pdfPath
	 *            pdf生成路径
	 * @throws Exception
	 */
	public static void executePdf(String url, String pdfPath) throws Exception {
		String command = "wkhtmltopdf " + url + " " + pdfPath;
		String system = System.getProperty("os.name");
		if (system.indexOf("Windows") != -1) {
			command = JZMushroom.getInstance().get("wkhtmltopdf.dir") + "bin//wkhtmltopdf.exe " + url + " " + pdfPath;
		}
		Process process = null;
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			LOG.info("开始PDF生成 ：" + command);
			process = Runtime.getRuntime().exec(command);
			is = process.getErrorStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				LOG.info(line);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (is != null) {
				is.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (br != null) {
				br.close();
			}
		}
		LOG.info("完成PDF生成 ");
	}

	public static void main(String[] args) throws Exception {
		PdfTool.executePdf("http://www.baidu.com", "F:/test/test.pdf");
	}

}