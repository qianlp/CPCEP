package htos.coresys.service;

import htos.coresys.entity.WorkFlow;

import java.util.List;
import java.util.Map;



public interface WorkFlowService {

	public void addWorkFlow(WorkFlow workFlow, String menuId);

	public List<Map<String, String>> findWFTree();

	public WorkFlow getWorkFlowById(String id);
	
	public WorkFlow getWorkFlowByMenuId(String menuId);

	public void updateWorkFlow(WorkFlow workFlow, String menuId);

}
