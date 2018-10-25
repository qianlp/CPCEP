package htos.business.dao.procurement.impl;


import htos.business.dao.procurement.PurchaseCandiateDao;
import htos.business.entity.procurement.PurchaseCandiate;
import htos.coresys.dao.impl.BaseDaoImpl;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

public class PurchaseCandiateDaoImpl extends BaseDaoImpl<PurchaseCandiate> implements PurchaseCandiateDao {

    @Override
    public void delPurchaseCandiate(String purchaseUUID,int type) {
        String hql = "delete  from  PurchaseCandiate where planId =:planId and type =:type";
        this.getCurrentSession().createQuery(hql)
                .setParameter("planId", purchaseUUID)
                .setParameter("type",type)
                .executeUpdate();
    }
}