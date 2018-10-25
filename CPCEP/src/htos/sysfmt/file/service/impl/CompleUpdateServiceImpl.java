package htos.sysfmt.file.service.impl;

import htos.common.entity.PageInfo;
import htos.common.util.StatusUtil;
import htos.coresys.entity.Menu;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.CommonService;
import htos.coresys.service.MenuService;
import htos.coresys.service.RightService;
import htos.coresys.util.CommonUtil;
import htos.sysfmt.file.dao.CompleUpdateDao;
import htos.sysfmt.file.entity.CompleUpdateModel;
import htos.sysfmt.file.entity.FileDirectory;
import htos.sysfmt.file.service.CompleUpdateService;
import htos.sysfmt.file.service.FileDirectoryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class CompleUpdateServiceImpl implements CompleUpdateService {
	private CompleUpdateDao compleUpdateDao;
	private RightService rightService;
	private CommonService<CompleUpdateModel> commonService;
	private MenuService menuService;
	private CommonFacadeService<CompleUpdateModel> commonFacadeService;
	private FileDirectoryService fileDirectoryService;

	
	public FileDirectoryService getFileDirectoryService() {
		return fileDirectoryService;
	}
	public void setFileDirectoryService(FileDirectoryService fileDirectoryService) {
		this.fileDirectoryService = fileDirectoryService;
	}
	public CommonFacadeService<CompleUpdateModel> getCommonFacadeService() {
		return commonFacadeService;
	}
	public void setCommonFacadeService(
			CommonFacadeService<CompleUpdateModel> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}
	public MenuService getMenuService() {
		return menuService;
	}
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	public CompleUpdateDao getCompleUpdateDao() {
		return compleUpdateDao;
	}
	public void setCompleUpdateDao(CompleUpdateDao compleUpdateDao) {
		this.compleUpdateDao = compleUpdateDao;
	}
	
	public CommonService<CompleUpdateModel> getCommonService() {
		return commonService;
	}
	public void setCommonService(CommonService<CompleUpdateModel> commonService) {
		this.commonService = commonService;
	}
	public RightService getRightService() {
		return rightService;
	}
	public void setRightService(RightService rightService) {
		this.rightService = rightService;
	}
	
	@Override
	public  Map<String, Object>  loadListForPage(String model,PageInfo pageInfo, String orgIds,CompleUpdateModel compleUpdateModel) {
		Set<String> docSet = rightService.getDocIds(orgIds,model);
		String docIds = rightService.convertToStr(docSet);
		List<CompleUpdateModel> objList = compleUpdateDao.getPageDataList(model, pageInfo, docIds,compleUpdateModel);
		int count = compleUpdateDao.getAllDataCount(model, docIds,compleUpdateModel);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("data", objList);
		return map;
	}
	@Override
	public List<CompleUpdateModel> findAllParentTaskUIDJson(String parentTaskUID) {
		return compleUpdateDao.findAllParentTaskUIDJson(parentTaskUID);
	}
	@Override
	public void updateCompleFile(CompleUpdateModel compleUpdateModel) throws Exception {
		if(StatusUtil.STATUS_FASONG.equals(compleUpdateModel.getStatus())){
			//先更新主记录，再更新子记录，再将子数据插入right表中
			Menu menu = menuService.findOneMenuById("entityClsName","CompleUpdateModel");	
			compleUpdateDao.update(compleUpdateModel);
			List<CompleUpdateModel> lists = compleUpdateDao.findAllParentTaskUIDJson(compleUpdateModel.getUuid());
			if(lists.size()>0){
				for (CompleUpdateModel comUpdateModel : lists) {
					comUpdateModel.setPid(compleUpdateModel.getPid());
					comUpdateModel.setFid(compleUpdateModel.getFid());
					comUpdateModel.setDelFlag("0");
					comUpdateModel.setProversion(StatusUtil.STATUS_GUOCHENG_YES);//过程型
					comUpdateModel.setProjectName(compleUpdateModel.getProjectName());
					comUpdateModel.setProjectNo(compleUpdateModel.getProjectName());
					comUpdateModel.setStatus(compleUpdateModel.getStatus());
					//判断有无来源,有来源表示下面有相应节点，无来源表示无相应节点
					List<CompleUpdateModel> list = compleUpdateDao.findAllParentTaskUIDJson(comUpdateModel.getUuid());
					if(list.size()>0){
						comUpdateModel.setComeTo(StatusUtil.STATUS_COMETO_YES);
					}else{
						comUpdateModel.setComeTo(StatusUtil.STATUS_COMETO_NO);
					}
					commonFacadeService.updateOrSave(comUpdateModel, menu);
				}
			}
		}else{
			compleUpdateDao.update(compleUpdateModel);
		}
	}
	
	@Override
	public void saveCompleUploadFile(String parentTaskUID, String filename,
			String path, String filenameNew, String size,String userName) {
		//CompleUpdateModel compleUpdateModel = commonService.getEntityByID("CompleUpdateModel", parentTaskUID);//查询父语句相关信息
		CompleUpdateModel comUpdateModel = new CompleUpdateModel();
		comUpdateModel.setParentTaskUID(parentTaskUID);
		comUpdateModel.setProfilename(filename);
		comUpdateModel.setProfilenameNew(filenameNew);
		comUpdateModel.setProfilepath(path+"/"+filenameNew);
		comUpdateModel.setProfilesize(size+"KB");
		comUpdateModel.setCreateBy(userName);
		commonService.save(comUpdateModel);
	}
	@Override
	public Map<String, Object> loadMapListForPageHead(String model,PageInfo pageInfo, String orgIds) {
		List<CompleUpdateModel> objList = commonService.getPageDataMapHeadOrList(model, pageInfo, orgIds);
		int count = commonService.getAllCount(model, orgIds,true);
		Map<String, Object> map = new HashMap<String, Object>(2);
		if(!CommonUtil.isNullOrEmpty(objList)){
			for (CompleUpdateModel compleUpdateModel : objList) {
				if(!StringUtils.isEmpty(compleUpdateModel.getFid())){
					FileDirectory fileDirectory = fileDirectoryService.fileDirectory(compleUpdateModel.getFid());
					if(!CommonUtil.isNullOrEmpty(fileDirectory)){
						compleUpdateModel.setCatalogName(fileDirectory.getCatalogName());
					}
				}
			}
		}
		map.put("total", count);
		map.put("data", objList);
		return map;
	}
	
	@Override
	public CompleUpdateModel getId(String uuid) {
		return compleUpdateDao.get(CompleUpdateModel.class, uuid);
	}
}
