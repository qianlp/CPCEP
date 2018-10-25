package htos.sysfmt.file.dao;

import htos.coresys.dao.BaseDao;
import htos.sysfmt.file.entity.FileDirectory;

import java.util.List;

public interface FileDirectoryDao extends BaseDao<FileDirectory> {
	
	//添加文件目录
	public void addFileDirectory(FileDirectory fileDirectory);
	
	//通过code查询文件目录详细
	public FileDirectory fileDirectory(String fileDirectoryCode);
	
	//查询所有一级目录
	public List<FileDirectory> fileDirectoryList();
	
	//查询所有一级目录
	public List<FileDirectory> fileDirectoryList(String orgIds);
	
	//条件查询目录
	public List<FileDirectory> fileDirectoryList(FileDirectory fileDirectory);
	
	
	//更新文件目录信息
	public void updateFileDirectory(FileDirectory fileDirectory);
	
	//删除部门
	public void deleteFileDirectory(String attr,String value);
	
}