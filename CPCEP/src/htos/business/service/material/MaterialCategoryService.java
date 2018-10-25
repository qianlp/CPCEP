package htos.business.service.material;

import htos.business.entity.material.MaterialCategory;
import htos.common.entity.PageInfo;

import java.util.List;
import java.util.Map;

public interface MaterialCategoryService {
	List<Map<String,String>> findCategoryTreeJson();

	Map<String,Object> loadCategoriesForPage(String pid, List search, PageInfo pageInfo);

	void updateDelFlagByIds(String[] ids);

	List<MaterialCategory> getHqlList(String pid);
}
