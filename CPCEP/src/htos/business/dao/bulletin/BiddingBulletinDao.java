package htos.business.dao.bulletin;

import htos.business.entity.bulletin.BiddingBulletin;
import htos.coresys.dao.BaseDao;

import java.util.Date;
import java.util.List;

public interface BiddingBulletinDao extends BaseDao<BiddingBulletin> {
	List findBidStartInDate(Date date);
}
