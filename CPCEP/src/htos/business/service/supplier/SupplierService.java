package htos.business.service.supplier;

import htos.business.entity.supplier.*;
import htos.business.entity.supplier.view.Page;
import htos.business.entity.supplier.view.ViewAttachment;
import htos.business.entity.supplier.view.ViewSupplierTrader;
import htos.business.entity.supplier.view.ViewUserSupplier;
import htos.coresys.entity.User;

import java.util.List;
import java.util.Map;

public interface SupplierService {

	boolean register(ViewUserSupplier model);

	void check(String id, String checkStatus, User checkUser,String content);

	ViewUserSupplier findById(String uuid);

	List<ViewAttachment> findAttachments(String supplierId);

	void findTender(Page<ViewSupplierTrader> page, User user);
	void findInvite(Page<ViewSupplierTrader> page, User user);

	void signup(ViewUserSupplier model, User user,String tabType);

	SupplierExt findByUser(String userId);

	SupplierSignup findMySignupByTrader(String traderId, String supplier);

	void findNeedCheckSignup(Page<ViewSupplierTrader> page, User user, String supplierName);

	void signCheck(String id, String checkStatus, User checkUser);

	void enable(String id);

	SupplierBilling findBillig(String supplierId);

	void update(ViewUserSupplier model);

	List<GoodsScope> findGoodsScope();

	List<SupplierGoodsScope> findMyScope(String supplierId);

	GoodsScope getGoodsScope(String uuid);

	void deleteScope(String uuid);

	List<Map<String, Object>> findScope();

	void findPage(Page<ViewUserSupplier> page, String name, String scopeId,String pass,String wfStatus);

	List<SupplierSignup> findBulletinSignUp(String uuid);
}
