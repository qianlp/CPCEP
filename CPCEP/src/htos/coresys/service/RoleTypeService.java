package htos.coresys.service;

import htos.coresys.entity.RoleType;

import java.util.List;
import java.util.Map;


public interface RoleTypeService {
	// 保存角色类型
	public void saveRoleType(RoleType roleType);

	// 更新角色类型
	public void updateRoleType(RoleType roleType);

	// 根据id查找角色类型
	public RoleType findRoleTypeById(String id);

	// 删除角色类型
	public void deleteRoleType(RoleType roleType);

	// 根据父节点id，找出对应子节点最大编号，再加1
	public int updateLaterRoleTypeNo(RoleType roleType);

	// 获取同父节点下最大节点编号
	public Object findMaxRoleTypeNo(RoleType roleType);

	public List<Map<String, String>> findAllList();

	// 查找某个节点编码之后的所有部门
	public List<RoleType> findLaterNodesList(RoleType roleType);

	// 根据父节点编号，获取该节点下面的所有子节点
	public List<RoleType> findChildNodesList(RoleType roleType);
}
