package htos.sysfmt.file.service;

import htos.common.entity.PageInfo;
import htos.sysfmt.file.entity.CompleUpdateModel;

import java.util.List;
import java.util.Map;

public interface CompleUpdateService {

	 Map<String, Object>  loadListForPage(String model, PageInfo pageInfo, String orgIds,CompleUpdateModel compleUpdateModel);

	List<CompleUpdateModel> findAllParentTaskUIDJson(String parentTaskUID);

	void updateCompleFile(CompleUpdateModel compleUpdateModel) throws Exception;
	
	//保存上传的文件
	void saveCompleUploadFile(String parentTaskUID, String filename,String path, String filenameNew, String size,String userName);

	Map<String, Object>  loadMapListForPageHead(String model, PageInfo pageInfo, String orgIds);

	CompleUpdateModel getId(String uuid);

}
