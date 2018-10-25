package htos.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
/**
 * 数据excel导出
 * @author zcl
 *
 * @param <T>
 */
public class ExportExcel<T> {

	private static final Logger logger = Logger.getLogger(ExportExcel.class);

	public void exportExcel(String title, Collection<T> dataset,
			String[] headers, OutputStream out) {
		exportExcel(title, headers, dataset, out, "yyyy-MM-dd");
	}

	/**
	 * 导出Excel文件
	 * 
	 * @param title
	 * @param dataset
	 * @param headers
	 * @param excelFile
	 * @throws IOException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 */
	public void exportExcel(String title, Collection<T> dataset,
			String[] headers, String excelFile) throws IOException,
			SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		exportExcel(title, headers, dataset, excelFile, "yyyy-MM-dd");
	}

	/**
	 * 
	 * @param title
	 *            表格标题
	 * 
	 * @param headers
	 *            表格属性列名数组
	 * @param dataset
	 *            javabean数据集合
	 * @param out
	 *            流对象
	 * @param pattern
	 *            时间格式
	 */
	public void exportExcel(String title, String[] headers,
			Collection<T> dataset, OutputStream out, String pattern) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
				0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new HSSFRichTextString("相关信息！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("..");

		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		Iterator<T> it = dataset.iterator();
		int index = 0;

		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			T t = (T) it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();
			for (short i = 0; i < fields.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style2);
				Field field = fields[i];
				String fieldName = field.getName();
				String getMethodName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				try {
					Class tCls = t.getClass();
					Method getMethod = tCls.getMethod(getMethodName,
							new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					// 判断值的类型后进行强制类型转换
					String textValue = null;
					// if (value instanceof Integer) {
					// int intValue = (Integer) value;
					// cell.setCellValue(intValue);
					// } else if (value instanceof Float) {
					// float fValue = (Float) value;
					// textValue = new HSSFRichTextString(
					// String.valueOf(fValue));
					// cell.setCellValue(textValue);
					// } else if (value instanceof Double) {
					// double dValue = (Double) value;
					// textValue = new HSSFRichTextString(
					// String.valueOf(dValue));
					// cell.setCellValue(textValue);
					// } else if (value instanceof Long) {
					// long longValue = (Long) value;
					// cell.setCellValue(longValue);
					// }
					if (value instanceof Boolean) {
						boolean bValue = (Boolean) value;
						textValue = "男";
						if (!bValue) {
							textValue = "女";
						}
					} else if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else if (value instanceof byte[]) {
						// 有图片时，设置行高为60px;
						row.setHeightInPoints(60);
						// 设置图片所在列宽度为80px,注意这里单位的一个换算
						sheet.setColumnWidth(i, (short) (35.7 * 80));
						// sheet.autoSizeColumn(i);
						byte[] bsValue = (byte[]) value;
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
								1023, 255, (short) 6, index, (short) 6, index);
						anchor.setAnchorType(2);
						patriarch.createPicture(anchor, workbook.addPicture(
								bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
					} else {
						// 其它数据类型都当作字符串简单处理
						if (value != null) {

							textValue = value.toString();
						}
					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							HSSFRichTextString richString = new HSSFRichTextString(
									textValue);
							HSSFFont font3 = workbook.createFont();
							font3.setColor(HSSFColor.BLUE.index);
							richString.applyFont(font3);
							cell.setCellValue(richString);
						}
					}
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					// 清理资源
				}
			}

		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void exportExcelByArray(String title, String[] headers,
			Collection<T> dataset, OutputStream out, String pattern) throws Exception {

		try {
			// 声明一个工作薄
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 生成一个表格
			HSSFSheet sheet = workbook.createSheet(title);
			// 设置表格默认列宽度为15个字节
			// sheet.setDefaultColumnWidth((short) 15);
			// // 生成一个样式
			HSSFCellStyle style = workbook.createCellStyle();
			// 设置这些样式
			style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 生成一个字体
			HSSFFont font = workbook.createFont();
			font.setColor(HSSFColor.VIOLET.index);
			font.setFontHeightInPoints((short) 12);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			// 把字体应用到当前的样式
			style.setFont(font);
			// 生成并设置另一个样式
			HSSFCellStyle style2 = null;// workbook.createCellStyle();
			HSSFPatriarch patriarch = null;// sheet.createDrawingPatriarch();

			// 产生表格标题行
			this.createTitleRow(sheet, style, headers);

			Iterator<T> it = dataset.iterator();
			int index = 0;
			int sheetIdx = 0;
			while (it.hasNext()) {
				index++;
				if (index % 65000 == 0) {
					sheetIdx++;
					sheet = workbook.createSheet(title + sheetIdx);
					this.createTitleRow(sheet, style, headers);
					index = 1;
				}
				this.createContentRow(index, sheet, it, style2, patriarch,
						workbook, pattern);
			}
			// 生成Excel文件
			workbook.write(out);
		} catch (IOException e) {
			throw e;
		} 

	}

	
	
	private HSSFWorkbook getHSSFWorkbook(String  workbook) throws IOException{
		File file = new File(workbook);
		boolean bool = file.exists();
		if(bool){
			FileInputStream is = new FileInputStream(file);
			return new HSSFWorkbook(is);
		}
		return new HSSFWorkbook();
	}
	
	
	
	/**
	 * 
	 * @param title
	 *            表格标题
	 * 
	 * @param headers
	 *            表格属性列名数组
	 * @param dataset
	 *            javabean数据集合
	 * @param excelFile
	 *            Excel文件
	 * @param pattern
	 *            时间格式
	 * @throws IOException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 */
	public void exportExcel(String title, String[] headers,
			Collection<T> dataset, String excelFile, String pattern)
			throws IOException, SecurityException, IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		logger.debug("exportExcel() start");
		FileOutputStream fout = null;
		try {
			// 声明一个工作薄
			HSSFWorkbook workbook = this.getHSSFWorkbook(excelFile);
			// 生成一个表格
			HSSFSheet sheet = workbook.createSheet(title);
			// 设置表格默认列宽度为15个字节
			// sheet.setDefaultColumnWidth((short) 15);
			// // 生成一个样式
			HSSFCellStyle style = workbook.createCellStyle();
			// 设置这些样式
			style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 生成一个字体
			HSSFFont font = workbook.createFont();
			font.setColor(HSSFColor.VIOLET.index);
			font.setFontHeightInPoints((short) 12);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			// 把字体应用到当前的样式
			style.setFont(font);
			// 生成并设置另一个样式
			HSSFCellStyle style2 = null;// workbook.createCellStyle();
			// style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
			// style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			// style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			// style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			// style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
			// style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
			// style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			// // 生成另一个字体
			// HSSFFont font2 = workbook.createFont();
			// font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			// // 把字体应用到当前的样式
			// style2.setFont(font2);
			//
			// // 声明一个画图的顶级管理器
			HSSFPatriarch patriarch = null;// sheet.createDrawingPatriarch();
			// // 定义注释的大小和位置,详见文档
			// HSSFComment comment = patriarch.createComment(new
			// HSSFClientAnchor(0,
			// 0, 0, 0, (short) 4, 2, (short) 6, 5));
			// // 设置注释内容
			// comment.setString(new HSSFRichTextString("相关信息！"));
			// // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
			// comment.setAuthor("zmq");

			logger.debug("exportExcel().writeExcel start");
			// 产生表格标题行
			this.createTitleRow(sheet, style, headers);

			Iterator<T> it = dataset.iterator();
			int index = 0;
			int sheetIdx = 0;
			while (it.hasNext()) {
				index++;
				if (index % 65000 == 0) {
					sheetIdx++;
					sheet = workbook.createSheet(title + sheetIdx);
					this.createTitleRow(sheet, style, headers);
					index = 1;
				}
				this.createContentRow(index, sheet, it, style2, patriarch,
						workbook, pattern);
			}
			logger.debug("exportExcel().writeExcel end");
			logger.debug("exportExcel().createExcelFile start");
			// 生成Excel文件
			fout = new FileOutputStream(excelFile);
			workbook.write(fout);
			logger.debug("exportExcel().createExcelFile end");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("exportExcel() error:", e);
			throw e;
		} finally {
			if (fout != null) {
				fout.close();
			}
		}
		logger.debug("exportExcel() end"); 
	}

	/**
	 * 填充单元格值
	 * 
	 * @param value
	 * @param row
	 * @param i
	 * @param sheet
	 * @param index
	 * @param patriarch
	 * @param workbook
	 * @param pattern
	 * @param cell
	 */
	private void setCellValue(Object value, HSSFRow row, int i,
			HSSFSheet sheet, int index, HSSFPatriarch patriarch,
			HSSFWorkbook workbook, String pattern, HSSFCell cell) {
		String textValue = null;
		HSSFRichTextString richString = null;
		HSSFFont font3 = null;
		if (value instanceof Boolean) {
			boolean bValue = (Boolean) value;
			textValue = "男";
			if (!bValue) {
				textValue = "女";
			}
		} else if (value instanceof Date) {
			Date date = (Date) value;
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			textValue = sdf.format(date);
		} else if (value instanceof byte[]) {
			// 有图片时，设置行高为60px;
			row.setHeightInPoints(60);
			// 设置图片所在列宽度为80px,注意这里单位的一个换算
			sheet.setColumnWidth(i, (short) (35.7 * 80));
			// sheet.autoSizeColumn(i);
			// byte[] bsValue = (byte[]) value;
			// HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255,
			// (short) 6, index, (short) 6, index);
			// anchor.setAnchorType(2);
			// patriarch.createPicture(anchor, workbook.addPicture(bsValue,
			// HSSFWorkbook.PICTURE_TYPE_JPEG));
		} else {
			// 其它数据类型都当作字符串简单处理
			if (value != null) {
				textValue = value.toString();
			}
		}
		// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
		if (textValue != null) {
			Pattern p = Pattern.compile("^//d+(//.//d+)?$");
			Matcher matcher = p.matcher(textValue);
			if (matcher.matches()) {
				// 是数字当作double处理
				cell.setCellValue(Double.parseDouble(textValue));
			} else {
				richString = new HSSFRichTextString(textValue);
				// if (i == 0)
				// font3 = workbook.createFont();
				// else
				// font3 = workbook.getFontAt((short) 0);
				// font3.setColor(HSSFColor.BLUE.index);
				// richString.applyFont(font3);
				cell.setCellValue(richString);
			}
		}
	}

	/**
	 * 生成标题
	 * 
	 * @param sheet
	 * @param style
	 * @param headers
	 */
	private void createTitleRow(HSSFSheet sheet, HSSFCellStyle style,
			String[] headers) {
		HSSFRow row = sheet.createRow(0);
		int len = headers.length;
		HSSFRichTextString text = null;
		HSSFCell cell = null;
		for (int i = 0; i < len; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(style);
			text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
	}

	/**
	 * 生成内容（一行）
	 * 
	 * @param index
	 * @param sheet
	 * @param it
	 * @param style2
	 * @param patriarch
	 * @param workbook
	 * @param pattern
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private void createContentRow(int index, HSSFSheet sheet, Iterator<T> it,
			HSSFCellStyle style2, HSSFPatriarch patriarch,
			HSSFWorkbook workbook, String pattern) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		HSSFRow row = sheet.createRow(index);
		T t = (T) it.next();
		if (t.getClass().isArray()) {
			HSSFCell cell = null;
			Object s = null;
			// 返回数据类型为数组
			Object strArray[] = ((Object[]) t);
			int len = strArray.length;
			for (int i = 0; i < len; i++) {
				cell = row.createCell(i);
				// cell.setCellStyle(style2);
				s = strArray[i];
				this.setCellValue(s, row, i, sheet, index, patriarch, workbook,
						pattern, cell);
			}
		} else {
			// 返回数据类型为其他类型
			HSSFCell cell = null;
			Field field = null;
			Object value = null;
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();
			int len = fields.length;
			for (int i = 0; i < len; i++) {
				cell = row.createCell(i);
				// cell.setCellStyle(style2);
				field = fields[i];
				String fieldName = field.getName();
				String getMethodName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				Class tCls = t.getClass();
				Method getMethod = tCls
						.getMethod(getMethodName, new Class[] {});
				value = getMethod.invoke(t, new Object[] {});
				// 判断值的类型后进行强制类型转换
				this.setCellValue(value, row, i, sheet, index, patriarch,
						workbook, pattern, cell);
			}
		}
	}
}