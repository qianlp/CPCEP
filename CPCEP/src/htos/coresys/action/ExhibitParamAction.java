package htos.coresys.action;

import htos.common.entity.PageInfo;
import htos.common.util.StringUtil;
import htos.coresys.entity.BaseEntity;
import htos.coresys.entity.ExhibitParamModel;
import htos.coresys.entity.Menu;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.ExhibitParamService;
import htos.coresys.service.MenuService;
import htos.coresys.util.CommonUtil;

import java.beans.IntrospectionException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import PluSoft.Utils.JSON;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 数据展现配置
 * @author zcl
 */
public class ExhibitParamAction extends ActionSupport implements ModelDriven<ExhibitParamModel>{
	private static final Logger log = Logger.getLogger(ExhibitParamAction.class);
	private static final long serialVersionUID = 1L;
	private ExhibitParamModel exhibitParamModel;
	private CommonFacadeService<ExhibitParamModel> commonFacadeService;
	private ExhibitParamService<ExhibitParamModel> exhibitParamService;
	private ExhibitParamService<BaseEntity> exhibitParamBaseService;
	private MenuService menuService;
	
	//查询所有表中数据
	public String findExhibitListJson(){
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),commonFacadeService.loadList(ExhibitParamModel.class.getSimpleName()));
		return null;
	}
	
	//保存或者更新
	public String exhibitParamJson() throws IntrospectionException{
		Map<String, Object> map = new HashMap<String, Object>(2);
		if(exhibitParamModel!=null && !StringUtil.isEmpty(exhibitParamModel.getUuid())){
			//更新
			exhibitParamService.updateExhibitParamJson(exhibitParamModel);
			log.info("==========exhibitParamJson=====更新成功==========");
			map.put("success",true);
			map.put("msg", "更新成功");
		}else{
			//新增
			exhibitParamService.saveExhibitParamJson(exhibitParamModel);
			log.info("==========exhibitParamJson=====保存成功==========");
			map.put("success",true);
			map.put("msg", "保存成功");
		}
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),map);
		return null;
	}
	
	// 删除配置展现下面的数据,当exhibitpid只有一条数据时则同时删除父节点，如果有多条时则只删除改节点。
	public String deleteExhibit() {
		exhibitParamModel = commonFacadeService.get(ExhibitParamModel.class, exhibitParamModel.getUuid());
		List<ExhibitParamModel> lists = commonFacadeService.getListByProperty(ExhibitParamModel.class.getSimpleName(), "exhibitpid", exhibitParamModel.getExhibitPid());
		if(lists.size()<=1){
			commonFacadeService.deleteId("ExhibitParamModel", "uuid",exhibitParamModel.getExhibitPid());//删除父节点
		}
		commonFacadeService.deleteId("ExhibitParamModel", "uuid", exhibitParamModel.getUuid());//删除子节点
		log.info("delMenu=======>删除配置展现操作成功！uuid=" + exhibitParamModel.getUuid());
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("success", true);
		map.put("msg", "删除菜单成功！");
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), map);
		return null;
	}

	
	//新增或修改前查询
	public String queryExhibitById(){
		HttpServletRequest request = ServletActionContext.getRequest();
		if(!StringUtil.isEmpty(request.getParameter("uuid"))){
			exhibitParamModel = commonFacadeService.getEntityByID(ExhibitParamModel.class.getSimpleName(), request.getParameter("uuid"));
			ExhibitParamModel exhibitParam = commonFacadeService.getEntityByID(ExhibitParamModel.class.getSimpleName(), exhibitParamModel.getExhibitPid());
			exhibitParamModel.setModularName(exhibitParam.getModularName());
		}
		request.setAttribute("randomId",UUID.randomUUID());
		return "success";
	}

	//查询数据展现
	public String findExhibitById(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getSession().getServletContext().getContextPath();
		exhibitParamModel = commonFacadeService.getEntityByProperty(ExhibitParamModel.class.getSimpleName(), "exhibitPath","/admin/findExhibitById.action?randomId="+request.getParameter("randomId"));
		if(exhibitParamModel!=null && !StringUtil.isEmpty(exhibitParamModel.getMenuId())){
			Menu menu=menuService.findMenuById(exhibitParamModel.getMenuId());
			exhibitParamModel.setEntityClsName(menu.getEntityClsName());
			//exhibitParamModel.setPageSearchAddress(menu.getPageSearchAddress());
		}
		return "success";
	}
	
	//数据列表查询
	public String loadListForPageExhibit() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String menuId = request.getParameter("menuId");
		String uuid = request.getParameter("uuid");
		HttpSession session = request.getSession();
		String orgIds = (String) session.getAttribute("orgIds");
		
		if(request.getParameter("search")!=null){
			List searchList=(List)JSON.Decode(request.getParameter("search"));
			CommonUtil.toJsonStr(ServletActionContext.getResponse(), exhibitParamBaseService.loadMapListForPageHeadOrListOrViewSearch(menuId,uuid, getpageInfo(),orgIds,searchList));
		}else{
			CommonUtil.toJsonStr(ServletActionContext.getResponse(), exhibitParamBaseService.loadMapListForPageHeadOrListOrview(menuId,uuid, getpageInfo(),orgIds));
		}
		return null;
	}
	
	public PageInfo getpageInfo(){
		HttpServletRequest request = ServletActionContext.getRequest();
		int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		PageInfo pageInfo = new PageInfo(pageIndex, pageSize, sortField, sortOrder);
		return pageInfo;
	}
	
	public ExhibitParamModel getExhibitParamModel() {
		return exhibitParamModel;
	}

	public void setExhibitParamModel(ExhibitParamModel exhibitParamModel) {
		this.exhibitParamModel = exhibitParamModel;
	}

	public CommonFacadeService<ExhibitParamModel> getCommonFacadeService() {
		return commonFacadeService;
	}

	public void setCommonFacadeService(CommonFacadeService<ExhibitParamModel> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}


	@Override
	public ExhibitParamModel getModel() {
		if(CommonUtil.isNullOrEmpty(exhibitParamModel)){
			exhibitParamModel = new ExhibitParamModel();
		}
		return exhibitParamModel;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public ExhibitParamService<ExhibitParamModel> getExhibitParamService() {
		return exhibitParamService;
	}

	public void setExhibitParamService(
			ExhibitParamService<ExhibitParamModel> exhibitParamService) {
		this.exhibitParamService = exhibitParamService;
	}

	public ExhibitParamService<BaseEntity> getExhibitParamBaseService() {
		return exhibitParamBaseService;
	}

	public void setExhibitParamBaseService(ExhibitParamService<BaseEntity> exhibitParamBaseService) {
		this.exhibitParamBaseService = exhibitParamBaseService;
	}
	
}
