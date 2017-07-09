package com.jz.crm.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;

import com.jz.crm.base.config.SystemCode;
import com.jz.crm.base.exception.ApplicationException;

public class ImageUtil {
	/**
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static String getImageStr(String filePath) throws Exception {
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				return null;
			}
			in = new FileInputStream(filePath);
			data = new byte[in.available()];
			in.read(data);
		} catch (Exception e) {
			throw e;
		} finally {
			if (in != null) {
				in.close();
			}
		}
		// 对字节数组Base64编码
		Base64 base64 = new Base64();
		return new String(base64.encode(data));
	}

	/**
	 * 对字节数组字符串进行Base64解码并生成图片
	 * 
	 * @param imgStr
	 * @param imgFilePath
	 * @return
	 * @throws Exception
	 */
	public static void generateImage(String imgStr, String imgFilePath) throws ApplicationException {
		Base64 base64 = new Base64();
		OutputStream out = null;
		try {
			// Base64解码
			byte[] b = base64.decode(new String(imgStr).getBytes());//decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(SystemCode.SYSTEM_EXCEPTION, "generateImage 失败", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					throw new ApplicationException(SystemCode.SYSTEM_EXCEPTION, "generateImage 失败", e);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {

		String strImg = getImageStr("F:\\data\\JZMushroom\\logo\\201612\\logo_201612231450037.png");
		System.out.println(strImg);
//		System.out.println(strImg.length());
//		generateImage(strImg, "C:\\Users\\Administrator\\Desktop\\资料\\资料\\罗爱平1.pdf");
	}
}
