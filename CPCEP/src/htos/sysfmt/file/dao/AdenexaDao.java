package htos.sysfmt.file.dao;

import htos.common.entity.PageInfo;
import htos.coresys.dao.BaseDao;
import htos.sysfmt.file.entity.AdenexaModel;
import htos.sysfmt.file.entity.CompleUpdateModel;

import java.util.List;

public interface AdenexaDao extends BaseDao<AdenexaModel> {
	List<AdenexaModel> getPageDataList(String model, PageInfo pageInfo,String docIds,AdenexaModel adenexaModel);
	
	int getAllDataCount(String model, String docIds,AdenexaModel adenexaModel);

	List<AdenexaModel> findAllAdenexaListJson(String pid);
	
	List<AdenexaModel> findAllAdenexaListJson(AdenexaModel adenexaModel);

	AdenexaModel findViewAdenexaById(String uuid);

	void updateAdenexaVersion(String uuid, String version);
}
