package htos.business.dao.material;

import htos.business.entity.material.MaterialCategory;
import htos.common.entity.PageInfo;

import java.util.List;

public interface MaterialCategoryDao {
	List<MaterialCategory> findAllCategories();

	List<MaterialCategory> loadCategoriesForPage(String pid, List search, PageInfo pageInfo);

	Integer countBy(String pid, List search);

	List<MaterialCategory> findByPid(String pid);

	void updateDelFlagById(String uuid);
}
