package htos.coresys.service.impl;

import htos.coresys.dao.RightDao;
import htos.coresys.entity.Button;
import htos.coresys.entity.Dept;
import htos.coresys.entity.DeptToUser;
import htos.coresys.entity.DocInfo;
import htos.coresys.entity.Menu;
import htos.coresys.entity.MenuToBtn;
import htos.coresys.entity.Right;
import htos.coresys.entity.User;
import htos.coresys.entity.UserToRole;
import htos.coresys.service.ButtonService;
import htos.coresys.service.DeptService;
import htos.coresys.service.DeptToUserService;
import htos.coresys.service.MenuFacadeService;
import htos.coresys.service.MenuService;
import htos.coresys.service.MenuToBtnService;
import htos.coresys.service.RightService;
import htos.coresys.service.UserService;
import htos.coresys.util.CommonUtil;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;


/**
 * 菜单外观模型服务层
 * 
 * @author 庞成祥
 * 
 */
public class MenuFacadeServiceImpl implements MenuFacadeService {
	private MenuService menuService;
	private ButtonService buttonService;
	private RightService rightService;
	private MenuToBtnService menuToBtnService;

	private UserService userService;
	private DeptService deptService;
	private DeptToUserService deptToUserService;
	// ---加载获取权限过滤后的菜单-----
	@Override
	public List<Map<String, String>> findMenusData(String orgIds) {
		//如果为超级管理员则直接查询
		HttpServletRequest request = ServletActionContext.getRequest();
		User user=(User)request.getSession().getAttribute("user");
		if(user.getUserType()!=null && user.getUserType()==0){
			return menuService.dataList(menuService.findAllMenu());
		}
		
		Set<String> set = rightService.getDocIds(orgIds, Menu.class.getSimpleName());
		if (set.size()==0) {
			return null;
		} else {
			List<Menu> s = menuService.findMenusByOrgIds(set);
			return menuService.dataList(s);
		}
	}

	@Override
	public void addMenu(Menu menu, String sameLevelNodeId, String rigthData, String btnIds) {
		//添加菜单
		String menuId = menuService.addMenu(menu, sameLevelNodeId);
		//添加菜单按钮
		if (!CommonUtil.isNullOrEmpty(btnIds)) {
			addBtnToMenu(menu,btnIds);
		}
		//添加添加菜单查看权限
		if (!CommonUtil.isNullOrEmpty(rigthData)) {
			if (!"[]".equals(rigthData)) {
				rightService.updateRight(menuId, Menu.class.getSimpleName(), rigthData);
			}
		}
	}

	public void addBtnToMenu(Menu menu, String btnIds) {
		// ------保存菜单按钮信息-------
		List<Button> btnList = buttonService.findAllButton();
		Map<String, Button> btnMap = new HashMap<String, Button>();
		for (Button btn : btnList) {
			btnMap.put(btn.getUuid(), btn);
		}
		String[] arrBtnIds = btnIds.split(",");
		Button btn = null;
		MenuToBtn menuToBtn = null;
		for (int i = 0; i < arrBtnIds.length; i++) {
			btn = btnMap.get(arrBtnIds[i]);
			menuToBtn = new MenuToBtn();
			menuToBtn.setMenu(menu);
			if (CommonUtil.isNullOrEmpty(btn)) {
			} else {
				menuToBtn.setBtnId(btn.getUuid());
				menuToBtn.setBtnName(btn.getBtnName());
				menuToBtn.setBtnFunction(btn.getBtnFunction());
				menuToBtn.setBtnIcon(btn.getBtnIcon());
				menuToBtn.setBtnTitle(btn.getBtnTitle());
				menuToBtn.setBtnStyle(btn.getBtnStyle());
				menuToBtn.setCreateDate(new Date());
			}
			menuToBtn.setBtnIsLook(1);
			menuToBtnService.save(menuToBtn);
		}
	}

