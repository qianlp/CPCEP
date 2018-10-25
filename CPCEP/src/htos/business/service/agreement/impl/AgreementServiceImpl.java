package htos.business.service.agreement.impl;

import htos.business.dao.agreement.AgreementDao;
import htos.business.dto.SupplierBidInfo;
import htos.business.entity.procurement.PurchaseMaterial;
import htos.business.service.agreement.AgreementService;
import htos.common.entity.PageInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgreementServiceImpl implements AgreementService {

	private AgreementDao agreementDao;

	public AgreementDao getAgreementDao() {
		return agreementDao;
	}

	public void setAgreementDao(AgreementDao agreementDao) {
		this.agreementDao = agreementDao;
	}

	@Override
	public Map queryPurchaseWithProject(PageInfo pageInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		List list = agreementDao.queryPurchaseWithProject(pageInfo);
		Integer total =  agreementDao.queryPurchaseWithProjectCount();
		map.put("total", total);
		map.put("data", list);
		return map;
	}

	@Override
	public SupplierBidInfo loadSupplierBidInfo(String confirmUuid) {
		return agreementDao.loadSupplierBidInfo(confirmUuid);
	}

	@Override
	public SupplierBidInfo loadSupplierInfo(String supplierId) {
		return agreementDao.loadSupplierInfo(supplierId);
	}

	@Override
	public String loadPurchaseDeviceInfo(String purchasePlanUuid) {
		List<PurchaseMaterial> pms = agreementDao.findPurchaseMaterial(purchasePlanUuid);
		int sum = 0;
		StringBuilder result = new StringBuilder();
		StringBuilder devicesStr = new StringBuilder("其中");
		for (PurchaseMaterial pm : pms) {
			sum += pm.getNum();
			devicesStr.append(pm.getNum()).append("台").append(pm.getMaterialName()).append(",");
		}
		result.append("共").append(sum).append("台，").append(devicesStr);
		return result.toString();
	}
}
