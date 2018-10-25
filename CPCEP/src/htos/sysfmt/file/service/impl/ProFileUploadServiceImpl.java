package htos.sysfmt.file.service.impl;

import htos.common.util.CopyUtil;
import htos.coresys.entity.Menu;
import htos.coresys.entity.User;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.MenuService;
import htos.sysfmt.file.dao.ProFileDao;
import htos.sysfmt.file.entity.ProFileploadModel;
import htos.sysfmt.file.service.ProFileUploadService;

import java.beans.IntrospectionException;
import java.util.Date;

import org.apache.struts2.ServletActionContext;

public class ProFileUploadServiceImpl implements ProFileUploadService{
	private ProFileDao proFileDao;
	private CommonFacadeService<ProFileploadModel> commonFacadeService;
	private MenuService menuService;

	public ProFileDao getProFileDao() {
		return proFileDao;
	}

	public void setProFileDao(ProFileDao proFileDao) {
		this.proFileDao = proFileDao;
	}

	@Override
	public void saveProFileJson(ProFileploadModel proFileploadModel) {
		Menu menu = menuService.findOneMenuById("entityClsName",proFileploadModel.getClass().getSimpleName());	
		commonFacadeService.save(proFileploadModel, menu.getUuid());
	}

	@Override
	public void updateProFileJson(ProFileploadModel proFileploadMode) throws IntrospectionException {
		ProFileploadModel proFileploadModel= commonFacadeService.get(ProFileploadModel.class, proFileploadMode.getUuid());
		proFileploadMode.setCreateDate(null);
		CopyUtil.copyToObj(proFileploadModel, proFileploadMode);
		User user = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		proFileploadModel.setUpdateBy(user.getUserPerEname());
		proFileploadModel.setUpdateDate(new Date());
		commonFacadeService.saveOrUpdate(proFileploadModel);
	}

	public CommonFacadeService<ProFileploadModel> getCommonFacadeService() {
		return commonFacadeService;
	}

	public void setCommonFacadeService(
			CommonFacadeService<ProFileploadModel> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	
}
