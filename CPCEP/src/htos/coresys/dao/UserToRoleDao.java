package htos.coresys.dao;

import htos.coresys.entity.UserToRole;

import java.util.List;


public interface UserToRoleDao extends BaseDao<UserToRole> {
	// 保存用户和角色关系
	public void saveUserToRole(UserToRole userToRole);

	// 更新用户和角色关系
	public void updateUserToRole(UserToRole userToRole);

	// 删除用户和角色关系
	public void deleteUserToRole(UserToRole userToRole);

	// 查询所有的用户和角色关系
	public List<UserToRole> findAllList();

	// 根据角色id查询对应的用户信息
	public List<UserToRole> findUserIdByRole(UserToRole userToRole);

	public List<UserToRole> findUserRole(UserToRole userToRole);
}
