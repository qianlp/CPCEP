package htos.coresys.dao.impl;

import java.util.List;

import htos.coresys.dao.PrtRoleDao;
import htos.coresys.entity.PrtRole;

public class PrtRoleDaoImpl extends BaseDaoImpl<PrtRole>  implements PrtRoleDao {

	@Override
	public List<PrtRole> findPrtRoles(String roleIds) {
		roleIds = "'"+roleIds.replace(",", "','")+"'";
		return super.find("from PrtRole where roleId in ("+ roleIds +")");
	}

	@Override
	public List<PrtRole> findSubRoles(String prtRoleId) {
		return super.find("from PrtRole where prtRoleId='"+ prtRoleId +"'");
	}
}
