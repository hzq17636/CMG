package com.jz.crm.base.filesystem;

import java.io.File;
import java.util.Date;

import com.jz.crm.base.exception.ApplicationException;
import com.jz.crm.base.util.DateTimeUtil;
import com.jz.crm.base.util.ImageUtil;
/**
 * 文件系统核心接口
 * @author Terry
 *
 */
public class FileCore {

	/**
	 * 生成文件到文件系统中，并返回数据库存放的路径
	 * 
	 * @param fileRootPath
	 *            文件根目录
	 * @param fileName
	 *            文件名称
	 * @param fileContent
	 *            文件内容，base64加密
	 * @return
	 * @throws ApplicationException
	 */
	public static String generateFile(String fileRootPath, String fileName, String fileContent)
			throws ApplicationException {
		String dateFile = DateTimeUtil.dateToString(new Date(), "yyyyMM");
		String fileDirStr = fileRootPath + dateFile;
		File fileDir = new File(fileDirStr);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}

		ImageUtil.generateImage(fileContent, fileDirStr + "/" + fileName);
		return dateFile + "/" + fileName;
	}

	/**
	 * 删除文件 
	 * @param filePath
	 */
	public static void deleteFile(String filePath) {
		new File(filePath).deleteOnExit();
	}
	
	/**
	 * 返回file base64 加密串
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static String getFileContent(String filePath) throws Exception {
		return ImageUtil.getImageStr(filePath);
	}

}
