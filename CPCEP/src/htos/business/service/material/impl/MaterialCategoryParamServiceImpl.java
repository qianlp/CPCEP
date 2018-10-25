package htos.business.service.material.impl;

import htos.business.dao.material.MaterialCategoryParamDao;
import htos.business.entity.material.MaterialCategoryParam;
import htos.business.service.material.MaterialCategoryParamService;
import htos.common.entity.PageInfo;
import htos.coresys.entity.User;
import htos.coresys.service.CommonFacadeService;
import org.apache.struts2.ServletActionContext;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaterialCategoryParamServiceImpl implements MaterialCategoryParamService {
	private MaterialCategoryParamDao materialCategoryParamDao;
	private CommonFacadeService<MaterialCategoryParam> commonFacadeService;

	@Override
	public Map<String, Object> loadCategoryParamForPage(String categoryId, PageInfo pageInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<MaterialCategoryParam> data = materialCategoryParamDao.loadCategoryParamForPage(categoryId, pageInfo);
		Integer total = materialCategoryParamDao.countBy(categoryId);
		map.put("total", total);
		map.put("data", data);
		return map;
	}

	@Override
	public List loadCategoryParam(String categoryId) {
		return materialCategoryParamDao.loadCategoryParam(categoryId);
	}

	@Override
	public void addOrModified(List rows, String categoryId) {
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if (rows==null) return ;
		for (int i = 0;i<rows.size();i++){
			Map map = (Map) rows.get(i);
			String state = map.get("_state").toString();
			if (state.equals("added")){
				MaterialCategoryParam materialCategoryParam = new MaterialCategoryParam();
				materialCategoryParam.setCreateBy(user.getUuid());
				materialCategoryParam.setCreateDate(new Date());
				materialCategoryParam.setCategoryUuid(categoryId);
				materialCategoryParam.setParamName(map.get("paramName").toString());
				if (map.get("paramUnit")!=null){
					materialCategoryParam.setParamUnit(map.get("paramUnit").toString());
				}
				if (map.get("requiredValue")!=null){
					materialCategoryParam.setRequiredValue(map.get("requiredValue").toString());
				}
				if (map.get("isKeyword")!=null){
					materialCategoryParam.setIsKeyword(map.get("isKeyword").toString());
				}
				if (map.get("remark")!=null){
					materialCategoryParam.setRemark(map.get("remark").toString());
				}
				materialCategoryParam.setDelFlag("1");
				commonFacadeService.save(materialCategoryParam);
			}else if (state.equals("modified")){
				String uuid = map.get("uuid").toString();
				MaterialCategoryParam materialCategoryParam = commonFacadeService.getEntityByID(MaterialCategoryParam.class.getSimpleName(), uuid);
				materialCategoryParam.setParamName(map.get("paramName").toString());
				materialCategoryParam.setUpdateBy(user.getUuid());
				materialCategoryParam.setUpdateDate(new Date());
				if (map.get("paramUnit")!=null){
					materialCategoryParam.setParamUnit(map.get("paramUnit").toString());
				}
				if (map.get("requiredValue")!=null){
					materialCategoryParam.setRequiredValue(map.get("requiredValue").toString());
				}
				if (map.get("isKeyword")!=null){
					materialCategoryParam.setIsKeyword(map.get("isKeyword").toString());
				}
				if (map.get("remark")!=null){
					materialCategoryParam.setRemark(map.get("remark").toString());
				}
				commonFacadeService.update(materialCategoryParam);
			}
		}
	}

	@Override
	public void updateDelFlagByIds(String[] ids) {
		materialCategoryParamDao.updateDelFlagByIds(ids);
	}

	public MaterialCategoryParamDao getMaterialCategoryParamDao() {
		return materialCategoryParamDao;
	}

	public void setMaterialCategoryParamDao(MaterialCategoryParamDao materialCategoryParamDao) {
		this.materialCategoryParamDao = materialCategoryParamDao;
	}

	public CommonFacadeService<MaterialCategoryParam> getCommonFacadeService() {
		return commonFacadeService;
	}

	public void setCommonFacadeService(CommonFacadeService<MaterialCategoryParam> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}
}
