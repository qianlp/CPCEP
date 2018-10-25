package htos.business.service.procurement;

import htos.common.entity.PageInfo;

import java.util.List;
import java.util.Map;

public interface PurchasePlanService {
	Map<String,Object> loadForPage(String wfStatus, String[] purchaseMethods, List searchList, PageInfo pageInfo);

	String createCode();

}
