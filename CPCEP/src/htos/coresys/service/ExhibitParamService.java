package htos.coresys.service;

import htos.common.entity.PageInfo;
import htos.coresys.entity.ExhibitParamModel;

import java.beans.IntrospectionException;
import java.util.List;
import java.util.Map;



public interface ExhibitParamService<T> {
	void saveExhibitParamJson(ExhibitParamModel exhibitParamModel);
	void updateExhibitParamJson(ExhibitParamModel exhibitParamModel) throws IntrospectionException;
	Map<String, Object>  loadMapListForPageHeadOrListOrViewSearch(String menuId, String uuid,PageInfo getpageInfo, String orgIds, List searchList);
	Map<String, Object>  loadMapListForPageHeadOrListOrview(String menuId, String uuid,PageInfo getpageInfo, String orgIds);
	
}