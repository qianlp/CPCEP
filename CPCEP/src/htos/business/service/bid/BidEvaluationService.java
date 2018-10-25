package htos.business.service.bid;

import htos.business.dto.SupplierScoreInfo;
import htos.business.entity.bid.CompBidEval;
import htos.business.entity.bid.TechBidEval;

import java.util.List;

public interface BidEvaluationService {
	List<SupplierScoreInfo> findSupplierScoreByBidFile(String bidFileUuid,String isFeasible);

	void saveOrUpdateSupllierScore(String techBidEvalUuid,List<SupplierScoreInfo> bidResultGrid);

	TechBidEval findByBidFile(String bidFileUuid);

	CompBidEval findCompByBidFile(String bidFileUuid);
}
