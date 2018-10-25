package htos.coresys.service.impl;

import htos.common.entity.PageInfo;
import htos.common.util.CopyUtil;
import htos.coresys.dao.UserDao;
import htos.coresys.entity.User;
import htos.coresys.service.UserService;
import htos.coresys.util.CommonUtil;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public  class UserServiceImpl implements UserService {
	
	private UserDao userDao;
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public User userLogin(User user){
		if (CommonUtil.isNullOrEmpty(user.getUserName())) {
			return null;
		}
		if (CommonUtil.isNullOrEmpty(user.getUserPassword())){
			return null;
		}
		List<User> list = userDao.userLogin(user);
		if(CommonUtil.isNullOrEmpty(list)){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	@Override
	public Map<String, Object> findListByDeptIdForPage(String deptId,User user, PageInfo pageInfo) {
		return userDao.findListByDeptIdForPage(deptId, user, pageInfo);
	}

	@Override
	public User findUserById(String userId) {
		// TODO Auto-generated method stub
		return userDao.get(User.class, userId);
	}

	@Override
	public int updateLaterUserPosition(User user) {
		// TODO Auto-generated method stub
		return userDao.updateLaterUserPosition(user);
	}

	@Override
	public Object findMaxUserPosition(User user) {
		// TODO Auto-generated method stub
		return userDao.findMaxUserPosition(user);
	}

	@Override
	public void saveUser(User user) {
		userDao.save(user);
	}
	
	@Override
	public List<User> findAllList(){
		return userDao.findAllList();
	}
	
	@Override
	public List<Map<String,String>>  findListByDeptId(User user) {
		// TODO Auto-generated method stub
		List<User> list = userDao.findListByDeptId(user);
		List<Map<String,String>> userList = new ArrayList<Map<String,String>> ();
		Map<String,String> map = null;
 		for(User u:list){
 			map = new HashMap<String, String>();
 			map.put("userId", u.getUuid());
 			map.put("userName", u.getUserName());
 			map.put("userPerEname", u.getUserPerEname());
 			userList.add(map);
		}
		return userList;
	}

	@Override
	public void deleteUsersByIds(String userIds) {
		// TODO Auto-generated method stub
		userIds = userIds.replaceAll(",", "','");
		List<User> list = userDao.findListByUserIds(userIds);
		for(int i=0;i<list.size();i++){
			userDao.delete(list.get(i));
		}
	}
	@Override
	public void deleteUserByDeptId(String deptId){
		List<User> list = userDao.findListByDeptId(deptId);
		for(int i=0;i<list.size();i++){
			userDao.delete(list.get(i));
		}
	}
	
	@Override
	public void updateUser(User user2, String sameLevelNodeId,String userPerEname) throws IntrospectionException {
		User user =userDao.get(User.class, user2.getUuid());
		CopyUtil.copyToObj(user, user2);
		if(!CommonUtil.isNullOrEmpty(sameLevelNodeId)){
			User laterUser = this.findUserById(sameLevelNodeId);
			int userPostion=0;
			if(user.getUserPostion()>laterUser.getUserPostion()){
				userPostion=laterUser.getUserPostion();
				userDao.updatePosition(user, laterUser);
			}else if(user.getUserPostion()<laterUser.getUserPostion()){
				userPostion=laterUser.getUserPostion()-1;
				userDao.updatePosition(user, laterUser);
			}else{
				userPostion=user.getUserPostion();
			}
			user.setUserPostion(userPostion);
		}
		user.setUpdateBy(userPerEname);
		user.setUpdateDate(new Date());
		userDao.update(user);
	}

	
	@Override
	public User findUserByUserName(String username) {
		User user = (User) userDao.findUnique("from User where userName = '"+username+"'");
		return user;
	}

	@Override
	public List<User> findLeaders(int userPostion, String userDeptId) {
		String hql = "from User where userPostion<? and userDeptId=?";
		return userDao.find(hql, new Object[]{userPostion, userDeptId});
	}
}
