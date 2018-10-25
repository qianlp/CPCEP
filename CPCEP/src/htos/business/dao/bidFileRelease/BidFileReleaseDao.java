package htos.business.dao.bidFileRelease;

import htos.business.dto.BidFileWithProject;
import htos.business.entity.biddingFile.BiddingFileRelease;
import htos.business.entity.material.MaterialCategory;
import htos.business.entity.procurement.PurchaseMaterial;
import htos.common.entity.PageInfo;
import htos.coresys.dao.BaseDao;

import java.util.List;

public interface BidFileReleaseDao extends BaseDao<BiddingFileRelease> {
    List<BiddingFileRelease> findAll();

    List<BiddingFileRelease> loadDataForPage(String pid, List search, PageInfo pageInfo);

    Integer countBy(String pid, List search);

    List<BiddingFileRelease> findByPid(String pid);

    void updateDelFlagById(String uuid);

	List findPurchaseMaterialParam(String bidFileUuid);

	BidFileWithProject findBidFileWithProject(String bidFileUuid);

	List<PurchaseMaterial> findPurchaseMaterial(String bidFileUuid);
}
