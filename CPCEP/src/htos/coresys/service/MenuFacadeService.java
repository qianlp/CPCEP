package htos.coresys.service;

import htos.coresys.entity.Button;
import htos.coresys.entity.Menu;
import htos.coresys.entity.MenuToBtn;

import java.beans.IntrospectionException;
import java.util.List;
import java.util.Map;


public interface MenuFacadeService {
	// 1获取带权限的菜单
	public List<Map<String, String>> findMenusData(String orgIds);

	// 2添加菜单
	public void addMenu(Menu menu, String sameLevelNodeId, String strJson, String menuIds);

	// 3更新菜单
	public void updateMenu(Menu menu, String sameLevelNodeId, String position, String rigthData, String btnIds, String userPerEname) throws IntrospectionException;

	// 4获取菜单根节点list
	public List<Menu> findRootMenuList();

	// 5获取某个菜单下所有的子菜单list
	public List<Map<String, String>> findChildMenuList(Menu menu);

	// 6获取根节点下所有的子菜单list
	public List<Map<String, String>> findSubMenuList(Menu menu);

	// 7获取所有的菜单Json
	public List<Map<String, String>> findAllMenuJson();

	// 9通过菜单id获取菜单
	public Menu findMenuById(String menuId);

	// 10根据带流程的菜单树
	public List<Map<String, String>> findMenusHasWF();

	// 菜单、按钮结构树
	public List<Map<String, String>> findMenuTreeNodeList(Menu menu);
	public List<Map<String, String>> findMenuTreeNodeListById(Menu menu);

	// -------------按钮------------------
	
	public void addButton(Button button);
	//添加带默认权限的菜单
	public void addButton(Button button, String rightId);
	
	public List<Map<String, String>> findAllButton();
	// ----------------------------------

	//-------菜单按钮开始------
	//更新菜单按钮
	public void updateMenuBtn(MenuToBtn menuToBtn,String rigthData);
	//根据id获取菜单按钮对象
	public MenuToBtn findMenuBtnById(String id);
	//根据id删除菜单按钮对象
	public void delMenuBtnById(String id);
	//-------菜单按钮结束------

	public Menu findMenuById(String menuId, String orgIds);	


	//bitwise 2016-06-22
	public List<Map<String, String>> getMenuRight(String orgId,String treeDocIds,String type);
	public void saveMenuRight(String orgId,String treeDocIds,String type);

	public List<Menu> findListMenuPid(String menuPid);

	public List<MenuToBtn> findListMenuBtnById(String menuId);
	
	/**
	 * 通过菜单名字查找菜单对象
	 * @param menuName
	 * @return
	 */
	public Menu getMeunByName(String menuName);


	/**
	 * 查找供应商菜单
	 */
	List<Map<String, String>> findSupplierMenu();

}