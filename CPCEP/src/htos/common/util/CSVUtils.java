package htos.common.util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**   
 * CSV操作(导出和导入)
 * @author zcl
 * @version 1.0 Jan 27, 2014 4:30:58 PM   
 */
public class CSVUtils {
	private static final Logger log = Logger.getLogger(CSVUtils.class);
    /**
     * 导出
     * @param file csv文件(路径+文件名)，csv文件不存在会自动创建
     * @param dataList 数据
     * @return
     */
    public static boolean exportCsv(File file, List<String> dataList){
        boolean isSucess=false;
        
        FileOutputStream out=null;
        OutputStreamWriter osw=null;
        BufferedWriter bw=null;
        try {
            out = new FileOutputStream(file);
            osw = new OutputStreamWriter(out,"GBK");
            bw =new BufferedWriter(osw);
            if(dataList!=null && !dataList.isEmpty()){
                for(String data : dataList){
                    bw.append(data).append("\r");
                }
            }
            isSucess=true;
            log.info("===================cvs数据exportCsv导出正常=================");
        } catch (Exception e) {
            isSucess=false;
            log.error("===================cvs数据exportCsv导出异常=================",e);
        }finally{
            if(bw!=null){
                try {
                    bw.close();
                    bw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(osw!=null){
                try {
                    osw.close();
                    osw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(out!=null){
                try {
                    out.close();
                    out=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        }
        
        return isSucess;
    }
    
    /**
     * 导入
     * 
     * @param file csv文件(路径+文件)
     * @return
     */
    public static List<String> importCsv(File file){
        List<String> dataList=new ArrayList<String>();
        BufferedReader br=null;
        try { 
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GBK"));
            String line = ""; 
            while ((line = br.readLine()) != null) {
                dataList.add(line);
            }
            log.info("===================cvs数据importCsv导入正常=================");
        }catch (Exception e) {
        	log.error("===================cvs数据importCsv导入异常=================",e);
        	e.printStackTrace();
        }finally{
            if(br!=null){
                try {
                    br.close();
                    br=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataList;
    }
    
    public static void main(String[] args) {
            List<String> dataList=CSVUtils.importCsv(new File("C:\\Users\\zcl\\Desktop\\组织机构模版.csv"));
            if(dataList!=null && !dataList.isEmpty()){
                for(String data : dataList){
                	System.out.println("11");
                    System.out.println(data);
                }
            }
	}
}
