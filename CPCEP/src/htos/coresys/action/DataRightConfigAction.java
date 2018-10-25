package htos.coresys.action;


import org.apache.struts2.ServletActionContext;

import htos.common.util.StringUtil;
import htos.coresys.entity.DataRightConfig;
import htos.coresys.entity.UserImgConfig;
import htos.coresys.service.CommonService;
import htos.coresys.util.CommonUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class DataRightConfigAction extends ActionSupport implements ModelDriven<DataRightConfig> {

	private CommonService commonService;
	private DataRightConfig dataRightConfig;
	private DataRightConfig comObj;
	
	public String dataRightOperate(){
		if(StringUtil.isEmpty(dataRightConfig.getUuid())){
			dataRightConfig.setUuid(commonService.save(dataRightConfig).toString());
		}else{
			commonService.update(dataRightConfig);
		}
		comObj=dataRightConfig;
		return SUCCESS;
	}
	
	public String findDataRightById(){
		comObj=(DataRightConfig) commonService.getEntityByProperty("DataRightConfig", "menuId", ServletActionContext.getRequest().getParameter("menuId"));
		return SUCCESS;
	}
	
	

	/**
	 * @return the commonService
	 */
	public CommonService getCommonService() {
		return commonService;
	}





	/**
	 * @param commonService the commonService to set
	 */
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}





	/**
	 * @return the dataRightConfig
	 */
	public DataRightConfig getDataRightConfig() {
		return dataRightConfig;
	}





	/**
	 * @param dataRightConfig the dataRightConfig to set
	 */
	public void setDataRightConfig(DataRightConfig dataRightConfig) {
		this.dataRightConfig = dataRightConfig;
	}





	/**
	 * @return the comObj
	 */
	public DataRightConfig getComObj() {
		return comObj;
	}

	/**
	 * @param comObj the comObj to set
	 */
	public void setComObj(DataRightConfig comObj) {
		this.comObj = comObj;
	}

	@Override
	public DataRightConfig getModel() {
		if(dataRightConfig==null){
			dataRightConfig=new DataRightConfig();
		}
		return dataRightConfig;
	}
	
}
