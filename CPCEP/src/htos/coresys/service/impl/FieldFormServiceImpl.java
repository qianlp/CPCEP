package htos.coresys.service.impl;

import htos.coresys.dao.FieldFormDao;
import htos.coresys.entity.Menu;
import htos.coresys.entity.FieldForm;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.MenuService;
import htos.coresys.service.FieldFormService;
import htos.coresys.util.CommonUtil;

public class FieldFormServiceImpl implements FieldFormService {

	private FieldFormDao fieldFormDao;
	private MenuService menuService;
	private CommonFacadeService<FieldForm> commonFacadeService;
	
	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public FieldFormDao getFieldFormDao() {
		return fieldFormDao;
	}

	public void setFieldFormDao(FieldFormDao fieldFormDao) {
		this.fieldFormDao = fieldFormDao;
	}
	
	@Override
	public FieldForm getFieldFormById(String id){
		return fieldFormDao.get(FieldForm.class, id);
	}

	@Override
	public void updateFieldForm(FieldForm fieldForm,String menuId){
		if(!CommonUtil.isNullOrEmpty(menuId)){
			Menu menu = menuService.findMenuById(menuId);
			fieldForm.setMenu(menu);
		}
	//	fieldFormDao.update(fieldForm);
		commonFacadeService.saveOrUpdate(fieldForm);
	}
	
	@Override
	public void addFieldForm(FieldForm fieldForm,String menuId){
		Menu menu2 = menuService.findOneMenuById("entityClsName",fieldForm.getClass().getSimpleName());
		if(!CommonUtil.isNullOrEmpty(menuId)){
			Menu menu = menuService.findMenuById(menuId);
			fieldForm.setMenu(menu);
		}
		//fieldFormDao.save(fieldForm);
		commonFacadeService.save(fieldForm, menu2.getUuid());
	}

	@Override
	public FieldForm getFieldFormByMenuId(String menuId) {
		return fieldFormDao.findFieldFormByMenuId(menuId);
	}

	public CommonFacadeService<FieldForm> getCommonFacadeService() {
		return commonFacadeService;
	}

	public void setCommonFacadeService(CommonFacadeService<FieldForm> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}
	
}
