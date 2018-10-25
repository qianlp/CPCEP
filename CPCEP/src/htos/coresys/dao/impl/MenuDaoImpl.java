package htos.coresys.dao.impl;

import htos.coresys.dao.MenuDao;
import htos.coresys.entity.Menu;

import java.util.List;


public class MenuDaoImpl extends BaseDaoImpl<Menu> implements MenuDao {

	@Override
	public Object findMaxMenuPosition(Menu menu) {
		String hql = "select max(m.menuOpsition) from Menu m where m.menuPid='"+menu.getMenuPid()+"' ";
		return super.findUnique(hql);
	}
	
	@Override
	public List<Menu> findMenusByIds(String ids){
		String hql = "from Menu u where u.uuid in ("+ ids +")";
		return super.find(hql);
	}
	
	@Override
	public int updatePosition(Menu curMenu, Menu samLevMenu) {
		// TODO Auto-generated method stub
		String hql="";
		if(curMenu.getMenuOpsition()>samLevMenu.getMenuOpsition()){
			hql = "update from Menu u set u.menuOpsition=(u.menuOpsition+1) where u.menuPid='"+curMenu.getMenuPid()+"' and u.menuOpsition>="+samLevMenu.getMenuOpsition()+"and u.menuOpsition<"+curMenu.getMenuOpsition();
		}else if(curMenu.getMenuOpsition()<samLevMenu.getMenuOpsition()){
			hql = "update from Menu u set u.menuOpsition=(u.menuOpsition-1) where u.menuPid='"+curMenu.getMenuPid()+"' and u.menuOpsition>="+curMenu.getMenuOpsition()+"and u.menuOpsition<"+samLevMenu.getMenuOpsition();
		}
		if("".equals(hql)){
			return 0;
		}else{
			return super.executeHql(hql);
		}
	}
	@Override
	public int updatePositionToLast(Menu curMenu, Menu samLevMenu) {
		// TODO Auto-generated method stub
		String hql = "update from Menu u set u.menuOpsition=(u.menuOpsition-1) where u.menuPid='"+curMenu.getMenuPid()+"' and u.menuOpsition>"+curMenu.getMenuOpsition();
		return super.executeHql(hql);
	}

	@Override
	public List<Menu> findAllList() {
		String hql="from Menu m where hasSupplier=0 order by m.menuLevel asc,m.menuOpsition asc";
		return super.find(hql);
	}
	
	
	@Override
	public List<Menu> findRootMeauList() {
		String hql="from Menu m where m.menuPid='-1' order by m.menuLevel asc,m.menuOpsition asc";
		return super.find(hql);
	}
	@Override
	public List<Menu> findSubMenuList(Menu menu){
		String hql="from Menu m where m.menuRootId='"+menu.getMenuRootId()+"' order by m.menuLevel asc,m.menuOpsition asc";
		return super.find(hql);
	}
	@Override
	public List<Menu> findChildNodesList(Menu menu) {
		String hql="from Menu m where m.menuPid='"+menu.getMenuPid()+"' order by m.menuLevel asc,m.menuOpsition asc";
		return super.find(hql);
	}
	
	
	public Integer updateLaterMenuPosition(Menu menu){
		String hql = "update from Menu m set m.menuOpsition=(m.menuOpsition+1) where m.menuPid='"+menu.getMenuPid()+"' and m.menuOpsition>="+menu.getMenuOpsition();
		return super.executeHql(hql);
	}
	
	@Override
	public Menu findOneMenuById(String attr, String value) {
		Menu menu = new Menu();
		String hql="from Menu m where m."+attr+"='"+value+"'";
		List<Menu> list = super.find(hql);
		if(list.size()>0) menu=list.get(0);
		return menu;
	}

	@Override
	public List<Menu> findListMenuPid(String menuPid) {
		String hql="from Menu where menuPid='"+menuPid+"'";
		List<Menu> list = super.find(hql);
		return list;
	}

	
	@Override
	public Menu getMeunByName(String menuName) {
		String hql="from Menu where menuName='"+menuName+"'";
		return (Menu) super.findUnique(hql);
	}
	
}