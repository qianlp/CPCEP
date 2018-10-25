package htos.business.dao.bid;

import htos.business.dto.SupplierScoreInfo;
import htos.business.entity.bid.CompBidEval;
import htos.business.entity.bid.TechBidEval;

import java.util.List;

public interface BidEvaluationDao {
	List<SupplierScoreInfo> findSupplierScoreByBidFile(String bidFileUuid,String isFeasible);

	TechBidEval findByBidFile(String bidFileUuid);

	CompBidEval findCompByBidFile(String bidFileUuid);
}
