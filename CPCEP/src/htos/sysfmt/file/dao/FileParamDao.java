package htos.sysfmt.file.dao;

import htos.coresys.dao.BaseDao;
import htos.sysfmt.file.entity.FileParamModel;

import java.util.List;

public interface FileParamDao extends BaseDao<FileParamModel>{
	List<FileParamModel> findAllFileParamJson();
}
