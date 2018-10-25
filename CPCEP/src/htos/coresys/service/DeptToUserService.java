package htos.coresys.service;

import java.util.List;

import htos.coresys.entity.DeptToUser;


public interface DeptToUserService {
	public void saveDeptToUser(DeptToUser deptToUser);

	public List<DeptToUser> findUserIdByDept(DeptToUser deptToUser);
}