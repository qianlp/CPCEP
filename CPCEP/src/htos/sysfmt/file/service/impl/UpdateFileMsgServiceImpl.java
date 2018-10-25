/**
 * 
 */
package htos.sysfmt.file.service.impl;

import htos.coresys.entity.Menu;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.MenuService;
import htos.sysfmt.file.entity.UpdateFileMsg;
import htos.sysfmt.file.service.UpdateFileMsgService;

/**
 * @author 温勋
 * @ClassName : UpdateFileMsgServiceImpl
 * @Version V1.1
 * @ModifiedBy 温勋
 * @Copyright 华胜龙腾
 * @date 2016年8月29日 下午2:23:27
 */
public class UpdateFileMsgServiceImpl implements UpdateFileMsgService {

	private MenuService menuService;
	private CommonFacadeService<UpdateFileMsg> commonFacadeService;
	/* (non-Javadoc)
	 * @see htos.sysfmt.file.service.UpdateFileMsgService#saveFileMsg(htos.sysfmt.file.entity.UpdateFileMsg)
	 */
	@Override
	public void saveFileMsg(UpdateFileMsg updateFileMsg) {
		System.out.println(updateFileMsg.getMsgTitle());
		Menu menu = menuService.findOneMenuById("entityClsName",updateFileMsg.getClass().getSimpleName());
		commonFacadeService.save(updateFileMsg, menu.getUuid());
	}

	/* (non-Javadoc)
	 * @see htos.sysfmt.file.service.UpdateFileMsgService#updateFileMsg(htos.sysfmt.file.entity.UpdateFileMsg)
	 */
	@Override
	public void updateFileMsg(UpdateFileMsg updateFileMsg) {
		commonFacadeService.saveOrUpdate(updateFileMsg);
	}

	/**
	 * @return the commonFacadeService
	 */
	public CommonFacadeService<UpdateFileMsg> getCommonFacadeService() {
		return commonFacadeService;
	}

	/**
	 * @param commonFacadeService the commonFacadeService to set
	 */
	public void setCommonFacadeService(
			CommonFacadeService<UpdateFileMsg> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}

	/**
	 * @return the menuService
	 */
	public MenuService getMenuService() {
		return menuService;
	}

	/**
	 * @param menuService the menuService to set
	 */
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	
}
