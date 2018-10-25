package htos.coresys.dao;

import htos.common.entity.PageInfo;
import htos.coresys.entity.Role;

import java.util.List;


public interface RoleDao extends BaseDao<Role> {
	// 保存角色信息
	public void saveRole(Role role);

	// 更新角色信息
	public void updateRole(Role role);

	// 根据id查找角色信息
	public Role findRoleById(Role role);

	// 根据id删除角色信息
	public void deleteRole(Role role);

	// 根据id删除角色信息
	public void deleteRoles(String roles);

	// 查询所有角色信息
	public List<Role> findAllList();
	
	// 查询所有角色信息分页
	public List<Role> findRoleListPage(PageInfo pageInfo, String cat,String deptIds);
}
