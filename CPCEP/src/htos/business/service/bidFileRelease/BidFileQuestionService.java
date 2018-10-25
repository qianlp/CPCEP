package htos.business.service.bidFileRelease;

import htos.business.entity.biddingFile.BidFileQuestion;
import htos.business.entity.biddingFile.BiddingFileRelease;
import htos.business.entity.supplier.view.Page;
import htos.common.entity.PageInfo;

import java.util.List;

public interface BidFileQuestionService {
    List<BidFileQuestion> search(String bidFileUUID,String supUUID);

    void findPage(String supUUID, Page<BidFileQuestion> page);
}
