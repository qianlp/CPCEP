package htos.business.job;

import htos.business.service.bulletin.BiddingBulletinService;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class BidJob extends QuartzJobBean {
	protected final Logger logger = Logger.getLogger(getClass());
	private BiddingBulletinService biddingBulletinService;

	public Logger getLogger() {
		return logger;
	}

	public BiddingBulletinService getBiddingBulletinService() {
		return biddingBulletinService;
	}

	public void setBiddingBulletinService(BiddingBulletinService biddingBulletinService) {
		this.biddingBulletinService = biddingBulletinService;
	}

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		logger.info("BidJob start");
		biddingBulletinService.sendSupplierNotice();
		logger.info("BidJob end");
	}
}
