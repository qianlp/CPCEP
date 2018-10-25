package htos.coresys.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import htos.common.entity.PageInfo;
import htos.coresys.entity.Role;
import htos.coresys.entity.User;
import htos.coresys.entity.UserToRole;
import htos.coresys.service.CommonService;
import htos.coresys.service.RoleService;
import htos.coresys.util.CommonUtil;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;






import com.opensymphony.xwork2.ActionSupport;

public class RoleAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private RoleService roleService;
	private CommonService<UserToRole> commonService;
	private Role role;

	public String findPrjRoleJson(){
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), roleService.findRoleListJsonPage(null,"5","1"));
		return null;
	}
	
	public String findFlowRoleJson(){
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), roleService.findRoleListJsonPage(null,"3","1"));
		return null;
	}
	
	public String findMenuRoleJson(){
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), roleService.findRoleListJsonPage(null,"2","1"));
		return null;
	}
	
	public String findAllRoleJson(){
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), roleService.findAllRoleJson());
		return null;
	}
	
	public String findRolePageJson(){
		String cat = ServletActionContext.getRequest().getParameter("listCategory");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> roleList=roleService.findRoleListPage(getpageInfo(), cat,"1");
		map.put("total", roleService.findRoleListPage(null, cat,"1").size());
		map.put("data",roleList);
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), map);
		return null;
	}
	
	public String findRoleUsersById(){
		String ids = ServletActionContext.getRequest().getParameter("ids");
		System.out.println(ids);
		String[] idArr=ids.split(",");
		List<Map<String, String>> roleList=new ArrayList<Map<String, String>>();


		
		for(String id:idArr){
			if(id!=null && !id.equalsIgnoreCase("")){
				Role role=roleService.get(Role.class,id);
				if(role.getUserToRoles()!=null && role.getUserToRoles().size()>0){
					for(UserToRole tu:role.getUserToRoles()){
						Map<String, String> user=new HashMap<String, String>();
						user.put("id", tu.getUser().getUuid());
						user.put("name", tu.getUser().getUserName());
						roleList.add(user);
					}
				}
			}
			
		}
		
		
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), roleList);
		return null;
	}
	
	
	public PageInfo getpageInfo(){
		
		int pageIndex = Integer.parseInt(ServletActionContext.getRequest().getParameter("pageIndex"));
		int pageSize = Integer.parseInt(ServletActionContext.getRequest().getParameter("pageSize"));
		String sortField = ServletActionContext.getRequest().getParameter("sortField");
		String sortOrder = ServletActionContext.getRequest().getParameter("sortOrder");
		PageInfo pageInfo = new PageInfo(pageIndex, pageSize, sortField, sortOrder);
		return pageInfo;
	}
	
	public String findRoleById() {
		role = roleService.findRoleById(role);
		return SUCCESS;
	}
	
	public String updateRole() {
		try {
			User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
			roleService.updateRole(role,user.getUserPerEname());
			String html = "<script>parent.roleLoad();parent.CloseWindow();</script>";
			CommonUtil.toString(ServletActionContext.getResponse(), html);
		} catch (Exception e) {
			CommonUtil
					.toString(ServletActionContext.getResponse(),
							"<script>alert(\"维护失败！\");parent.goCloseDlg(\"oWinDlg\");</script>");
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteRoles() {
		String roleIds = ServletActionContext.getRequest().getParameter("id");
		roleService.deleteRoles(roleIds);
		String html = "<script>parent.roleLoad();parent.CloseWindow();</script>";
		CommonUtil.toString(ServletActionContext.getResponse(), html);
	}
	
	public String addRole() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String addUserList = request.getParameter("Members");
			//System.out.println(addUserList);
			roleService.saveRole(role);
			List<UserToRole> rtList=commonService.getHqlList("from UserToRole where user.uuid='' or user.uuid=null ");
			for(UserToRole u:rtList){
				commonService.delete(u);
			}
			
			String html = "<script>parent.roleLoad();parent.CloseWindow();</script>";
			CommonUtil.toString(ServletActionContext.getResponse(), html);
			request.setAttribute("roleId", role.getUuid());
			request.setAttribute("addUserList", addUserList);
		} catch (Exception e) {
			CommonUtil.toString(ServletActionContext.getResponse(),
					"<script>alert(\"维护失败！\");</script>");
			e.printStackTrace();
		}
		return null;
	}
	

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public CommonService<UserToRole> getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService<UserToRole> commonService) {
		this.commonService = commonService;
	}
	
	
}
