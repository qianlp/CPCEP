package htos.business.action.biddingFile;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import htos.business.entity.biddingFile.IssueType;
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
 * 招标文件发布（问题类型）
 * @author
 */

public class IssueTypeAction extends ActionSupport implements ModelDriven<IssueType> {

	private static final long serialVersionUID = 1L;
	private CommonFacadeService<IssueType> commonFacadeService;

	private MenuService menuService;
	private IssueType issueType ;


	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public CommonFacadeService<IssueType> getCommonFacadeService() {
		return commonFacadeService;
	}

	public void setCommonFacadeService(
			CommonFacadeService<IssueType> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}

	/**
	 * struts2的驱动模型
	 * 对实体自动赋值
	 * @return
	 */
	//批量保存或更新类型
	public String saveUpdateIssueType() throws Exception{
		if(!StringUtil.isEmpty(issueType.getUuid())){//更新
			commonFacadeService.saveOrUpdate(issueType);
		}else{//保存

			issueType.setUpdateBy(issueType.getCreateBy());
			Menu menu = menuService.findOneMenuById("entityClsName", issueType.getClass().getSimpleName());
			commonFacadeService.save(issueType, menu.getUuid());
		}
		return "success";
	}
	//查询类型维护
	public String queryInfoTypeData(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String orgIds = (String) request.getSession().getAttribute("orgIds");
		List<IssueType> list = commonFacadeService.loadList(IssueType.class.getSimpleName());
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),list);
		return null;
	}

	//采购类型
	public String queryIssueTypeList(){


		List<IssueType> list = commonFacadeService.loadList(IssueType.class.getSimpleName());

		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();

		for (IssueType issueType : list) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("id", issueType.getUuid());
			m.put("text", issueType.getIssueType());
			m.put("pid", "-1");
			m.put("type", "root");
			dataList.add(m);

		}

		CommonUtil.toJsonStr( ServletActionContext.getResponse(), dataList);

		return null;
	}


	@Override
	public IssueType getModel() {
		if(CommonUtil.isNullOrEmpty(issueType)){
			issueType= new IssueType();
		}
		return issueType;
	}
}
