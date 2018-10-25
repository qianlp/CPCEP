package htos.business.service.bid;

import htos.business.dto.InviteSupplier;
import htos.business.dto.SupplierMaterialParamInfo;
import htos.business.dto.SupplierMaterialPriceInfo;
import htos.business.entity.bid.Invitation;
import htos.business.entity.bid.SupplierBid;
import htos.business.entity.bid.SupplierMaterialParam;
import htos.business.entity.bid.SupplierMaterialPrice;
import htos.business.entity.bid.SupplierScore;
import htos.business.entity.biddingFile.BidFileQuestion;
import htos.business.entity.supplier.SupplierSignup;
import htos.business.entity.supplier.view.ViewUserSupplier;

import java.util.List;

public interface SupplierBidService {
	SupplierBid findBy(String userId, String biddingFileId);

	List<SupplierMaterialPriceInfo> findMaterialInfo(String deviceType, String purchaseId, String supplierBidUuid);

	void saveOrUpdate(List<SupplierMaterialPrice> devices);

	List<SupplierMaterialParamInfo> findSupplierMaterialParam(String purchaseMaterialId,String supplierBidUuid);

	void saveOrUpdateMaterialParam(List<SupplierMaterialParam> supplierMaterialParams);

	List<InviteSupplier> findInviteSupplier(String bidFileId,String type);

	List<ViewUserSupplier> findSuppliers(String bidFileUuid);

	List findSupplierBidResults(String bidFileUuid);

	String findPlanId(String biddingFileId);

	void deleteSupplierBid(String uuid);

	SupplierSignup findSignupInfo(String biddingBulletinUuid, String supplierUuid);

    Invitation getInvitation(String uuid);
    
    SupplierScore getSupplierScore(String uuid,String teachId);
    
    List<BidFileQuestion> findBidQuestionList(String uuid,String supplierUuid);

}
