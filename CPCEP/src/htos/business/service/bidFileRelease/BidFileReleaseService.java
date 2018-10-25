package htos.business.service.bidFileRelease;

import htos.business.dto.BidFileWithProject;
import htos.business.entity.biddingFile.BiddingFileRelease;
import htos.common.entity.PageInfo;

import java.util.List;
import java.util.Map;

public interface BidFileReleaseService {
    List<Map<String,String>> findTreeJson();

    Map<String,Object> loadDataForPage(String pid, List search, PageInfo pageInfo);

    void updateDelFlagByIds(String[] ids);

	BiddingFileRelease findByBid(String uuid);

	BiddingFileRelease findById(String uuid);

	void open(String uuid);

	List findTechBidEvalMaterialParam(String bidFileUuid);

	BidFileWithProject findBidFileWithProject(String bidFileUuid);

	String findPurchaseDeviceStr(String bidFileUuid);

}
