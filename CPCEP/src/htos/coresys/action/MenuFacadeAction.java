package htos.coresys.action;

import htos.common.util.StringUtil;
import htos.coresys.entity.BaseWorkFlow;
import htos.coresys.entity.Button;
import htos.coresys.entity.Dept;
import htos.coresys.entity.DocInfo;
import htos.coresys.entity.Menu;
import htos.coresys.entity.MenuToBtn;
import htos.coresys.entity.PrtRole;
import htos.coresys.entity.Role;
import htos.coresys.entity.User;
import htos.coresys.entity.UserToRole;
import htos.coresys.entity.WorkFlow;
import htos.coresys.service.BaseWorkFlowService;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.DocNumberService;
import htos.coresys.service.MenuFacadeService;
import htos.coresys.service.PrtRoleService;
import htos.coresys.service.RightService;
import htos.coresys.service.RoleService;
import htos.coresys.service.WorkFlowService;
import htos.coresys.util.CommonUtil;
import htos.coresys.util.ReflectObjUtil;
import htos.sysfmt.file.entity.FileDirectory;
import htos.sysfmt.file.service.FileDirectoryService;
import htos.sysfmt.message.entity.MsgInfo;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import PluSoft.Utils.JSON;

import com.opensymphony.xwork2.ActionSupport;

public class MenuFacadeAction extends ActionSupport {
	protected static final Logger log = Logger.getLogger(MenuFacadeAction.class);
	private static final long serialVersionUID = 1L;
	private DocNumberService docNumberService;
	private PrtRoleService prtRoleService;
	protected MenuFacadeService menuFacadeService;
	protected CommonFacadeService<Object> commonFacadeService;
	protected FileDirectoryService fileDirectoryService;
	protected RoleService roleService;
	protected RightService rightService;
	protected BaseWorkFlowService baseWorkFlowService;
	protected WorkFlowService workFlowService;
	protected BaseWorkFlow baseWorkFlow;
	protected WorkFlow workFlow;
	protected DocInfo docInfo;
	protected Menu menu;
	protected Button button;
	protected MenuToBtn menuToBtn;
	protected Object comObj;
	protected String isRead = "false";
	protected String docNo="";

	public String findDocById() {
		String menuId = ServletActionContext.getRequest().getParameter("menuId");
		String uuid = ServletActionContext.getRequest().getParameter("uuid");
		String msgId = ServletActionContext.getRequest().getParameter("msgId");
		String type = ServletActionContext.getRequest().getParameter("type");
		User user=(User)ServletActionContext.getRequest().getSession().getAttribute("user");
		
		if (msgId != null && !msgId.equals("")) {
			MsgInfo msgInfo = (MsgInfo) commonFacadeService.getEntityByID(
					"MsgInfo", msgId);
			if (msgInfo != null) {
				msgInfo.setStatus(2);
				commonFacadeService.update(msgInfo);
				isRead = "true";
			}
		}
		
		//获取文档制单信息
		docInfo=(DocInfo)commonFacadeService.getEntityByProperty("DocInfo", "docId", uuid);
		if(docInfo==null){docInfo=new DocInfo();}
		
		menu = menuFacadeService.findMenuById(menuId);
		/*
		//超级管理员查看所有
		if(user.getUserType()==null){
			List<Right> rList=rightService.getRightsByProprty(menu.getEntityClsName(), user.getUuid(), uuid);
			if(rList==null || rList.size()==0){
				menu.setPageComAddress("/coresys/msg.jsp");
				return "success";
			}
		}
		*/
		//getCatalog(menuId);
		//获取流程
		if(menu.getMenuIsHasWF().equals("1")){
			baseWorkFlow=baseWorkFlowService.getWorkFlowByDocId(uuid);
			if(baseWorkFlow!=null){
				if(baseWorkFlow.getWfStatus().equals("2") || baseWorkFlow.getUserId().indexOf(user.getUuid())==-1){
					isRead = "true";
				}else{//如果是审核拒绝，且通知人不是制单人则设置只读状态，如果当前人是制单人则设置可编辑状态
					if(baseWorkFlow.getMsgTitle().indexOf("拒绝")!=-1){
						User users = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
						if(!users.getUuid().equals(baseWorkFlow.getUserId().split(";")[0])){
							isRead = "true";
						}else{
							isRead = "false";
						}
					}else{
						isRead = "false";
					}
					
				}
			}else{
				workFlow=workFlowService.getWorkFlowByMenuId(menuId);
				isRead = "true";
			}
			
			if(isRead.equals("false") && baseWorkFlow.getCurUser()!=null && baseWorkFlow.getCurUser().indexOf(";")>-1){
				if(StringUtil.isEmpty(baseWorkFlow.getCurReadyUser())){
					baseWorkFlow.setCurReadyUser(user.getUuid());
					commonFacadeService.save(baseWorkFlow);
				}
			}
		}
		
		if(menu.getIsPrower()!=null && menu.getIsPrower().equals("0") && !menu.getMenuIsHasWF().equals("1")){
			isRead = "false";
		}
		if (type != null && !type.equals("")) {
			isRead = "true";
		}
		if(isRead.equals("true")){
			menu.setActionAddress("");
		}
		comObj = commonFacadeService.getEntityByID(menu.getEntityClsName(),uuid);
		if(StringUtils.isNotBlank(menu.getQueryEntityClsname())){
			ServletActionContext.getRequest().setAttribute("viewObj",commonFacadeService.getEntityByID(menu.getQueryEntityClsname(),uuid));
		}
		return "success";
	}
	
