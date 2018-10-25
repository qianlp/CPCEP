package htos.sysfmt.file.service;

import htos.common.entity.PageInfo;
import htos.sysfmt.file.entity.AdenexaModel;

import java.util.List;
import java.util.Map;

public interface AdenexaService {
	Map<String, Object>  loadListForPage(String model, PageInfo pageInfo, String orgIds,AdenexaModel adenexaModel);

	List<AdenexaModel> findAllAdenexaListJson(String pid);
	
	List<AdenexaModel> findAllAdenexaListJson(AdenexaModel adenexaModel);

	AdenexaModel findViewAdenexaById(String uuid);

	String saveAdenexUploadFile(String pid, String filename, String path,String filenameNew, String size, String userName);
	
	void saveAdenexUploadFile(AdenexaModel adenexaModel);
	
	void updateAdenexaVersion(String uuid, String version);
	
	//非项目立项处理审核状态
	void updateAdenexaWfStatus(String curDocId);
	
	//项目立项处理审核状态
	void updateAdenexaWfStatus(String string, String uuid);

	//获取文件
	List<AdenexaModel> getDeptFiles(String deptId);
}
