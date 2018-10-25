package htos.coresys.dao;

import htos.coresys.entity.BaseWorkFlow;



public interface BaseWorkFlowDao extends BaseDao<BaseWorkFlow> {
	/**
	 * 通过文档id 获取执行中的流程
	 * @param menuId
	 * @return
	 */
	public BaseWorkFlow findWorkFlowByDocId(String docId);
}
