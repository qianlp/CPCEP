package htos.coresys.service.impl;

import htos.common.util.ClassModelUtil;
import htos.common.util.StringUtil;
import htos.coresys.entity.BaseWorkFlow;
import htos.coresys.entity.Menu;
import htos.coresys.entity.Right;
import htos.coresys.entity.Role;
import htos.coresys.entity.User;
import htos.coresys.service.BaseWorkFlowService;
import htos.coresys.service.CommonService;
import htos.coresys.service.CommonWorkFlowService;
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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

public class CommonWorkFlowServiceImpl<T> implements CommonWorkFlowService<T> {
	private CommonService<T> commonService;
	private MenuService menuService;
	private RightService rightService;
	private RoleService roleService;
	private MsgInfoService msgInfoService;
	private BaseWorkFlowService baseWorkFlowService;
	private AdenexaService adenexaService;
	private DataRightConfigService dataRightConfigService;
	
	
	public void saveOrUpdate(T o){
		HashSet h=new HashSet();
		h.add("curDocId");
		h.add("wfStatus");
		h.add("uuid");
		Map map=ReflectObjUtil.getAttrributesParentThreeValues(o,h);
		if(map.get("uuid")!=null && !map.get("uuid").toString().equals("")){
			commonService.saveOrUpdate(o);
		}
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String menuId = request.getParameter("menuId");
		Menu menu = menuService.findMenuById(menuId);
		//this.toMsgUser(o,menu.getUuid(),"");
		if(menu.getMenuIsHasWF().equals("1")){
			if(map.get("uuid")!=null && !map.get("uuid").toString().equals("")){
				this.wfFlowMsg(request, "", menu, false,o.getClass().getSimpleName());
				map=ReflectObjUtil.getAttrributesParentThreeValues(o,h);
			}else{
				map=ReflectObjUtil.getAttrributesParentThreeValues(o,h);
				this.wfFlowMsg(request, map.get("uuid").toString(), menu, true,o.getClass().getSimpleName());
				//this.wfFlowMsg(request, request.getParameter("biddingFileUuid"), menu, true,o.getClass().getSimpleName());//更改为项目ID
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
		
		}
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
				//this.addDocRight(o.getClass().getSimpleName(),uuid.toString(),user.getUuid(),2,user.getUserDeptId());
				
				if(menu.getMenuIsHasWF().equals("1")){
					this.wfFlowMsg(request, uuid.toString(), menu, true,o.getClass().getSimpleName());
				}else{
					//不走流程时待办消息（需要页面有必要字段）
					//this.toMsgUser(o,rightId,uuid.toString());
				}
				
				//当前模块角色默认可见文档
				Role role=(Role) commonService.getEntityByProperty("Role", "modeName", o.getClass().getSimpleName());
				if(role!=null){
					this.addDocRight(o.getClass().getSimpleName(),uuid.toString(),role.getUuid(),3);
				}
				
				//添加默认查看权限
				//this.createRight(o, uuid.toString(), user,rightId);

				//添加默认查看权限
				//this.addDocInfo(user, uuid.toString());
		}
		return uuid.toString();
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
				//docId =request.getParameter("biddingFileUuid");//更改为项目ID
				//baseWorkFlow=baseWorkFlowService.getWorkFlowByDocId(menu.getUuid());
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
				String biddingFileUuid = request.getParameter("biddingFileUuid");//新增项目ID
				this.toMsgUser(baseWorkFlow, menu.getUuid(), docId,className,biddingFileUuid);
			}
		}
		/**
		 * @param baseWorkFlow
		 * @param rightId
		 * @param docId
		 */
		public void toMsgUser(BaseWorkFlow baseWorkFlow, String rightId,String docId,String className,String biddingFileUuid) {
			if(baseWorkFlow.getWfStatus().equals("0")){
				return;
			}
			//待办更改为已阅状态
			User user=(User)ServletActionContext.getRequest().getSession().getAttribute("user");
			MsgInfo minfo=new MsgInfo();
			minfo.setDocId(docId);
			minfo.setUserId(user.getUuid());
			minfo.setBiddingFileUuid(biddingFileUuid);
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
					msgInfo.setBiddingFileUuid(biddingFileUuid);
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
				msgInfo.setBiddingFileUuid(biddingFileUuid);
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
					msgInfo.setBiddingFileUuid(biddingFileUuid);
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
		//为文档添加阅读权限
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
		public RoleService getRoleService() {
			return roleService;
		}
		public void setRoleService(RoleService roleService) {
			this.roleService = roleService;
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
		public AdenexaService getAdenexaService() {
			return adenexaService;
		}
		public void setAdenexaService(AdenexaService adenexaService) {
			this.adenexaService = adenexaService;
		}
		public DataRightConfigService getDataRightConfigService() {
			return dataRightConfigService;
		}
		public void setDataRightConfigService(
				DataRightConfigService dataRightConfigService) {
			this.dataRightConfigService = dataRightConfigService;
		}
		
}
