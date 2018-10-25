package htos.coresys.dao;

import htos.coresys.entity.MenuToBtn;

import java.util.List;


/** 
 *  
 *  
 */

public interface MenuToBtnDao extends BaseDao<MenuToBtn> {
	//通过菜单id获取菜单对应的按钮
	public List<MenuToBtn> findListByMenuId(String menuId);
	//获取所有的菜单按钮
	public List<MenuToBtn> findAllMenuBtn();
	//根据多个菜单id，获取对应的多个菜单按钮
	public List<MenuToBtn> findMenuBtnByMenuIds(String menuIds);
	//根据多个菜单id及指定多个的菜单按钮id，获取对应的多个菜单按钮
	public List<MenuToBtn> findListByMenuId(String menuId, String docIds);
}