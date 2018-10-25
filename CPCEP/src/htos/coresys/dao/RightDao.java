package htos.coresys.dao;

import htos.coresys.entity.Right;

import java.util.List;


public interface RightDao extends BaseDao<Right> {

	public List<Right> getRightByOrgIds(String orgIds,String modoName);
	

	public List<Right> getRightsByDocId(Right right);
	
	public List<Right> getRightsByProprty(String modoName,String orgIds,String docId);


	public List<Right> getRightsByDocId(String docId, String modoName);

	//2016-06-11
	public List<Right> getRightByOrgIdAndDocId(String orgIds,String docIds);


	public List<Right> getRightsList(Right right);

}