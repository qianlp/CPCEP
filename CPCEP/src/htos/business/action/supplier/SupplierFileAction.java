package htos.business.action.supplier;

import com.opensymphony.xwork2.ActionSupport;
import htos.business.entity.supplier.SupplierAttachment;
import htos.business.service.supplier.SupplierAttachmentService;
import htos.business.utils.ajax.AjaxCode;
import htos.business.utils.ajax.AjaxUtils;
import htos.common.util.DateUtil;
import htos.common.util.FileCommonUtil;
import htos.sysfmt.file.action.AdenexaAction;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * 供应商附件处理
 *
 * @author qinj
 * @date 2018-02-13 18:03
 **/
public class SupplierFileAction extends ActionSupport {

	private Logger log = LoggerFactory.getLogger(SupplierFileAction.class);

	private SupplierAttachmentService supplierAttachmentService;

	public void upload() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String type = request.getParameter("type");		// 上传文件类型
			if(StringUtils.isBlank(type)) {
				AjaxUtils.error(AjaxCode.FILE_UPLOAD_FAILED, "没有指定上传文件的类型!");
				return ;
			}
			SupplierAttachment attachment = supplierAttachmentService.save(Short.parseShort(type), fileFileName, file);
			AjaxUtils.renderData(attachment);
		} catch (IOException e) {
			log.error("", e);
			AjaxUtils.error(AjaxCode.FILE_UPLOAD_FAILED);
		}
	}

	public void setSupplierAttachmentService(SupplierAttachmentService supplierAttachmentService) {
		this.supplierAttachmentService = supplierAttachmentService;
	}

	private File file;
	private String fileFileName;
	private String fileContentType;


	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

}
