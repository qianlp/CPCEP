package htos.coresys.service.impl;

import java.beans.IntrospectionException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Element;

import htos.common.entity.PageInfo;
import htos.common.util.CopyUtil;
import htos.common.util.StringUtil;
import htos.coresys.entity.ExhibitParamModel;
import htos.coresys.entity.Menu;
import htos.coresys.entity.User;
import htos.coresys.service.CommonService;
import htos.coresys.service.ExhibitParamService;
import htos.coresys.service.MenuService;
import htos.coresys.util.CommonUtil;


public class ExhibitParamServiceImpl<T> implements ExhibitParamService<T> {
	private CommonService<ExhibitParamModel> commonService;
	private CommonService<T> commonServiceParam;
	private MenuService menuService;

	@Override
	public void saveExhibitParamJson(ExhibitParamModel exhibitParamModel) {
		User user =	(User)ServletActionContext.getRequest().getSession().getAttribute("user");
		String exhibitPid ="";
		//判断父节点是否存在，如果存在则将子菜单更新到原来目录下，否则新建父节点
		ExhibitParamModel exhibitParModel = commonService.getEntityByProperty(ExhibitParamModel.class.getSimpleName(), "modularName", exhibitParamModel.getModularName());
		if(exhibitParModel!=null){
			exhibitPid = exhibitParModel.getUuid();
		}else{
			exhibitParModel = new ExhibitParamModel();
			exhibitParModel.setModularName(exhibitParamModel.getModularName());
			exhibitParModel.setExhibitPid("-1");
			exhibitParModel.setCreateBy(user.getUserName());
			exhibitParModel.setCreateDate(new Date());
			commonService.save(exhibitParModel);
			exhibitPid = exhibitParModel.getUuid();
		}
		exhibitParamModel.setModularName(null);
		exhibitParamModel.setCreateBy(user.getUserName());
		exhibitParamModel.setCreateDate(new Date());
		exhibitParamModel.setExhibitPid(exhibitPid);
		commonService.save(exhibitParamModel);
	}

	@Override
	public void updateExhibitParamJson(ExhibitParamModel exhibitParamModel) throws IntrospectionException {
		User user =	(User)ServletActionContext.getRequest().getSession().getAttribute("user");
		String exhibitPid ="";
		//判断父节点是否存在，如果存在则将子菜单更新到原来目录下，否则新建父节点
		ExhibitParamModel exhibitParModel = commonService.getEntityByProperty(ExhibitParamModel.class.getSimpleName(), "modularName", exhibitParamModel.getModularName());
		if(exhibitParModel!=null){
			exhibitPid = exhibitParModel.getUuid();
		}else{
			exhibitParModel = new ExhibitParamModel();
			exhibitParModel.setModularName(exhibitParamModel.getModularName());
			exhibitParModel.setExhibitPid("-1");
			exhibitParModel.setCreateBy(user.getUserName());
			exhibitParModel.setCreateDate(new Date());
			commonService.save(exhibitParModel);
			exhibitPid = exhibitParModel.getUuid();
		}
		
		//ExhibitParamModel exhibitParam = commonService.get(ExhibitParamModel.class, exhibitParamModel.getUuid());
		//CopyUtil.copyToObj(exhibitParam, exhibitParamModel);
		exhibitParamModel.setModularName(null);
		exhibitParamModel.setUpdateBy(user.getUserName());
		exhibitParamModel.setUpdateDate(new Date());
		exhibitParamModel.setExhibitPid(exhibitPid);
		commonService.update(exhibitParamModel);//更新子节点
	}

	public CommonService<ExhibitParamModel> getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService<ExhibitParamModel> commonService) {
		this.commonService = commonService;
	}

	@Override
	public Map<String, Object> loadMapListForPageHeadOrListOrViewSearch(String menuId, String uuid, PageInfo pageInfo, String orgIds,List list) {
		if(StringUtil.isEmpty(uuid) || StringUtil.isEmpty(menuId)){
			return null;
		}
		Menu menu = menuService.findMenuById(menuId);
		if (CommonUtil.isNullOrEmpty(menu)) {
			return null;
		}
		ExhibitParamModel exhibitParamModel =commonService.get(ExhibitParamModel.class, uuid); 
		
		if(exhibitParamModel.getIsPower()!=null && exhibitParamModel.getIsPower().equals("0")){
			orgIds="-";
		}
		if (CommonUtil.isNullOrEmpty(exhibitParamModel)) {
			return null;
		}
		
		List<T> objList = commonServiceParam.getPageDataMapHeadOrListSearch(menu.getEntityClsName(),menu.getQueryEntityClsname(), pageInfo, orgIds,list);
		int count = commonServiceParam.getAllCountSearch(menu.getEntityClsName(),menu.getQueryEntityClsname(), orgIds,true,list);
		String bodyHtml = "<root>" + exhibitParamModel.getExhibitHtmlBody() + "</root>";
		List<Element> eleList = CommonUtil.getXmlChildElements(bodyHtml);
		Set<String> fieldSet = new HashSet<String>();
		for (Element e : eleList) {
			if (!CommonUtil.isNullOrEmpty(e.attributeValue("field"))) {
				fieldSet.add(e.attributeValue("field"));
			} 
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("data", commonServiceParam.dataList(objList, fieldSet));
		return map;
	}

	@Override
	public Map<String, Object> loadMapListForPageHeadOrListOrview(String menuId, String uuid, PageInfo pageInfo, String orgIds) {
		if(StringUtil.isEmpty(uuid) || StringUtil.isEmpty(menuId)){
			return null;
		}
		Menu menu = menuService.findMenuById(menuId);
		if (CommonUtil.isNullOrEmpty(menu)) {
			return null;
		}
		ExhibitParamModel exhibitParamModel =commonService.get(ExhibitParamModel.class, uuid); 
		if (CommonUtil.isNullOrEmpty(exhibitParamModel)) {
			return null;
		}
		
		if(exhibitParamModel.getIsPower()!=null && exhibitParamModel.getIsPower().equals("0")){
			orgIds="-";
		}
		List<T> objList = commonServiceParam.getPageDataMapHeadOrListOrview(menu.getEntityClsName(),menu.getQueryEntityClsname(), pageInfo, orgIds);
		int count = commonServiceParam.getAllCount(menu.getEntityClsName(),menu.getQueryEntityClsname(), orgIds,true);
		String bodyHtml = "<root>" +  exhibitParamModel.getExhibitHtmlBody() + "</root>";
		List<Element> eleList = CommonUtil.getXmlChildElements(bodyHtml);
		Set<String> fieldSet = new HashSet<String>();
		for (Element e : eleList) {
			if (!CommonUtil.isNullOrEmpty(e.attributeValue("field"))) {
				fieldSet.add(e.attributeValue("field"));
			} 
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("data", commonServiceParam.dataList(objList, fieldSet));
		return map;
	}

	public CommonService<T> getCommonServiceParam() {
		return commonServiceParam;
	}

	public void setCommonServiceParam(CommonService<T> commonServiceParam) {
		this.commonServiceParam = commonServiceParam;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

}
