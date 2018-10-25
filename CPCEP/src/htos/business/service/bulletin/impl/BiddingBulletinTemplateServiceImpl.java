package htos.business.service.bulletin.impl;

import htos.business.dao.bulletin.BiddingBulletinTemplateDao;
import htos.business.service.bulletin.BiddingBulletinTemplateService;

public class BiddingBulletinTemplateServiceImpl implements BiddingBulletinTemplateService {
	private BiddingBulletinTemplateDao biddingBulletinTemplateDao;

	public BiddingBulletinTemplateDao getBiddingBulletinTemplateDao() {
		return biddingBulletinTemplateDao;
	}

	public void setBiddingBulletinTemplateDao(BiddingBulletinTemplateDao biddingBulletinTemplateDao) {
		this.biddingBulletinTemplateDao = biddingBulletinTemplateDao;
	}
}
