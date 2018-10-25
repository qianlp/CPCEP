package htos.business.service.bulletin;

import htos.business.entity.bid.Invitation;
import htos.business.entity.bulletin.BiddingBulletin;
import htos.business.entity.bulletin.view.ViewBiddingBulletin;
import htos.business.entity.supplier.view.Page;
import htos.business.entity.supplier.view.ViewUserSupplier;
import htos.coresys.entity.User;

public interface BiddingBulletinService {

	BiddingBulletin findById(String id);

	void findPage(Page<ViewBiddingBulletin> page, String bidName, User user);

	void findSupplier(Page<ViewBiddingBulletin> page, String bidId, String supplierName,String hasBid);

	void invite(String[] supplierIds, BiddingBulletin model);

	void findMyInvite(Page<Invitation> page, String supplierId);

	void findInvitationSupplier(Page<ViewUserSupplier> page, String bidId);

	void saveQuotePrice(String invitationId, float price);

    void sendBid(BiddingBulletin biddingBulletin);

	void sendSupplierNotice();
	
	Invitation findInvitation(String invitationId);
}
