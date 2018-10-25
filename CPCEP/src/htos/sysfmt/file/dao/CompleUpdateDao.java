package htos.sysfmt.file.dao;

import htos.common.entity.PageInfo;
import htos.coresys.dao.BaseDao;
import htos.sysfmt.file.entity.CompleUpdateModel;

import java.util.List;

public interface CompleUpdateDao extends BaseDao<CompleUpdateModel>{

	List<CompleUpdateModel> getPageDataList(String model, PageInfo pageInfo,String docIds,CompleUpdateModel compleUpdateModel);

	int getAllDataCount(String model, String docIds,CompleUpdateModel compleUpdateModel);

	List<CompleUpdateModel> findAllParentTaskUIDJson(String parentTaskUID);

}
