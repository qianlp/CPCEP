package htos.business.service.procurement.impl;

import htos.business.dao.procurement.PurchasePlanDao;
import htos.business.entity.procurement.PurchasePlan;
import htos.business.service.procurement.PurchasePlanService;
import htos.common.entity.PageInfo;

import java.util.*;

public class PurchasePlanServiceImpl implements PurchasePlanService{
	private PurchasePlanDao purchasePlanDao;
	@Override
	public Map<String, Object> loadForPage(String wfStatus, String[] purchaseMethods, List searchList, PageInfo pageInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PurchasePlan> data = purchasePlanDao.loadForPage(wfStatus,purchaseMethods, searchList, pageInfo);
		Integer total = purchasePlanDao.countBy(wfStatus,purchaseMethods, searchList);
		map.put("total", total);
		map.put("data", data);
		return map;
	}

	@Override
	public String createCode() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		String hql = "select count(*) from PurchasePlan ";
//		Date start = calendar.getTime();
//		calendar.add(Calendar.DAY_OF_MONTH, 1);
//		Date end = calendar.getTime();
		Number count = (Number) purchasePlanDao.getCurrentSession().createQuery(hql)
				.uniqueResult();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		StringBuilder code = new StringBuilder();
		code.append("CGJH");
		code.append("-");
		code.append(calendar.get(Calendar.YEAR));
		code.append("-");
		code.append(number2String(calendar.get(Calendar.MONTH) + 1, 2));
		code.append("-");
		code.append(number2String(calendar.get(Calendar.DAY_OF_MONTH), 2));
		code.append("-");
		code.append(number2String(count.intValue() + 1, 3));
		return code.toString();
	}

	private String number2String(int number, int length) {
		String str = number + "";
		if(str.length() == length)
			return str;
		for (int index = str.length(); index < length; index++) {
			str = "0" + str;
		}
		return str;
	}

	public PurchasePlanDao getPurchasePlanDao() {
		return purchasePlanDao;
	}

	public void setPurchasePlanDao(PurchasePlanDao purchasePlanDao) {
		this.purchasePlanDao = purchasePlanDao;
	}
}