	//会签文档占用释放
	public void docReadUserClear(){
		String uuid = ServletActionContext.getRequest().getParameter("docId");
		baseWorkFlow=baseWorkFlowService.getWorkFlowByDocId(uuid);
		baseWorkFlow.setCurReadyUser("");
		commonFacadeService.save(baseWorkFlow);
		CommonUtil.toString(ServletActionContext.getResponse(), "ok");
	}
	
	
	//文档流程作废
	public void docInvalid(){
		String modoName = ServletActionContext.getRequest().getParameter("modoName");
		String uuid = ServletActionContext.getRequest().getParameter("docId");
		
		//文档作废
		comObj = commonFacadeService.getEntityByID(modoName,uuid);
		ReflectObjUtil.setAttrributeValue(comObj, "wfStatus", "5");
		commonFacadeService.update(comObj);
		
		//流程作废
		baseWorkFlow=(BaseWorkFlow)commonFacadeService.getEntityByProperty("BaseWorkFlow", "docId", uuid);
		baseWorkFlow.setCurUser("");
		baseWorkFlow.setUserId("");
		baseWorkFlow.setWfStatus("5");
		commonFacadeService.update(baseWorkFlow);
		
		//当前处理中待办移除
		List<Object> objList=commonFacadeService.getHqlList("from MsgInfo where docId='"+uuid+"' and (status=null or status=1)");
		System.out.println(objList.size());
		for(Object o:objList){
			commonFacadeService.delete(o);
		}
		
		CommonUtil.toString(ServletActionContext.getResponse(), "ok");
	}

	public String findDocByCurDocId() {
		String menuId = ServletActionContext.getRequest()
				.getParameter("menuId");
		String uuid = ServletActionContext.getRequest().getParameter("uuid");
		String type = ServletActionContext.getRequest().getParameter("type");
		menu = menuFacadeService.findMenuById(menuId);
		getCatalog(menuId);
		comObj = commonFacadeService.getEntityByProperty(
				menu.getEntityClsName(), "curDocId", uuid);
		
		if (type != null && !type.equals("")) {
			isRead = "true";
		}
		return "success";
	}
	
	//获取文档流程信息
	public void findWorkFlowByDocId(){
		String docId = ServletActionContext.getRequest().getParameter("docId");
		baseWorkFlow=baseWorkFlowService.getWorkFlowByDocId(docId);
		System.out.println("--------------------");
		System.out.println(docId);
		ServletActionContext.getRequest().setAttribute("baseWorkFlow",baseWorkFlow);
	}
	
