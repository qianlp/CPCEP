package htos.sysfmt.file.service.impl;

import htos.sysfmt.file.dao.FileParamDao;
import htos.sysfmt.file.entity.FileParamModel;
import htos.sysfmt.file.service.FileParamService;

import java.util.List;

public class FileParamServiceImpl implements FileParamService {
	private FileParamDao fileParamDao;
	
	public FileParamDao getFileParamDao() {
		return fileParamDao;
	}

	public void setFileParamDao(FileParamDao fileParamDao) {
		this.fileParamDao = fileParamDao;
	}

	@Override
	public List<FileParamModel> findAllFileParamJson() {
		return fileParamDao.findAllFileParamJson();
	}

	@Override
	public void saveOrupdate(FileParamModel fileParamModel) {
		fileParamDao.saveOrUpdate(fileParamModel);
	}

	@Override
	public void save(FileParamModel fileParamModel) {
		fileParamDao.save(fileParamModel);
	}
}
