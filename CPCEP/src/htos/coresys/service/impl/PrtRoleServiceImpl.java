package htos.coresys.service.impl;

import java.util.List;

import htos.coresys.dao.PrtRoleDao;
import htos.coresys.entity.PrtRole;
import htos.coresys.service.PrtRoleService;


public class PrtRoleServiceImpl implements PrtRoleService {

	private PrtRoleDao prtRoleDao;

	
	@Override
	public List<PrtRole> findPrtRoles(String roleIds) {
		
		return prtRoleDao.findPrtRoles(roleIds);
	}


	public PrtRoleDao getPrtRoleDao() {
		return prtRoleDao;
	}


	public void setPrtRoleDao(PrtRoleDao prtRoleDao) {
		this.prtRoleDao = prtRoleDao;
	}


	@Override
	public List<PrtRole> findSubRoles(String prtRoleId) {
		return prtRoleDao.findSubRoles(prtRoleId);
	}

	
	@Override
	public void deletePrtRole(PrtRole prtRole) {
		prtRoleDao.delete(prtRole);
	}

}
