package htos.business.action.procurement;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import htos.business.entity.procurement.PurchaseInfoType;
import htos.common.util.StringUtil;
import htos.coresys.entity.Dept;
import htos.coresys.entity.Menu;
import htos.coresys.entity.User;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.MenuService;
import htos.coresys.util.CommonUtil;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 采购管理（采购类型）
 * @author 
 */

public class PurchaseInfoTypeAction extends ActionSupport implements ModelDriven<PurchaseInfoType> {

	private static final long serialVersionUID = 1L;
	private CommonFacadeService<PurchaseInfoType> commonFacadeService;

	private MenuService menuService;
	private PurchaseInfoType purchaseInfoType ;


	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public CommonFacadeService<PurchaseInfoType> getCommonFacadeService() {
		return commonFacadeService;
	}

	public void setCommonFacadeService(
			CommonFacadeService<PurchaseInfoType> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}

	/**
	 * struts2的驱动模型
	 * 对实体自动赋值
	 * @return
     */
	//批量保存或更新类型
	public String saveUpdateInfoType() throws Exception{
		if(!StringUtil.isEmpty(purchaseInfoType.getUuid())){//更新
			commonFacadeService.saveOrUpdate(purchaseInfoType);
		}else{//保存

			purchaseInfoType.setUpdateBy(purchaseInfoType.getCreateBy());
			Menu menu = menuService.findOneMenuById("entityClsName", purchaseInfoType.getClass().getSimpleName());
			commonFacadeService.save(purchaseInfoType, menu.getUuid());
		}
		return "success";
	}
	//查询类型维护
	public String queryInfoTypeData(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String orgIds = (String) request.getSession().getAttribute("orgIds");
		List<PurchaseInfoType> list = commonFacadeService.loadList(PurchaseInfoType.class.getSimpleName());
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),list);
		return null;
	}

	//采购类型
	public String queryInfoTypeList(){


		List<PurchaseInfoType> list = commonFacadeService.loadList(PurchaseInfoType.class.getSimpleName());

		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();

		for (PurchaseInfoType purchaseInfoType : list) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("id", purchaseInfoType.getUuid());
			m.put("text", purchaseInfoType.getPurType());
			m.put("pid", "-1");
			m.put("type", "root");
			dataList.add(m);

		}

		CommonUtil.toJsonStr( ServletActionContext.getResponse(), dataList);

		return null;
	}


	@Override
	public PurchaseInfoType getModel() {
		if(CommonUtil.isNullOrEmpty(purchaseInfoType)){
			purchaseInfoType= new PurchaseInfoType();
		}
		return purchaseInfoType;
	}
}
