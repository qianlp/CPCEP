package htos.sysfmt.file.service;

import htos.sysfmt.file.entity.FileDirectory;
import htos.sysfmt.file.entity.FileDirectorynewModel;
import htos.sysfmt.file.entity.PrjFileDirectoryModel;

import java.beans.IntrospectionException;
import java.util.List;
import java.util.Map;

public interface FileDirectoryService {
	
	//添加文件目录
	public void addFileDirectory(FileDirectory fileDirectory);
	
	//通过code查询文件目录详细
	public FileDirectory fileDirectory(String fileDirectoryCode);
	
	//查询所有一级目录
	public List<FileDirectory> fileDirectoryList();
	public List<FileDirectory> fileDirectoryList(String orgIds);
	//条件查询目录
	public List<FileDirectory> fileDirectoryList(FileDirectory fileDirectory);
	
	//更新文件目录信息
	public void updateFileDirectory(FileDirectory fileDirectory);
	
	//删除部门
	public void deleteFileDirectory(String attr,String value);

	//文档目录树
	public List<Map<String, String>> findFileDirectoryTree();

	public List<Map<String, String>> findFileDirectoryTree(String orgIds,String prjID);

	public List<FileDirectorynewModel> fileDirectoryProList(String prjID) throws IntrospectionException;
	
	//查询自己新建的可维护文档目录
	List<FileDirectorynewModel> findFileDirectoryMenu(String orgIds,String prjID, String menuId);

	String findFileDirectoryNo();

	String findFileDirectoryUID();

	//查找项目目录文件
	List<PrjFileDirectoryModel> prjFileDirectoryProList(String prjID)throws IntrospectionException;

	//项目文件分配权限批量更新新目录和文件权限
	void updatePrjDirFileUpdate(String data) throws IntrospectionException;
	
	//根据权限项目获取自己和按人员角色分配的新目录文件
	public List<FileDirectorynewModel> fileNewDirOrgListByPower();

	
}