package htos.coresys.dao.impl;

import htos.coresys.dao.MenuToBtnDao;
import htos.coresys.entity.MenuToBtn;
import htos.coresys.entity.User;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

public class MenuToBtnDaoImpl extends BaseDaoImpl<MenuToBtn> implements
		MenuToBtnDao {

	@Override
	public List<MenuToBtn> findListByMenuId(String menuId) {
		// TODO Auto-generated method stub
		String hql = "from MenuToBtn m where m.menu.uuid='" + menuId
				+ "'  order by m.uuid asc";
		return super.find(hql);
	}

	@Override
	public List<MenuToBtn> findListByMenuId(String menuId, String docIds) {
		// TODO Auto-generated method stub
		String hql = "from MenuToBtn m where m.menu.uuid='" + menuId
				+ "' and m.uuid in (" + docIds + ") order by m.uuid asc";
		
		//超级管理员查看所有
		HttpServletRequest request = ServletActionContext.getRequest();
		User user=(User)request.getSession().getAttribute("user");
		if(user.getUserType()!=null && user.getUserType()==0){
			hql="from MenuToBtn m where m.menu.uuid='" + menuId
					+ "' order by m.uuid asc";
		}
		return super.find(hql);
	}

	@Override
	public List<MenuToBtn> findAllMenuBtn() {
		String hql = " from MenuToBtn ";
		return super.find(hql);
	}

	// 根据多个菜单id，获取对应的多个菜单按钮
	@Override
	public List<MenuToBtn> findMenuBtnByMenuIds(String menuIds) {
		menuIds = "'"
				+ menuIds.replace("[", "").replace("]", "").replace(",", "','")
				+ "'";
		String hql = "from MenuToBtn m where m.menu.uuid in (" + menuIds
				+ ") order by m.uuid asc";
		return super.find(hql);
	}

}