	//查询文档审核状态
	public String findDocByCurDocStatus() {
		String menuId = ServletActionContext.getRequest().getParameter("menuId");
		String uuid = ServletActionContext.getRequest().getParameter("uuid");
		menu = menuFacadeService.findMenuById(menuId);
		comObj = commonFacadeService.getEntityByProperty(menu.getEntityClsName(), "curDocId", uuid);
		Map<String, String> map = new HashMap<String, String>(2);
		Object obj = ReflectObjUtil.getAttrributeParentThreeValue(comObj,"wfStatus");
		if(obj!=null){
			map.put("wfStatus", obj.toString());
		}
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),map);
		return null;
	}

	// ------------------菜单---------------------
	public String findMenuByHasWF() {
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),
				menuFacadeService.findMenusHasWF());
		return null;
	}

	public String findMenuById() {
		menu = menuFacadeService.findMenuById(menu.getUuid());
		return "success";
	}
	
	

	public String rtuMenuById() {
		String menuId = ServletActionContext.getRequest().getParameter("menuId");
		menu = menuFacadeService.findMenuById(menuId);
		// 表单流水号 
		docNo=docNumberService.findNumberByMode(menu.getEntityClsName());
		String msgId = ServletActionContext.getRequest().getParameter("msgId");
		if (!StringUtil.isEmpty(msgId)) {
			MsgInfo msgInfo = (MsgInfo) commonFacadeService.getEntityByID("MsgInfo", msgId);
			if (msgInfo != null && msgInfo.getMsgType() == 1 && StringUtil.isEmpty(msgInfo.getDocId()) && !StringUtil.isEmpty(msgInfo.getRemark())) {//处理进度代办（进度页面弹出是新建所以doc为空，且有备注描述信息）
				msgInfo.setStatus(2);
				commonFacadeService.update(msgInfo);
			}
		}
		//目录ID
	//	getCatalog(menuId);
		//获取流程
		if(menu.getMenuIsHasWF().equals("1")){
			workFlow=workFlowService.getWorkFlowByMenuId(menuId);
		}
		

		User user=(User)ServletActionContext.getRequest().getSession().getAttribute("user");
		DocInfo docInfo=new DocInfo();
		if(user.getUserDeptId()==null || user.getUserDeptId().equals("")){
			docInfo.setCreateDeptId("");
			docInfo.setCreateDeptName("");
		}else{
			Dept dept=(Dept)commonFacadeService.getEntityByID("Dept", user.getUserDeptId());
			if(dept!=null){
				docInfo.setCreateDeptId(dept.getUuid());
				docInfo.setCreateDeptName(dept.getDeptFullName());
			}
		}

		ServletActionContext.getRequest().setAttribute("docInfo",docInfo);
		ServletActionContext.getRequest().setAttribute("curDocId",UUID.randomUUID());
		ServletActionContext.getRequest().setAttribute("docBusId", ServletActionContext.getRequest().getParameter("docBusinessId"));
		return "success";
	}

	protected void getCatalog(String menuId) {
		FileDirectory fileDirectory = new FileDirectory();
		fileDirectory.setRelationMenuID(menuId);
		List<FileDirectory> fileDir = fileDirectoryService.fileDirectoryList(fileDirectory);
		String catalogId = "";
		for (FileDirectory f : fileDir) {
			if (!catalogId.equals("")) {
				catalogId += ",";
			}
			catalogId += f.getUuid();
		}

		if (!catalogId.equals("")) {
			ServletActionContext.getRequest().setAttribute("catalogId",catalogId);
		}
	}

	public String findHasRightMenu() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String orgIds = (String) session.getAttribute("orgIds");
		menu = menuFacadeService.findMenuById(menu.getUuid(), orgIds);
		return "success";
	}

	public String findAllMenu() {
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),
				menuFacadeService.findAllMenuJson());
		return null;
	}

	public String findSubMenuList() {
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),
				menuFacadeService.findSubMenuList(menu));
		return null;
	}

	public String findMenuTreeNodeList() {
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),
				menuFacadeService.findMenuTreeNodeList(menu));
		return null;
	}
	
	public String findMenuTreeNodeListById() {
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),
				menuFacadeService.findMenuTreeNodeListById(menu));
		return null;
	}
	
	public String findChildMenuList() {
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),
				menuFacadeService.findChildMenuList(menu));
		return null;
	}

	public String findRootMenuList() {
		ServletActionContext.getRequest().setAttribute("menuList",
				menuFacadeService.findRootMenuList());
		return "success";
	}

	public String findHasRightMenus() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String orgIds = (String) session.getAttribute("orgIds");
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),
				menuFacadeService.findMenusData(orgIds));
		return null;
	}

	public void findSupplierMenu() {
		// TODO
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), menuFacadeService.findSupplierMenu());
	}
	
	public String findHasRightMenusAndBtn() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String orgIds = (String) session.getAttribute("orgIds");
		
		List<Map<String,String>> menuList=menuFacadeService.findMenusData(orgIds);
		if(menuList==null){
			CommonUtil.toJsonStr(ServletActionContext.getResponse(),menuList);
			return null;
		}
		orgIds = orgIds.replace(",", "','");
		List<Object> objList = commonFacadeService.getHqlList("from MenuToBtn where uuid in (select docId from Right r where r.orgId in ('"+orgIds+"'))");
		for(int i=0;i<menuList.size();i++){
			Map<String,String> map=menuList.get(i);
			List<Map<String,String>> btnList=new ArrayList<Map<String,String>>();
			for(Object o:objList){
				MenuToBtn mtb=(MenuToBtn)o;
				if(!mtb.getMenu().getUuid().equals(map.get("id"))){
					continue;
				}
				Map<String,String> btnMap=new HashMap<String,String>();
				btnMap.put("id", mtb.getUuid());
				btnMap.put("name", mtb.getBtnName());
				btnMap.put("btnFunction", mtb.getBtnFunction());
				btnList.add(btnMap);
			}
			map.put("functions", JSON.Encode(btnList));
		}
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),menuList);
		return null;
	}

	public String addMenu() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String sameLevelNodeId = request.getParameter("sameLevelNodeId");
		String rigthData = request.getParameter("rightJson");
		String btnIds = request.getParameter("btnData");
		menuFacadeService.addMenu(menu, sameLevelNodeId, rigthData, btnIds);
		// 添加菜单角色为默认隐藏角色用于菜单文档权限
		this.menuRole(menu,menu.getCreateBy());
		String html = null;
		try {
			html = "<script>parent.goReload();</script>";
		} catch (Exception e) {
			html = "<script>alert(\"添加失败\");parent.goCloseDlg(\"oWinDlg\");</script>";
			e.printStackTrace();
		}
		CommonUtil.toString(ServletActionContext.getResponse(), html);
		return null;

	}

	public String updateMenu() throws IntrospectionException {
		ServletRequest request = ServletActionContext.getRequest();
		String sameLevelNodeId = request.getParameter("sameLevelNodeId"); // 相邻层级节点id
		String position = request.getParameter("position"); // 菜单位置(保存当前、指定位置、最后)
		String rigthData = request.getParameter("rightJson"); // 菜单查看权限json
		String btnIds = request.getParameter("btnData"); // 新增按钮id
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		System.out.println(menu.getPageSearchAddress());
		menuFacadeService.updateMenu(menu, sameLevelNodeId, position,
				rigthData, btnIds,user.getUserPerEname());
		String html = null;
		// 添加菜单角色为默认隐藏角色用于菜单文档权限
		this.menuRole(menu,user.getUserPerEname());
		try {
			html = "<script>parent.goReload();</script>";
		} catch (Exception e) {
			html = "<script>alert(\"修改失败\");parent.goCloseDlg(\"oWinDlg\");</script>";
			e.printStackTrace();
		}
		CommonUtil.toString(ServletActionContext.getResponse(), html);
		return null;
	}
	
	private void menuRole(Menu menu,String user){
		if (!CommonUtil.isNullOrEmpty(menu.getEntityClsName())) {
			if (commonFacadeService.getEntityByProperty("Role", "modeName",
					menu.getEntityClsName()) == null) {
				Role role = new Role();
				role.setModeName(menu.getEntityClsName());
				role.setRoleName(menu.getMenuName());
				role.setIsHide(1);
				role.setCreateBy(menu.getCreateBy());
				role.setCreateDate(menu.getCreateDate());
				role.setUpdateBy(user);
				role.setUpdateDate(new Date());
				roleService.saveRole(role);
			}
			
			Role role=(Role)commonFacadeService.getEntityByProperty("Role", "modeName",menu.getEntityClsName());
			List<PrtRole> prtList=prtRoleService.findSubRoles(role.getUuid());
			if(prtList!=null && prtList.size()>0){
				for(PrtRole pr:prtList){
					prtRoleService.deletePrtRole(pr);
				}
			}
			
			
			Set<UserToRole> s1=role.getUserToRoles();
			List list=(List)JSON.Decode(menu.getDataRightJson());
			if(list!=null && list.size()>0){
				for(Object o:list){
					Map m=(Map)o;
					if(m.get("type")!=null && m.get("type").toString().equals("user")){
						boolean isOk=false;
						for (UserToRole ut : s1) {
							if(ut.getUser().getUuid().equals(m.get("id").toString())){
								isOk=true;
								break;
							}
						}
						if(!isOk){
							UserToRole userRole=new UserToRole();
							User ruser=new User();
							ruser.setUuid(m.get("id").toString());
							userRole.setUser(ruser);
							userRole.setRole(role);
							userRole.setRoleType("1");
							commonFacadeService.save(userRole);
						}
					}else{
						PrtRole prtRole=new PrtRole();
						prtRole.setRoleId(m.get("id").toString());
						prtRole.setPrtRoleId(role.getUuid());
						commonFacadeService.save(prtRole);
					}
				}
			}
		}
	}

	// '-----------按钮开始---------------'

	public String addButton() {
		ServletRequest request = ServletActionContext.getRequest();
		String rightId = request.getParameter("menuId");
		menuFacadeService.addButton(button, rightId);
		return "success";
	}

	public String findAllButton() {
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),
				menuFacadeService.findAllButton());
		return null;
	}

	// '-----------按钮结束---------------'

	// -------菜单按钮开始------
	public String findMenuBtnById() {
		menuToBtn = menuFacadeService.findMenuBtnById(menuToBtn.getUuid());
		return "success";
	}

	public String delMenuBtnById() {
		menuFacadeService.delMenuBtnById(menuToBtn.getUuid());
		commonFacadeService.deleteRight("MenuToBtn", menuToBtn.getUuid());
		log.info("delMenuBtnById=======>删除按钮操作成功！");
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("success", true);
		map.put("msg", "删除按钮成功！");
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), map);
		return null;
	}

	public String updateMenuBtn() {
		ServletRequest request = ServletActionContext.getRequest();
		String rigthData = request.getParameter("rightJson"); // 菜单查看权限json
		menuFacadeService.updateMenuBtn(menuToBtn, rigthData);
		String html = null;
		try {
			html = "<script>parent.goReload();</script>";
		} catch (Exception e) {
			html = "<script>alert(\"修改失败\");parent.goCloseDlg(\"oWinDlg\");</script>";
			e.printStackTrace();
		}
		CommonUtil.toString(ServletActionContext.getResponse(), html);
		return null;
	}

	// -------菜单按钮结束------

	// -------菜单权限获取------
	/*
	 * 通过个人、部门、角色的唯一标识，获取其拥有的所有菜单与按钮权限
	 */
	public String getMenuRight() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String type = request.getParameter("type");
		String orgId = request.getParameter("orgId");
		String treeDocIds = request.getParameter("treeDocIds");
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),
				menuFacadeService.getMenuRight(orgId, treeDocIds, type));
		return null;
	}

	// -------菜单权限保存------
	public String saveMenuRight() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String type = request.getParameter("type");
		type = type == "" ? "0" : type;
		String orgId = request.getParameter("orgId");
		String[] ids=orgId.split("\\,");
		String treeDocIds = request.getParameter("treeDocIds");
		
		for(String id:ids){
			menuFacadeService.saveMenuRight(id, treeDocIds, type);
		}
		return null;
	}

	// 根据菜单id删除菜单下面的子菜单及按钮以及菜单权限right数据
	public String delMenu() {
		deleteChildMenu(menu.getUuid());// 递归删除子菜单
		// 删除菜单下对应的按钮
		List<MenuToBtn> list = menuFacadeService.findListMenuBtnById(menu
				.getUuid());
		for (MenuToBtn menuToBtn : list) {
			commonFacadeService.deleteRightId("MenuToBtn", "uuid",
					menuToBtn.getUuid());
		}
		commonFacadeService.deleteRightId("Menu", "uuid", menu.getUuid());
		log.info("delMenu=======>删除菜单及菜单按钮操作成功！uuid=" + menu.getUuid());
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("success", true);
		map.put("msg", "删除菜单成功！");
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), map);
		return null;
	}

	// 递归删除子菜单和按钮
	protected void deleteChildMenu(String uuid) {
		List<Menu> lists = menuFacadeService.findListMenuPid(uuid);
		for (Menu menu2 : lists) {// 先递归删除子菜单及子菜单对应的按钮
			// 删除子菜单下对应的按钮
			List<MenuToBtn> list = menuFacadeService.findListMenuBtnById(menu2
					.getUuid());
			for (MenuToBtn menuToBtn : list) {
				commonFacadeService.deleteRightId("MenuToBtn", "uuid",
						menuToBtn.getUuid());
			}
			// 删除子菜单
			commonFacadeService.deleteRightId("Menu", "uuid", menu2.getUuid());
			log.info("delMenu=======>删除子菜单及子菜单按钮操作成功！uuid=" + menu2.getUuid());
			deleteChildMenu(menu2.getUuid());
		}
	}

	/**
	 * @return the commonFacadeService
	 */
	public CommonFacadeService<Object> getCommonFacadeService() {
		return commonFacadeService;
	}

	/**
	 * @param commonFacadeService
	 *            the commonFacadeService to set
	 */
	public void setCommonFacadeService(
			CommonFacadeService<Object> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}

	/**
	 * @return the fileDirectoryService
	 */
	public FileDirectoryService getFileDirectoryService() {
		return fileDirectoryService;
	}

	/**
	 * @param fileDirectoryService
	 *            the fileDirectoryService to set
	 */
	public void setFileDirectoryService(
			FileDirectoryService fileDirectoryService) {
		this.fileDirectoryService = fileDirectoryService;
	}

	/**
	 * @return the comObj
	 */
	public Object getComObj() {
		return comObj;
	}

	/**
	 * @param comObj
	 *            the comObj to set
	 */
	public void setComObj(Object comObj) {
		this.comObj = comObj;
	}

	public MenuFacadeService getMenuFacadeService() {
		return menuFacadeService;
	}

	public void setMenuFacadeService(MenuFacadeService menuFacadeService) {
		this.menuFacadeService = menuFacadeService;
	}

	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public MenuToBtn getMenuToBtn() {
		return menuToBtn;
	}

	public void setMenuToBtn(MenuToBtn menuToBtn) {
		this.menuToBtn = menuToBtn;
	}

	/**
	 * @return the roleService
	 */
	public RoleService getRoleService() {
		return roleService;
	}

	/**
	 * @param roleService
	 *            the roleService to set
	 */
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	/**
	 * @return the workFlowService
	 */
	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	/**
	 * @param workFlowService the workFlowService to set
	 */
	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	/**
	 * @return the workFlow
	 */
	public WorkFlow getWorkFlow() {
		return workFlow;
	}

	/**
	 * @param workFlow the workFlow to set
	 */
	public void setWorkFlow(WorkFlow workFlow) {
		this.workFlow = workFlow;
	}

	/**
	 * @return the baseWorkFlowService
	 */
	public BaseWorkFlowService getBaseWorkFlowService() {
		return baseWorkFlowService;
	}

	/**
	 * @param baseWorkFlowService the baseWorkFlowService to set
	 */
	public void setBaseWorkFlowService(BaseWorkFlowService baseWorkFlowService) {
		this.baseWorkFlowService = baseWorkFlowService;
	}

	/**
	 * @return the baseWorkFlow
	 */
	public BaseWorkFlow getBaseWorkFlow() {
		return baseWorkFlow;
	}

	/**
	 * @param baseWorkFlow the baseWorkFlow to set
	 */
	public void setBaseWorkFlow(BaseWorkFlow baseWorkFlow) {
		this.baseWorkFlow = baseWorkFlow;
	}

	public RightService getRightService() {
		return rightService;
	}

	public void setRightService(RightService rightService) {
		this.rightService = rightService;
	}


	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	public PrtRoleService getPrtRoleService() {
		return prtRoleService;
	}

	public void setPrtRoleService(PrtRoleService prtRoleService) {
		this.prtRoleService = prtRoleService;
	}

	/**
	 * @return the docInfo
	 */
	public DocInfo getDocInfo() {
		return docInfo;
	}

	/**
	 * @param docInfo the docInfo to set
	 */
	public void setDocInfo(DocInfo docInfo) {
		this.docInfo = docInfo;
	}

	public DocNumberService getDocNumberService() {
		return docNumberService;
	}

	public void setDocNumberService(DocNumberService docNumberService) {
		this.docNumberService = docNumberService;
	}

	
}
