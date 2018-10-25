package htos.business.service.material;

import htos.common.entity.PageInfo;

import java.util.List;
import java.util.Map;

public interface MaterialService {
	Map<String, Object> loadMaterialForPage(String categoryUuid, List searchList, PageInfo pageInfo);

	void updateDelFlagByIds(String[] ids);
}
