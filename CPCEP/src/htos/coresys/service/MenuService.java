package htos.coresys.service;

import htos.coresys.entity.Menu;

import java.beans.IntrospectionException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;



public interface MenuService {
	//获取菜单根节点list
	public List<Menu> findRootMenuList();
	//获取某个菜单下所有的子菜单list
	public List<Menu> findChildMenuList(Menu menu);
	//获取根节点下所有的子菜单list
	public List<Menu> findSubMenuList(Menu menu);
	//获取所有的菜单Json
	public List<Menu>  findAllMenuJson();
	//获取所有的菜单List
	public List<Menu> findAllMenu();
	//通过菜单id获取菜单
	public Menu findMenuById(String menuId);
	//保存菜单
	public String addMenu(Menu menu,String sameLevelNodeId);
	//根据带流程的菜单树
	public Set<Menu> findMenusHasWF();
	//对象list转map
	public List<Map<String, String>> dataList(Collection<Menu> menuList);
	//根据组织id获取需要展示的菜单,bitwise修改返回类型，原类型数组中元素无顺序
	public List<Menu>  findMenusByOrgIds(Set<String> set);
	//更改菜单
	public String updateMenu(Menu menu, String sameLevelNodeId, String position,String userPerEname) throws IntrospectionException;	

	//根据条件查询菜单数据记录
	public Menu findOneMenuById(String attr,String value);
	public List<Menu> findListMenuPid(String menuPid);
	
	public Menu getMeunByName(String menuName);

	List<Menu> findSupplierMenu();

}