package htos.business.dao.procurement.impl;

import htos.business.dao.procurement.PurchasePlanDao;
import htos.business.entity.procurement.PurchasePlan;
import htos.business.utils.CommonDaoUtils;
import htos.common.entity.PageInfo;
import htos.coresys.dao.impl.BaseDaoImpl;
import htos.coresys.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public class PurchasePlanDaoImpl extends BaseDaoImpl<PurchasePlan> implements PurchasePlanDao {
	@Override
	public List<PurchasePlan> loadForPage(String wfStatus, String[] purchaseMethods, List searchList, PageInfo pageInfo) {
		StringBuffer hql = new StringBuffer("from PurchasePlan where wfStatus = ? and delFlag='1' ");
		List<Object> param = new ArrayList<>();
		param.add(wfStatus);
		for (int i = 0 ;i<purchaseMethods.length;i++){
			if (i==0){
				hql.append(" and purchaseMethod in (?,");

			}else if (i==purchaseMethods.length-1){
				hql.append("?)");
			}else {
				hql .append("?,");
			}
			param.add(purchaseMethods[i]);
		}
		hql.append(CommonDaoUtils.createSearch(searchList,param));
		if(CommonUtil.isNullOrEmpty(pageInfo)){
			return super.find(hql.toString(), new String[] {},0, 0);
		}else{
			hql.append(CommonDaoUtils.createSort(pageInfo));
		}
		return super.find(hql.toString(), param, pageInfo.getpageIndex(), pageInfo.getPageSize());
	}

	@Override
	public Integer countBy(String wfStatus, String[] purchaseMethods, List searchList) {
		StringBuffer hql = new StringBuffer(" select count(*) from PurchasePlan where wfStatus = ? and delFlag='1' ");
		List<Object> param = new ArrayList<>();
		param.add(wfStatus);
		for (int i = 0 ;i<purchaseMethods.length;i++){
			if (i==0){
				hql.append(" and purchaseMethod in (?,");

			}else if (i==purchaseMethods.length-1){
				hql.append("?)");
			}else {
				hql .append("?,");
			}
			param.add(purchaseMethods[i]);
		}
		hql.append(CommonDaoUtils.createSearch(searchList,param));
		return Integer.valueOf(super.findUnique(hql.toString(),param).toString());
	}
}
