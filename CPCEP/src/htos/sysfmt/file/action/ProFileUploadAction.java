package htos.sysfmt.file.action;

import htos.common.util.StringUtil;
import htos.coresys.util.CommonUtil;
import htos.sysfmt.file.entity.ProFileploadModel;
import htos.sysfmt.file.service.ProFileUploadService;

import java.beans.IntrospectionException;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 项目文件上传管理
 * @author zcl
 */
public class ProFileUploadAction extends ActionSupport implements ModelDriven<ProFileploadModel>{ 
	private static final long serialVersionUID = 1L;
	private ProFileploadModel proFileploadModel;
	private ProFileUploadService proFileUploadService;
	
	public String proFileOperate() throws IntrospectionException{
		if(proFileploadModel!=null && !StringUtil.isEmpty(proFileploadModel.getCatalogId())){
			String[] str = proFileploadModel.getCatalogId().split(",");
			int length = str.length;
			System.out.println(length);
			String catalogId = str[str.length-1];
			System.out.println(catalogId.trim());
			proFileploadModel.setCatalogId(catalogId.trim());
		}
		if(!StringUtils.isEmpty(proFileploadModel.getUuid())){
			return updateProFileJson();
		}else{
			return saveProFileJson();
		}
	}
	
	private String saveProFileJson(){
		proFileUploadService.saveProFileJson(proFileploadModel);
		return "success";
	}
	private String updateProFileJson() throws IntrospectionException{
		proFileUploadService.updateProFileJson(proFileploadModel);
		return "success";
	}

	public ProFileploadModel getProFileploadModel() {
		return proFileploadModel;
	}

	public void setProFileploadModel(ProFileploadModel proFileploadModel) {
		this.proFileploadModel = proFileploadModel;
	}

	public ProFileUploadService getProFileUploadService() {
		return proFileUploadService;
	}

	public void setProFileUploadService(ProFileUploadService proFileUploadService) {
		this.proFileUploadService = proFileUploadService;
	}

	@Override
	public ProFileploadModel getModel() {
		if(CommonUtil.isNullOrEmpty(proFileploadModel)){
			proFileploadModel= new ProFileploadModel();
		}
		return proFileploadModel;
	}
	
}
