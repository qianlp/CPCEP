package htos.common.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
/**
 * excel导入
 * 导入版本excel2007或者以上的xlsx后缀名
 * @author zcl
 */
public class ImportExcelUtil {
	public static List<Map<Integer,String>> imExcel(File file){
		List<Map<Integer,String>> list = new ArrayList<Map<Integer,String>>();
		try {
			InputStream is = new FileInputStream(file);
			//XSSFWorkbook work = new XSSFWorkbook(is);
			//XSSFSheet sheet = work.getSheetAt(0);
			//因excel2003不能兼容，所以加上这些内容
			Workbook workbook = WorkbookFactory.create(is);
			Sheet sheet = workbook.getSheetAt(0);  //示意访问sheet
			int rows = sheet.getLastRowNum();//取得行值
			for (int i = 1; i <= rows; i++) {
			     Row row = sheet.getRow(i);
				int cellNum = row.getLastCellNum();// 列 
				Map<Integer, String> map = new HashMap<Integer, String>(2);
				for (int j = 0; j < cellNum; j++) {
					map.put(j, POIUtil.getStringCellValue(row.getCell(j)));
				}
				list.add(map);
			}
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	 /**    
	  * 获取单元格数据内容为字符串类型的数据     *     
	  * @param cell Excel单元格  
	  * @return String 单元格数据内容     */    
	private static String getStringCellValue(XSSFCell cell) {      
		String strCell = "";    
		switch (cell.getCellType()) {     
		case XSSFCell.CELL_TYPE_STRING:       
			strCell = cell.getStringCellValue();     
			break;
		case XSSFCell.CELL_TYPE_NUMERIC:     
			strCell = String.valueOf(cell.getNumericCellValue());
			strCell = strCell.substring(0,strCell.indexOf("."));
			break;
		case XSSFCell.CELL_TYPE_BOOLEAN: 
			strCell = String.valueOf(cell.getBooleanCellValue());    
			break;
		default:        
			strCell = "";    
			break;     
		}     
		if (strCell.equals("") || strCell == null) {        
			strCell= "";    
		}       
		if (cell == null) {
			strCell= "";
		}       
		return strCell;  
	}
}
