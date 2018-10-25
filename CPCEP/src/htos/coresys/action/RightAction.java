package htos.coresys.action;

import htos.coresys.entity.Right;
import htos.coresys.service.RightService;
import htos.coresys.util.CommonUtil;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class RightAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private RightService rightService;
	private Right right;
	
	public String findOrgIds(){
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), rightService.getOrgIdsByDocId(right));
		return null;
	}

	public String updateRight(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String docId = request.getParameter("docId");
		String modeName = request.getParameter("modeName");
		String strJson = request.getParameter("rightJson");
		
		String html = null;
		try {
			rightService.updateRight(docId, modeName, strJson);
			html = "<script>parent.goCloseDlg(\"oWinDlg\");</script>";
		} catch (Exception e) {
			html = "<script>alert(\"修改失败\");parent.goCloseDlg(\"oWinDlg\");</script>";
			e.printStackTrace();
		}
		CommonUtil.toString(ServletActionContext.getResponse(), html);
		return null;
	}
	
	public Right getRight() {
		return right;
	}


	public void setRight(Right right) {
		this.right = right;
	}


	public RightService getRightService() {
		return rightService;
	}
	public void setRightService(RightService rightService) {
		this.rightService = rightService;
	}
	
}
