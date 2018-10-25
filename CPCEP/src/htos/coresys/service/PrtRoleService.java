package htos.coresys.service;

import java.util.List;

import htos.coresys.entity.PrtRole;


public interface PrtRoleService {

	public List<PrtRole> findPrtRoles(String roleIds);
	public List<PrtRole> findSubRoles(String prtRoleId);
	public void deletePrtRole(PrtRole prtRole);
}
