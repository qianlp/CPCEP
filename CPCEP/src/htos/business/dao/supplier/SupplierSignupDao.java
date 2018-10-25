package htos.business.dao.supplier;

import htos.business.entity.supplier.SupplierSignup;
import htos.coresys.dao.BaseDao;

import java.util.List;

public interface SupplierSignupDao extends BaseDao<SupplierSignup> {
	List<SupplierSignup> findByBulletin(String bulletinUuid);

	SupplierSignup findSignupInfo(String biddingBulletinUuid, String supplierUuid);
}
