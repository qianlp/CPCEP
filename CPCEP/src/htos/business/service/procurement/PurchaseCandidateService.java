package htos.business.service.procurement;

import htos.business.entity.procurement.PurchaseMaterial;
import htos.common.entity.PageInfo;
import htos.coresys.service.CommonFacadeService;

import java.util.List;
import java.util.Map;

public interface PurchaseCandidateService   {


    Map<String, Object> loadMaterialForPage(String planId, int type, PageInfo pageInfo);

    void delPurchaseCandiate(String planUUID,int type);
}
