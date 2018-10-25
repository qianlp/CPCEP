package htos.business.dao.agreement;

import htos.business.dto.SupplierBidInfo;
import htos.business.entity.procurement.PurchaseMaterial;
import htos.common.entity.PageInfo;

import java.util.List;

public interface AgreementDao {
	List queryPurchaseWithProject(PageInfo pageInfo);

	Integer queryPurchaseWithProjectCount();

	SupplierBidInfo loadSupplierBidInfo(String confirmUuid);

	SupplierBidInfo loadSupplierInfo(String supplierId);

	List<PurchaseMaterial> findPurchaseMaterial(String purchasePlanUuid);
}
