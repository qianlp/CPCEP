package htos.sysfmt.file.action;

import htos.coresys.util.CommonUtil;
import htos.sysfmt.file.entity.FileParamModel;
import htos.sysfmt.file.service.FileParamService;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 文件参数配置
 * @author zcl
 */
public class FileParamAction  extends ActionSupport implements ModelDriven<FileParamModel>{
	private static final long serialVersionUID = -5337435682070953918L;
	private FileParamModel fileParamModel;
	private FileParamService fileParamService;
	
	public String findAllFileParamJson(){//由于本功能只存在一条数据所以查询后返回对象放入列表中
		List<FileParamModel> list = fileParamService.findAllFileParamJson();
		if(list.size()>0){
			fileParamModel = list.get(0);
		}
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),fileParamModel);
		return null;
	}
	
	public String updateFileParam(){
		if(StringUtils.isEmpty(fileParamModel.getUuid())){
			fileParamModel.setUuid(UUID.randomUUID().toString());
			fileParamService.save(fileParamModel);
		}else{
			fileParamService.saveOrupdate(fileParamModel);//修改
		}
		
		return "success";
	}

	public FileParamModel getFileParamModel() {
		return fileParamModel;
	}

	public void setFileParamModel(FileParamModel fileParamModel) {
		this.fileParamModel = fileParamModel;
	}

	public FileParamService getFileParamService() {
		return fileParamService;
	}

	public void setFileParamService(FileParamService fileParamService) {
		this.fileParamService = fileParamService;
	}

	@Override
	public FileParamModel getModel() {
		if(CommonUtil.isNullOrEmpty(fileParamModel)){
			fileParamModel= new FileParamModel();
		}
		return fileParamModel;
	}
	
	
}
