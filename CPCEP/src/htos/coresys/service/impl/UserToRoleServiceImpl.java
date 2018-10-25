package htos.coresys.service.impl;

import htos.coresys.dao.UserToRoleDao;
import htos.coresys.entity.UserToRole;
import htos.coresys.service.UserToRoleService;

import java.util.List;


public class UserToRoleServiceImpl implements UserToRoleService {

	private UserToRoleDao userToRoleDao;
	public UserToRoleDao getUserToRoleDao() {
		return userToRoleDao;
	}

	public void setUserToRoleDao(UserToRoleDao userToRoleDao) {
		this.userToRoleDao = userToRoleDao;
	}

	@Override
	public void saveUserToRole(UserToRole userToRole) {
		// TODO Auto-generated method stub
		userToRoleDao.saveUserToRole(userToRole);
	}

	@Override
	public void updateUserToRole(UserToRole userToRole) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUserToRole(UserToRole userToRole) {
		// TODO Auto-generated method stub
		userToRoleDao.deleteUserToRole(userToRole);
	}

	@Override
	public List<UserToRole> findAllList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserToRole> findUserIdByRole(UserToRole userToRole) {
		// TODO Auto-generated method stub
		return userToRoleDao.findUserIdByRole(userToRole);
	}

//	@Override
//	public List<Map<String,String>> findUserIdByRole(UserToRole userToRole) {
//		// TODO Auto-generated method stub
//		Map<String, String> map = null;
//		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//		List<UserToRole> listUser = userToRoleDao.findUserIdByRole(userToRole);
//		for (UserToRole ut : listUser) {
//			map = new HashMap<String, String>();
//			map.put("id", ut.getUser().getUserId());
//			map.put("text", ut.getUser().getUserName());
//			list.add(map);
//		}
//		return list;
//	}
	@Override
	public List<UserToRole> findUserRole(UserToRole userToRole) {
		return userToRoleDao.findUserRole(userToRole);
	}
}
