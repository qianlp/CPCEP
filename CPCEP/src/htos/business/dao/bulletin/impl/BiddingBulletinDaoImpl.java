package htos.business.dao.bulletin.impl;

import htos.business.dao.bulletin.BiddingBulletinDao;
import htos.business.entity.bulletin.BiddingBulletin;
import htos.business.entity.bulletin.view.ViewBiddingBulletin;
import htos.coresys.dao.impl.BaseDaoImpl;
import org.hibernate.transform.Transformers;

import java.util.Date;
import java.util.List;

public class BiddingBulletinDaoImpl extends BaseDaoImpl<BiddingBulletin> implements BiddingBulletinDao{
	@Override
	public List findBidStartInDate(Date date) {
		String sql = " select DISTINCT " +
				"  bb.uuid as uuid, " +
				" bb.bid_start_time as bidStartTime, " +
				"  pb.project_name as projectName " +
				"from bs_bidding_bulletin bb " +
				"join bs_purchase_plan pp on  bb.purchase_plan_uuid = pp.uuid " +
				"join bs_purchase_apply pa on pp.purchase_apply_uuid = pa.uuid " +
				"join bs_project_base pb on pa.project_uuid = pb.uuid " +
				"where bb.bid_start_time = :date ";
		return getCurrentSession().createSQLQuery(sql)
				.setParameter("date",date)
				.setResultTransformer(Transformers.aliasToBean(ViewBiddingBulletin.class))
				.list();
	}
}
