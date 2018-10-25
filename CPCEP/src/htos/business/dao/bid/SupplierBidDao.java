package htos.business.dao.bid;

import htos.business.dto.InviteSupplier;
import htos.business.entity.bid.SupplierBid;
import htos.business.entity.biddingFile.BidFileQuestion;
import htos.business.entity.supplier.view.ViewUserSupplier;
import htos.coresys.dao.BaseDao;

import java.util.List;

public interface SupplierBidDao extends BaseDao<SupplierBid> {
	SupplierBid findBy(String userId, String biddingFileId);

	List<InviteSupplier> findInviteSupplier(String bidFileId,String type);

	List<ViewUserSupplier> findSuppliers(String bidFileUuid);

	List findSupplierMaterialParams(String bidFileUuid);

	List findByBidFile(String bidFileUuid);
	
	List<BidFileQuestion> findBidQuestionList(String uuid,String suppId);
}
