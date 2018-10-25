package htos.common.util;

import org.apache.poi.ss.usermodel.Cell;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * Class Comments:
 * Version:
 * 1.0 创建
 * 1.1
 */
public class POIUtil {

    /**
     * 获取单元格数据内容为字符串类型的数据
     * 
     * @param cell
     * @return
     */
    public static String getStringCellValue(Cell cell) {

        String strCell = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    strCell = cell.getStringCellValue().trim();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                        strCell = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue()).toString();
                    } else {
                        strCell = new DecimalFormat("#.#").format(cell.getNumericCellValue());
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    strCell = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_BLANK:
                    break;
                case Cell.CELL_TYPE_FORMULA:
                	try{
	                	if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)){
	                		strCell = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue()).toString();
	                	}else{
	                		strCell = new DecimalFormat("#.#").format(cell.getNumericCellValue());
	                	}
                	}catch(IllegalStateException e){
                		strCell = String.valueOf(cell.getRichStringCellValue()); 
                	}
                	break;
            }
        }

        return strCell;
    }

    /**
     * 获取单元格数据内容为数字类型的数据
     *
     * @param cell
     * @return
     */
    public static Double getDoubleCellValue(Cell cell) {
        Double d = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    d = Double.valueOf(cell.getStringCellValue().trim());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    d = cell.getNumericCellValue();
                    break;
            }
        }

        return d;
    }

    /**
     * 获取单元格数据内容为数字类型的数据
     *
     * @param cell
     * @return
     */
    public static Integer getIntegerCellValue(Cell cell) {
        Integer i = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    i = Integer.valueOf(cell.getStringCellValue().trim());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    i = Double.valueOf(cell.getNumericCellValue()).intValue();
                    break;
            }
        }

        return i;
    }



    /**
     * 获取单元格数据内容为日期类型的数据
     *
     * @param cell
     * @return
     */
    public static Date getDateCellValue(Cell cell) {
        Date date = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                        date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue());
                    }
            }
        }

        return date;
    }

}
