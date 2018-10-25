package htos.coresys.action;

import htos.business.entity.bid.ConfirmMessage;
import htos.coresys.entity.BaseEntity;
import htos.coresys.entity.User;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.util.CommonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONArray;

import PluSoft.Utils.JSON;

public class CommonFacadeAction  extends BaseAction{

	private static final long serialVersionUID = 1L;
	private CommonFacadeService<BaseEntity> commonFacadeService;
	private BaseEntity baseEntity;
	
	public String getEntityById(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String uuid = request.getParameter("uuid");
		String className = request.getParameter("className");
		baseEntity = commonFacadeService.getEntityByID(className, uuid);
		return "success";
	}
	
	public String deleteModelId() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String uuid = request.getParameter("uuid");
		String className = request.getParameter("className");
		String[] str = uuid.split("\\^");
		for (String struuid : str) {
			commonFacadeService.deleteRightId(className, "uuid", struuid);
		}
		return null;
	}
	
	public String deleteModel() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String uuid = request.getParameter("uuid");
		String className = request.getParameter("className");
		String[] str = uuid.split("\\^");
		for (String struuid : str) {
			baseEntity = this.commonFacadeService.getEntityByID(className, struuid);
			commonFacadeService.deleteObjectRight(baseEntity);
		}
		return null;
	}
	
	public String loadAllList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String menuId = request.getParameter("menuId");
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), commonFacadeService.loadAllList(menuId));
		return null;
	}

	public String loadListForPage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String menuId = request.getParameter("menuId");
		HttpSession session = request.getSession();
		String orgIds = (String) session.getAttribute("orgIds");
		if(request.getParameter("search")!=null){
			List searchList=(List)JSON.Decode(request.getParameter("search"));
			//CommonUtil.toJsonStr(ServletActionContext.getResponse(), commonFacadeService.loadListForPage(menuId, getpageInfo(),orgIds,searchList));
			CommonUtil.toJsonStr(ServletActionContext.getResponse(), commonFacadeService.loadMapListForPageHeadOrListOrViewSearch(menuId, getpageInfo(),orgIds,searchList));
		}else{
			//CommonUtil.toJsonStr(ServletActionContext.getResponse(), commonFacadeService.loadListForPage(menuId, getpageInfo(),orgIds));
			CommonUtil.toJsonStr(ServletActionContext.getResponse(), commonFacadeService.loadMapListForPageHeadOrListOrview(menuId, getpageInfo(),orgIds));
		}
		return null;
	}
	
	public String loadMapListForPageHeadOrList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String menuId = request.getParameter("menuId");
		HttpSession session = request.getSession();
		String orgIds = (String) session.getAttribute("orgIds");

		if(request.getParameter("search")!=null){
			List searchList=(List)JSON.Decode(request.getParameter("search"));
			CommonUtil.toJsonStr(ServletActionContext.getResponse(), commonFacadeService.loadMapListForPageHeadOrListOrViewSearch(menuId, getpageInfo(),orgIds,searchList));
		}else{
			CommonUtil.toJsonStr(ServletActionContext.getResponse(), commonFacadeService.loadMapListForPageHeadOrListOrview(menuId, getpageInfo(),orgIds));
		}
		return null;
	}
	public String loadSuppilerId() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String menuId = request.getParameter("menuId");
		HttpSession session = request.getSession();
		String orgIds = (String) session.getAttribute("orgIds");
		User user = (User) session.getAttribute("user");
		List<ConfirmMessage>  conList =new ArrayList<>();
  		if(menuId.equals("402880e464d9854e0164dabb4453008f")){//定标信息的筛选
			Map<String, Object> map = commonFacadeService.loadMapListForPageHeadOrListOrview(menuId, getpageInfo(),orgIds);
			System.out.println(map.get("data"));
			Object object = map.get("data");
	       net.sf.json.JSONArray array = net.sf.json.JSONArray.fromObject(object);
	      List list = array.toList(array, ConfirmMessage.class);
			System.out.println(list);
			for(int i=0;i<list.size();i++){
				ConfirmMessage con = (ConfirmMessage)list.get(i);
				System.out.println(con.getSuppilerId());
				System.out.println(user.getUuid());
				if(user.getUuid().equals(con.getSuppilerId())){
					conList.add(con);
				}
			}
		}
  		CommonUtil.toJsonStr(ServletActionContext.getResponse(),conList);
		/*if(request.getParameter("search")!=null){
			List searchList=(List)JSON.Decode(request.getParameter("search"));
			CommonUtil.toJsonStr(ServletActionContext.getResponse(), commonFacadeService.loadMapListForPageHeadOrListOrViewSearch(menuId, getpageInfo(),orgIds,searchList));
		}else{
			CommonUtil.toJsonStr(ServletActionContext.getResponse(), commonFacadeService.loadMapListForPageHeadOrListOrview(menuId, getpageInfo(),orgIds));
		}*/
		return null;
	}
	
	//不带权限的列表查询
	public String loadMapListForNotOrgPage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String menuId = request.getParameter("menuId");
		HttpSession session = request.getSession();
		String orgIds = (String) session.getAttribute("orgIds");
		if(request.getParameter("search")!=null){
			List searchList=(List)JSON.Decode(request.getParameter("search"));
			CommonUtil.toJsonStr(ServletActionContext.getResponse(), commonFacadeService.loadMapListForNotOrgPageSearch(menuId, getpageInfo(),searchList));
		}else{
			CommonUtil.toJsonStr(ServletActionContext.getResponse(), commonFacadeService.loadMapListForNotOrgPage(menuId, getpageInfo()));
		}
		return null;
	}
	
	public String loadMapListForPageview() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String menuId = request.getParameter("menuId");
		String viewName = request.getParameter("viewName");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), commonFacadeService.loadMapListForPageview(menuId, getpageInfo(),viewName,user.getUserPerEname(),true));
		return null;
	}
	
	public String loadMapListForPageHeadList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String menuId = request.getParameter("menuId");
		HttpSession session = request.getSession();
		String orgIds = (String) session.getAttribute("orgIds");
		if(request.getParameter("search")!=null){
			List searchList=(List)JSON.Decode(request.getParameter("search"));
			CommonUtil.toJsonStr(ServletActionContext.getResponse(), commonFacadeService.loadMapListForPageHeadListSearch(menuId, getpageInfo(),orgIds,searchList));
		}else{
			CommonUtil.toJsonStr(ServletActionContext.getResponse(), commonFacadeService.loadMapListForPageHeadList(menuId, getpageInfo(),orgIds));
		}
		return null;
	}
	
	public String loadListView() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String menuId = request.getParameter("menuId");
		String viewName = request.getParameter("viewName");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), commonFacadeService.loadListView(menuId, viewName,user.getUserPerEname(),true));
		return null;
	}
	
	public String loadMapListForPageList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String menuId = request.getParameter("menuId");
		HttpSession session = request.getSession();
		String orgIds = (String) session.getAttribute("orgIds");
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), commonFacadeService.loadMapListForPageList(menuId, getpageInfo(),orgIds));
		return null;
	}
	
	public String loadListForList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String menuId = request.getParameter("menuId");
		HttpSession session = request.getSession();
		String orgIds = (String) session.getAttribute("orgIds");
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), commonFacadeService.loadListForList(menuId, orgIds));
		return null;
	}

	public BaseEntity getBaseEntity() {
		return baseEntity;
	}
	
	public void setBaseEntity(BaseEntity baseEntity) {
		this.baseEntity = baseEntity;
	}
	
	public CommonFacadeService<BaseEntity> getCommonFacadeService() {
		return commonFacadeService;
	}

	public void setCommonFacadeService(CommonFacadeService<BaseEntity> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}
	
}
