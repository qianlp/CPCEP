package htos.coresys.dao.impl;

import htos.coresys.dao.BaseWorkFlowDao;
import htos.coresys.entity.BaseWorkFlow;


public class BaseWorkFlowDaoImpl extends BaseDaoImpl<BaseWorkFlow> implements
BaseWorkFlowDao {

	@Override
	public BaseWorkFlow findWorkFlowByDocId(String docId) {
		String hql = " from BaseWorkFlow bwf where bwf.docId='" + docId + "'";
		return super.get(hql, new String[] {});
	}
}
