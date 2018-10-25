package htos.coresys.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import htos.common.entity.PageInfo;
import htos.coresys.entity.Dept;
import htos.coresys.entity.DeptToUser;
import htos.coresys.entity.User;
import htos.coresys.service.CommonService;
import htos.coresys.service.UserService;
import htos.coresys.util.CommonUtil;
import htos.coresys.util.MD5Util;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private UserService userService;
	private CommonService<User> commonService;
	private CommonService<Object> commonObj;
	private User user;
	
	public void updateUserMd5(){
		List<User> userList=commonService.getHqlList(" from User");
		for(User u:userList){
			u.setUserPassword(MD5Util.EncoderByMd5(u.getUserPassword()));
			commonService.update(u);
		}
	}
	
	public String loginOut(){
		//清空session
		ActionContext.getContext().getSession().clear();
		//使HttpSession失效
		ServletActionContext.getRequest().getSession().invalidate();
		return "success";
	}
	
	public String findUsersJson(){
		HttpServletRequest request = ServletActionContext.getRequest();
		int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		String deptId = request.getParameter("deptId");
		PageInfo pageInfo = new PageInfo(pageIndex, pageSize, sortField,sortOrder);
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),userService.findListByDeptIdForPage(deptId,user, pageInfo));
		return null;
	}
	
	public void findUserByName(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String userName="";
		try {
			userName = new String(request.getParameter("userName").getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(userName);
		user=commonService.getEntityByProperty("User", "userName", userName);
		if(user==null){
			CommonUtil.toString(ServletActionContext.getResponse(),"true");
		}else{
			CommonUtil.toString(ServletActionContext.getResponse(),"false");
		}
	}
	
	public String findUsersByDeptId(){
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),userService.findListByDeptId(user));
		return null;
	}
	
	
	public String findUserById(){
		String userId = ServletActionContext.getRequest().getParameter("userId");
		user = userService.findUserById(userId);
		return SUCCESS;
	}
	
	public String updateUser(){
		String html =null;
		try{
			HttpServletRequest request = ServletActionContext.getRequest();
			User users = (User) request.getSession().getAttribute("user");
			String sameLevelNodeId = request.getParameter("sameLevelNodeId");
			updateUserDept(user);
			userService.updateUser(user,sameLevelNodeId,users.getUserPerEname());
			html = "<script>alert('修改用户成功！');parent.goCloseDlg(\"oWinDlg\");</script>";
		}catch(Exception e){
			html = "<script>alert('修改用户失败！');parent.goCloseDlg(\"oWinDlg\");</script>";
			e.printStackTrace();
		}
		CommonUtil.toString(ServletActionContext.getResponse(), html);
		return null;
		
	}
	
	//更新用户归属部门
	private void updateUserDept(User u){
		User linU =commonService.getEntityByID("User", u.getUuid());
		if(u.getUserDeptId().equals(linU.getUserDeptId())){
			return;
		}
		
		List<Object> objList=commonObj.getHqlList("from DeptToUser where user.uuid='"+u.getUuid()+"'");
		for(Object o:objList){
			commonObj.delete(o);
		}
		

		List<String> ids=new ArrayList<String>();
		ids=dgGetDept(ids,u.getUserDeptId());
		for(String id:ids){
			DeptToUser dtu=new DeptToUser();
			Dept dept=new Dept();
			dept.setUuid(id);
			dtu.setUser(u);
			dtu.setDept(dept);
			dtu.setIsParttimeDept("1");
			dtu.setDeptRole("1");
			commonObj.save(dtu);
		}
	}
	
	//递归获取部门的上级部门id
	private List<String> dgGetDept(List<String> ids,String deptId){
		ids.add(deptId);
		Dept dept=(Dept) commonObj.getEntityByID("Dept", deptId);
		if(!dept.getDeptPid().equals("-1")){
			ids=dgGetDept(ids,dept.getDeptPid());
		}
		return ids;
	}
	
	public void updatePwd(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String passwordNew = request.getParameter("passwordNew");
		String passwordConfirm = request.getParameter("passwordConfirm");
		User user=userService.findUserById(userId);
		
		PrintWriter out;
		try {
			ServletActionContext.getResponse().setCharacterEncoding("utf-8");
			out = ServletActionContext.getResponse().getWriter();
			if(user.getUserPassword().equals(MD5Util.EncoderByMd5(password))){
				if(passwordNew.equals(passwordConfirm)){
					user.setUserPassword(MD5Util.EncoderByMd5(passwordNew));
					commonService.update(user);
					out.println("<script>alert('修改成功!');parent.closeWin();</script>");
				}else{
					out.println("<script>alert('二次新密码不相同!');window.history.go(-1);</script>");
				}
			}else{
				out.println("<script>alert('原密码输入错误!');window.history.go(-1);</script>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void resetPwd(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String userId = request.getParameter("userId");
		String passwordNew = request.getParameter("passwordNew");
		
		User user=userService.findUserById(userId);
		user.setUserPassword(MD5Util.EncoderByMd5(passwordNew));
		commonService.update(user);
	}
	
	public void deleteUsers(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String userIds = request.getParameter("userIds");
		userService.deleteUsersByIds(userIds);
	}
	
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public CommonService<User> getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService<User> commonService) {
		this.commonService = commonService;
	}

	public CommonService<Object> getCommonObj() {
		return commonObj;
	}

	public void setCommonObj(CommonService<Object> commonObj) {
		this.commonObj = commonObj;
	}

	
}
