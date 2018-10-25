package htos.business.action.bid;

import htos.business.dto.BidFileWithProject;
import htos.business.dto.SupplierScoreInfo;
import htos.business.entity.bid.CompBidEval;
import htos.business.entity.bid.SupplierBid;
import htos.business.entity.bid.SupplierScore;
import htos.business.entity.bid.TechBidEval;
import htos.business.entity.biddingFile.BiddingFileRelease;
import htos.business.entity.supplier.view.ViewUserSupplier;
import htos.business.service.bid.BidEvaluationService;
import htos.business.service.bid.SupplierBidService;
import htos.business.service.bidFileRelease.BidFileReleaseService;
import htos.common.util.StringUtil;
import htos.coresys.action.BaseAction;
import htos.coresys.entity.BaseWorkFlow;
import htos.coresys.entity.Dept;
import htos.coresys.entity.DocInfo;
import htos.coresys.entity.Menu;
import htos.coresys.entity.User;
import htos.coresys.entity.WorkFlow;
import htos.coresys.service.BaseWorkFlowService;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.CommonWorkFlowService;
import htos.coresys.service.MenuFacadeService;
import htos.coresys.service.MenuService;
import htos.coresys.service.WorkFlowService;
import htos.coresys.util.CommonUtil;
import htos.sysfmt.message.entity.MsgInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BidEvaluationAction extends ActionSupport implements ModelDriven<Object> {
	private TechBidEval techBidEval;
	private BidEvaluationService bidEvaluationService;
	private CommonFacadeService<TechBidEval> techBidEvalCommonFacadeService;
	private CommonFacadeService<BiddingFileRelease> biddingFileReleaseCommonFacadeService;
	private BidFileReleaseService bidFileReleaseService;
	private SupplierBidService supplierBidService;
	private CommonFacadeService<CompBidEval> compBidEvalCommonFacadeService ;
	private MenuFacadeService menuFacadeService;
	private WorkFlowService workFlowService;
	private CommonFacadeService<Object> commonFacadeService;
	private CommonWorkFlowService<CompBidEval> commonWorkFlowService;
	private CommonWorkFlowService<TechBidEval> commontechFlowService;
	protected String isRead = "false";
	protected String docNo="";
	protected DocInfo docInfo;
	protected BaseWorkFlowService baseWorkFlowService;
	public CommonFacadeService<CompBidEval> getCompBidEvalCommonFacadeService() {
		return compBidEvalCommonFacadeService;
	}

	public void setCompBidEvalCommonFacadeService(CommonFacadeService<CompBidEval> compBidEvalCommonFacadeService) {
		this.compBidEvalCommonFacadeService = compBidEvalCommonFacadeService;
	}

	public CommonFacadeService<BiddingFileRelease> getBiddingFileReleaseCommonFacadeService() {
		return biddingFileReleaseCommonFacadeService;
	}

	public void setBiddingFileReleaseCommonFacadeService(CommonFacadeService<BiddingFileRelease> biddingFileReleaseCommonFacadeService) {
		this.biddingFileReleaseCommonFacadeService = biddingFileReleaseCommonFacadeService;
	}

	public BidFileReleaseService getBidFileReleaseService() {
		return bidFileReleaseService;
	}

	public void setBidFileReleaseService(BidFileReleaseService bidFileReleaseService) {
		this.bidFileReleaseService = bidFileReleaseService;
	}

	public SupplierBidService getSupplierBidService() {
		return supplierBidService;
	}

	public void setSupplierBidService(SupplierBidService supplierBidService) {
		this.supplierBidService = supplierBidService;
	}

	public CommonFacadeService<TechBidEval> getTechBidEvalCommonFacadeService() {
		return techBidEvalCommonFacadeService;
	}

	public void setTechBidEvalCommonFacadeService(CommonFacadeService<TechBidEval> techBidEvalCommonFacadeService) {
		this.techBidEvalCommonFacadeService = techBidEvalCommonFacadeService;
	}

	public TechBidEval getTechBidEval() {
		return techBidEval;
	}

	public void setTechBidEval(TechBidEval techBidEval) {
		this.techBidEval = techBidEval;
	}

	public BidEvaluationService getBidEvaluationService() {
		return bidEvaluationService;
	}

	public void setBidEvaluationService(BidEvaluationService bidEvaluationService) {
		this.bidEvaluationService = bidEvaluationService;
	}
   /**
    * 主页面技术评标
    */
	public String openTech(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String bidFileUuid = request.getParameter("bidFileUuid");
		techBidEval = bidEvaluationService.findByBidFile(bidFileUuid);
		if(techBidEval==null){
			openTechBidEval(bidFileUuid);
		}else{
			techBid(bidFileUuid);
		}
		return "success";
	}
	/**
	 * 主页面进入技术招标（uuid不为null）
	 */
	public void techBid(String bidFileUuid){
		 HttpServletRequest request = ServletActionContext.getRequest();
		 HttpSession session = request.getSession();
		// String bidFileUuid = request.getParameter("bidFileUuid");
			BiddingFileRelease entity = biddingFileReleaseCommonFacadeService.getEntityByID(BiddingFileRelease.class.getSimpleName(), bidFileUuid);
			techBidEval = bidEvaluationService.findByBidFile(bidFileUuid);
			request.setAttribute("bidFile", entity);
			//获取供应商
			List<ViewUserSupplier> suppliers = supplierBidService.findSuppliers(bidFileUuid);
			request.setAttribute("suppliers", suppliers);
			
			//String menuId = ServletActionContext.getRequest().getParameter("menuId");
			String menuId="40288481659e0b8d01659e1517180000";
			//获取流程中相关信
	       Menu menu = menuFacadeService.findMenuById(menuId);
	   	WorkFlow workFlow;
	   	BaseWorkFlow baseWorkFlow;
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
			//获取文档制单信息
		/*	docInfo=(DocInfo)commonFacadeService.getEntityByProperty("DocInfo", "docId", uuid);
			if(docInfo==null){docInfo=new DocInfo();}
			*/
		}
		
		//	menu = menuFacadeService.findMenuById(menuId);
			//获取流程
					if(menu.getMenuIsHasWF().equals("1")){
						baseWorkFlow=baseWorkFlowService.getWorkFlowByDocId(techBidEval.getUuid());
						request.setAttribute("baseWorkFlow",baseWorkFlow);
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
							request.setAttribute("workFlow", workFlow);
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
					request.setAttribute("comObj", techBidEval);
					request.setAttribute("isRead", isRead);
	}
	
	
	/**
	 * 技术评标
	 */
	public String openTechBidEval(String bidFileUuid) {
		HttpServletRequest request = ServletActionContext.getRequest();
		//String bidFileUuid = request.getParameter("bidFileUuid");
		BiddingFileRelease entity = biddingFileReleaseCommonFacadeService.getEntityByID(BiddingFileRelease.class.getSimpleName(), bidFileUuid);
		techBidEval = bidEvaluationService.findByBidFile(bidFileUuid);
		WorkFlow workFlow;
		request.setAttribute("bidFile", entity);
		//获取供应商
		List<ViewUserSupplier> suppliers = supplierBidService.findSuppliers(bidFileUuid);
		request.setAttribute("suppliers", suppliers);
		//获取流程中相关信息
        String menuId="40288481659e0b8d01659e1517180000";
        Menu menu = menuFacadeService.findMenuById(menuId);
        //获取流程
       if(menu.getMenuIsHasWF().equals("1")){
      		workFlow=workFlowService.getWorkFlowByMenuId(menuId);
      		ServletActionContext.getRequest().setAttribute("workFlow",workFlow);
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
		ServletActionContext.getRequest().setAttribute("comObj",techBidEval);
		request.setAttribute("isRead", false);
		ServletActionContext.getRequest().setAttribute("docInfo",docInfo);
		ServletActionContext.getRequest().setAttribute("curDocId",UUID.randomUUID());
		ServletActionContext.getRequest().setAttribute("docBusId", ServletActionContext.getRequest().getParameter("docBusinessId"));
		return ActionSupport.SUCCESS;
	}
	
	public String openComp(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String bidFileUuid = request.getParameter("bidFileUuid");
		CompBidEval bidEval = bidEvaluationService.findCompByBidFile(bidFileUuid);
		if(bidEval==null){
			openCompBidEval(bidFileUuid);
		}else{
			openComp(bidFileUuid);
		}
		return "success";
	}
	/**
	 * 主页面综合评审
	 */
	public void openComp(String bidFileUuid){
		HttpServletRequest request = ServletActionContext.getRequest();
		BiddingFileRelease entity = biddingFileReleaseCommonFacadeService.getEntityByID(BiddingFileRelease.class.getSimpleName(), bidFileUuid);
		techBidEval = bidEvaluationService.findByBidFile(bidFileUuid);
		CompBidEval compBidEval = bidEvaluationService.findCompByBidFile(bidFileUuid);
		request.setAttribute("bidFile", entity);
		request.setAttribute("compBidEval", compBidEval);
		request.setAttribute("comObj", compBidEval);
		if(compBidEval !=null)
		 request.setAttribute("role", "view");
		//获取项目信息
		BidFileWithProject bidFileWithProject = bidFileReleaseService.findBidFileWithProject(bidFileUuid);
		request.setAttribute("bidFileWithProject", bidFileWithProject);
		//获取设备参数
		String deviceStr = bidFileReleaseService.findPurchaseDeviceStr(bidFileUuid);
		request.setAttribute("deviceStr",deviceStr);
		//获取供应商
		List<ViewUserSupplier> suppliers = supplierBidService.findSuppliers(bidFileUuid);
		request.setAttribute("suppliers", suppliers);
		String menuId="402880e6658db7ec01658dcc6ad30000";
		//获取流程中相关信
        Menu menu = menuFacadeService.findMenuById(menuId);
        BaseWorkFlow baseWorkFlow;
        WorkFlow workFlow;
        Object comObj;
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
		//获取文档制单信息
		/*DocInfo docInfo=(DocInfo)commonFacadeService.getEntityByProperty("DocInfo", "docId", uuid);
		if(docInfo==null){docInfo=new DocInfo();*/
		
	}
		//获取流程
				if(menu.getMenuIsHasWF().equals("1")){
					baseWorkFlow=baseWorkFlowService.getWorkFlowByDocId(compBidEval.getUuid());
					request.setAttribute("baseWorkFlow", baseWorkFlow);
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
						request.setAttribute("workFlow", workFlow);
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
				comObj = bidEvaluationService.findCompByBidFile(bidFileUuid);
				request.setAttribute("comObj", comObj);
				request.setAttribute("isRead", isRead);
	}
	
	/**
	 * 综合评审
	 * @return
	 */

	public String openCompBidEval(String bidFileUuid) {
		HttpServletRequest request = ServletActionContext.getRequest();
		//String bidFileUuid = request.getParameter("bidFileUuid");
		BiddingFileRelease entity = biddingFileReleaseCommonFacadeService.getEntityByID(BiddingFileRelease.class.getSimpleName(), bidFileUuid);
		techBidEval = bidEvaluationService.findByBidFile(bidFileUuid);
		CompBidEval compBidEval = bidEvaluationService.findCompByBidFile(bidFileUuid);
		request.setAttribute("bidFile", entity);
		request.setAttribute("compBidEval", compBidEval);
		request.setAttribute("comObj", compBidEval);
		if(compBidEval !=null)
		 request.setAttribute("role", "view");
		//获取项目信息
		BidFileWithProject bidFileWithProject = bidFileReleaseService.findBidFileWithProject(bidFileUuid);
		request.setAttribute("bidFileWithProject", bidFileWithProject);
		//获取设备参数
		String deviceStr = bidFileReleaseService.findPurchaseDeviceStr(bidFileUuid);
		request.setAttribute("deviceStr",deviceStr);
		//获取供应商
		List<ViewUserSupplier> suppliers = supplierBidService.findSuppliers(bidFileUuid);
		request.setAttribute("suppliers", suppliers);
		//获取流程中相关信息
        String menuId="402880e6658db7ec01658dcc6ad30000";
       Menu menu = menuFacadeService.findMenuById(menuId);
      //获取流程
       if(menu.getMenuIsHasWF().equals("1")){
    	   WorkFlow	workFlow=workFlowService.getWorkFlowByMenuId(menuId);
      	 request.setAttribute("workFlow",workFlow);
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
		request.setAttribute("isRead", false);
		return ActionSupport.SUCCESS;
	}
	/**
	 * 通过代办获取技术招标内容
	 */
	 public String findTechBid(){
		 HttpServletRequest request = ServletActionContext.getRequest();
		 HttpSession session = request.getSession();
		 String bidFileUuid = request.getParameter("bidFileUuid");
			BiddingFileRelease entity = biddingFileReleaseCommonFacadeService.getEntityByID(BiddingFileRelease.class.getSimpleName(), bidFileUuid);
			techBidEval = bidEvaluationService.findByBidFile(bidFileUuid);
			request.setAttribute("bidFile", entity);
			//获取供应商
			List<ViewUserSupplier> suppliers = supplierBidService.findSuppliers(bidFileUuid);
			request.setAttribute("suppliers", suppliers);
			
			String menuId = ServletActionContext.getRequest().getParameter("menuId");
			//获取流程中相关信
	       Menu menu = menuFacadeService.findMenuById(menuId);
	   	WorkFlow workFlow;
	   	BaseWorkFlow baseWorkFlow;
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
			//获取文档制单信息
		docInfo=(DocInfo)commonFacadeService.getEntityByProperty("DocInfo", "docId", techBidEval.getUuid());
			if(docInfo==null){docInfo=new DocInfo();}
			
		}
		
			menu = menuFacadeService.findMenuById(menuId);
			//获取流程
					if(menu.getMenuIsHasWF().equals("1")){
						baseWorkFlow=baseWorkFlowService.getWorkFlowByDocId(techBidEval.getUuid());
						request.setAttribute("baseWorkFlow",baseWorkFlow);
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
							request.setAttribute("workFlow", null);
							session.setAttribute("workFlow", null);
						}else{
							workFlow=workFlowService.getWorkFlowByMenuId(menuId);
							isRead = "true";
							request.setAttribute("workFlow", workFlow);
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
					request.setAttribute("comObj", techBidEval);
					request.setAttribute("isRead", isRead);
					
		    return "success";
	 }
	/**
	 * 通过代办获取综合评审及流程
	 */
	public String findDocGeneralBid(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String bidFileUuid = request.getParameter("bidFileUuid");
		BiddingFileRelease entity = biddingFileReleaseCommonFacadeService.getEntityByID(BiddingFileRelease.class.getSimpleName(), bidFileUuid);
		techBidEval = bidEvaluationService.findByBidFile(bidFileUuid);
		CompBidEval compBidEval = bidEvaluationService.findCompByBidFile(bidFileUuid);
		request.setAttribute("bidFile", entity);
		request.setAttribute("compBidEval", compBidEval);
		request.setAttribute("comObj", compBidEval);
		if(compBidEval !=null)
		 request.setAttribute("role", "view");
		//获取项目信息
		BidFileWithProject bidFileWithProject = bidFileReleaseService.findBidFileWithProject(bidFileUuid);
		request.setAttribute("bidFileWithProject", bidFileWithProject);
		//获取设备参数
		String deviceStr = bidFileReleaseService.findPurchaseDeviceStr(bidFileUuid);
		request.setAttribute("deviceStr",deviceStr);
		//获取供应商
		List<ViewUserSupplier> suppliers = supplierBidService.findSuppliers(bidFileUuid);
		request.setAttribute("suppliers", suppliers);
		
		String menuId = ServletActionContext.getRequest().getParameter("menuId");
		//获取流程中相关信
        Menu menu = menuFacadeService.findMenuById(menuId);
        BaseWorkFlow baseWorkFlow;
        WorkFlow workFlow;
        Object comObj;
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
		//获取文档制单信息
	/*	docInfo=(DocInfo)commonFacadeService.getEntityByProperty("DocInfo", "docId", uuid);
		if(docInfo==null){docInfo=new DocInfo();}
		*/
	}
		//获取流程
				if(menu.getMenuIsHasWF().equals("1")){
					baseWorkFlow=baseWorkFlowService.getWorkFlowByDocId(compBidEval.getUuid());
					request.setAttribute("baseWorkFlow", baseWorkFlow);
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
						request.setAttribute("workFlow", workFlow);
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
				comObj = bidEvaluationService.findCompByBidFile(bidFileUuid);
				request.setAttribute("comObj", comObj);
				request.setAttribute("isRead", isRead);
		   return "success";
	}
	
	
    public void getBidGrid(){
    	HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
    	String bidFileUuid = request.getParameter("bidFileUuid");
		BiddingFileRelease entity = biddingFileReleaseCommonFacadeService.getEntityByID(BiddingFileRelease.class.getSimpleName(), bidFileUuid);
		//获取供应商
		List<ViewUserSupplier> suppliers = supplierBidService.findSuppliers(bidFileUuid);
		request.setAttribute("suppliers", suppliers);
    	  List<Object> supplierBids=new ArrayList<>();
  		//wfp 获取投标、澄清、竞价的报价
    	  techBidEval = bidEvaluationService.findByBidFile(bidFileUuid);
  		for(ViewUserSupplier sup:suppliers){
  			String userId = sup.getAccount();
  			SupplierBid supplierBid = supplierBidService.findBy(userId, bidFileUuid);
  			supplierBid.setSupplierName(sup.getName());
  			SupplierScore supplierScore = supplierBidService.getSupplierScore(supplierBid.getUuid(),techBidEval.getUuid());
  			if(supplierScore.getIsFeasible().equals("可行")){
  				supplierBids.add(supplierBid);
  			}	
  		}
  		 JsonConfig config = new JsonConfig();  
  		 config.setExcludes(new String[]{"firstUnitPrice","secondUnitPrice","thirdUnitPrice","finalUnitPrice","purchaseMaterialUuid","supplierBidUuid","remark","supplierBid","uuid"}); 
  		//CommonUtil.toJsonStr(ServletActionContext.getResponse(), supplierBids,config);
  		JSONArray json = JSONArray.fromObject(supplierBids, config); 
  		CommonUtil.toString(response, json.toString());
    }
	
	public void supplierBidResults(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String bidFileUuid = request.getParameter("bidFileUuid");
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), supplierBidService.findSupplierBidResults(bidFileUuid));
	}
    /**
     * 综合评审保存
     * @return
     */
	public String saveOrUpdateCompBidEval(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String uuid = request.getParameter("uuid");
		String biddingFileUuid = request.getParameter("biddingFileUuid");
		String wfStatus = request.getParameter("wfStatus");
		String suggestSupplierBid = request.getParameter("suggestSupplierBid");
		String content = request.getParameter("content");
		String catalogId = request.getParameter("catalogId");
		String curDocId = request.getParameter("curDocId");
		if (StringUtils.isNotBlank(uuid)) {
			CompBidEval compBidEval = compBidEvalCommonFacadeService.getEntityByID(CompBidEval.class.getSimpleName(), uuid);
			//修改
			compBidEval.setBiddingFileUuid(biddingFileUuid);
			compBidEval.setCatalogId(catalogId);
			compBidEval.setCurDocId(curDocId);
			compBidEval.setSuggestSupplierBid(suggestSupplierBid);
			compBidEval.setWfStatus(wfStatus);
			compBidEval.setUpdateBy(user.getUuid());
			compBidEval.setUpdateDate(new Date());
			compBidEval.setContent(content);
			//compBidEvalCommonFacadeService.update(compBidEval);
			commonWorkFlowService.saveOrUpdate(compBidEval);
		} else {
			//新增
			CompBidEval comp = new CompBidEval();
			comp.setBiddingFileUuid(biddingFileUuid);
			comp.setSuggestSupplierBid(suggestSupplierBid);
			comp.setUuid(uuid);
			comp.setCatalogId(catalogId);
			comp.setCurDocId(curDocId);
			comp.setWfStatus(wfStatus);
			comp.setCreateBy(user.getUuid());
			comp.setCreateDate(new Date());
			comp.setContent(content);
			comp.setWfStatus("1");
			//compBidEvalCommonFacadeService.save(comp);
			//commonWorkFlowService.saveOrUpdate(comp);
			commonWorkFlowService.save(comp, request.getParameter("menuId"));
		}
		BiddingFileRelease biddingFileRelease = biddingFileReleaseCommonFacadeService.getEntityByID(BiddingFileRelease.class.getSimpleName(), techBidEval.getBiddingFileUuid());
		biddingFileRelease.setStatus(new Short("3"));
		biddingFileReleaseCommonFacadeService.update(biddingFileRelease);
		return ActionSupport.SUCCESS;
	}

	public void techBidEvalMaterialParam() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String bidFileUuid = request.getParameter("bidFileUuid");
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), bidFileReleaseService.findTechBidEvalMaterialParam(bidFileUuid));
	}

	public void supplierScore() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String bidFileUuid = request.getParameter("bidFileUuid");
		String isFeasible = request.getParameter("isFeasible");
		CommonUtil.toJsonStrData(ServletActionContext.getResponse(), bidEvaluationService.findSupplierScoreByBidFile(bidFileUuid,isFeasible));
	}
	/**
	 * 技术评标保存
	 * @return
	 */

	public String saveOrUpdateTechBidEval() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String uuid;
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String catalogId = request.getParameter("catalogId");
		String curDocId = request.getParameter("curDocId");
		if (StringUtils.isNotBlank(techBidEval.getUuid())) {
			//修改
			techBidEval.setUpdateBy(user.getUuid());
			uuid = techBidEval.getUuid();
			techBidEval.setCatalogId(catalogId);
			techBidEval.setCurDocId(curDocId);
			//techBidEvalCommonFacadeService.update(techBidEval);
			commontechFlowService.saveOrUpdate(techBidEval);
		} else {
			//新增
			techBidEval.setCreateBy(user.getUuid());
			techBidEval.setCatalogId(catalogId);
			techBidEval.setCurDocId(curDocId);
			//uuid = techBidEvalCommonFacadeService.save(techBidEval);
			//uuid = commontechFlowService.saveOrUpdate(techBidEval);;
			 uuid = commontechFlowService.save(techBidEval, request.getParameter("menuId"));
		}
		BiddingFileRelease biddingFileRelease = biddingFileReleaseCommonFacadeService.getEntityByID(BiddingFileRelease.class.getSimpleName(), techBidEval.getBiddingFileUuid());
		biddingFileRelease.setStatus(new Short("2"));
		biddingFileReleaseCommonFacadeService.update(biddingFileRelease);
		String grid = request.getParameter("bidResultGrid");
		System.out.println(request.getParameter("bidResultGrid"));
		List<SupplierScoreInfo> bidResultGrid = JSONObject.parseArray(request.getParameter("bidResultGrid"), SupplierScoreInfo.class);
		bidEvaluationService.saveOrUpdateSupllierScore(uuid, bidResultGrid);
		return ActionSupport.SUCCESS;
	}

	@Override
	public TechBidEval getModel() {
		if (CommonUtil.isNullOrEmpty(techBidEval)) {
			techBidEval = new TechBidEval();
		}
		return techBidEval;
	}

	public MenuFacadeService getMenuFacadeService() {
		return menuFacadeService;
	}

	public void setMenuFacadeService(MenuFacadeService menuFacadeService) {
		this.menuFacadeService = menuFacadeService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public CommonFacadeService<Object> getCommonFacadeService() {
		return commonFacadeService;
	}

	public void setCommonFacadeService(
			CommonFacadeService<Object> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}


	public CommonWorkFlowService<CompBidEval> getCommonWorkFlowService() {
		return commonWorkFlowService;
	}

	public void setCommonWorkFlowService(
			CommonWorkFlowService<CompBidEval> commonWorkFlowService) {
		this.commonWorkFlowService = commonWorkFlowService;
	}

	public BaseWorkFlowService getBaseWorkFlowService() {
		return baseWorkFlowService;
	}

	public void setBaseWorkFlowService(BaseWorkFlowService baseWorkFlowService) {
		this.baseWorkFlowService = baseWorkFlowService;
	}

	public CommonWorkFlowService<TechBidEval> getCommontechFlowService() {
		return commontechFlowService;
	}

	public void setCommontechFlowService(
			CommonWorkFlowService<TechBidEval> commontechFlowService) {
		this.commontechFlowService = commontechFlowService;
	}

	
}
