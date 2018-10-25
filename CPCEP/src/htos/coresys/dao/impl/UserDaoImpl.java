package htos.coresys.dao.impl;

import htos.common.entity.PageInfo;
import htos.coresys.dao.UserDao;
import htos.coresys.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	@Override
	public List<User> userLogin(User user) {
		String hql = "from User u where u.userName='" + user.getUserName() + "' and u.userPassword='" + user.getUserPassword() + "'";
		return super.find(hql);
	}

	@Override
	public int updateLaterUserPosition(User user) {
		// TODO Auto-generated method stub
		String hql = "update from User u set u.userPostion=(u.userPostion+1) where u.userDeptId='" + user.getUserDeptId() + "'  and u.userPostion>=" + user.getUserPostion();
		return super.executeHql(hql);
	}

	@Override
	public Object findMaxUserPosition(User user) {
		// TODO Auto-generated method stub
		String hql = "select max(u.userPostion) from User u where u.userDeptId='" + user.getUserDeptId() + "' ";
		return super.findUnique(hql);
	}

	@Override
	public List<User> findAllList() {
		String hql = "from User where userType is null or (userType!=2 and userType!=1)";
		return super.find(hql);
	}

	@Override
	public List<User> findListByDeptId(User user) {
		StringBuffer hql = new StringBuffer(" from User u ");
		hql = hql.append("  where u.userDeptId='" + user.getUserDeptId() + "'");
		return super.find(hql.toString());
	}

	@Override
	public List<User> findListByDeptId(String deptId) {
		StringBuffer hql = new StringBuffer(" from User u ");
		hql = hql.append("  where u.userDeptId='" + deptId + "'");
		return super.find(hql.toString());
	}

	@Override
	public List<User> findListByDeptIdInOrder(User user) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer(" from Dept dt,User u ");
		hql.append(" join u.deptToUsers du join du.dept d where d.uuid='" + user.getUserDeptId() + "' and dt.uuid in u.uuid ");
		hql.append(" order by dt.nodeLevel asc, dt.deptNo asc ");
		return super.find(hql.toString());
	}

	public List<User> findListByUserIds(String userIds) {
		String hql = "from User u where u.uuid in ('" + userIds + "')";
		return super.find(hql);

	}

	@Override
	public Map<String, Object> findListByDeptIdForPage(String deptId, User user, PageInfo pageInfo) {
		StringBuffer hql = new StringBuffer("select new map(u.uuid as userId,u.userName as userName,u.userSex as userSex,dt.deptFullName as deptName,u.userPerEname as userPerEname,u.userMail as userMail) from Dept dt,User u");
		StringBuffer hqlCount = new StringBuffer("select count(*) from User u ");
		String conSql = " join u.deptToUsers du join du.dept d where d.uuid='" + deptId + "'";
		hql.append(conSql);
		hqlCount.append(conSql);
		hql.append("  and dt.uuid in u.userDeptId ");
		if (null != user.getUserName() && user.getUserName().length() > 0) {
			hql.append(" and u.userName like '%" + user.getUserName() + "%' ");
			hqlCount.append(" and u.userName like '%" + user.getUserName() + "%' ");
		}
		if (pageInfo.getSortField() != null && pageInfo.getSortField().length() > 0) {
			hql.append(" order by " + pageInfo.getSortField());
		}
		if (pageInfo.getSortOrder() != null && pageInfo.getSortOrder().length() > 0) {
			hql.append(" " + pageInfo.getSortOrder());
		}
		if (hql.indexOf("order by") == -1) {
			hql.append(" order by ");
		} else {
			hql.append(" , ");
		}
		hql.append(" dt.nodeLevel asc, dt.deptNo asc ,u.userPostion asc");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("total", super.findUnique(hqlCount.toString()));
		dataMap.put("data", super.find(hql.toString(), new String[] {}, pageInfo.getpageIndex(), pageInfo.getPageSize()));
		return dataMap;

	}

	@Override
	public int updatePosition(User user, User laterUser) {
		// TODO Auto-generated method stub
		String hql = "";
		if (user.getUserPostion() > laterUser.getUserPostion()) {
			hql = "update from User u set u.userPostion=(u.userPostion+1) where u.uuid='" + user.getUserDeptId() + "' and u.userPostion>=" + laterUser.getUserPostion() + " and u.userPostion<"
					+ user.getUserPostion();
		} else if (user.getUserPostion() < laterUser.getUserPostion()) {
			hql = "update from User u set u.userPostion=(u.userPostion-1) where u.uuid='" + user.getUserDeptId() + "' and u.userPostion>" + user.getUserPostion() + " and u.userPostion<"
					+ laterUser.getUserPostion();

		}
		return super.executeHql(hql);
	}
}