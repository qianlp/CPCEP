package htos.business.service.bid.impl;

import htos.business.dao.bid.BidEvaluationDao;
import htos.business.dto.SupplierScoreInfo;
import htos.business.entity.bid.CompBidEval;
import htos.business.entity.bid.SupplierScore;
import htos.business.entity.bid.TechBidEval;
import htos.business.service.bid.BidEvaluationService;
import htos.coresys.dao.CommonDao;

import java.util.List;

public class BidEvaluationServiceImpl implements BidEvaluationService {
	private BidEvaluationDao bidEvaluationDao;
	private CommonDao<SupplierScore> supplierScoreCommonDao;

	public CommonDao<SupplierScore> getSupplierScoreCommonDao() {
		return supplierScoreCommonDao;
	}

	public void setSupplierScoreCommonDao(CommonDao<SupplierScore> supplierScoreCommonDao) {
		this.supplierScoreCommonDao = supplierScoreCommonDao;
	}

	public BidEvaluationDao getBidEvaluationDao() {
		return bidEvaluationDao;
	}

	public void setBidEvaluationDao(BidEvaluationDao bidEvaluationDao) {
		this.bidEvaluationDao = bidEvaluationDao;
	}

	@Override
	public List<SupplierScoreInfo> findSupplierScoreByBidFile(String bidFileUuid,String isFeasible) {
		return bidEvaluationDao.findSupplierScoreByBidFile(bidFileUuid,isFeasible);
	}

	@Override
	public void saveOrUpdateSupllierScore(String techBidEvalUuid, List<SupplierScoreInfo> bidResultGrid) {
		for (SupplierScoreInfo info : bidResultGrid) {
			supplierScoreCommonDao.saveOrUpdate(info.intoEntityWith(techBidEvalUuid));
		}
	}

	@Override
	public TechBidEval findByBidFile(String bidFileUuid) {
		return bidEvaluationDao.findByBidFile(bidFileUuid);
	}

	@Override
	public CompBidEval findCompByBidFile(String bidFileUuid) {
		return bidEvaluationDao.findCompByBidFile(bidFileUuid);
	}
}
