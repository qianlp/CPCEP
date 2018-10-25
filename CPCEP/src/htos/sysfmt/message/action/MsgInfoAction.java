package htos.sysfmt.message.action;

import org.apache.struts2.ServletActionContext;

import htos.common.entity.PageInfo;
import htos.coresys.entity.User;
import htos.coresys.service.CommonService;
import htos.coresys.util.CommonUtil;
import htos.sysfmt.message.entity.MsgInfo;
import htos.sysfmt.message.service.MsgInfoService;
import PluSoft.Utils.JSON;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class MsgInfoAction extends ActionSupport implements ModelDriven<MsgInfo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3656092960678332307L;
	private MsgInfo msgInfo;
	private MsgInfoService msgInfoService;
	private CommonService<MsgInfo> commonService;
	
	public void upMsgStatus(){
		String uuid=ServletActionContext.getRequest().getParameter("docId");
		msgInfo=commonService.getEntityByID("MsgInfo", uuid);
		msgInfo.setStatus(2);
		commonService.update(msgInfo);
		CommonUtil.toString(ServletActionContext.getResponse(), "ok");
	}
	
	public void removeMsgStatus(){
		String uuid=ServletActionContext.getRequest().getParameter("docId");
		msgInfo=commonService.getEntityByID("MsgInfo", uuid);
		msgInfo.setStatus(3);
		commonService.update(msgInfo);
		CommonUtil.toString(ServletActionContext.getResponse(), "ok");
	}
	
	public void getMsgList(){
		User user=(User)ServletActionContext.getRequest().getSession().getAttribute("user");
		String msgTitle=ServletActionContext.getRequest().getParameter("msgTitle");
		String msgType=ServletActionContext.getRequest().getParameter("msgType");
		String createBy=ServletActionContext.getRequest().getParameter("createBy");
		String status=ServletActionContext.getRequest().getParameter("status");
		if(!CommonUtil.isNullOrEmpty(msgType)){	// msgType 1 代办；2 通知；3 预警
			msgInfo.setMsgType(Integer.parseInt(msgType));
		}
		if(!CommonUtil.isNullOrEmpty(msgTitle)){
			msgInfo.setMsgTitle(msgTitle);
		}
		if(!CommonUtil.isNullOrEmpty(status)){
			msgInfo.setStatus(Integer.parseInt(status));
		}if(!CommonUtil.isNullOrEmpty(createBy)){
			msgInfo.setCreateBy(createBy);
		}
		msgInfo.setUserId(user.getUuid());
		CommonUtil.toString(ServletActionContext.getResponse(), JSON.Encode(msgInfoService.getMsgList(msgInfo)));
	}
	
	public void getMsgCount(){
		User user=(User)ServletActionContext.getRequest().getSession().getAttribute("user");
		String msgTitle=ServletActionContext.getRequest().getParameter("msgTitle");
		String msgType=ServletActionContext.getRequest().getParameter("msgType");
		String createBy=ServletActionContext.getRequest().getParameter("createBy");
		String status=ServletActionContext.getRequest().getParameter("status");
		if(!CommonUtil.isNullOrEmpty(msgType)){
			msgInfo.setMsgType(Integer.parseInt(msgType));
		}
		if(!CommonUtil.isNullOrEmpty(msgTitle)){
			msgInfo.setMsgTitle(msgTitle);
		}
		if(!CommonUtil.isNullOrEmpty(status)){
			msgInfo.setStatus(Integer.parseInt(status));
		}if(!CommonUtil.isNullOrEmpty(createBy)){
			msgInfo.setCreateBy(createBy);
		}
		msgInfo.setUserId(user.getUuid());
		CommonUtil.toString(ServletActionContext.getResponse(), JSON.Encode(msgInfoService.getMsgList(null,msgInfo)));
	}
	
	public void getPageMsgList(){
		User user=(User)ServletActionContext.getRequest().getSession().getAttribute("user");
		String msgTitle=ServletActionContext.getRequest().getParameter("msgTitle");
		String msgType=ServletActionContext.getRequest().getParameter("msgType");
		String createBy=ServletActionContext.getRequest().getParameter("createBy");
		String status=ServletActionContext.getRequest().getParameter("status");
		if(!CommonUtil.isNullOrEmpty(msgType)){
			msgInfo.setMsgType(Integer.parseInt(msgType));
		}
		if(!CommonUtil.isNullOrEmpty(msgTitle)){
			msgInfo.setMsgTitle(msgTitle);
		}
		if(!CommonUtil.isNullOrEmpty(status)){
			msgInfo.setStatus(Integer.parseInt(status));
		}if(!CommonUtil.isNullOrEmpty(createBy)){
			msgInfo.setCreateBy(createBy);
		}
		msgInfo.setUserId(user.getUuid());
		CommonUtil.toString(ServletActionContext.getResponse(), JSON.Encode(msgInfoService.getMsgList(getpageInfo(), msgInfo)));
	}
	
	public PageInfo getpageInfo(){
		
		int pageIndex = Integer.parseInt(ServletActionContext.getRequest().getParameter("pageIndex"));
		int pageSize = Integer.parseInt(ServletActionContext.getRequest().getParameter("pageSize"));
		String sortField = ServletActionContext.getRequest().getParameter("sortField");
		String sortOrder = ServletActionContext.getRequest().getParameter("sortOrder");
		PageInfo pageInfo = new PageInfo(pageIndex, pageSize, sortField, sortOrder);
		return pageInfo;
	}
	
	/**
	 * @return the msgInfoService
	 */
	public MsgInfoService getMsgInfoService() {
		return msgInfoService;
	}



	/**
	 * @param msgInfoService the msgInfoService to set
	 */
	public void setMsgInfoService(MsgInfoService msgInfoService) {
		this.msgInfoService = msgInfoService;
	}



	/**
	 * @return the msgInfo
	 */
	public MsgInfo getMsgInfo() {
		return msgInfo;
	}


	/**
	 * @param msgInfo the msgInfo to set
	 */
	public void setMsgInfo(MsgInfo msgInfo) {
		this.msgInfo = msgInfo;
	}


	
	public CommonService<MsgInfo> getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService<MsgInfo> commonService) {
		this.commonService = commonService;
	}

	@Override
	public MsgInfo getModel() {
		if(CommonUtil.isNullOrEmpty(msgInfo)){
			msgInfo = new MsgInfo();
		}
		return msgInfo;
	}

}
