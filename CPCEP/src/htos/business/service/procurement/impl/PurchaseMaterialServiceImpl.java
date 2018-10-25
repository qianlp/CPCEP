package htos.business.service.procurement.impl;

import htos.business.dao.procurement.PurchaseMaterialDao;
import htos.business.entity.procurement.PurchaseMaterial;
import htos.business.service.procurement.PurchaseMaterialService;
import htos.common.entity.PageInfo;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.CommonService;
import htos.coresys.service.impl.CommonFacadeServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseMaterialServiceImpl extends CommonFacadeServiceImpl<PurchaseMaterial> implements PurchaseMaterialService  {

    private CommonService<PurchaseMaterial> commonService;
    private PurchaseMaterialDao dao;
    @Override
    public void updatePurchaseMaterial(PurchaseMaterial purchaseMaterial) {
        // TODO Auto-generated method stub
        super.update(purchaseMaterial);
    }

    @Override
    public void deletePurchaseMaterial(PurchaseMaterial purchaseMaterial) {
        // TODO Auto-generated method stub
        String uuid = purchaseMaterial.getUuid();
        String[] uuidArr;
        if (uuid.indexOf("^") > 0) {
            uuidArr = uuid.split("\\^");
        } else {
            uuidArr = new String[] { uuid };
        }
        for (String str : uuidArr) {
            this.deleteId("CheckTask", "planId", str);
            this.deleteId("CheckReport", "planId", str);
            this.deleteId("CheckPlan", "uuid", str);
            this.deleteRight("CheckPlan", "uuid");
        }
    }

    @Override
    public void addPurchaseMaterial(PurchaseMaterial purchaseMaterial, String rightId) {
        // TODO Auto-generated method stub
        super.save(purchaseMaterial, rightId);
    }

    @Override
    public Map<String, Object> loadMaterialForPage(String purchaseId, String type, PageInfo pageInfo) {

        //[{dataType:"text",operator:"",name:"",value:""}]

        List<Map<String,String>> searchList = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("dataType","text");
        map.put("operator","=");
        map.put("name","purchaseId");
        map.put("value",purchaseId);
        Map<String,String> map2 = new HashMap<>();
        map2.put("dataType","text");
        map2.put("operator","=");
        map2.put("name","type");
        map2.put("value",type);
        searchList.add(map);
        searchList.add(map2);




        List<PurchaseMaterial> objList = commonService.getPageDataMapNotOrgSearch(PurchaseMaterial.class.getSimpleName(),null, pageInfo,searchList);
        int count = commonService.getAllCountNotOrgSearch(PurchaseMaterial.class.getSimpleName(),null,true,searchList);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("total", count);
        data.put("data", objList);
        return data;

    }


    @Override
    public PurchaseMaterial loadPurchaseMaterial(String uuid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPurchaseMaterialNo() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deletePurchaseMaterials(String ids) {
        dao.deleteMaterialByIds(ids);
    }


    @Override
    public CommonService<PurchaseMaterial> getCommonService() {
        return commonService;
    }

    @Override
    public void setCommonService(CommonService<PurchaseMaterial> commonService) {
        this.commonService = commonService;
    }

    public PurchaseMaterialDao getDao() {
        return dao;
    }

    public void setDao(PurchaseMaterialDao dao) {
        this.dao = dao;
    }
}
