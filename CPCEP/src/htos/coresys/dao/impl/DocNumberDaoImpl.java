package htos.coresys.dao.impl;

import java.io.Serializable;
import java.util.List;

import htos.coresys.dao.DocNumberDao;
import htos.coresys.entity.DocNumber;

public class DocNumberDaoImpl extends BaseDaoImpl<DocNumber>  implements DocNumberDao {

	
	@Override
	public DocNumber findNumberByMode(String modeName, String year) {
		
		List<DocNumber> list=super.find(" from DocNumber where modeName='"+modeName+"' and year='"+year+"'  order by createDate desc");
		
		if(list==null || list.size()==0){
			return null;
		}
		return list.get(0);
	}

	@Override
	public DocNumber findNumberByMode(String modeName) {
		List<DocNumber> list=super.find(" from DocNumber where modeName='"+modeName+"'  order by createDate desc");
		
		if(list==null || list.size()==0){
			return null;
		}
		return list.get(0);
	}

}
