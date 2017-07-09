package com.jz.crm.base.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class FileUtil {

	/**
	 * 获取指定的路径 的所有文件名
	 * 
	 * @param file
	 * @return
	 */
	public static List<String> findFile(File file) {
		List<String> result = new ArrayList();
		String[] arr = file.list();
		for (String fn : arr) {
			File f2 = new File(file.getAbsolutePath(), fn);
			if (!f2.isDirectory()) {
				result.add(fn);
			}
		}
		Collections.reverse(result);
		return result;
	}

	/**
	 * 获取目录所有文件路径
	 * 
	 * @param dirName
	 * @return
	 * @throws Exception
	 */
	public static List<String> getAllFile(String dirName) throws Exception {
		List<String> list = new ArrayList();
		Vector<String> ver = new Vector<String>();
		ver.add(dirName);
		while (ver.size() > 0) {
			File[] files = new File(ver.get(0).toString()).listFiles(); // 获取该文件夹下所有的文件(夹)名
			ver.remove(0);
			int len = files.length;
			for (int i = 0; i < len; i++) {
				String outputFile = files[i].getAbsolutePath();
				if (files[i].isDirectory()) {
					ver.add(outputFile);
				} else {
					list.add(outputFile);
				}
			}
		}
		return list;
	}

	public static String readFileByLines(String fileName, String coding) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), coding);
			reader = new BufferedReader(isr);
			String tempString = null;
			StringBuffer line = new StringBuffer();
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				line.append(tempString + "\n");
			}
			return line.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return null;
	}

	public static Map<String, String> readFileToMap(String fileName, String coding, String split) {
		File file = new File(fileName);
		BufferedReader reader = null;
		Map<String, String> mapCon = new HashMap<String, String>();
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), coding);
			reader = new BufferedReader(isr);
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				String[] ss = tempString.split(split);
				if (ss.length >= 2) {
					mapCon.put(ss[0], ss[1]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return mapCon;
	}

	public static void writeLines(String[] strs, String filePath, boolean append) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			int length = strs.length;
			if (strs != null && length > 0) {
				FileWriter fileWriter = new FileWriter(file.getPath(), append); // true
																				// =
																				// append
																				// file
				BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
				for (int i = 0; i < length; i++) {
					bufferWriter.write(strs[i] + "\n");
					if (i % 500 == 0)
						bufferWriter.flush();
				}
				bufferWriter.flush();
				bufferWriter.close();
				fileWriter.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		map = FileUtil.readFileToMap("C:\\Users\\admin\\Desktop\\b.txt", "GBK", ":");
		System.out.println(map.size());

		System.exit(0);

		String tempString = null;
		BufferedReader reader = null;
		File file2 = new File("C:\\Users\\Administrator\\Desktop\\b.txt");
		reader = new BufferedReader(new FileReader(file2));
		while ((tempString = reader.readLine()) != null) {
			int begin = -1, end = -1;
			begin = tempString.lastIndexOf("	");
			if (begin != -1) {
				String temp = tempString.substring(begin + 1, tempString.length());
				map.put(temp, "");
			}
		}

		System.out.println(map.size());

		File file1 = new File("C:\\Users\\Administrator\\Desktop\\c.txt");

		reader = new BufferedReader(new FileReader(file1));

		int line = 0;
		while ((tempString = reader.readLine()) != null) {
			line++;
			int begin = -1, end = -1;
			begin = tempString.lastIndexOf("	");
			if (begin != -1) {
				String temp = tempString.substring(begin + 1, tempString.length());
				map.put(temp, "");
				if (map.containsKey(temp)) {
					// System.out.println(temp);
				}
			}
		}

		System.out.println(map.size());

		File file3 = new File("C:\\Users\\Administrator\\Desktop\\a.txt");

		reader = new BufferedReader(new FileReader(file3));

		while ((tempString = reader.readLine()) != null) {
			int begin = -1, end = -1;
			begin = tempString.indexOf("	\"");
			if (begin != -1) {
				String temp = tempString.substring(begin + 2, tempString.length() - 1);
				if (!map.containsKey(temp)) {
					System.out.println(tempString);
				}
			}
		}
	}
}
