package htos.coresys.service.impl;

import htos.coresys.dao.RoleTypeDao;
import htos.coresys.entity.RoleType;
import htos.coresys.service.RoleTypeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RoleTypeServiceImpl implements RoleTypeService {

	private RoleTypeDao roleTypeDao;
	
	public RoleTypeDao getRoleTypeDao() {
		return roleTypeDao;
	}

	public void setRoleTypeDao(RoleTypeDao roleTypeDao) {
		this.roleTypeDao = roleTypeDao;
	}

	@Override
	public void saveRoleType(RoleType roleType) {
		// TODO Auto-generated method stub
		roleTypeDao.save(roleType);
	}

	@Override
	public void updateRoleType(RoleType roleType) {
		// TODO Auto-generated method stub
		roleTypeDao.update(roleType);
	}

	@Override
	public RoleType findRoleTypeById(String id) {
		// TODO Auto-generated method stub
		return roleTypeDao.findRoleTypeById(id);
	}

	@Override
	public void deleteRoleType(RoleType roleType) {
		// TODO Auto-generated method stub
		roleTypeDao.deleteRoleType(roleType);
	}

	@Override
	public int updateLaterRoleTypeNo(RoleType roleType) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object findMaxRoleTypeNo(RoleType roleType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, String>> findAllList() {
		// TODO Auto-generated method stub
		Map<String, String> map = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		List<RoleType> listRoleType = roleTypeDao.findAllList();
		for (RoleType roleType : listRoleType) {
			map = new HashMap<String,String>();
			map.put("id", roleType.getUuid());
			map.put("text", roleType.getRoleTypeName());
			list.add(map);
		}
		return list;
	}

	@Override
	public List<RoleType> findLaterNodesList(RoleType roleType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RoleType> findChildNodesList(RoleType roleType) {
		// TODO Auto-generated method stub
		return null;
	}

}
