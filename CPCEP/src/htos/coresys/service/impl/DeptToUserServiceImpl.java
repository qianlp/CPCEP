package htos.coresys.service.impl;

import java.util.List;

import htos.coresys.dao.DeptToUserDao;
import htos.coresys.entity.DeptToUser;
import htos.coresys.service.DeptToUserService;

public class DeptToUserServiceImpl implements DeptToUserService {
	private DeptToUserDao deptToUserDao;
	
	public void saveDeptToUser(DeptToUser deptToUser){
		deptToUserDao.save(deptToUser);
	}

	public DeptToUserDao getDeptToUserDao() {
		return deptToUserDao;
	}

	public void setDeptToUserDao(DeptToUserDao deptToUserDao) {
		this.deptToUserDao = deptToUserDao;
	}

	@Override
	public List<DeptToUser> findUserIdByDept(DeptToUser deptToUser) {
		return deptToUserDao.findUserIdByDept(deptToUser);
	}
	
}
