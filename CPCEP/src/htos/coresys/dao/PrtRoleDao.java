package htos.coresys.dao;

import java.util.List;

import htos.coresys.entity.PrtRole;



public interface PrtRoleDao extends BaseDao<PrtRole> {	
	
	public List<PrtRole> findPrtRoles(String roleIds);
	
	public List<PrtRole> findSubRoles(String prtRoleId);
}
