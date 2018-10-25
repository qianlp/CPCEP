/**
 * 
 */
package htos.sysfmt.file.action;

import org.apache.commons.lang.StringUtils;

import htos.coresys.util.CommonUtil;
import htos.sysfmt.file.entity.AdenexaModel;
import htos.sysfmt.file.entity.UpdateFileMsg;
import htos.sysfmt.file.service.UpdateFileMsgService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @author 温勋
 * @ClassName : UpdateFileMsgAction
 * @Version V1.1
 * @ModifiedBy 温勋
 * @Copyright 华胜龙腾
 * @date 2016年8月29日 下午2:28:33
 */
public class UpdateFileMsgAction extends ActionSupport implements
		ModelDriven<UpdateFileMsg> {
	private UpdateFileMsg updateFileMsg;
	private UpdateFileMsgService updateFileMsgService;
	
	
	public String fileMsgOperate(){
		if(!StringUtils.isEmpty(updateFileMsg.getUuid())){
			updateFileMsgService.updateFileMsg(updateFileMsg);
		}else{
			updateFileMsgService.saveFileMsg(updateFileMsg);
		}
		return "success";
	}
	
	
	/**
	 * @return the updateFileMsgService
	 */
	public UpdateFileMsgService getUpdateFileMsgService() {
		return updateFileMsgService;
	}


	/**
	 * @param updateFileMsgService the updateFileMsgService to set
	 */
	public void setUpdateFileMsgService(UpdateFileMsgService updateFileMsgService) {
		this.updateFileMsgService = updateFileMsgService;
	}


	/**
	 * @return the updateFileMsg
	 */
	public UpdateFileMsg getUpdateFileMsg() {
		return updateFileMsg;
	}



	/**
	 * @param updateFileMsg the updateFileMsg to set
	 */
	public void setUpdateFileMsg(UpdateFileMsg updateFileMsg) {
		this.updateFileMsg = updateFileMsg;
	}



	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	@Override
	public UpdateFileMsg getModel() {
		if (CommonUtil.isNullOrEmpty(updateFileMsg)) {
			updateFileMsg = new UpdateFileMsg();
		}
		return updateFileMsg;
	}

}
