package htos.business.dao.procurement;

import htos.business.entity.procurement.PurchaseMaterial;
import htos.business.entity.procurement.PurchasePlan;
import htos.common.entity.PageInfo;
import htos.coresys.dao.BaseDao;

import java.util.List;



public interface PurchaseMaterialDao extends BaseDao<PurchaseMaterial> {
    void deleteMaterialByIds(String ids);
}
