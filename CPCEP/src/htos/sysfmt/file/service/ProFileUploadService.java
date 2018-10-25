package htos.sysfmt.file.service;

import java.beans.IntrospectionException;

import htos.sysfmt.file.entity.ProFileploadModel;


public interface ProFileUploadService {

	void saveProFileJson(ProFileploadModel proFileploadModel);

	void updateProFileJson(ProFileploadModel proFileploadModel) throws IntrospectionException;
	
}
