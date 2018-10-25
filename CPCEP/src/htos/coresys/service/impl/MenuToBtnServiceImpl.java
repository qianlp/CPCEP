package htos.coresys.service.impl;

import htos.coresys.dao.MenuToBtnDao;
import htos.coresys.entity.MenuToBtn;
import htos.coresys.service.MenuToBtnService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;


public class MenuToBtnServiceImpl implements MenuToBtnService {
	private MenuToBtnDao menuToBtnDao;
	
	@Override
	public void save(MenuToBtn menuToBtn){
		menuToBtnDao.save(menuToBtn);
	}
	
	@Override
	public void update(MenuToBtn menuToBtn){
		System.out.println("-------------------------------");
		System.out.println(menuToBtn.getUuid());
		menuToBtnDao.update(menuToBtn);
	}
	
	@Override
	public void delMenuBtnById(String id){
	//	menuToBtnDao.delete(menuToBtnDao.get(MenuToBtn.class, id));
		String hql ="delete from MenuToBtn where uuid='"+id+"'";
		menuToBtnDao.delete(hql);
	}
	
	@Override
	public MenuToBtn findMenuBtnById(String id){
		return menuToBtnDao.get(MenuToBtn.class, id);
	}
	
	@Override
	public List<MenuToBtn> findBtnListByMenuId(String menuId){
		return menuToBtnDao.findListByMenuId(menuId);
	}
	
	@Override
	public List<MenuToBtn> findBtnListByMenuId(String menuId, Set<String> docIdSet) {
		// TODO Auto-generated method stub
		String docIds = "'"+StringUtils.join(docIdSet.toArray(), "','")+"'";
		return  menuToBtnDao.findListByMenuId(menuId,docIds);
	}
	
	@Override
	public List<MenuToBtn> findAllMenuBtn() {
		// TODO Auto-generated method stub
		return menuToBtnDao.findAllMenuBtn();
	}
	
	@Override
	public List<MenuToBtn> findMenuBtnByMenuIds(String menuIds) {
		// TODO Auto-generated method stub
		return menuToBtnDao.findMenuBtnByMenuIds(menuIds);
	}
	
	@Override
	public List<Map<String, String>> dataList(Collection<MenuToBtn> menuBtnList) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (MenuToBtn m : menuBtnList) {
			map = new HashMap<String, String>();
			map.put("id", m.getUuid());
			map.put("text", m.getBtnName());
			map.put("pid", m.getMenu().getUuid());
			map.put("type",MenuToBtn.class.getSimpleName());
			map.put("status","0");
			list.add(map);
		}
		return list;
	}
	
	public MenuToBtnDao getMenuToBtnDao() {
		return menuToBtnDao;
	}

	public void setMenuToBtnDao(MenuToBtnDao menuToBtnDao) {
		this.menuToBtnDao = menuToBtnDao;
	}


	

	

	
	
}
