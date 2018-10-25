package htos.coresys.service;

import htos.common.entity.PageInfo;
import htos.coresys.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {
	public void saveRole(Role role);

	// 更新角色
	public void updateRole(Role role,String userPerEname);

	// 通过role获得角色信息
	public Role findRoleById(Role role);

	// 根据role获得删除对应的角色信息
	public void deleteRole(Role role);

	// 根据roleId删除多个角色
	public void deleteRoles(String roles);

	// 查询所有角色信息
	public List<Map<String, String>> findAllRoleJson();
	
	//根据uuid 查询角色
	public Role get(Class<Role> class1, String uuid);
	// 查询所有角色信息
	public List<Role> findAllList();


	// 查询所有角色信息分页
	public List<Map<String, String>> findRoleListPage(PageInfo pageInfo, String cat,String deptIds);
	// 查询所有角色信息分页
	public List<Map<String, String>> findRoleListJsonPage(PageInfo pageInfo, String cat,String deptIds);
}
