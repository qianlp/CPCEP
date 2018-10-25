package htos.business.dao.supplier.impl;

import htos.business.dao.supplier.SupplierExtDao;
import htos.business.dao.supplier.SupplierSignupDao;
import htos.business.entity.supplier.SupplierExt;
import htos.business.entity.supplier.SupplierSignup;
import htos.coresys.dao.BaseDao;
import htos.coresys.dao.impl.BaseDaoImpl;

import java.util.List;

/**
 * @author qinj
 * @date 2018-03-07 22:10
 **/
public class SupplierSignupDaoImpl extends BaseDaoImpl<SupplierSignup> implements SupplierSignupDao {


	@Override
	public List<SupplierSignup> findByBulletin(String bulletinUuid) {
		String hql = " from SupplierSignup where bidding.uuid=? and wfStatus=2 ";
		return find(hql,new Object[]{bulletinUuid});
	}

	@Override
	public SupplierSignup findSignupInfo(String biddingBulletinUuid, String account) {
		String hql = " from SupplierSignup where bidding.uuid=? and supplier.account=? ";
		return (SupplierSignup) findUnique(hql,new Object[]{biddingBulletinUuid,account});
	}
}
