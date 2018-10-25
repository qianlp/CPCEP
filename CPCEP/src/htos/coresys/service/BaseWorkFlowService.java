package htos.coresys.service;

import htos.coresys.entity.BaseWorkFlow;



public interface BaseWorkFlowService {

	public void addBaseWorkFlow(BaseWorkFlow workFlow);

	public BaseWorkFlow getWorkFlowByDocId(String docId);

	public void updateBaseWorkFlow(BaseWorkFlow workFlow);

}
