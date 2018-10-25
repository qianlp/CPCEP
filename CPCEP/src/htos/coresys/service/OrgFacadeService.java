package htos.coresys.service;

import htos.coresys.entity.Dept;
import htos.coresys.entity.Role;
import htos.coresys.entity.User;
import htos.coresys.entity.UserToRole;

import java.beans.IntrospectionException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;



public interface OrgFacadeService {
	//添加用户
	public void addUser(User user, String sameLevelNodeId);
	//删除部门
	public void deleteDept(String deptId);
	//创建组织结构树
	public List<Map<String, String>> findOrgTree();
	//登录验证
	public Map<String, Object> login(User user);
	//根据roleid查找users
	public List<Map<String, String>> findUserIdByRole(Role role);
	//保存角色时，生产中间表
	public void saveUserToRole(Role role,String userList);
	//更新角色及中间表
	public void updateUserToRole(Role role, UserToRole userToRole,String addUserList,String userPerEname) throws IntrospectionException;
	//导入用户组织数据
	public Map<String, Object> saveImportOrgUser(List<String> list,String userPerEname);
	//导出用户组织数据
	public void exportOrgUser(HttpServletResponse response);
	//保存角色及中间表
	public void saveDeptToUser(User user,Dept dept);
	//查询所有角色
	public List<Map<String, String>>  findAllRoleUserJson();
	//根据deptid查找users
	public List<Map<String, String>> findUserIdByDept(Dept dept);
	//创建组织结构树不包含人员
	List<Map<String, String>> findOrgTreeWithoutUser();
	public List<Map<String, String>> findUserRoleJson(User user);
}