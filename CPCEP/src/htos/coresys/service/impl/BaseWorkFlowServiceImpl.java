package htos.coresys.service.impl;

import htos.coresys.dao.BaseWorkFlowDao;
import htos.coresys.entity.BaseWorkFlow;
import htos.coresys.service.BaseWorkFlowService;


public class BaseWorkFlowServiceImpl implements BaseWorkFlowService {

	private BaseWorkFlowDao baseWorkFlowDao;
	
	/**
	 * @return the baseWorkFlowDao
	 */
	public BaseWorkFlowDao getBaseWorkFlowDao() {
		return baseWorkFlowDao;
	}

	/**
	 * @param baseWorkFlowDao the baseWorkFlowDao to set
	 */
	public void setBaseWorkFlowDao(BaseWorkFlowDao baseWorkFlowDao) {
		this.baseWorkFlowDao = baseWorkFlowDao;
	}

	@Override
	public void addBaseWorkFlow(BaseWorkFlow workFlow) {
		baseWorkFlowDao.save(workFlow);
	}

	@Override
	public BaseWorkFlow getWorkFlowByDocId(String docId) {
		return baseWorkFlowDao.findWorkFlowByDocId(docId);
	}

	@Override
	public void updateBaseWorkFlow(BaseWorkFlow workFlow) {
		baseWorkFlowDao.update(workFlow);
	}

}
