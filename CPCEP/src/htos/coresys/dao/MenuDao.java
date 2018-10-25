package htos.coresys.dao;

import htos.coresys.entity.Menu;

import java.util.List;


/** 
 *  
 *  
 */

public interface MenuDao extends BaseDao<Menu> {
	// 获取同父节点下最大节点编号
		public Object findMaxMenuPosition(Menu menu);

		public int updatePosition(Menu curMenu, Menu laterMenu);
		//将节点放置同层次最后
		public int updatePositionToLast(Menu curMenu,Menu samLevMenu);

		public List<Menu> findAllList();
		//获取某个菜单节点下的所有子节点
		public List<Menu> findChildNodesList(Menu menu) ;
		//获取菜单根节点下的所有子节点
		public List<Menu> findSubMenuList(Menu menu);
		public List<Menu> findRootMeauList();
		//将该节点后所有位置编号+1
		public Integer updateLaterMenuPosition(Menu menu);
		//根据菜单id字符串获取对应的菜单对象
		public List<Menu> findMenusByIds(String ids);
		public Menu findOneMenuById(String attr, String value);

		public List<Menu> findListMenuPid(String menuPid);
		
		public Menu getMeunByName(String menuName);
}