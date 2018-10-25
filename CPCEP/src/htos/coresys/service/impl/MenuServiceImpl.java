package htos.coresys.service.impl;

import htos.common.util.CopyUtil;
import htos.coresys.dao.MenuDao;
import htos.coresys.entity.Menu;
import htos.coresys.service.MenuService;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MenuServiceImpl implements MenuService {

	private MenuDao menuDao;

	public MenuDao getMenuDao() {
		return menuDao;
	}

	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}
	
	
	@Override
	public Menu findMenuById(String menuId) {
		return menuDao.get(Menu.class, menuId);

	}

	@Override
	public List<Menu> findAllMenuJson() {
		return menuDao.findAllList();

	}

	@Override
	public List<Menu> findAllMenu() {
		return menuDao.findAllList();

	}

	// 根据带流程的菜单树
	@Override
	public Set<Menu> findMenusHasWF() {
		List<Menu> allList = menuDao.findAllList();
		Map<String, Menu> map = new HashMap<String, Menu>();
		Set<Menu> s = new HashSet<Menu>();
		for (Menu m : allList) {
			map.put(m.getUuid(), m);
			// 1 启用流程 0 不启用流程(默认)
			if ("1".equals(m.getMenuIsHasWF())) {
				s.add(m);
			}
		}
		String pid = null;
		Menu pMenu = null;
		Set<Menu> dataSet = new HashSet<Menu>();
		dataSet.addAll(s);
		for (Menu m : s) {
			pid = m.getMenuPid();
			while (map.containsKey(pid)) {
				pMenu = map.get(pid);
				if (dataSet.contains(pMenu)) {
					break;
				}
				dataSet.add(pMenu);
				pid = pMenu.getMenuPid();
			}
		}
		return dataSet;
	}
	//bitwise修改返回类型，原类型数组中元素无顺序
	@Override
	public List<Menu> findMenusByOrgIds(Set<String> set) {
		//所有菜单
		List<Menu> allList = menuDao.findAllList();
		int intM=set.size();
		int intN = 0;
		List<Menu> s = new ArrayList<Menu>();
		Menu m = null;
		//bitwise修改
		Iterator<Menu> it = allList.iterator();
		while (it.hasNext()) {
			m=it.next();
			if(set.contains(m.getUuid())){
				s.add(m);
				//从所有菜单散列中移除已经添加过的对象，目的为了提高从散列中查找菜单对象速度
				it.remove();
				intN++;
			}
			//如果增加的菜单数与拥有权限菜单数相同就跳出循环
			if(intM==intN){break;}
		}
		/*
		while (it.hasNext()) {
			id=(String)it.next();
			//System.out.println(id);
			m = map.get(id);
			if(CommonUtil.isNullOrEmpty(m)){
				//System.out.println("no:" + id);
			}else{
				//父节点ID
				pid = m.getMenuPid();
				//父节点对象
				pm = map.get(pid);
				if(pm != null){
					//有权限菜单列表中不包含父对象
					if (!s.contains(pm)) {
						//所有菜单散列中是否包含父对象
						if (map.containsKey(pid)) {
							s.add(pm);
							//从所有菜单散列中移除已经添加过的对象，目的为了提高从散列中查找菜单对象速度
							map.remove(pid);
							//System.out.println("move pid:" + pid);
						}
					}
				}
				s.add(m);
				//从所有菜单散列中移除已经添加过的对象，目的为了提高从散列中查找菜单对象速度
				map.remove(id);
				//System.out.println("move id:" + id);
			}
		}
		*/
		return s;
	}

	@Override
	public List<Menu> findSubMenuList(Menu menu) {
		return menuDao.findSubMenuList(menu);
	}

	@Override
	public List<Menu> findChildMenuList(Menu menu) {
		return menuDao.findChildNodesList(menu);
	}

	@Override
	public List<Menu> findRootMenuList() {
		return menuDao.findRootMeauList();
	}

	@Override
	public List<Map<String, String>> dataList(Collection<Menu> menuList) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		//int intQ=0;
		for (Menu m : menuList) {
			map = new HashMap<String, String>();
			map.put("id", m.getUuid());
			map.put("text", m.getMenuName());
			map.put("pid", m.getMenuPid());
			map.put("rootId", m.getMenuRootId());
			map.put("type", Menu.class.getSimpleName());
			map.put("pageSubAddress", m.getPageSubAddress());
			map.put("icon", m.getMenuIcon());
			//bitwise修改，增加了此属性，用于菜单的展现
			map.put("status", "0");
			map.put("AppType", "1");
			/*bitwise
			if(intQ%2==0){
				map.put("img", "addfolder.gif");
			}else{
				map.put("img", "filter.gif");
			}
			*/
			list.add(map);
			//intQ++;
		}
		return list;
	}

	@Override
	public String addMenu(Menu menu, String sameLevelNodeId) {
		int tempNo = 0; // 菜单位置编号（系统自己维护，用于菜单排序）
		int nodeLevel = 0; // 菜单层级
		if (sameLevelNodeId != null && sameLevelNodeId.length() > 0) {
			Menu laterMenu = findMenuById(sameLevelNodeId);
			tempNo = laterMenu.getMenuOpsition();
			nodeLevel = laterMenu.getMenuLevel();
			menuDao.updateLaterMenuPosition(laterMenu);
		} else {
			Object obj = menuDao.findMaxMenuPosition(menu);
			if (null == obj) {
				tempNo = 0;
			} else {
				tempNo = Integer.parseInt(obj.toString());
			}
			tempNo = tempNo + 1;
			if ("-1".equals(menu.getMenuPid().toString())) {
				nodeLevel = 1;
			} else {
				Menu pMenu = findMenuById(menu.getMenuPid());
				if (pMenu != null) {
					nodeLevel = pMenu.getMenuLevel() + 1;
				}

			}
		}
		menu.setMenuLevel(nodeLevel);
		menu.setMenuOpsition(tempNo);
		menuDao.save(menu);
		if ("-1".equals(menu.getMenuPid().toString())) {
			menu.setMenuRootId(menu.getUuid());
			menuDao.update(menu);
		}
		return menu.getUuid();
	}

	@Override
	public String updateMenu(Menu pMenu, String sameLevelNodeId, String position, String userPerEname) throws IntrospectionException {
		// TODO Auto-generated method stub
		int positionNo = 0;
		Menu samLevMenu = null;
		Menu menu = findMenuById(pMenu.getUuid());
		CopyUtil.copyToObj(menu, pMenu);
		menu.setPageSearchAddress(pMenu.getPageSearchAddress());
		menu.setActionAddress(pMenu.getActionAddress());
		menu.setEntityClsName(pMenu.getEntityClsName());
		menu.setPageComAddress(pMenu.getPageComAddress());
		menu.setPageSubAddress(pMenu.getPageSubAddress());
		menu.setQueryEntityClsname(pMenu.getQueryEntityClsname());
		menu.setHasSupplier(pMenu.getHasSupplier());
		
		Menu penu=findMenuById(pMenu.getMenuPid());
		if(penu!=null){
			menu.setMenuRootId(penu.getMenuRootId());
		}
		
		if ("0".equals(position)) {
			Object obj = menuDao.findMaxMenuPosition(menu);
			positionNo = Integer.parseInt(obj.toString());
			samLevMenu = new Menu();
			samLevMenu.setMenuOpsition(positionNo);
			menuDao.updatePositionToLast(menu, samLevMenu);
		} else if ("1".equals(position)) {
			if (sameLevelNodeId != null && sameLevelNodeId.length() > 0) {
				samLevMenu = findMenuById(sameLevelNodeId);
				if (menu.getMenuOpsition() > samLevMenu.getMenuOpsition()) {
					positionNo = samLevMenu.getMenuOpsition();
					menuDao.updatePosition(menu, samLevMenu);
				} else if (menu.getMenuOpsition() < samLevMenu.getMenuOpsition()) {
					positionNo = samLevMenu.getMenuOpsition() - 1;
					menuDao.updatePosition(menu, samLevMenu);
				} else {
					positionNo = menu.getMenuOpsition();
				}
			}
		} else {
			positionNo = menu.getMenuOpsition();
		}
		menu.setUpdateBy(userPerEname);
		menu.setUpdateDate(new Date());
		if(positionNo!=0){
			menu.setMenuOpsition(positionNo);
		}
		menuDao.merge(menu);
		return menu.getUuid();

	}
	@Override
	public Menu findOneMenuById(String attr, String value) {
		return menuDao.findOneMenuById(attr,value);
	}

	@Override
	public List<Menu> findListMenuPid(String menuPid) {
		return menuDao.findListMenuPid(menuPid);
	}
	
	@Override
	public Menu getMeunByName(String menuName) {
		return menuDao.getMeunByName(menuName);
	}

	@Override
	public List<Menu> findSupplierMenu() {
		List<Menu> list = menuDao.find("from Menu where hasSupplier=1");
		return list;
	}
}
