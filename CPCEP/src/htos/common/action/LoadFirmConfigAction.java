package htos.common.action;

import java.util.List;

import htos.common.entity.FirmConfig;
import htos.coresys.service.CommonFacadeService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class LoadFirmConfigAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private CommonFacadeService<FirmConfig> commonFacadeService;
	
	public void loadFirmConfig(){
		List<FirmConfig> list=commonFacadeService.getHqlList(" from FirmConfig");
		FirmConfig fc=new FirmConfig();
		fc.setCssType("1");
		fc.setFirmName("---");
		ActionContext.getContext().getApplication().put("firmConfig", fc);
		if(list!=null && list.size()>0){
			ActionContext.getContext().getApplication().put("firmConfig", list.get(0));
		}
	}
	
	public CommonFacadeService<FirmConfig> getCommonFacadeService() {
		return commonFacadeService;
	}
	public void setCommonFacadeService(
			CommonFacadeService<FirmConfig> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}
	
}
