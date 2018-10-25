package htos.sysfmt.file.dao.impl;

import htos.coresys.dao.impl.BaseDaoImpl;
import htos.sysfmt.file.dao.FileParamDao;
import htos.sysfmt.file.entity.FileParamModel;

import java.util.List;

public class FileParamDaoImpl  extends BaseDaoImpl<FileParamModel> implements FileParamDao {
	@Override
	public List<FileParamModel> findAllFileParamJson() {
		String hql = "from FileParamModel ";
		return super.find(hql);
	}
}
