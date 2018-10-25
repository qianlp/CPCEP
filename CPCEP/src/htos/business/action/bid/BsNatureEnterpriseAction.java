package htos.business.action.bid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import htos.business.entity.bid.BsNatureEnterprise;
import htos.business.entity.project.ManagerOrg;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.util.CommonUtil;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BsNatureEnterpriseAction extends ActionSupport implements ModelDriven<BsNatureEnterprise>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -317649891031759942L;
	private BsNatureEnterprise bsNatureEnterprise;
	private CommonFacadeService<BsNatureEnterprise> commonFacadeService;

	
	
	/**
	 * 企业性质保存和更新
	 * @return
	 */
	public String newsbsNatureEnterprise() {
		String menuId=ServletActionContext.getRequest().getParameter("menuId");
		if (!StringUtils.isEmpty(bsNatureEnterprise.getUuid())) {
			commonFacadeService.update(bsNatureEnterprise);
		} else {
			commonFacadeService.save(bsNatureEnterprise,menuId);
		}
		return "success";
	}
	
	@Override
	public BsNatureEnterprise getModel() {
		if(CommonUtil.isNullOrEmpty(bsNatureEnterprise)){
			bsNatureEnterprise = new BsNatureEnterprise();
		}
		return bsNatureEnterprise;
	}
	
	//企业性质名称
	public String queryBsNatureEnterpriseList(){
			List<BsNatureEnterprise> list = commonFacadeService.loadList(BsNatureEnterprise.class.getCanonicalName());
			List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
			for (BsNatureEnterprise bsNatureEnterprise : list) {
				Map<String, String> m = new HashMap<String, String>();
				m.put("id", bsNatureEnterprise.getUuid());
				m.put("text", bsNatureEnterprise.getEnterpriseName());
				m.put("pid", "-1");
				m.put("type", "root");
				dataList.add(m);
			}
			CommonUtil.toJsonStr( ServletActionContext.getResponse(), dataList);
			return null;
	}
	
	
	

	public BsNatureEnterprise getBsNatureEnterprise() {
		return bsNatureEnterprise;
	}

	public void setBsNatureEnterprise(BsNatureEnterprise bsNatureEnterprise) {
		this.bsNatureEnterprise = bsNatureEnterprise;
	}

	public CommonFacadeService<BsNatureEnterprise> getCommonFacadeService() {
		return commonFacadeService;
	}

	public void setCommonFacadeService(
			CommonFacadeService<BsNatureEnterprise> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}

	
	
}
