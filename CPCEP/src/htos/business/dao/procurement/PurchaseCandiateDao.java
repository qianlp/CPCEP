package htos.business.dao.procurement;

import htos.business.entity.procurement.PurchaseCandiate;
import htos.coresys.dao.BaseDao;


public interface PurchaseCandiateDao extends BaseDao<PurchaseCandiate> {

    void delPurchaseCandiate (String purchaseUUID,int type);
}
