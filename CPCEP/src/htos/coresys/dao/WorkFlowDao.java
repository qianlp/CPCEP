package htos.coresys.dao;

import htos.coresys.entity.WorkFlow;

import java.util.List;



public interface WorkFlowDao extends BaseDao<WorkFlow> {
	public List<WorkFlow> findAllWF();
	
	/**
	 * 通过菜单id 获取唯一激活的流程id
	 * @param menuId
	 * @return
	 */
	public WorkFlow findWorkFlowByMenuId(String menuId);
}
