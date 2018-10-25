package htos.coresys.dao;

import htos.coresys.entity.DocNumber;


public interface DocNumberDao extends BaseDao<DocNumber> {	
	
	public DocNumber findNumberByMode(String modeName,String year);
	public DocNumber findNumberByMode(String modeName);
}
