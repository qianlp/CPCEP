package htos.coresys.action;

import htos.common.util.ImportExcelUtil;
import htos.coresys.service.ImportExcelService;
import htos.coresys.util.CommonUtil;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
/**
 * excel数据导入
 * @author zcl
 */
public class ImportExcelAction extends ActionSupport {
	private static final Logger log = Logger.getLogger(ImportExcelAction.class);
	private static final long serialVersionUID = 1L;
	private ImportExcelService importExcelService;
	private File file;
	/**
	 * 导入数据
	 * @return
	 */
	public String saveImportExcel(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, Object> map = new ConcurrentHashMap<String, Object>(2);
		List<Map<Integer,String>> list  = ImportExcelUtil.imExcel(file);
		String imptype = request.getParameter("imptype");
		map = importExcelService.saveImportExcel(list,imptype);
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), map);
		log.info("======importUser===========数据导入=====");
		return null;
	}
	public ImportExcelService getImportExcelService() {
		return importExcelService;
	}
	public void setImportExcelService(ImportExcelService importExcelService) {
		this.importExcelService = importExcelService;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	
}
