package htos.business.dao.material;

import htos.business.entity.material.MaterialCategoryParam;
import htos.common.entity.PageInfo;

import java.util.List;

public interface MaterialCategoryParamDao {
	List<MaterialCategoryParam> loadCategoryParamForPage(String categoryId, PageInfo pageInfo);
	List<MaterialCategoryParam> loadCategoryParam(String categoryId);
	Integer countBy(String categoryId);

	void updateDelFlagByIds(String[] ids);
}
