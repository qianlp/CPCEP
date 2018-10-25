package htos.business.dao.material.impl;

import htos.business.dao.material.MaterialCategoryParamDao;
import htos.business.entity.material.MaterialCategoryParam;
import htos.common.entity.PageInfo;
import htos.coresys.dao.impl.BaseDaoImpl;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;

public class MaterialCategoryParamDaoImpl extends BaseDaoImpl<MaterialCategoryParam> implements MaterialCategoryParamDao {
	@Override
	public List<MaterialCategoryParam> loadCategoryParamForPage(String categoryId, PageInfo pageInfo) {
		StringBuffer hql = new StringBuffer("from MaterialCategoryParam where categoryUuid = ? and delFlag='1' order by createDate asc ");
		List<Object> param = new ArrayList<>();
		param.add(categoryId);
		return super.find(hql.toString(), param, pageInfo.getpageIndex(), pageInfo.getPageSize());
	}

	@Override
	public List<MaterialCategoryParam> loadCategoryParam(String categoryId) {
		StringBuffer hql = new StringBuffer("from MaterialCategoryParam where categoryUuid = ? and delFlag='1' order by createDate desc ");
		List<Object> param = new ArrayList<>();
		param.add(categoryId);
		return super.find(hql.toString(), param);
	}

	@Override
	public Integer countBy(String categoryId) {
		StringBuffer hql = new StringBuffer(" select count(*) from MaterialCategoryParam where categoryUuid = ? and delFlag='1' ");
		List<Object> param = new ArrayList<>();
		param.add(categoryId);
		return Integer.valueOf(super.findUnique(hql.toString(), param).toString());
	}

	@Override
	public void updateDelFlagByIds(String[] ids) {
		String hql = " update MaterialCategoryParam set delFlag='2' where uuid in (:ids) ";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameterList("ids",ids);
		query.executeUpdate();
	}
}
