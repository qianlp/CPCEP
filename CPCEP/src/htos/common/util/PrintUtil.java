package htos.common.util;

import htos.coresys.exception.ActionException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

/**
 * 模板打印
 * @author zcl
 */
public class PrintUtil {
	//向word模板传递值
	public static String printDoc(String filePath,Map<String, String> map) throws ActionException {
		File destFile = new File(filePath.substring(0,
				filePath.lastIndexOf(File.separator) + 1)
				+ DateUtil.DateConversion14String(new Date())
				+ ".doc");
		File srcFile = new File(filePath);
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(srcFile);
			HWPFDocument srcHdt = new HWPFDocument(in);
			Range range = srcHdt.getRange();
			for (Entry<String, String> entry : map.entrySet()) {
				range.replaceText(entry.getKey(), entry.getValue());
			}
			out = new FileOutputStream(destFile);
			srcHdt.write(out);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ActionException("print file error", e);
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new ActionException("close print file i/o stream error.", e);
			}
		}
		return destFile.getName();
	}
}
