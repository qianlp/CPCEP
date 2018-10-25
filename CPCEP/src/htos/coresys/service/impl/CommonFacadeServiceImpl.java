package htos.coresys.service.impl;

import htos.common.entity.PageInfo;
import htos.common.util.ClassModelUtil;
import htos.common.util.StringUtil;
import htos.coresys.entity.BaseWorkFlow;
import htos.coresys.entity.Dept;
import htos.coresys.entity.DocInfo;
import htos.coresys.entity.Menu;
import htos.coresys.entity.Right;
import htos.coresys.entity.Role;
import htos.coresys.entity.User;
import htos.coresys.service.BaseWorkFlowService;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.CommonService;
import htos.coresys.service.DataRightConfigService;
import htos.coresys.service.MenuService;
import htos.coresys.service.RightService;
import htos.coresys.service.RoleService;
import htos.coresys.util.CommonUtil;
import htos.coresys.util.ReflectObjUtil;
import htos.sysfmt.file.service.AdenexaService;
import htos.sysfmt.message.entity.MsgInfo;
import htos.sysfmt.message.service.MsgInfoService;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Element;


/**
 * 通用方法外观模型服务层
 * @author 庞成祥
 * 
 */
public class CommonFacadeServiceImpl<T> implements CommonFacadeService<T> {
	private CommonService<T> commonService;
	private MenuService menuService;
	private RightService rightService;
	private RoleService roleService;
	private MsgInfoService msgInfoService;
	private BaseWorkFlowService baseWorkFlowService;
	private AdenexaService adenexaService;
	private DataRightConfigService dataRightConfigService;
	
	

	public DataRightConfigService getDataRightConfigService() {
		return dataRightConfigService;
	}

	public void setDataRightConfigService(
			DataRightConfigService dataRightConfigService) {
		this.dataRightConfigService = dataRightConfigService;
	}

	public MsgInfoService getMsgInfoService() {
		return msgInfoService;
	}

	public void setMsgInfoService(MsgInfoService msgInfoService) {
		this.msgInfoService = msgInfoService;
	}
	
	public BaseWorkFlowService getBaseWorkFlowService() {
		return baseWorkFlowService;
	}

	public void setBaseWorkFlowService(BaseWorkFlowService baseWorkFlowService) {
		this.baseWorkFlowService = baseWorkFlowService;
	}

	@Override
	public List<Map<String, String>> loadAllList(String menuId) {
		Menu menu = menuService.findMenuById(menuId);
		if (CommonUtil.isNullOrEmpty(menu)) {
			return null;
		}
		List<T> objList = commonService.loadAllList(menu.getEntityClsName());
		String bodyHtml = "<root>" + menu.getMenuHtmlBody() + "</root>";
		List<Element> eleList = CommonUtil.getXmlChildElements(bodyHtml);
		Set<String> fieldSet = new HashSet<String>();
		for (Element e : eleList) {
			if (!CommonUtil.isNullOrEmpty(e.attributeValue("field"))) {
				fieldSet.add(e.attributeValue("field"));
			} 
		}
		return commonService.dataList(objList, fieldSet);
	}

