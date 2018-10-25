package htos.coresys.service.impl;

import htos.coresys.dao.WorkFlowDao;
import htos.coresys.entity.Menu;
import htos.coresys.entity.WorkFlow;
import htos.coresys.service.MenuService;
import htos.coresys.service.WorkFlowService;
import htos.coresys.util.CommonUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


public class WorkFlowServiceImpl implements WorkFlowService {

	private WorkFlowDao workFlowDao;
	private MenuService menuService;
	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public WorkFlowDao getWorkFlowDao() {
		return workFlowDao;
	}

	public void setWorkFlowDao(WorkFlowDao workFlowDao) {
		this.workFlowDao = workFlowDao;
	}
	
	@Override
	public WorkFlow getWorkFlowById(String id){
		return workFlowDao.get(WorkFlow.class, id);
	}
	
	@Override
	public List<Map<String,String>> findWFTree(){
		List<Map<String,String>> list =menuService.dataList(menuService.findMenusHasWF());
		List<WorkFlow> wfList = workFlowDao.findAllWF();
		
		HashSet<String> uniqueKeySet = new HashSet<String>();
		
		Map<String,String> map=null;
		for(WorkFlow wf:wfList){
			map = new HashMap<String,String>();
			String strID=new String( CommonUtil.MD5(wf.getWfName()) );
			map.put("id", strID );
			map.put("text", wf.getWfName());
			map.put("pid", wf.getMenu().getUuid());
			//map.put("type","menu");
			if(!uniqueKeySet.contains(strID)){
				list.add(map);
				uniqueKeySet.add(strID);
			}
			
			map = new HashMap<String,String>();
			map.put("id", wf.getUuid());
			map.put("text", wf.getWfVersion().toString());
			map.put("pid", strID);
			map.put("status", wf.getWfStatus());
			//map.put("type","workFlow");
			list.add(map);
		}
		return list;
	}
	
	@Override
	public void updateWorkFlow(WorkFlow workFlow,String menuId){
		if(CommonUtil.isNullOrEmpty(menuId)){
		}else{
			Menu menu = menuService.findMenuById(menuId);
			workFlow.setMenu(menu);
		}
		workFlowDao.update(workFlow);
	}
	
	@Override
	public void addWorkFlow(WorkFlow workFlow,String menuId){
		if(CommonUtil.isNullOrEmpty(menuId)){
		}else{
			Menu menu = menuService.findMenuById(menuId);
			workFlow.setMenu(menu);
		}
		workFlowDao.save(workFlow);
	}

	@Override
	public WorkFlow getWorkFlowByMenuId(String menuId) {
		// TODO Auto-generated method stub
		return workFlowDao.findWorkFlowByMenuId(menuId);
	}
	
}
