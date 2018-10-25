package htos.coresys.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import htos.coresys.entity.Dept;
import htos.coresys.entity.User;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.DeptService;
import htos.coresys.util.CommonUtil;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class DeptAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private CommonFacadeService<Object> commonFacadeService;
	private DeptService deptService;
	private Dept dept;
	/**
	 * 根据主键查询部门
	 * @return
	 */
	public String findDept(){
		User user = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		Dept dept = new Dept();
		dept.setUuid(user.getUserDeptId());
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), deptService.findDept(dept),"deptToUsers");
		return null;
	}
	public String findDeptById() {
		String deptId = ServletActionContext.getRequest().getParameter("deptId");
		dept = deptService.findDeptById(deptId);
		return "success";
	}
	//根据部门id获取部门名称@zsw
	public String findDeptNameById() {
		String deptId = ServletActionContext.getRequest().getParameter("deptId");
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),
				"{deptName:'"+deptService.findDeptById(deptId).getDeptName()+"'}");
		return null;
	}
	
	/**
	 * 参数：dept.deptPid
	 * 用途：获取deptPid=dept.deptPid部门下子部门或同级部门集合
	 */
	public String findChildDeptsJson() {
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),
				deptService.findChildDeptsJson(dept));
		return null;

	}

	// 获取所有的部门json
	public String findAllDeptsJson() {
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),deptService.findAllDeptsJson());
		return null;
	}
	
	// 获取所有的二级部门json，获取区域用
	public String findTwoDeptsJson() {
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),deptService.findTwoDeptsJson());
		return null;
	}
	
	//获取部门负责人
	public void findDeptUserById() {
		String deptId = ServletActionContext.getRequest().getParameter("deptId");
		dept = deptService.findDeptById(deptId);
		User user=(User)commonFacadeService.getEntityByID("User", dept.getDeptUserId());
		List<Map<String, String>> newList=new ArrayList<Map<String, String>>();
		Map<String,String> nu=new HashMap<String, String>();
		if(user!=null){
			nu.put("id", user.getUuid());
			nu.put("name", user.getUserName());
			newList.add(nu);
		}
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),newList);
	}

	public String deleteDept() {
		return null;
	}

	public String addDept() {
		String sameLevelNodeId = ServletActionContext.getRequest()
				.getParameter("sameLevelNodeId");
		String html = null;
		try {
			deptService.addDept(dept, sameLevelNodeId);
			html = "<script>parent.goReload();</script>";
		} catch (Exception e) {
			html = "<script>alert(\"添加部门失败！\");parent.goCloseDlg(\"oWinDlg\");</script>";
			e.printStackTrace();
		}
		CommonUtil.toString(ServletActionContext.getResponse(), html);
		return null;
	}

	public String updateDept() {
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		String html = null;
		String sameLevelNodeId = ServletActionContext.getRequest()
				.getParameter("sameLevelNodeId");
		try {
			deptService.updateDept(dept, sameLevelNodeId,user.getUserPerEname());
			html = "<script>parent.goReload();</script>";
		} catch (Exception e) {
			html = "<script>alert(\"部门更新失败！\");parent.goCloseDlg(\"oWinDlg\");</script>";
			e.printStackTrace();
		}
		CommonUtil.toString(ServletActionContext.getResponse(), html);
		return null;

	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public DeptService getDeptService() {
		return deptService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	public CommonFacadeService<Object> getCommonFacadeService() {
		return commonFacadeService;
	}
	public void setCommonFacadeService(
			CommonFacadeService<Object> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}
}
