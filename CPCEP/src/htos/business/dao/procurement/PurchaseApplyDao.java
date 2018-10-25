package htos.business.dao.procurement;

import java.util.List;

import htos.business.entity.procurement.PurchaseApply;
import htos.common.entity.PageInfo;
import htos.coresys.dao.BaseDao;

public  interface PurchaseApplyDao extends BaseDao<PurchaseApply>  {

	
	public PurchaseApply findPurchaseApplyById(String uuid);

    int getAllCountForPage(PurchaseApply purchaseApply);

    List<PurchaseApply> findPurchaseApplyForPage(PurchaseApply purchaseApply, PageInfo pageInfo);
}
