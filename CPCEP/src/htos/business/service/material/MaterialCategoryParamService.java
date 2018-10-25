package htos.business.service.material;

import htos.common.entity.PageInfo;

import java.util.List;
import java.util.Map;

public interface MaterialCategoryParamService {
	Map<String, Object> loadCategoryParamForPage(String categoryId, PageInfo pageInfo);
	List loadCategoryParam(String categoryId);
	void addOrModified(List rows, String categoryId);

	void updateDelFlagByIds(String[] ids);
}
