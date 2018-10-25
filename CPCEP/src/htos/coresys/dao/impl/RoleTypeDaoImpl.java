package htos.coresys.dao.impl;


import htos.coresys.dao.RoleTypeDao;
import htos.coresys.entity.RoleType;

import java.util.List;


public class RoleTypeDaoImpl extends BaseDaoImpl<RoleType> implements
		RoleTypeDao {

	@Override
	public void saveRoleType(RoleType roleType) {
		// TODO Auto-generated method stub
		super.save(roleType);
	}

	@Override
	public void updateRoleType(RoleType roleType) {
		// TODO Auto-generated method stub
		super.update(roleType);
	}

	@Override
	public RoleType findRoleTypeById(String id) {
		// TODO Auto-generated method stub
		return super.get(RoleType.class, id);
	}

	@Override
	public void deleteRoleType(RoleType roleType) {
		// TODO Auto-generated method stub
		super.delete(roleType);
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
	public List<RoleType> findAllList() {
		// TODO Auto-generated method stub
		String hql ="from RoleType";
		return super.find(hql);
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
