package htos.coresys.dao.impl;

import htos.coresys.dao.DeptToUserDao;
import htos.coresys.entity.DeptToUser;

import java.util.List;


public class DeptToUserDaoImpl extends BaseDaoImpl<DeptToUser> implements DeptToUserDao {
	@Override
	public List<DeptToUser> findDeptsByUserId(String userId){
		String hql = "from DeptToUser du where du.user.uuid = '"+ userId +"'";
		return super.find(hql);
	}

	@Override
	public List<DeptToUser> findUserIdByDept(DeptToUser deptToUser) {
		String hql = "from DeptToUser u where u.dept ='"+ deptToUser.getDept().getUuid() + "'";
	return super.find(hql);
	}
}