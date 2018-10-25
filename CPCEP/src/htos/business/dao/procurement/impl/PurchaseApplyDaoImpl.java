package htos.business.dao.procurement.impl;

import java.util.List;

import htos.business.dao.procurement.PurchaseApplyDao;
import htos.business.entity.procurement.PurchaseApply;
import org.hibernate.Query;

import htos.common.entity.PageInfo;
import htos.coresys.dao.impl.BaseDaoImpl;


public class PurchaseApplyDaoImpl extends BaseDaoImpl<PurchaseApply> implements PurchaseApplyDao {

	@Override
	public PurchaseApply findPurchaseApplyById(String uuid) {
		 return this.get(PurchaseApply.class, uuid);
	}

	@Override
	public int getAllCountForPage(PurchaseApply purchaseApply) {
		String hql = "select count(*) from PurchaseApply";
	    Query query = getCurrentSession().createQuery(hql);
	    return ((Number) query.uniqueResult()).intValue();
	}

	@Override
	public List<PurchaseApply> findPurchaseApplyForPage(PurchaseApply purchaseApply,
			PageInfo pageInfo) {
		// TODO Auto-generated method stub
		String hql = "select c from PurchaseApply c where c.projectCode=:projectCode";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("projectCode", purchaseApply.getProjectCode());
        if (pageInfo != null) {
            query.setMaxResults(pageInfo.getPageSize())
                    .setFirstResult(pageInfo.getpageIndex() * pageInfo.getPageSize());
        }
        return query.list();
	}

}
