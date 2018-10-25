package htos.business.service.agreement;

import htos.business.dto.SupplierBidInfo;
import htos.business.dto.SupplierScoreInfo;
import htos.common.entity.PageInfo;

import java.util.Map;

public interface AgreementService {
	Map queryPurchaseWithProject(PageInfo pageInfo);

	SupplierBidInfo loadSupplierBidInfo(String confirmUuid);

	SupplierBidInfo loadSupplierInfo(String supplierId);

	String loadPurchaseDeviceInfo(String purchasePlanUuid);
}
