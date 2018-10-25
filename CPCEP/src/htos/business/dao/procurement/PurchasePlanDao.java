package htos.business.dao.procurement;

import htos.business.entity.procurement.PurchasePlan;
import htos.common.entity.PageInfo;
import htos.coresys.dao.BaseDao;

import java.util.List;

public interface PurchasePlanDao extends BaseDao<PurchasePlan> {
	List<PurchasePlan> loadForPage(String wfStatus, String[] purchaseMethods, List searchList, PageInfo pageInfo);

	Integer countBy(String wfStatus, String[] purchaseMethods, List searchList);
}
