package htos.coresys.dao;

import htos.coresys.entity.DeptToUser;

import java.util.List;


/** 
 *  
 *  
 */

public interface DeptToUserDao extends BaseDao<DeptToUser> {
	//2016-06-20
	public List<DeptToUser> findDeptsByUserId(String userId);

	public List<DeptToUser> findUserIdByDept(DeptToUser deptToUser);
}