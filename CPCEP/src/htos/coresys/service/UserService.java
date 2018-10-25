package htos.coresys.service;

import htos.common.entity.PageInfo;
import htos.coresys.entity.User;

import java.beans.IntrospectionException;
import java.util.List;
import java.util.Map;


public interface UserService {

	public Map<String, Object> findListByDeptIdForPage(String deptId, User user,PageInfo pageInfo);

	public User findUserById(String userId);

	public int updateLaterUserPosition(User user);

	public Object findMaxUserPosition(User user);

	public void saveUser(User user);

	public List<Map<String, String>> findListByDeptId(User user);

	public void deleteUsersByIds(String userIds);

	public void updateUser(User user, String sameLevelNodeId,String userPerEname) throws IntrospectionException;
	//通过部门id删除该部门下的所有用户信息
	public void deleteUserByDeptId(String deptId);
	//获取所有的用户
	public List<User> findAllList();

	public User userLogin(User user);

	public User findUserByUserName(String username);

	List<User> findLeaders(int userPostion, String userDeptId);

}