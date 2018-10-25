package htos.business.dao.material.impl;

import htos.business.dao.material.MaterialCategoryDao;
import htos.business.entity.material.MaterialCategory;
import htos.business.utils.CommonDaoUtils;
import htos.common.entity.PageInfo;
import htos.coresys.dao.impl.BaseDaoImpl;
import htos.coresys.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public class MaterialCategoryDaoImpl extends BaseDaoImpl<MaterialCategory> implements MaterialCategoryDao {
	@Override
	public List<MaterialCategory> findAllCategories() {
		String hql = "from MaterialCategory mc where mc.delFlag = '1' order by mc.createDate asc";
		return super.find(hql);
	}

	@Override
	public List<MaterialCategory> loadCategoriesForPage(String pid, List search, PageInfo pageInfo) {
		StringBuffer hql = new StringBuffer("from MaterialCategory where pid = ? and delFlag='1' ");
		List<Object> param = new ArrayList<>();
		param.add(pid);
		hql.append(CommonDaoUtils.createSearch(search,param));
		if(CommonUtil.isNullOrEmpty(pageInfo)){
			return super.find(hql.toString(), new String[] {},0, 0);
		}else{
			hql.append(CommonDaoUtils.createSort(pageInfo));
		}
		return super.find(hql.toString(), param, pageInfo.getpageIndex(), pageInfo.getPageSize());
	}

	@Override
	public Integer countBy(String pid, List search) {
		StringBuffer hql = new StringBuffer(" select count(*) from MaterialCategory where pid = ? and delFlag='1' ");
		List<Object> param = new ArrayList<>();
		param.add(pid);
		hql.append(CommonDaoUtils.createSearch(search,param));
		return Integer.valueOf(super.findUnique(hql.toString(),param).toString());
	}

	@Override
	public List<MaterialCategory> findByPid(String pid) {
		String hql = "from MaterialCategory where pid = ? and delFlag='1' ";
		return super.find(hql,new Object[]{pid});
	}

	@Override
	public void updateDelFlagById(String uuid) {
		String hql = " update MaterialCategory set delFlag='2' where uuid = ? ";
		super.executeHql(hql,new Object[]{uuid});
	}

}
