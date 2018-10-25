package htos.business.service.material.impl;

import htos.business.dao.material.MaterialCategoryDao;
import htos.business.entity.material.MaterialCategory;
import htos.business.service.material.MaterialCategoryService;
import htos.common.entity.PageInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaterialCategoryServiceImpl implements MaterialCategoryService{
	private MaterialCategoryDao materialCategoryDao;
	@Override
	public List<Map<String, String>> findCategoryTreeJson() {
		return getList(materialCategoryDao.findAllCategories());
	}

	@Override
	public Map<String, Object> loadCategoriesForPage(String pid, List search, PageInfo pageInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<MaterialCategory> data = materialCategoryDao.loadCategoriesForPage(pid, search, pageInfo);
		Integer total = materialCategoryDao.countBy(pid, search);
		map.put("total", total);
		map.put("data", data);
		return map;
	}

	@Override
	public void updateDelFlagByIds(String[] ids) {
		for (String uuid:ids) {
			materialCategoryDao.updateDelFlagById(uuid);
			//updateChildDelFlagByIds(uuid);
			//materialCategoryDao.updateDelFlagById(uuid);
		}
	}

	private void updateChildDelFlagByIds(String pid) {
		List<MaterialCategory> lists = materialCategoryDao.findByPid(pid);
		for (MaterialCategory materialCategory : lists) {
			updateChildDelFlagByIds(materialCategory.getUuid());
			materialCategoryDao.updateDelFlagById(materialCategory.getUuid());
		}
	}

	private List<Map<String,String>> getList(List<MaterialCategory> materialCategoryList){
		List<Map<String,String>> list = new ArrayList<>(materialCategoryList.size());
		for(MaterialCategory materialCategory : materialCategoryList){
			Map<String,String> map = new HashMap<>(4);
			map.put("id", materialCategory.getUuid());
			map.put("text", materialCategory.getCategoryName());
			map.put("pid", materialCategory.getPid());
			map.put("type", "materialCategory");
			list.add(map);
		}
		return list;
	}

	public MaterialCategoryDao getMaterialCategoryDao() {
		return materialCategoryDao;
	}

	public void setMaterialCategoryDao(MaterialCategoryDao materialCategoryDao) {
		this.materialCategoryDao = materialCategoryDao;
	}

	@Override
	public List<MaterialCategory> getHqlList(String pid) {
		// TODO Auto-generated method stub
		return materialCategoryDao.findByPid(pid);
	}
}
