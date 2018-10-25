package htos.coresys.dao;

import htos.common.entity.PageInfo;
import htos.coresys.entity.User;

import java.util.List;
import java.util.Map;


/** 
 *  
 *  
 */

public interface UserDao extends BaseDao<User> {
	//miniiui分页获取分页数据json
	public Map<String, Object> findListByDeptIdForPage(String deptId, User user,PageInfo pageInfo);
	// 使用户位置编号>=该用户的,所有用户位置编号+1
	public int updateLaterUserPosition(User user);
	// 获取部门下最大的位置编号
	public Object findMaxUserPosition(User user);
	//获取所有的用户List
	public List<User> findAllList();
	//通过部门id获取所有的用户List
	public List<User> findListByDeptId(User user);
	//通过部门id获取所有的用户List
	public List<User> findListByDeptId(String deptId);
	//通过部门id获取所有的用户List，按编号进行排序
	public List<User> findListByDeptIdInOrder(User user);
	//根据多个用户id删除对应的用户
	public List<User> findListByUserIds(String userIds);
	public int updatePosition(User user, User laterUser);
	public List<User> userLogin(User user);

	
	
}