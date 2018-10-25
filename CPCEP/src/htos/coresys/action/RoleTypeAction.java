package htos.coresys.action;

import htos.coresys.entity.RoleType;
import htos.coresys.service.RoleTypeService;
import htos.coresys.util.CommonUtil;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class RoleTypeAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private RoleTypeService roleTypeService;
	private RoleType roleType;
	
	/**
	 * 保存角色类型
	 */
	public void saveRoleType() {
		roleType = new RoleType();
		HttpServletRequest request = ServletActionContext.getRequest();
		//System.out.println(request.getParameter("roleTypeName"));
		roleType.setRoleTypeName(request.getParameter("roleTypeName"));
		roleTypeService.saveRoleType(roleType);
	}
	
	/**
	 * 删除角色类型
	 */
	public void deleteRoleType() {
		String roleTypeId = ServletActionContext.getRequest().getParameter(
				"roleTypeId");
		roleTypeService.deleteRoleType(roleTypeService
				.findRoleTypeById(roleTypeId));
	}
	
	/**
	 * 查询所有角色类型
	 * @return
	 */
	public String findAllRoleTypeJson() {
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),
				roleTypeService.findAllList());
		return null;
	}
	
	/**
	 * 根据角色类型id查询角色
	 * @return
	 */
	public String findRoleTypeById() {
		String roleTypeId = ServletActionContext.getRequest().getParameter(
				"roleTypeId");
		roleType = roleTypeService.findRoleTypeById(roleTypeId);
		return SUCCESS;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public RoleTypeService getRoleTypeService() {
		return roleTypeService;
	}

	public void setRoleTypeService(RoleTypeService roleTypeService) {
		this.roleTypeService = roleTypeService;
	}

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}
}
