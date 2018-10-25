package htos.business.action.bulletin;

import PluSoft.Utils.JSON;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import htos.business.entity.biddingFile.BiddingFileRelease;
import htos.business.entity.biddingFile.IssueType;
import htos.business.entity.bulletin.BiddingBulletin;
import htos.business.entity.procurement.PurchasePlan;
import htos.business.service.bulletin.BiddingBulletinService;
import htos.common.util.StringUtil;
import htos.coresys.action.BaseAction;
import htos.coresys.entity.Menu;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.MenuService;
import htos.coresys.util.CommonUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BiddingBulletinAction extends BaseAction {
	private BiddingBulletin biddingBulletin;
	private CommonFacadeService<BiddingBulletin> commonFacadeService;
	private CommonFacadeService<PurchasePlan> planCommonFacadeService;
	private MenuService menuService;
	private BiddingBulletinService bulletinService;

	public String saveUpdateBulletin(){
		if(!StringUtil.isEmpty(biddingBulletin.getUuid())){//更新
			commonFacadeService.saveOrUpdate(biddingBulletin);
		}else{//保存
			String menuId=ServletActionContext.getRequest().getParameter("menuId");
			PurchasePlan plan = planCommonFacadeService.getEntityByID(PurchasePlan.class.getSimpleName(),biddingBulletin.getPurchasePlanUuid());
			plan.setProjectNode("招标公告");
			plan.setWfStatus("9");
			planCommonFacadeService.update(plan);
			//planCommonFacadeService.save(plan);
			commonFacadeService.save(biddingBulletin, menuId);
//            bulletinService.sendBid(biddingBulletin);
		}
		return "success";
	}

	public CommonFacadeService<BiddingBulletin> getCommonFacadeService() {
		return commonFacadeService;
	}

	public void setCommonFacadeService(CommonFacadeService<BiddingBulletin> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}


    public void setBulletinService(BiddingBulletinService bulletinService) {
        this.bulletinService = bulletinService;
    }

    //查询类型维护
	public String queryBulletinData(){

		List<BiddingBulletin> list = commonFacadeService.loadList(BiddingBulletin.class.getSimpleName());

		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();

		for (BiddingBulletin biddingBulletin : list) {
			if(!biddingBulletin.getWfStatus().equals("2"))
				continue ;
			Map<String, String> m = new HashMap<String, String>();
			m.put("uuid", biddingBulletin.getUuid());
			m.put("name", biddingBulletin.getName());
			m.put("code", biddingBulletin.getCode());
			dataList.add(m);

		}

		CommonUtil.toJsonStr( ServletActionContext.getResponse(), dataList);
		return null;
	}

	@Override
	public BiddingBulletin getModel() {
		if(CommonUtil.isNullOrEmpty(biddingBulletin)){
			biddingBulletin= new BiddingBulletin();
		}
		return biddingBulletin;
	}

	public CommonFacadeService<PurchasePlan> getPlanCommonFacadeService() {
		return planCommonFacadeService;
	}

	public void setPlanCommonFacadeService(CommonFacadeService<PurchasePlan> planCommonFacadeService) {
		this.planCommonFacadeService = planCommonFacadeService;
	}
}
