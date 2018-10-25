package htos.coresys.dao;

import htos.coresys.entity.FieldForm;


public interface FieldFormDao extends BaseDao<FieldForm> {	
	/**
	 * 通过菜单id 获取唯一激活的流程id
	 * @param menuId
	 * @return
	 */
	public FieldForm findFieldFormByMenuId(String menuId);
}
