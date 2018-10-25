package htos.business.dao.procurement.impl;

import htos.business.dao.procurement.PurchaseMaterialDao;
import htos.business.dao.procurement.PurchasePlanDao;
import htos.business.entity.procurement.PurchaseMaterial;
import htos.business.entity.procurement.PurchasePlan;
import htos.coresys.dao.impl.BaseDaoImpl;
import org.hibernate.Query;


public class PurchaseMaterialDaoImpl extends BaseDaoImpl<PurchaseMaterial> implements PurchaseMaterialDao {

    @Override
    public void deleteMaterialByIds(String ids) {
        String hql = " delete  from  PurchaseMaterial where uuid in ('"+ids.replace(",","','")+"') ";
        Query query = this.getCurrentSession().createQuery(hql);
        query.executeUpdate();
    }


}