	@Override
	public Map<String, Object> loadListForPage(String menuId, PageInfo pageInfo, String orgIds) {
		Menu menu = menuService.findMenuById(menuId);
		if (CommonUtil.isNullOrEmpty(menu)) {
			return null;
		}
		Set<String> docSet = rightService.getDocIds(orgIds, menu.getEntityClsName());
		String docIds = rightService.convertToStr(docSet);
		
		List<T> objList = commonService.getPageDataList(menu.getEntityClsName(), pageInfo, docIds);
		int count = commonService.getAllDataCount(menu.getEntityClsName(), docIds);
		String bodyHtml = "<root>" + menu.getMenuHtmlBody() + "</root>";
		List<Element> eleList = CommonUtil.getXmlChildElements(bodyHtml);
		Set<String> fieldSet = new HashSet<String>();
		for (Element e : eleList) {
			if (!CommonUtil.isNullOrEmpty(e.attributeValue("field"))) {
				fieldSet.add(e.attributeValue("field"));
			} 
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("data", commonService.dataList(objList, fieldSet));
		objList=null;
		fieldSet=null;
		eleList=null;
		return map;
	}
	
	@Override
	public Map<String, Object> loadListForPage(String menuId, PageInfo pageInfo, String orgIds,List list) {
		Menu menu = menuService.findMenuById(menuId);
		if (CommonUtil.isNullOrEmpty(menu)) {
			return null;
		}
		Set<String> docSet = rightService.getDocIds(orgIds, menu.getEntityClsName());
		String docIds = rightService.convertToStr(docSet);

		List<T> objList = commonService.getPageDataList(menu.getEntityClsName(), pageInfo, docIds,list);
		int count = commonService.getAllDataCount(menu.getEntityClsName(), docIds);
		String bodyHtml = "<root>" + menu.getMenuHtmlBody() + "</root>";
		List<Element> eleList = CommonUtil.getXmlChildElements(bodyHtml);
		Set<String> fieldSet = new HashSet<String>();
		for (Element e : eleList) {
			if (!CommonUtil.isNullOrEmpty(e.attributeValue("field"))) {
				fieldSet.add(e.attributeValue("field"));
			} 
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("data", commonService.dataList(objList, fieldSet));
		
		objList=null;
		fieldSet=null;
		eleList=null;
		return map;
	}

	@Override
	public List<T> getPageDataList(String entityClsName, PageInfo pageInfo, String docIds) {
		return commonService.getPageDataList(entityClsName, pageInfo, docIds);
	}

	@Override
	public String save(T o, String rightId) {
		Serializable uuid=commonService.save(o);
		if(rightId!=null){
				Menu menu = menuService.findMenuById(rightId);
				//Method m = (Method) o.getClass().getMethod("getUuid");
				//String uuid = (String) m.invoke(o);
				
				//添加数据阅读权限
				//rightService.addRight(uuid.toString(), o.getClass().getSimpleName(), menu.getDataRightJson());
				
				HttpServletRequest request = ServletActionContext.getRequest();
				User user=(User)request.getSession().getAttribute("user");
				this.addDocRight(o.getClass().getSimpleName(),uuid.toString(),user.getUuid(),2,user.getUserDeptId());
				
				if(menu.getMenuIsHasWF().equals("1")){
					this.wfFlowMsg(request, uuid.toString(), menu, true,o.getClass().getSimpleName());
				}else{
					//不走流程时待办消息（需要页面有必要字段）
					this.toMsgUser(o,rightId,uuid.toString());
				}
				
				//当前模块角色默认可见文档
				Role role=(Role) commonService.getEntityByProperty("Role", "modeName", o.getClass().getSimpleName());
				if(role!=null){
					this.addDocRight(o.getClass().getSimpleName(),uuid.toString(),role.getUuid(),3);
				}
				
				//添加默认查看权限
				this.createRight(o, uuid.toString(), user,rightId);

				//添加默认查看权限
				this.addDocInfo(user, uuid.toString());
		}
		return uuid.toString();
	}
	
	//为文档添加阅读权限
	@Override
	public void addDocRight(String modeName,String docId,String orgId,int type){
		List<Right> rList=rightService.getRightsByProprty(modeName, orgId, docId);
		if(rList==null || rList.size()>0){
			return;
		}
		Right r = new Right();
		r.setDocId(docId);
		r.setOrgId(orgId);
		r.setOperType(1);
		r.setRightType(type);
		r.setModoName(modeName);
		rightService.saveRight(r);
		r=null;
	}
	
	public void addDocInfo(User user,String uuid){
		if(user.getUserDeptId()==null || user.getUserDeptId().equals("")){
			return;
		}
		Dept dept=(Dept)commonService.getEntityByID("Dept", user.getUserDeptId());
		DocInfo docInfo=new DocInfo();
		if(dept!=null){
			docInfo.setCreateDeptId(dept.getUuid());
			docInfo.setCreateDeptName(dept.getDeptFullName());
			docInfo.setPosNo(user.getPosNo());
			docInfo.setPosName(user.getPosName());
		}
		docInfo.setUserId(user.getUuid());
		docInfo.setDocId(uuid);
		commonService.save((T)docInfo);
	}
	
	@Override
	public void addDocRight(String modeName,String docId,String orgId,int type,String deptId){
		List<Right> rList=rightService.getRightsByProprty(modeName, orgId, docId);
		if(rList==null || rList.size()>0){
			return;
		}
		Right r = new Right();
		r.setDocId(docId);
		r.setOrgId(orgId);
		r.setOperType(1);
		r.setRightType(type);
		r.setModoName(modeName);
		r.setCreateDeptId(deptId);
		rightService.saveRight(r);
		r=null;
	}
	
	public void createRight(T o,String uuid,User user,String rightId){
		//系统默认可见文档角色
		List<Map<String, String>> pRole=roleService.findRoleListPage(null, "4", "");
		if(pRole!=null && pRole.size()>0){
			for(Map ma:pRole){
				if(ma.get("menus")==null || ma.get("menus").toString().equals("") || ma.get("menus").toString().indexOf(rightId)>-1){
					this.addDocRight(o.getClass().getSimpleName(),uuid.toString(),ma.get("uuid").toString(),3);
				}
			}
		}
		
		//关联部门角色
		pRole=roleService.findRoleListPage(null, "4", user.getUserDeptId());
		if(pRole!=null && pRole.size()>0){
			for(Map ma:pRole){
				if(ma.get("menus")==null || ma.get("menus").toString().equals("") || ma.get("menus").toString().indexOf(rightId)>-1){
					this.addDocRight(o.getClass().getSimpleName(),uuid.toString(),ma.get("uuid").toString(),3,user.getUserDeptId());
				}
			}
		}
	}
	//流程
	public void wfFlowMsg(HttpServletRequest request,String docId,Menu menu,boolean isNewDoc,String className){

		BaseWorkFlow baseWorkFlow=null;
		if(isNewDoc){
			baseWorkFlow=new BaseWorkFlow();
			baseWorkFlow.setCreateBy(request.getParameter("createBy"));
			baseWorkFlow.setWfInitiator(request.getParameter("wfInitiator"));
			baseWorkFlow.setCreateDate(new Date());
			baseWorkFlow.setDocId(docId);
		}else{
			docId=request.getParameter("uuid");
			baseWorkFlow=baseWorkFlowService.getWorkFlowByDocId(docId);
		}
		
		baseWorkFlow.setAllUser(request.getParameter("allUser"));
		baseWorkFlow.setMsgTitle(request.getParameter("msgTitle"));
		baseWorkFlow.setCurUser(request.getParameter("curUser"));
		baseWorkFlow.setUserId(request.getParameter("userId"));
		baseWorkFlow.setSubPerson(request.getParameter("subPerson"));
		baseWorkFlow.setSubTime(new Date());
		baseWorkFlow.setWfCurNodeId(request.getParameter("wfCurNodeId"));
		baseWorkFlow.setWfFinishApproval(request.getParameter("wfFinishApproval"));
		baseWorkFlow.setWfWaitApproval(request.getParameter("wfWaitApproval"));
		baseWorkFlow.setWfFlowLogXml(request.getParameter("wfFlowLogXml"));
		baseWorkFlow.setWfGraphXml(request.getParameter("wfGraphXml"));
		baseWorkFlow.setWfPreNodeId(request.getParameter("wfPreNodeId"));
		baseWorkFlow.setWfPreUser(request.getParameter("wfPreUser"));
		baseWorkFlow.setWfPreUserId(request.getParameter("wfPreUserId"));
		baseWorkFlow.setWfMsgUser(request.getParameter("wfMsgUser"));
		baseWorkFlow.setWfProcessXml(request.getParameter("wfProcessXml"));
		baseWorkFlow.setWfRouterId(request.getParameter("wfRouterId"));
		baseWorkFlow.setWfStatus(request.getParameter("wfStatus"));
		baseWorkFlow.setWfTacheName(request.getParameter("wfTacheName"));
		baseWorkFlow.setWfDocLink(request.getParameter("wfDocLink"));
		baseWorkFlow.setIsReject(request.getParameter("isReject"));
		baseWorkFlow.setApprovalOrder(Integer.parseInt(request.getParameter("approvalOrder")));
		baseWorkFlow.setAlreadyUser(request.getParameter("alreadyUser"));
		baseWorkFlow.setCurReadyUser("");
		if(baseWorkFlow.getMsgTitle()==null || baseWorkFlow.getMsgTitle().equals("")){
			if(baseWorkFlow.getWfStatus().equals("2")){
				baseWorkFlow.setMsgTitle("您发起的["+menu.getMenuName()+"]已审核通过！");
			}else if(baseWorkFlow.getWfStatus().equals("1")){
				if(baseWorkFlow.getIsReject()!=null && baseWorkFlow.getIsReject().equals("1")){
					baseWorkFlow.setMsgTitle("您发起的["+menu.getMenuName()+"]已被["+baseWorkFlow.getWfPreUser()+"]拒绝，请及时处理！");
				}else{
					baseWorkFlow.setMsgTitle(baseWorkFlow.getCreateBy()+"的["+menu.getMenuName()+"]，请及时处理！");
				}
			}
		}
		if(isNewDoc){
			baseWorkFlowService.addBaseWorkFlow(baseWorkFlow);
		}else{
			baseWorkFlowService.updateBaseWorkFlow(baseWorkFlow);
		}
		if(baseWorkFlow.getApprovalOrder()==0){
			this.toMsgUser(baseWorkFlow, menu.getUuid(), docId,className);
		}
	}
	
	/**
	 * @param baseWorkFlow
	 * @param rightId
	 * @param docId
	 */
	public void toMsgUser(BaseWorkFlow baseWorkFlow, String rightId,String docId,String className) {
		if(baseWorkFlow.getWfStatus().equals("0")){
			return;
		}
		//待办更改为已阅状态
		User user=(User)ServletActionContext.getRequest().getSession().getAttribute("user");
		MsgInfo minfo=new MsgInfo();
		minfo.setDocId(docId);
		minfo.setUserId(user.getUuid());
		minfo.setMsgType(1);
		List<MsgInfo> msgList=msgInfoService.getMsgList(minfo);
		if(msgList!=null && msgList.size()>0){
			for(MsgInfo m:msgList){
				m.setStatus(2);
				msgInfoService.updateMsg(m);
			}
		}
		
		if(!CommonUtil.isNullOrEmpty(baseWorkFlow.getCurUser()) && !CommonUtil.isNullOrEmpty(baseWorkFlow.getUserId())){
			//生成待办
			String [] uIds=baseWorkFlow.getUserId().split(";");
			for (String uId:uIds) {
				if(StringUtil.isEmpty(uId)){
					continue;
				}
				MsgInfo msgInfo=new MsgInfo();
				if(docId.equals("")){
					msgInfo.setDocId(baseWorkFlow.getDocId());
				}else{
					msgInfo.setDocId(docId);
				}
				msgInfo.setCreateBy(baseWorkFlow.getCreateBy());
				msgInfo.setCreateDate(new Date());
				msgInfo.setMsgTitle(baseWorkFlow.getMsgTitle());
				msgInfo.setUserId(uId);
				msgInfo.setMenuId(rightId);
				if(baseWorkFlow.getWfStatus().equals("1")){
					msgInfo.setMsgType(1);
				}else if(baseWorkFlow.getWfStatus().equals("2")){
					msgInfo.setMsgType(2);
				}else{
					msgInfo.setMsgType(3);
				}
				msgInfoService.saveMsg(msgInfo);
				//处理人可查看文档
				this.addDocRight(className,docId,uId,2);
			}
		}else{
			MsgInfo msgInfo=new MsgInfo();
			if(docId.equals("")){
				msgInfo.setDocId(baseWorkFlow.getDocId());
			}else{
				msgInfo.setDocId(docId);
			}
			msgInfo.setCreateBy(baseWorkFlow.getCreateBy());
			msgInfo.setCreateDate(new Date());
			msgInfo.setMsgTitle(baseWorkFlow.getMsgTitle());
			msgInfo.setUserId(baseWorkFlow.getWfInitiator());
			msgInfo.setMenuId(rightId);
			if(baseWorkFlow.getWfStatus().equals("2")){
				msgInfo.setMsgType(2);
			}
			msgInfoService.saveMsg(msgInfo);
		}
		
		//通知当前路由所定义的人员
		if (!CommonUtil.isNullOrEmpty(baseWorkFlow.getWfMsgUser())) {
			String[] userIds = baseWorkFlow.getWfMsgUser().split(";");
			Set<String> ids = new HashSet<String>();
			for (String o : userIds) {
				if (o != null && !o.equals("")
						&& !o.equals(baseWorkFlow.getWfInitiator())
						&& baseWorkFlow.getUserId().indexOf(o) == -1) {
					ids.add(o);
				}
			}
			Menu menu = menuService.findMenuById(rightId);
			for (String b : ids) {
				MsgInfo msgInfo = new MsgInfo();
				if (docId.equals("")) {
					msgInfo.setDocId(baseWorkFlow.getDocId());
				} else {
					msgInfo.setDocId(docId);
				}
				msgInfo.setCreateBy(baseWorkFlow.getCreateBy());
				msgInfo.setCreateDate(new Date());

				msgInfo.setUserId(b);
				msgInfo.setMenuId(rightId);
				msgInfo.setMsgType(2);
				if (baseWorkFlow.getWfStatus().equals("2")) {
					msgInfo.setMsgTitle(baseWorkFlow.getCreateBy() + "的["+ menu.getMenuName() + "]审核通过，点击查看！");
				} else if (baseWorkFlow.getWfStatus().equals("1")) {
					if (baseWorkFlow.getIsReject().equals("1")) {
						msgInfo.setMsgTitle(baseWorkFlow.getCreateBy() + "的["+ menu.getMenuName() + "]被"+ baseWorkFlow.getWfPreUser() + "拒绝，点击查看！");
					} else {
						msgInfo.setMsgTitle(baseWorkFlow.getCreateBy() + "的[" + menu.getMenuName() + "]通知，点击查看！");
					}
				}
				msgInfoService.saveMsg(msgInfo);
				msgInfo = null;
				// 处理人可查看文档
				this.addDocRight(className, docId, b, 2);
			}
		}
	}

	@Override
	public void toMsgUser(T o,String rightId,String docId){
		//待办更改为已阅状态
		List<MsgInfo> msgList=msgInfoService.getMsgList(docId,1);
		if(msgList!=null && msgList.size()>0){
			for(MsgInfo m:msgList){
				m.setStatus(2);
				msgInfoService.updateMsg(m);
			}
		}
		
		//消息通知
		HashSet h=new HashSet();
		h.add("curUser");
		h.add("userId");
		h.add("createBy");
		h.add("msgTitle");
		h.add("wfStatus");
		h.add("uuid");
		Map map=ReflectObjUtil.getAttrributesParentThreeValues(o,h); 
		if(!CommonUtil.isNullOrEmpty(map.get("curUser")) && !CommonUtil.isNullOrEmpty(map.get("userId"))){
			//生成待办
			String [] uIds=map.get("userId").toString().split(";");
			for (String uId:uIds) {
				MsgInfo msgInfo=new MsgInfo();
				if(docId.equals("")){
					msgInfo.setDocId(map.get("uuid").toString());
				}else{
					msgInfo.setDocId(docId);
				}
				
				msgInfo.setCreateBy(map.get("createBy").toString());
				msgInfo.setCreateDate(new Date());
				msgInfo.setMsgTitle(map.get("msgTitle").toString());
				msgInfo.setUserId(uId);
				msgInfo.setMenuId(rightId);
				if(map.get("wfStatus").toString().equals("1")){
					msgInfo.setMsgType(1);
				}else if(map.get("wfStatus").toString().equals("2")){
					msgInfo.setMsgType(2);
				}else{
					msgInfo.setMsgType(3);
				}
				msgInfoService.saveMsg(msgInfo);
				//处理人可查看文档
				this.addDocRight(o.getClass().getSimpleName(),docId,uId,2);
			}
		}
	}

	@Override
	public void delete(T o) {
		commonService.delete(o);
	}
	
	//使用子查询，返回Map集合查询表头或表体数据
	@Override
	public Map<String, Object> loadMapListForPageHeadOrList(String menuId, PageInfo pageInfo, String orgIds) {
		Menu menu = menuService.findMenuById(menuId);
		if (CommonUtil.isNullOrEmpty(menu)) {
			return null;
		}
		if(menu.getIsPrower()!=null && menu.getIsPrower().equals("0")){
			orgIds="-";
		}else{
			String deptId=dataRightConfigService.findDataRightByMenu(menuId);
			if(deptId.equals("-")){
				orgIds="-";
			}else if(!deptId.equals("")){
				orgIds+="^HT^"+deptId;
			}
		}
		List<T> objList = commonService.getPageDataMapHeadOrList(menu.getEntityClsName(), pageInfo, orgIds);
		int count = commonService.getAllCount(menu.getEntityClsName(), orgIds,true);
		String bodyHtml = "<root>" + menu.getMenuHtmlBody() + "</root>";
		List<Element> eleList = CommonUtil.getXmlChildElements(bodyHtml);
		Set<String> fieldSet = new HashSet<String>();
		for (Element e : eleList) {
			if (!CommonUtil.isNullOrEmpty(e.attributeValue("field"))) {
				fieldSet.add(e.attributeValue("field"));
			} 
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("data", commonService.dataList(objList, fieldSet));
		
		objList=null;
		fieldSet=null;
		eleList=null;
		return map;
	}
	
	//使用子查询，返回Map集合查询表头或表体数据(条件查询)
	@Override
	public Map<String, Object> loadMapListForPageHeadOrListSearch(String menuId, PageInfo pageInfo, String orgIds,List list) {
		Menu menu = menuService.findMenuById(menuId);
		if (CommonUtil.isNullOrEmpty(menu)) {
			return null;
		}
		if(menu.getIsPrower()!=null && menu.getIsPrower().equals("0")){
			orgIds="-";
		}else{
			String deptId=dataRightConfigService.findDataRightByMenu(menuId);
			if(deptId.equals("-")){
				orgIds="-";
			}else if(!deptId.equals("")){
				orgIds+="^HT^"+deptId;
			}
		}
		List<T> objList = commonService.getPageDataMapHeadOrListSearch(menu.getEntityClsName(), pageInfo, orgIds,list);
		int count = commonService.getAllCountSearch(menu.getEntityClsName(), orgIds,true,list);
		String bodyHtml = "<root>" + menu.getMenuHtmlBody() + "</root>";
		List<Element> eleList = CommonUtil.getXmlChildElements(bodyHtml);
		Set<String> fieldSet = new HashSet<String>();
		for (Element e : eleList) {
			if (!CommonUtil.isNullOrEmpty(e.attributeValue("field"))) {
				fieldSet.add(e.attributeValue("field"));
			} 
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("data", commonService.dataList(objList, fieldSet));
		
		objList=null;
		fieldSet=null;
		eleList=null;
		return map;
	}

	//查询视图及表名分页
	@Override
	public Map<String, Object> loadMapListForPageview(String menuId, PageInfo pageInfo,String viewName,String userPerEname,boolean flag) {
		Menu menu = menuService.findMenuById(menuId);
		if (CommonUtil.isNullOrEmpty(menu)) {
			return null;
		}
		List<T> objList = commonService.loadMapListForPageView(viewName,pageInfo,userPerEname,flag);
		int count = commonService.getAllCountView(viewName,userPerEname,flag);
		String bodyHtml = "<root>" + menu.getMenuHtmlBody() + "</root>";
		List<Element> eleList = CommonUtil.getXmlChildElements(bodyHtml);
		Set<String> fieldSet = new HashSet<String>();
		for (Element e : eleList) {
			if (!CommonUtil.isNullOrEmpty(e.attributeValue("field"))) {
				fieldSet.add(e.attributeValue("field"));
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("data", commonService.dataList(objList, fieldSet));
		
		objList=null;
		fieldSet=null;
		eleList=null;
		return map;
	}

	//查询视图及表名
	@Override
	public List<Map<String, String>> loadListView(String menuId,String viewName,String userPerEname,boolean flag) {
		Menu menu = menuService.findMenuById(menuId);
		if (CommonUtil.isNullOrEmpty(menu)) {
			return null;
		}
		List<T> objList = commonService.loadListView(viewName,userPerEname,flag);
		String bodyHtml = "<root>" + menu.getMenuHtmlBody() + "</root>";
		List<Element> eleList = CommonUtil.getXmlChildElements(bodyHtml);
		Set<String> fieldSet = new HashSet<String>();
		for (Element e : eleList) {
			if (!CommonUtil.isNullOrEmpty(e.attributeValue("field"))) {
				fieldSet.add(e.attributeValue("field"));
			} 
		}
		
		objList=null;
		fieldSet=null;
		eleList=null;
		return commonService.dataList(objList, fieldSet);
	}
	
	
	//根据表头id查询表体数据列表
	@Override
	public  List<T> loadList(String entityClsName, String pid){
		return commonService.loadList(entityClsName, pid);
	}
	
	
	//使用子查询，返回Map集合表头表体关联数据
	@Override
	public Map<String, Object> loadMapListForPageHeadList(String menuId, PageInfo pageInfo, String orgIds) {
		Menu menu = menuService.findMenuById(menuId);
		if (CommonUtil.isNullOrEmpty(menu)) {
			return null;
		}
		if(menu.getIsPrower()!=null && menu.getIsPrower().equals("0")){
			orgIds="-";
		}else{
			String deptId=dataRightConfigService.findDataRightByMenu(menuId);
			if(deptId.equals("-")){
				orgIds="-";
			}else if(!deptId.equals("")){
				orgIds+="^HT^"+deptId;
			}
		}
		List<T> objList = commonService.loadMapListForPageHeadList(menu.getEntityClsName(), pageInfo, orgIds);
		int count = commonService.getAllCount(menu.getEntityClsName(), orgIds,false);
		String bodyHtml = "<root>" + menu.getMenuHtmlBody() + "</root>";
		List<Element> eleList = CommonUtil.getXmlChildElements(bodyHtml);
		Set<String> fieldSet = new HashSet<String>();
		for (Element e : eleList) {
			if (!CommonUtil.isNullOrEmpty(e.attributeValue("field"))) {
				fieldSet.add(e.attributeValue("field"));
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("data", commonService.dataList(objList, fieldSet));
		
		objList=null;
		fieldSet=null;
		eleList=null;
		return map;
	}
	
	//使用子查询，返回Map集合表头表体关联数据(条件查询)
	@Override
	public Map<String, Object> loadMapListForPageHeadListSearch(String menuId, PageInfo pageInfo, String orgIds,List list) {
		Menu menu = menuService.findMenuById(menuId);
		if (CommonUtil.isNullOrEmpty(menu)) {
			return null;
		}
		if(menu.getIsPrower()!=null && menu.getIsPrower().equals("0")){
			orgIds="-";
		}else{
			String deptId=dataRightConfigService.findDataRightByMenu(menuId);
			if(deptId.equals("-")){
				orgIds="-";
			}else if(!deptId.equals("")){
				orgIds+="^HT^"+deptId;
			}
		}
		List<T> objList = commonService.loadMapListForPageHeadListSearch(menu.getEntityClsName(), pageInfo, orgIds,list);
		int count = commonService.getAllCountSearch(menu.getEntityClsName(), orgIds,false,list);
		String bodyHtml = "<root>" + menu.getMenuHtmlBody() + "</root>";
		List<Element> eleList = CommonUtil.getXmlChildElements(bodyHtml);
		Set<String> fieldSet = new HashSet<String>();
		for (Element e : eleList) {
			if (!CommonUtil.isNullOrEmpty(e.attributeValue("field"))) {
				fieldSet.add(e.attributeValue("field"));
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("data", commonService.dataList(objList, fieldSet));
		
		objList=null;
		fieldSet=null;
		eleList=null;
		return map;
	}
	
	
	@Override
	public void update(T o,String rigthData) throws Exception {
		commonService.update(o);
		if (!CommonUtil.isNullOrEmpty(rigthData)) {
			if (!"[]".equals(rigthData)) {
				Method m = (Method) o.getClass().getMethod("getUuid");
				String uuid = (String) m.invoke(o);
				rightService.updateRight(uuid, o.getClass().getSimpleName(), rigthData);
			}
		}
	}

	@Override
	public void saveOrUpdate(T o) {
		HashSet h=new HashSet();
		h.add("curDocId");
		h.add("wfStatus");
		h.add("uuid");
		Map map=ReflectObjUtil.getAttrributesParentThreeValues(o,h);
		commonService.saveOrUpdate(o);
		
		HttpServletRequest request = ServletActionContext.getRequest();
		Menu menu = menuService.findMenuById(request.getParameter("menuId"));
		//this.toMsgUser(o,menu.getUuid(),"");
		if(menu.getMenuIsHasWF().equals("1")){
			if(map.get("uuid")!=null && !map.get("uuid").toString().equals("")){
				this.wfFlowMsg(request, "", menu, false,o.getClass().getSimpleName());
				map=ReflectObjUtil.getAttrributesParentThreeValues(o,h);
			}else{
				map=ReflectObjUtil.getAttrributesParentThreeValues(o,h);
				this.wfFlowMsg(request, map.get("uuid").toString(), menu, true,o.getClass().getSimpleName());
			}
			if(!CommonUtil.isNullOrEmpty(map.get("curDocId")) && !CommonUtil.isNullOrEmpty(map.get("wfStatus")) && "2".equals(map.get("wfStatus"))){
				//如果是项目立项
				if(ClassModelUtil.STATUS_PROAMANAGEHEADMODEL.equals(menu.getEntityClsName())){
					adenexaService.updateAdenexaWfStatus(map.get("curDocId").toString(),map.get("uuid").toString());
				}else{
					adenexaService.updateAdenexaWfStatus(map.get("curDocId").toString());
				}
			}
		}else{
			//不走流程时待办消息（需要页面有必要字段）
			this.toMsgUser(o,menu.getUuid(),"");
		}
	}
	
	@Override
	public void updateOrSave(T o,Menu menu) throws Exception{
		commonService.update(o);
		if(menu!=null){
			Method m = (Method) o.getClass().getMethod("getUuid");
			String uuid = (String) m.invoke(o);
			rightService.addRight(uuid, o.getClass().getSimpleName(), menu.getDataRightJson());
		}
	}

	public CommonService<T> getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService<T> commonService) {
		this.commonService = commonService;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public RightService getRightService() {
		return rightService;
	}

	public void setRightService(RightService rightService) {
		this.rightService = rightService;
	}

	@Override
	public T get(Class<T> c, Serializable id) {
		return commonService.get(c, id);
	}

	@Override
	public T getEntityByID(String entityClsName, String uuid) {
		return commonService.getEntityByID(entityClsName, uuid);
	}
	
	@Override
	public void deleteId(String model,String attr,String param){
		commonService.deleteId(model, attr, param);
	}
	
	
	
	/**
	 * @return the roleService
	 */
	public RoleService getRoleService() {
		return roleService;
	}

	/**
	 * @param roleService the roleService to set
	 */
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Override
	public List<Map<String, String>> loadListForList(String menuId,String orgIds) {
		Menu menu = menuService.findMenuById(menuId);
		if (CommonUtil.isNullOrEmpty(menu)) {
			return null;
		}
		if(menu.getIsPrower()!=null && menu.getIsPrower().equals("0")){
			orgIds="-";
		}else{
			String deptId=dataRightConfigService.findDataRightByMenu(menuId);
			if(deptId.equals("-")){
				orgIds="-";
			}else if(!deptId.equals("")){
				orgIds+="^HT^"+deptId;
			}
		}
		List<T> objList = commonService.loadListForList(menu.getEntityClsName(),orgIds);
		String bodyHtml = "<root>" + menu.getMenuHtmlBody() + "</root>";
		List<Element> eleList = CommonUtil.getXmlChildElements(bodyHtml);
		Set<String> fieldSet = new HashSet<String>();
		for (Element e : eleList) {
			if (!CommonUtil.isNullOrEmpty(e.attributeValue("field"))) {
				fieldSet.add(e.attributeValue("field"));
			} 
		}
		return commonService.dataList(objList, fieldSet);
	}
	
	//使用子查询，返回Map集合查询表体数据
	@Override
	public Map<String, Object> loadMapListForPageList(String menuId, PageInfo pageInfo, String orgIds) {
		Menu menu = menuService.findMenuById(menuId);
		if (CommonUtil.isNullOrEmpty(menu)) {
			return null;
		}
		if(menu.getIsPrower()!=null && menu.getIsPrower().equals("0")){
			orgIds="-";
		}else{
			String deptId=dataRightConfigService.findDataRightByMenu(menuId);
			if(deptId.equals("-")){
				orgIds="-";
			}else if(!deptId.equals("")){
				orgIds+="^HT^"+deptId;
			}
		}
		List<T> objList = commonService.loadMapListForPageList(menu.getEntityClsName(), pageInfo, orgIds);
		int count = commonService.getAllCountList(menu.getEntityClsName(), orgIds);
		String bodyHtml = "<root>" + menu.getMenuHtmlBody() + "</root>";
		List<Element> eleList = CommonUtil.getXmlChildElements(bodyHtml);
		Set<String> fieldSet = new HashSet<String>();
		for (Element e : eleList) {
			if (!CommonUtil.isNullOrEmpty(e.attributeValue("field"))) {
				fieldSet.add(e.attributeValue("field"));
			} 
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("data", commonService.dataList(objList, fieldSet));
		
		objList=null;
		fieldSet=null;
		eleList=null;
		return map;
	}

	@Override
	public void deleteIdList(String model, String param) {
		commonService.deleteIdList(model, param);
	}

	@Override
	public void update(T o) {
		commonService.update(o);
	}
	
	//页面配置字段列表分页查询
	@Override
	public  Map<String, Object>  loadMapListForPageHead(String model,PageInfo pageInfo, String orgIds) {
		Menu menu=menuService.findOneMenuById("entityClsName",model);
		if(menu!=null && menu.getIsPrower()!=null && menu.getIsPrower().equals("0")){
			orgIds="-";
		}else{
			String deptId=dataRightConfigService.findDataRightByMenu(menu.getUuid());
			if(deptId.equals("-")){
				orgIds="-";
			}else if(!deptId.equals("")){
				orgIds+="^HT^"+deptId;
			}
		}
		List<T> objList = commonService.getPageDataMapHeadOrList(model, pageInfo, orgIds);
		int count = commonService.getAllCount(model, orgIds,true);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("data", objList);
		
		return map;
	}
	
	//页面配置字段列表分页查询(条件查询)
	@Override
	public  Map<String, Object>  loadMapListForPageHeadSearch(String model,PageInfo pageInfo, String orgIds,List list) {
		Menu menu=menuService.findOneMenuById("entityClsName",model);
		if(menu!=null && menu.getIsPrower()!=null && menu.getIsPrower().equals("0")){
			orgIds="-";
		}else{
			String deptId=dataRightConfigService.findDataRightByMenu(menu.getUuid());
			if(deptId.equals("-")){
				orgIds="-";
			}else if(!deptId.equals("")){
				orgIds+="^HT^"+deptId;
			}
		}
		List<T> objList = commonService.getPageDataMapHeadOrListSearch(model, pageInfo, orgIds,list);
		int count = commonService.getAllCountSearch(model, orgIds,true,list);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("data", objList);
		return map;
	}

	/* (non-Javadoc)
	 * @see htos.coresys.service.CommonFacadeService#getEntityByProperty(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public T getEntityByProperty(String entityClsName, String property,
			String value) {
		return commonService.getEntityByProperty(entityClsName, property, value);
	}

	/* (non-Javadoc)
	 * @see htos.coresys.service.CommonFacadeService#loadList(java.lang.String)
	 */
	@Override
	public List<T> loadList(String enClsName) {
		return commonService.loadAllList(enClsName);
	}

	@Override
	public List<T> getListByProperty(String entityClsName, String property,
			String value) {
		return commonService.getListByProperty(entityClsName, property, value);
	}

	@Override
	public void deleteRightId(String model, String attr, String param) {
		commonService.deleteRightId(model,attr,param);
	}
	
	@Override
	public void deleteRight(String model,String param) {
		commonService.deleteRight(model, param);
	}

	@Override
	public List<T> getListProperty(String entityClsName, String property, String value){
		return commonService.getListProperty(entityClsName, property, value);
	}

	@Override
	public void deleteObjectRight(T o) {
		commonService.deleteObjectRight(o);
	}


	//使用子查询，返回Map集合查询表头或表体或视图数据
	@Override
	public Map<String, Object> loadMapListForPageHeadOrListOrview(String menuId, PageInfo pageInfo, String orgIds) {
		Menu menu = menuService.findMenuById(menuId);
		if (CommonUtil.isNullOrEmpty(menu)) {
			return null;
		}
		if(menu.getIsPrower()!=null && menu.getIsPrower().equals("0")){
			orgIds="-";
		}else{
			String deptId=dataRightConfigService.findDataRightByMenu(menuId);
			if(deptId.equals("-")){
				orgIds="-";
			}else if(!deptId.equals("")){
				orgIds+="^HT^"+deptId;
			}
		}
		List<T> objList = commonService.getPageDataMapHeadOrListOrview(menu.getEntityClsName(),menu.getQueryEntityClsname(), pageInfo, orgIds);
		int count = commonService.getAllCount(menu.getEntityClsName(),menu.getQueryEntityClsname(), orgIds,true);
		String bodyHtml = "<root>" + menu.getMenuHtmlBody() + "</root>";
		List<Element> eleList = CommonUtil.getXmlChildElements(bodyHtml);
		Set<String> fieldSet = new HashSet<String>();
		for (Element e : eleList) {
			if (!CommonUtil.isNullOrEmpty(e.attributeValue("field"))) {
				fieldSet.add(e.attributeValue("field"));
			} 
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("data", commonService.dataList(objList, fieldSet));
		
		objList=null;
		fieldSet=null;
		eleList=null;
		return map;
	}
	
	//使用子查询，返回Map集合查询表头或表体或视图数据(条件查询)
	@Override
	public Map<String, Object> loadMapListForPageHeadOrListOrViewSearch(String menuId, PageInfo pageInfo, String orgIds,List list) {
		Menu menu = menuService.findMenuById(menuId);
		if (CommonUtil.isNullOrEmpty(menu)) {
			return null;
		}
		if(menu.getIsPrower()!=null && menu.getIsPrower().equals("0")){
			orgIds="-";
		}else{
			String deptId=dataRightConfigService.findDataRightByMenu(menuId);
			if(deptId.equals("-")){
				orgIds="-";
			}else if(!deptId.equals("")){
				orgIds+="^HT^"+deptId;
			}
		}
		List<T> objList = commonService.getPageDataMapHeadOrListSearch(menu.getEntityClsName(),menu.getQueryEntityClsname(), pageInfo, orgIds,list);
		int count = commonService.getAllCountSearch(menu.getEntityClsName(),menu.getQueryEntityClsname(), orgIds,true,list);
		String bodyHtml = "<root>" + menu.getMenuHtmlBody() + "</root>";
		List<Element> eleList = CommonUtil.getXmlChildElements(bodyHtml);
		Set<String> fieldSet = new HashSet<String>();
		for (Element e : eleList) {
			if (!CommonUtil.isNullOrEmpty(e.attributeValue("field"))) {
				fieldSet.add(e.attributeValue("field"));
			} 
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("data", commonService.dataList(objList, fieldSet));
		
		objList=null;
		fieldSet=null;
		eleList=null;
		return map;
	}

	@Override
	public List<T> getListByNotProperty(String entityClsName, String property,String value) {
		return commonService.getListByNotProperty(entityClsName, property, value);
	}

	@Override
	public List<T> getListByLikeProperty(String entityClsName, String property,String value) {
		return commonService.getListByLikeProperty(entityClsName, property, value);
	}

	@Override
	public T getEntityByNotProperty(String entityClsName, String property,String value) {
		return commonService.getEntityByNotProperty(entityClsName, property, value);
	}

	@Override
	public T getEntityByLikeProperty(String entityClsName, String property,String value) {
		return commonService.getEntityByLikeProperty(entityClsName, property, value);
	}

	@Override
	public List<T> loadListOrgIds(String enClsName, String orgIds) {
		return commonService.loadListOrgIds(enClsName, orgIds);
	}

	@Override
	public List<T> getListByPropertyOrgIds(String entityClsName,String property, String value, String orgIds) {
		Menu menu=menuService.findOneMenuById("entityClsName",entityClsName);
		if(menu!=null && menu.getIsPrower()!=null && menu.getIsPrower().equals("0")){
			orgIds=null;
		}else{
			String deptId=dataRightConfigService.findDataRightByMenu(menu.getUuid());
			if(deptId.equals("-")){
				orgIds="-";
			}else if(!deptId.equals("")){
				orgIds+="^HT^"+deptId;
			}
		}
		return commonService.getListByPropertyOrgIds(entityClsName, property, value, orgIds);
	}

	@Override
	public List<T> getListPropertyOrgIds(String entityClsName, String property,String value, String orgIds) {
		return commonService.getListPropertyOrgIds(entityClsName, property, value, orgIds);
	}

	@Override
	public List<T> getListByNotPropertyOrgIds(String entityClsName,String property, String value, String orgIds) {
		return commonService.getListByNotPropertyOrgIds(entityClsName, property, value, orgIds);
	}

	@Override
	public List<T> getListByLikePropertyOrgIds(String entityClsName,String property, String value, String orgIds) {
		return commonService.getListByLikePropertyOrgIds(entityClsName, property, value, orgIds);
	}

	@Override
	public T getEntityByIDOrgIds(String entityClsName, String uuid,String orgIds) {
		return commonService.getEntityByIDOrgIds(entityClsName, uuid, orgIds);
	}

	@Override
	public T getEntityByPropertyOrgIds(String entityClsName, String property,String value, String orgIds) {
		return commonService.getEntityByPropertyOrgIds(entityClsName, property, value, orgIds);
	}

	@Override
	public T getEntityByNotPropertyOrgIds(String entityClsName,String property, String value, String orgIds) {
		return commonService.getEntityByNotPropertyOrgIds(entityClsName, property, value, orgIds);
	}

	@Override
	public T getEntityByLikePropertyOrgIds(String entityClsName,String property, String value, String orgIds) {
		return commonService.getEntityByLikePropertyOrgIds(entityClsName, property, value, orgIds);
	}

	@Override
	public List<T> getHqlList(String hql) {
		return commonService.getHqlList(hql);
	}

	@Override
	public T getHqlObject(String hql) {
		return commonService.getHqlObject(hql);
	}

	@Override
	public String save(T o) {
		Serializable uuid=commonService.save(o);
		return uuid.toString();
	}

	@Override
	public Map<String, Object> loadMapListForNotOrgPageSearch(String menuId,PageInfo pageInfo, List list) {
		Menu menu = menuService.findMenuById(menuId);
		if (CommonUtil.isNullOrEmpty(menu)) {
			return null;
		}
		List<T> objList = commonService.getPageDataMapNotOrgSearch(menu.getEntityClsName(),menu.getQueryEntityClsname(), pageInfo, list);
		int count = commonService.getAllCountNotOrgSearch(menu.getEntityClsName(),menu.getQueryEntityClsname(), true,list);
		String bodyHtml = "<root>" + menu.getMenuHtmlBody() + "</root>";
		List<Element> eleList = CommonUtil.getXmlChildElements(bodyHtml);
		Set<String> fieldSet = new HashSet<String>();
		for (Element e : eleList) {
			if (!CommonUtil.isNullOrEmpty(e.attributeValue("field"))) {
				fieldSet.add(e.attributeValue("field"));
			} 
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("data", commonService.dataList(objList, fieldSet));
		return map;
	}

	@Override
	public Map<String, Object> loadMapListForNotOrgPage(String menuId,PageInfo pageInfo) {
		Menu menu = menuService.findMenuById(menuId);
		if (CommonUtil.isNullOrEmpty(menu)) {
			return null;
		}
		List<T> objList = commonService.getPageDataMapNotOrg(menu.getEntityClsName(),menu.getQueryEntityClsname(), pageInfo);
		int count = commonService.getAllCountNotOrg(menu.getEntityClsName(),menu.getQueryEntityClsname(), true);
		String bodyHtml = "<root>" + menu.getMenuHtmlBody() + "</root>";
		List<Element> eleList = CommonUtil.getXmlChildElements(bodyHtml);
		Set<String> fieldSet = new HashSet<String>();
		for (Element e : eleList) {
			if (!CommonUtil.isNullOrEmpty(e.attributeValue("field"))) {
				fieldSet.add(e.attributeValue("field"));
			} 
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("data", commonService.dataList(objList, fieldSet));
		
		objList=null;
		fieldSet=null;
		eleList=null;
		return map;
	}
	
	@Override
	public void saveOrUpdate(String sql) {
		commonService.saveOrUpdate(sql);
	}

	public AdenexaService getAdenexaService() {
		return adenexaService;
	}

	public void setAdenexaService(AdenexaService adenexaService) {
		this.adenexaService = adenexaService;
	}
	
}
