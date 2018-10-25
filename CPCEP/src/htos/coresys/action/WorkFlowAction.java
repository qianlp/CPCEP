package htos.coresys.action;

import java.io.IOException;
import java.io.PrintWriter;

import htos.coresys.entity.Menu;
import htos.coresys.entity.WorkFlow;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.WorkFlowService;
import htos.coresys.util.CommonUtil;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class WorkFlowAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private WorkFlowService workFlowService;
	private CommonFacadeService<WorkFlow> commonFacadeService;
	private WorkFlow workFlow;
	private String wfId;
	//bitwise
	public String getWfId() {
		 return wfId;
	}
	//bitwise
	public void setWfId(String wfId) {
		 this.wfId = wfId;
	}
	public String addWorkFlow(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String menuId = request.getParameter("menuId");
		workFlowService.addWorkFlow(workFlow,menuId);
		this.wfId = workFlow.getUuid();//bitwise
		return "success";
	}
	
	public String updateWorkFlow(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String menuId = request.getParameter("menuId");
		workFlowService.updateWorkFlow(workFlow,menuId);
		this.wfId = workFlow.getUuid();//bitwise
		return "success";
	}
	
	public void copyWorkFlow(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String uuid = request.getParameter("id");
		String wfName = request.getParameter("wfName");
		String wfStatus = request.getParameter("wfStatus");
		String wfVersion = request.getParameter("wfVersion");
		String copyId = request.getParameter("copyId");
		
		Menu menu=new Menu();
		menu.setUuid(copyId);
		workFlow=commonFacadeService.getEntityByID("WorkFlow", uuid);
		WorkFlow nwf=new WorkFlow();
		nwf.setMenu(menu);
		nwf.setWfName(wfName);
		nwf.setWfStatus(wfStatus);
		nwf.setWfVersion(Integer.parseInt(wfVersion));
		nwf.setCreateBy(workFlow.getCreateBy());
		nwf.setCreateDate(workFlow.getCreateDate());
		nwf.setWfGraphXml(workFlow.getWfGraphXml());
		nwf.setWfProcessXml(workFlow.getWfProcessXml());
		String wfId=commonFacadeService.save(nwf);
		
		PrintWriter out = null;
		try {
			out = ServletActionContext.getResponse().getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.println("<script>parent.goNewWf('"+wfId+"')</script>");
	}

	public String delWorkFlow(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String workFlowId = request.getParameter("wfId");
		workFlow=new WorkFlow();
		workFlow.setUuid(workFlowId);
		commonFacadeService.delete(workFlow);
		return "success";
	}
	
	public String getWorkFlowByMenuId(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String menuId = request.getParameter("menuId");
		workFlow = workFlowService.getWorkFlowByMenuId(menuId);
		return "success";
	}
	
	public String getWorkFlowXml(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String workFlowId = request.getParameter("wfId");
		workFlow =  workFlowService.getWorkFlowById(workFlowId);
		CommonUtil.toString(ServletActionContext.getResponse(), "<WorkFlow>"+workFlow.getWfProcessXml()+"<Log/></WorkFlow>");
		return null;
	}
	
	public String getWorkFlowById(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String workFlowId = request.getParameter("wfId");
		workFlow =  workFlowService.getWorkFlowById(workFlowId);
		return "success";
	}
	
	public String findAllWF(){
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), workFlowService.findWFTree());
		return null;
	}
	
	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}
	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}


	public WorkFlow getWorkFlow() {
		return workFlow;
	}


	public void setWorkFlow(WorkFlow workFlow) {
		this.workFlow = workFlow;
	}
	public CommonFacadeService<WorkFlow> getCommonFacadeService() {
		return commonFacadeService;
	}
	public void setCommonFacadeService(
			CommonFacadeService<WorkFlow> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}
	
	
}
