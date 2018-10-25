package htos.business.service.material.impl;

import htos.business.dao.material.MaterialDao;
import htos.business.entity.material.Material;
import htos.business.service.material.MaterialService;
import htos.common.entity.PageInfo;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaterialServiceImpl implements MaterialService {
	private MaterialDao materialDao;

	@Override
	public Map<String, Object> loadMaterialForPage(String categoryUuid, List searchList, PageInfo pageInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Material> data = materialDao.loadMaterialForPage(categoryUuid, searchList, pageInfo);
		Integer total = materialDao.countBy(categoryUuid, searchList);
		map.put("total", total);
		map.put("data", data);
		return map;
	}

	@Override
	public void updateDelFlagByIds(String[] ids) {
		materialDao.updateDelFlagByIds(ids);
	}

	public MaterialDao getMaterialDao() {
		return materialDao;
	}

	public void setMaterialDao(MaterialDao materialDao) {
		this.materialDao = materialDao;
	}
}
