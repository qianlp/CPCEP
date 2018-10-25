package htos.sysfmt.file.service.impl;

import htos.common.entity.PageInfo;
import htos.coresys.service.CommonService;
import htos.coresys.service.RightService;
import htos.sysfmt.file.dao.AdenexaDao;
import htos.sysfmt.file.entity.AdenexaModel;
import htos.sysfmt.file.service.AdenexaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdenexaServiceImpl implements AdenexaService {
	private AdenexaDao adenexaDao;
	private CommonService<AdenexaModel> commonService;
	private RightService rightService;
	
	public AdenexaDao getAdenexaDao() {
		return adenexaDao;
	}
	public void setAdenexaDao(AdenexaDao adenexaDao) {
		this.adenexaDao = adenexaDao;
	}
	@Override
	public List<AdenexaModel> findAllAdenexaListJson(String pid) {
		return adenexaDao.findAllAdenexaListJson(pid);
	}

	@Override
	public AdenexaModel findViewAdenexaById(String uuid) {
		return adenexaDao.findViewAdenexaById(uuid);
	}
	public CommonService<AdenexaModel> getCommonService() {
		return commonService;
	}
	public void setCommonService(CommonService<AdenexaModel> commonService) {
		this.commonService = commonService;
	}
	
	/**
	 * @return the rightService
	 */
	public RightService getRightService() {
		return rightService;
	}
	/**
	 * @param rightService the rightService to set
	 */
	public void setRightService(RightService rightService) {
		this.rightService = rightService;
	}
	@Override
	public String saveAdenexUploadFile(String pid, String filename, String path,String filenameNew, String size, String userName) {
		AdenexaModel adenexaModel = new AdenexaModel();
		adenexaModel.setPid(pid);
		adenexaModel.setFilename(filename);
		adenexaModel.setFilenameNew(filenameNew);
		adenexaModel.setFilepath(path+"/"+filenameNew);
		adenexaModel.setFilesize(size+"KB");
		adenexaModel.setCreateBy(userName);
		commonService.save(adenexaModel);
		return adenexaModel.getUuid();
	}
	
	//审核通过后修改文件审核状态
	
	@Override
	public List<AdenexaModel> findAllAdenexaListJson(AdenexaModel adenexaModel) {
		
		return adenexaDao.findAllAdenexaListJson(adenexaModel);
	}
	@Override
	public void saveAdenexUploadFile(AdenexaModel adenexaModel) {
		adenexaDao.save(adenexaModel);
	}
	@Override
	public Map<String, Object> loadListForPage(String model, PageInfo pageInfo,
			String orgIds, AdenexaModel adenexaModel) {
//		Set<String> docSet = rightService.getDocIds(orgIds,model);
//		String docIds = rightService.convertToStr(docSet);
		List<AdenexaModel> objList = adenexaDao.getPageDataList(model, pageInfo, orgIds,adenexaModel);
		int count = adenexaDao.getAllDataCount(model, orgIds,adenexaModel);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("data", objList);
		return map;
	}
	
	@Override
	public void updateAdenexaWfStatus(String curDocId){
		List<AdenexaModel> list = commonService.getListProperty("AdenexaModel", "curDocId", curDocId);
		for (AdenexaModel adenexaModel : list) {
			adenexaModel.setWfStatus("2");
			commonService.update(adenexaModel);
		}
	}
	
	public void updateAdenexaVersion(String uuid, String version) {
		adenexaDao.updateAdenexaVersion(uuid,version);
	}
	@Override
	public void updateAdenexaWfStatus(String curDocId, String uuid) {
		List<AdenexaModel> list = commonService.getListProperty("AdenexaModel", "curDocId", curDocId);
		for (AdenexaModel adenexaModel : list) {
			adenexaModel.setWfStatus("2");
			adenexaModel.setPrjID(uuid);
			commonService.update(adenexaModel);
		}
		//修改文件目录中的PrjID
		String sql = "update ht_newfile_directory set prjID='"+uuid+"' where curDocId='"+curDocId+"' ";
		commonService.saveOrUpdate(sql);
	}
	/* (non-Javadoc)
	 * @see htos.sysfmt.file.service.AdenexaService#getDeptFiles(java.lang.String)
	 */
	@Override
	public List<AdenexaModel> getDeptFiles(String deptId) {
		
		return adenexaDao.find("from AdenexaModel where createDeptId!='' and createDeptId!=null and createDeptId like '%"+deptId+"%'");
	}
}
