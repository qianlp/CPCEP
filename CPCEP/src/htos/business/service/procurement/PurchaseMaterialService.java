package htos.business.service.procurement;

import htos.business.entity.procurement.PurchaseMaterial;
import htos.common.entity.PageInfo;
import htos.coresys.service.CommonFacadeService;

import java.util.Map;

public interface PurchaseMaterialService   {

    void updatePurchaseMaterial(PurchaseMaterial purchaseMaterial);

    void deletePurchaseMaterial(PurchaseMaterial purchaseMaterial);

    void addPurchaseMaterial(PurchaseMaterial purchaseMaterial, String rightId);

    Map<String, Object> loadMaterialForPage(String purchaseId, String type, PageInfo pageInfo);

    PurchaseMaterial loadPurchaseMaterial(String uuid);

    String getPurchaseMaterialNo();

    void deletePurchaseMaterials(String  ids);


}
