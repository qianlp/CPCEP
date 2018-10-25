package htos.business.service.procurement;

import htos.business.entity.procurement.PurchaseApply;
import htos.business.entity.procurement.view.PurchaseMaterialParamView;
import htos.coresys.service.CommonFacadeService;

import java.util.List;

public interface PurchaseApplyService extends CommonFacadeService<PurchaseApply>  {

	   void updatePurchaseApply(PurchaseApply purchaseApply);

	    void deletePurchaseApply(PurchaseApply purchaseApply);

	    void addPurchaseApply(PurchaseApply purchaseApply, String rightId);

	   PurchaseApply loadPurchaseApply(String uuid);

	    String getPurchaseApplyNo();

	List<PurchaseApply> findMyApply(String uuid, String wfStatus,List searchList);

	List findMaterialParam(String pmUuid);

	void saveUpdatePurchaseMaterialParam(List<PurchaseMaterialParamView> purchaseMaterialParamViews, String haveEditParam, String pmUuid);

	void deletePurchaseMaterialParam(String pmUuid);
}
