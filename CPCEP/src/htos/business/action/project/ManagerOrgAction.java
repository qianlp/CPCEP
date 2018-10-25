package htos.business.action.project;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import htos.business.entity.project.ManagerOrg;
import htos.common.util.StringUtil;
import htos.coresys.entity.Menu;
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

public class ManagerOrgAction extends ActionSupport implements ModelDriven<ManagerOrg> {

	private static final long serialVersionUID = 1L;
	private CommonFacadeService<ManagerOrg> commonFacadeService;

	private MenuService menuService;
	private ManagerOrg managerOrg ;


	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public CommonFacadeService<ManagerOrg> getCommonFacadeService() {
		return commonFacadeService;
	}

	public void setCommonFacadeService(
			CommonFacadeService<ManagerOrg> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}

	/**
	 * struts2的驱动模型
	 * 对实体自动赋值
	 * @return
     */
	//批量保存或更新类型
	public String saveUpdateManagerOrg() throws Exception{
		if(!StringUtil.isEmpty(managerOrg.getUuid())){//更新
			commonFacadeService.saveOrUpdate(managerOrg);
		}else{//保存

			managerOrg.setUpdateBy(managerOrg.getCreateBy());
			Menu menu = menuService.findOneMenuById("entityClsName", managerOrg.getClass().getSimpleName());
			commonFacadeService.save(managerOrg, menu.getUuid());
		}
		return "success";
	}
	//查询类型维护
	public String queryManagerOrgData(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String orgIds = (String) request.getSession().getAttribute("orgIds");
		List<ManagerOrg> list = commonFacadeService.loadList(ManagerOrg.class.getSimpleName());
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),list);
		return null;
	}

	//采购类型
	public String queryManagerOrgList(){


		List<ManagerOrg> list = commonFacadeService.loadList(ManagerOrg.class.getSimpleName());

		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();

		for (ManagerOrg managerOrg : list) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("id", managerOrg.getCompanyName());
			m.put("text", managerOrg.getCompanyName());
			m.put("pid", "-1");
			m.put("type", "root");
			dataList.add(m);

		}

		CommonUtil.toJsonStr( ServletActionContext.getResponse(), dataList);

		return null;
	}


	@Override
	public ManagerOrg getModel() {
		if(CommonUtil.isNullOrEmpty(managerOrg)){
			managerOrg= new ManagerOrg();
		}
		return managerOrg;
	}
}