	@Override
	public void updateMenu(Menu menu, String sameLevelNodeId, String position, String rigthData,String btnIds, String userPerEname) throws IntrospectionException {
		String menuId = menuService.updateMenu(menu, sameLevelNodeId, position,userPerEname);
		//添加菜单按钮
		if (!CommonUtil.isNullOrEmpty(btnIds)) {
			addBtnToMenu(menu,btnIds);
		}
		if (!CommonUtil.isNullOrEmpty(rigthData)) {
			if (!"[]".equals(rigthData)) {
				rightService.updateRight(menuId, Menu.class.getSimpleName(), rigthData);
			}
		}
	}

	@Override
	public List<Menu> findRootMenuList() {
		// TODO Auto-generated method stub
		return menuService.findRootMenuList();
	}

	@Override
	public List<Map<String, String>> findChildMenuList(Menu menu) {
		// TODO Auto-generated method stub
		return menuService.dataList(menuService.findChildMenuList(menu));
	}

	@Override
	public List<Map<String, String>> findSubMenuList(Menu menu) {
		// TODO Auto-generated method stub
		return menuService.dataList(menuService.findSubMenuList(menu));
	}
	
	//菜单、按钮结构树
	@Override
	public List<Map<String, String>> findMenuTreeNodeList(Menu menu) {
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		List<Menu> menuList = menuService.findSubMenuList(menu);
		dataList.addAll(menuService.dataList(menuList));
		StringBuffer sb = new StringBuffer();
		for(Menu m:menuList){
			sb.append(",");
			sb.append(m.getUuid());
		}
		if(sb.length()>0){
			List<MenuToBtn> menuToBtnList = menuToBtnService.findMenuBtnByMenuIds(sb.substring(1));
			dataList.addAll(menuToBtnService.dataList(menuToBtnList));
		}
 		return dataList;
	}
	
	public List<Map<String, String>> findMenuTreeNodeListById(Menu menu) {
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		
		List<Menu> menuList=new ArrayList<Menu>();
		Menu pMenu=menuService.findMenuById(menu.getUuid());
		menuList.add(pMenu);
		dataList.addAll(menuService.dataList(menuList));
		menuList.clear();
		
		menuList=rtuSubList(pMenu.getUuid(),menuService.findSubMenuList(menu));
		dataList.addAll(menuService.dataList(menuList));
		
		StringBuffer sb = new StringBuffer();
		sb.append(",");
		sb.append(pMenu.getUuid());
		for(Menu m:menuList){
			sb.append(",");
			sb.append(m.getUuid());
		}
		if(sb.length()>0){
			List<MenuToBtn> menuToBtnList = menuToBtnService.findMenuBtnByMenuIds(sb.substring(1));
			dataList.addAll(menuToBtnService.dataList(menuToBtnList));
		}
 		return dataList;
	}
	
	private List<Menu> rtuSubList(String pid,List<Menu> mList){
		List<Menu> subMList=new ArrayList<Menu>();
		for(Menu m:mList){
			if(m.getMenuPid().equals(pid)){
				subMList.add(m);
				subMList.addAll(rtuSubList(m.getUuid(),mList));
			}
		}
		return subMList;
	}
	
	@Override
	public List<Map<String, String>> findAllMenuJson() {
		// TODO Auto-generated method stub
		return menuService.dataList(menuService.findAllMenuJson());
	}

	@Override
	public Menu findMenuById(String menuId) {
		// TODO Auto-generated method stub
		Menu menu = menuService.findMenuById(menuId);
		menu.setMenuToBtn(new LinkedHashSet<MenuToBtn>(menuToBtnService.findBtnListByMenuId(menuId)));
		return menu;
	}
	
