package htos.coresys.service;

import htos.coresys.entity.MenuToBtn;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;





public interface MenuToBtnService {
	//保存菜单按钮
	public void save(MenuToBtn menuToBtn);
	//根据菜单id获取相应的按钮列表
	public List<MenuToBtn> findBtnListByMenuId(String menuId);
	//获取所有的菜单按钮
	public List<MenuToBtn> findAllMenuBtn();
	//根据多个菜单id，获取对应的多个菜单按钮
	public List<MenuToBtn> findMenuBtnByMenuIds(String menuIds);
	
	public List<Map<String, String>> dataList(Collection<MenuToBtn> menuList);
	
	//更新菜单按钮
	public void update(MenuToBtn menuToBtn);
	//根据id获取对象
	public MenuToBtn findMenuBtnById(String id);
	//根据id删除菜单按钮
	public void delMenuBtnById(String id);
	
	public Collection<MenuToBtn> findBtnListByMenuId(String menuId, Set<String> docIdSet);
}