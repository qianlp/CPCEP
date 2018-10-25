package htos.business.action.bulletin;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import htos.business.entity.bulletin.BiddingBulletinTemplate;
import htos.business.entity.procurement.PurchaseInfoType;
import htos.common.util.StringUtil;
import htos.coresys.entity.Menu;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.MenuService;
import htos.coresys.util.CommonUtil;
import org.apache.struts2.ServletActionContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BiddingBulletinTemplateAction extends ActionSupport implements ModelDriven<BiddingBulletinTemplate> {
	private BiddingBulletinTemplate biddingBulletinTemplate;
	private MenuService menuService;
	private CommonFacadeService<BiddingBulletinTemplate> commonFacadeService;

	public String saveUpdateTemplate(){
		if(!StringUtil.isEmpty(biddingBulletinTemplate.getUuid())){//更新
			biddingBulletinTemplate.setUpdateBy(biddingBulletinTemplate.getCreateBy());
			commonFacadeService.saveOrUpdate(biddingBulletinTemplate);
		}else{//保存
			biddingBulletinTemplate.setUpdateBy(biddingBulletinTemplate.getCreateBy());
			Menu menu = menuService.findOneMenuById("entityClsName", biddingBulletinTemplate.getClass().getSimpleName());
			commonFacadeService.save(biddingBulletinTemplate, menu.getUuid());
		}
		return "success";
	}

	public void queryTemplate(){

		List<BiddingBulletinTemplate> list = commonFacadeService.loadList(BiddingBulletinTemplate.class.getSimpleName());

		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();

		for (BiddingBulletinTemplate biddingBulletinTemplate : list) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("id", biddingBulletinTemplate.getUuid());
			m.put("text", biddingBulletinTemplate.getName());
			m.put("content",biddingBulletinTemplate.getContent());
			m.put("pid", "-1");
			m.put("type", "root");
			dataList.add(m);

		}
		CommonUtil.toJsonStr( ServletActionContext.getResponse(), dataList);
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public CommonFacadeService<BiddingBulletinTemplate> getCommonFacadeService() {
		return commonFacadeService;
	}

	public void setCommonFacadeService(CommonFacadeService<BiddingBulletinTemplate> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}

	@Override
	public BiddingBulletinTemplate getModel() {
		if(CommonUtil.isNullOrEmpty(biddingBulletinTemplate)){
			biddingBulletinTemplate= new BiddingBulletinTemplate();
		}
		return biddingBulletinTemplate;
	}
}
