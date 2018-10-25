package htos.business.dao.material.impl;

import htos.business.dao.material.MaterialDao;
import htos.business.entity.material.Material;
import htos.business.utils.CommonDaoUtils;
import htos.common.entity.PageInfo;
import htos.coresys.dao.impl.BaseDaoImpl;
import htos.coresys.util.CommonUtil;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;

public class MaterialDaoImpl extends BaseDaoImpl<Material> implements MaterialDao {
	@Override
	public List<Material> loadMaterialForPage(String categoryUuid, List searchList, PageInfo pageInfo) {
		StringBuffer hql = new StringBuffer("from Material where categoryUuid = ? and delFlag='1' ");
		List<Object> param = new ArrayList<>();
		param.add(categoryUuid);
		hql.append(CommonDaoUtils.createSearch(searchList, param));
		if (CommonUtil.isNullOrEmpty(pageInfo)) {
			return super.find(hql.toString(), new String[]{}, 0, 0);
		} else {
			hql.append(CommonDaoUtils.createSort(pageInfo));
		}
		return super.find(hql.toString(), param, pageInfo.getpageIndex(), pageInfo.getPageSize());
	}

	@Override
	public Integer countBy(String categoryUuid, List search) {
		StringBuffer hql = new StringBuffer(" select count(*) from Material where categoryUuid = ? and delFlag='1' ");
		List<Object> param = new ArrayList<>();
		param.add(categoryUuid);
		hql.append(CommonDaoUtils.createSearch(search, param));
		return Integer.valueOf(super.findUnique(hql.toString(), param).toString());
	}

	@Override
	public void updateDelFlagByIds(String[] ids) {
		String hql = " update Material set delFlag='2' where uuid in (:ids) ";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameterList("ids",ids);
		query.executeUpdate();
	}
}
