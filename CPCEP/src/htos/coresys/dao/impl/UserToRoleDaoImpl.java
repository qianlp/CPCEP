package htos.coresys.dao.impl;

import htos.coresys.dao.UserToRoleDao;
import htos.coresys.entity.UserToRole;

import java.util.ArrayList;
import java.util.List;

public class UserToRoleDaoImpl extends BaseDaoImpl<UserToRole> implements
		UserToRoleDao {

	@Override
	public void saveUserToRole(UserToRole userToRole) {
		// TODO Auto-generated method stub
		super.save(userToRole);
	}

	@Override
	public void updateUserToRole(UserToRole userToRole) {
		// TODO Auto-generated method stub
		super.update(userToRole);
	}

	@Override
	public void deleteUserToRole(UserToRole userToRole) {
		// TODO Auto-generated method stub
		super.delete(userToRole);
	}

	@Override
	public List<UserToRole> findAllList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserToRole> findUserIdByRole(UserToRole userToRole) {
		List<UserToRole> tlist=new ArrayList<UserToRole>();
		String hql = "from UserToRole u where u.role ='"
				+ userToRole.getRole().getUuid() + "'";
		try{
			tlist=super.find(hql);
		}catch(Exception e){
		}
		return tlist;
	}
	
	@Override
	public List<UserToRole> findUserRole(UserToRole userToRole) {
		String hql = "from UserToRole u where u.user ='"+ userToRole.getUser().getUuid() + "'";
		return super.find(hql);
	}
}
