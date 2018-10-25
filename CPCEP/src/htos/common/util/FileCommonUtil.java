package htos.common.util;

import htos.common.util.swftools.SwfToolsUtil;
import htos.sysfmt.file.action.AdenexaAction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class FileCommonUtil {
	private final static Logger log = Logger.getLogger(FileCommonUtil.class);

	// 文件下载
	public static void dowloadFile(String fileName, String path,
			HttpServletResponse response) {
		InputStream inStream = null;
		try {
			inStream = new FileInputStream(path);
			// String name = fileName+path.substring(path.lastIndexOf("."));
			fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
			// 设置输出格式
			response.reset();
			response.setContentType("bin");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ fileName);
			// 循环输出流数据
			byte[] b = new byte[100];
			int len;
			while ((len = inStream.read(b)) > 0)
				response.getOutputStream().write(b, 0, len);

			// 关闭流
			inStream.close();
			response.getOutputStream().close();
			log.info("==============dowloadFile下载成功===============");
		} catch (Exception e) {
			log.info("==============dowloadFile下载失败===============", e);
			inStream = null;
			//e.printStackTrace();
		}
	}

	// 文件上传（返回文件的大小M）
	public static String getUpload(String path, String filenameNew,
			File temppath) {
		String msize = "";
		try {
			File file = new File(path);
			if (!file.exists())
				file.mkdirs();// 创建文件目录

			file = new File(path, filenameNew);
			if (!file.exists())
				file.createNewFile();// 创建文件

			// 往相应目录写文件
			InputStream is = new FileInputStream(temppath);
			OutputStream os = new FileOutputStream(file);
			int size = is.available();// 文件字节数
			int sizeM = size / 1024;
			msize = String.valueOf(sizeM);
			byte[] bytefer = new byte[size];
			int length = 0;
			while ((length = is.read(bytefer)) != -1) {
				os.write(bytefer, 0, length);
			}
			os.close();
			is.close();

			// 生成pdf和swf文件
			SwfToolsUtil.convert2SWF(path + "/" + filenameNew);
			log.info("==============getUpload上传成功===============");
		} catch (Exception e) {
			log.info("==============getUpload上传失败===============", e);
			e.printStackTrace();
		}
		return msize;
	}

	// 删除文件
	public static void getDelete(String path) {
		File file = new File(path);
		if (file.exists())
			file.delete();// 删除原文件
		String pdfFile = FileUtilBetas.getFilePrefix2(path) + ".pdf";
		File file2 = new File(pdfFile);
		if (file2.exists())
			file2.delete();// 删除pdf文件
		String swfFile = FileUtilBetas.getFilePrefix2(path) + ".swf";
		File file3 = new File(swfFile);
		if (file3.exists())
			file3.delete();// 删除swf文件
		log.info("==============getDelete删除附件成功===============");
	}
}
