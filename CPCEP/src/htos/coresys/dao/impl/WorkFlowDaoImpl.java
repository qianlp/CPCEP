package htos.coresys.dao.impl;

import htos.coresys.dao.WorkFlowDao;
import htos.coresys.entity.WorkFlow;

import java.util.List;

public class WorkFlowDaoImpl extends BaseDaoImpl<WorkFlow> implements
		WorkFlowDao {

	@Override
	public List<WorkFlow> findAllWF() {
		String hql = " from WorkFlow ";
		return super.find(hql);
	}

	@Override
	public WorkFlow findWorkFlowByMenuId(String menuId) {
		String hql = " from WorkFlow wf where wf.menu.uuid='" + menuId + "' and wf.wfStatus='1'";
		return super.get(hql, new String[] {});
	}

}
