package htos.sysfmt.file.service;

import htos.sysfmt.file.entity.FileParamModel;

import java.util.List;

public interface FileParamService {
	
	List<FileParamModel> findAllFileParamJson();

	void saveOrupdate(FileParamModel fileParamModel);

	void save(FileParamModel fileParamModel);
}