	//点击菜单时，根据权限过滤按钮
	@Override
	public Menu findMenuById(String menuId,String orgIds) {
		// TODO Auto-generated method stub
		Menu menu = menuService.findMenuById(menuId);
		Set<String> set = rightService.getDocIds(orgIds, MenuToBtn.class.getSimpleName());
		menu.setMenuToBtn(new LinkedHashSet<MenuToBtn>(menuToBtnService.findBtnListByMenuId(menuId,set)));//页面按钮排序
		return menu;
	}
	
	@Override
	public List<Map<String, String>> findMenusHasWF() {
		// TODO Auto-generated method stub
		return menuService.dataList(menuService.findMenusHasWF());
	}

	// --------------按钮-----------------

	@Override
	public void addButton(Button button) {
		buttonService.saveButton(button);
	}
	
	@Override
	public void addButton(Button button, String rightId) {
		buttonService.saveButton(button);
		try {
			Menu  menu = menuService.findMenuById(rightId);
			rightService.addRight(button.getUuid(),Button.class.getSimpleName(), menu.getDataRightJson());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public List<Map<String, String>> findAllButton() {
		return buttonService.dataList(buttonService.findAllButton());
	}

	// -----------------------------------------

	//-------菜单按钮开始------
	@Override
	public void updateMenuBtn(MenuToBtn menuToBtn,String rigthData){
		menuToBtnService.update(menuToBtn);
		if (!CommonUtil.isNullOrEmpty(rigthData)) {
			if (!"[]".equals(rigthData)) {
				rightService.updateRight(menuToBtn.getUuid(), MenuToBtn.class.getSimpleName(), rigthData);
			}
		}
	}
	
	@Override
	public MenuToBtn findMenuBtnById(String id){
		return menuToBtnService.findMenuBtnById(id);
	}
	
	@Override
	public void delMenuBtnById(String id){
		menuToBtnService.delMenuBtnById(id);
	}
	//-------菜单按钮结束------
	
	
	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public RightService getRightService() {
		return rightService;
	}

	public void setRightService(RightService rightService) {
		this.rightService = rightService;
	}

	public ButtonService getButtonService() {
		return buttonService;
	}

	public void setButtonService(ButtonService buttonService) {
		this.buttonService = buttonService;
	}

	public MenuToBtnService getMenuToBtnService() {
		return menuToBtnService;
	}

	public void setMenuToBtnService(MenuToBtnService menuToBtnService) {
		this.menuToBtnService = menuToBtnService;
	}

	//-----------------
	/**
	* 获取菜单权限
	* @param 
	* 	orgId：组织或角色对应的唯一标识
	* 	treeDocIds：需要分配地菜单的唯一标识
	* 	righttype：权限类型（部门，个人，角色）

	* @return
	* 	菜单权限列表
	* @exception
	* 	
	* @author bitwise
	* @Time 2016-06-22
	*/
	@Override
	public List<Map<String, String>> getMenuRight(String orgId,String treeDocIds,String righttype){
		if(CommonUtil.isNullOrEmpty(orgId) || CommonUtil.isNullOrEmpty(treeDocIds)){
			return null;
		}else{
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			String tempTree=treeDocIds.replaceAll("\\$", "'").replaceAll("\\^", ",");

			RightDao rightDao=rightService.getRightDao();
			List<Right> rightList = null;
			StringBuffer sb = null;
			Map<String, String> map = new HashMap<String, String>();
			
			if("1".equals(righttype)){
				//获取当前部门的所有父部门
				List<Dept> deptList=deptService.getAncestors(orgId);
				sb = new StringBuffer("------");
				for (Dept dt : deptList) {
					sb.append("','");
					sb.append(dt.getUuid());
				}
				//获取当前部门的所有父部门的菜单权限
				rightList = rightDao.getRightByOrgIdAndDocId(sb.toString(),tempTree);
				for(Right r:rightList){
					map.put(r.getDocId(), "3");
				}
				//获取当前部门所拥有的菜单权限
				rightList = rightDao.getRightByOrgIdAndDocId(orgId,tempTree);
				for(Right r:rightList){
					//未继承的菜单
					if (!map.containsKey(r.getDocId())) {
						map.put(r.getDocId(), "2");
					}
				}
				list.add(map);				
			}else if("2".equals(righttype)){
				User user = userService.findUserById(orgId);
				Set<DeptToUser> s = user.getDeptToUsers();
				sb = new StringBuffer("------");
				for (DeptToUser dt : s) {
					sb.append("','");
					sb.append(dt.getDept().getUuid());
				}
				Set<UserToRole> s1 = user.getUserToRoles();
				for (UserToRole ut : s1) {
					sb.append("','");
					sb.append(ut.getRole().getUuid());
				}
				//获取当前用户所属部门或角色的菜单权限
				rightList = rightDao.getRightByOrgIdAndDocId(sb.toString(),tempTree);
				for(Right r:rightList){
					map.put(r.getDocId(), "3");
				}
				//获取当前用户所拥有的菜单权限
				rightList = rightDao.getRightByOrgIdAndDocId(orgId,tempTree);
				for(Right r:rightList){
					//未继承的菜单
					if (!map.containsKey(r.getDocId())) {
						map.put(r.getDocId(), "2");
					}
				}
				list.add(map);
			}else if("3".equals(righttype)){
				rightList = rightDao.getRightByOrgIdAndDocId(orgId,tempTree);
				for(Right r:rightList){
					map.put(r.getDocId(), "2");
				}
				list.add(map);
			}
			return list;
		}		
	}
	
	/**
	* 保存菜单权限
	* @param 
	* 	orgId：组织或角色对应的唯一标识
	* 	treeDocIds：需要分配地菜单的唯一标识
	* 	righttype：权限类型（部门，个人，角色）

	* @return
	* 	空
	* @exception
	* 	
	* @author bitwise
	* @Time 2016-06-22
	*/
	@Override
	public void saveMenuRight(String orgId,String treeDocIds,String righttype){
		if(CommonUtil.isNullOrEmpty(orgId) || CommonUtil.isNullOrEmpty(treeDocIds) || CommonUtil.isNullOrEmpty(righttype)){
		}else{
			String[] arrDocID = treeDocIds.split("\\^");
			List<Right> rightList = null;
			String docId=null;
			String status=null;
			String modoName=null;
			String[] arrPar = null;
			RightDao rightDao=rightService.getRightDao();
			for (String id : arrDocID) {
				arrPar = id.split("-");
				docId = arrPar[0];
				status = arrPar[1];
				modoName= arrPar[2];
				rightList = rightDao.getRightByOrgIdAndDocId(orgId,"'"+ docId +"'");
				//如果为空，证明未在权限中间表中创建记录
				if(rightList.isEmpty()){
					Right r = new Right();
					r.setDocId(docId);
					r.setOrgId(orgId);
					r.setModoName(modoName);
					r.setOperType(1);
					r.setRightType(Integer.parseInt(righttype));
					rightDao.save(r);
				}else{
					for(Right r:rightList){
						if("1".equals(status)){							
							rightDao.delete(r);
						}
					};
				}
			}
		}		
	}
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public DeptService getDeptService() {
		return deptService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	
	public DeptToUserService getDeptToUserService() {
		return deptToUserService;
	}

	public void setDeptToUserService(DeptToUserService deptToUserService) {
		this.deptToUserService = deptToUserService;
	}

	@Override
	public List<Menu> findListMenuPid(String menuPid) {
		return menuService.findListMenuPid(menuPid);
	}

	@Override
	public List<MenuToBtn> findListMenuBtnById(String menuId) {
		return menuToBtnService.findBtnListByMenuId(menuId);
	}

	@Override
	public Menu getMeunByName(String menuName) {
		
		return  menuService.getMeunByName(menuName);
	}

	@Override
	public List<Map<String, String>> findSupplierMenu() {
		return menuService.dataList(menuService.findSupplierMenu());
	}
}
