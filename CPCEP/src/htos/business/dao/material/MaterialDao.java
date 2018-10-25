package htos.business.dao.material;

import htos.business.entity.material.Material;
import htos.common.entity.PageInfo;

import java.util.List;

public interface MaterialDao {
	List<Material> loadMaterialForPage(String categoryUuid, List searchList, PageInfo pageInfo);

	Integer countBy(String categoryUuid, List searchList);

	void updateDelFlagByIds(String[] ids);
}
