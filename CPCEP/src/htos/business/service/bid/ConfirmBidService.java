package htos.business.service.bid;

import htos.business.entity.bid.view.ViewConfirmBid;
import htos.business.entity.bid.view.ViewMaterialDetail;
import htos.business.entity.supplier.view.Page;

import java.util.List;

public interface ConfirmBidService {

	void findConfirmList(Page<ViewConfirmBid> page, String name);

	ViewConfirmBid findBidInfo(String bidFileId);

	void findBidSupplier(Page<ViewConfirmBid> page, String bidFileId);

	List<ViewConfirmBid> findBidSupplier(String bidFileId);

	List<ViewMaterialDetail> findMaterialDetail(String supplierBidUuid,String userId);

	void updateMaterialAttrFinalPrice(List<ViewMaterialDetail> details);
}
