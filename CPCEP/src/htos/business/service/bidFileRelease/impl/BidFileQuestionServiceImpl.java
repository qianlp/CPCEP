package htos.business.service.bidFileRelease.impl;

import htos.business.dao.bidFileRelease.BidFileQuestionDao;
import htos.business.entity.biddingFile.BidFileQuestion;
import htos.business.entity.biddingFile.BiddingFileRelease;
import htos.business.entity.procurement.PurchaseMaterial;
import htos.business.entity.supplier.view.Page;
import htos.business.service.bidFileRelease.BidFileQuestionService;
import htos.coresys.service.CommonService;
import htos.coresys.util.CommonUtil;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BidFileQuestionServiceImpl implements BidFileQuestionService {

    private CommonService<BidFileQuestion> commonService;
    private BidFileQuestionDao bidfileQuestionDao;

    @Override
    public List<BidFileQuestion> search(String bidFileUUID,String supUUID) {

        String hql = "from BidFileQuestion bq where bq.bidFileUUID ="+bidFileUUID+" and bq.supUUID="+supUUID+" order by bq.createDate asc";
        return   commonService.getHqlList(hql);

    }

    @Override
    public void findPage(String supUUID, Page<BidFileQuestion> page) {

//        String sql = "  from BidFileQuestion  bq where bq.isPublic='1' or  bq.supUUID='"+supUUID+"'";
//        int from = (page.getpageIndex() ) * page.getPageSize();
//        Session session = bidfileQuestionDao.getCurrentSession();
//        Query query = session.createQuery(sql);
//        query.setMaxResults(page.getPageSize());
//        query .setFirstResult(from);
//        List<BidFileQuestion> list =  query .list();


//          SELECT * from bs_bid_file_question bq
//         JOIN bs_bidding_file bf ON bf.uuid = bq.bid_file_uuid
//         JOIN bs_purchase_plan bp ON bp.uuid=bf.uuid
//         JOIN bs_purchase_plan_candiate  bc.plan_id=bp.uuid
//         WHERE bp.purchase_method ='邀请招标' AND bc.supplier_id=:supUUID AND bq.is_public = '1' or bq.supUUID =:supUUID
        String countSql = "SELECT count(*) " +
                "from bs_bid_file_question bq " +
                "JOIN bs_bidding_file bf ON bf.uuid = bq.bid_file_uuid " +
                "JOIN bs_bidding_bulletin bb ON bb.uuid = bf.bulletin_uuid " +
                "JOIN bs_purchase_plan bp ON bp.uuid=bb.purchase_plan_uuid " +
                "JOIN bs_supplier_ext be ON be.account =:supUUID " +
                "left JOIN bs_purchase_plan_candiate  bc ON bc.plan_id=bp.uuid  and bc.supplier_id = be.uuid " +
                "JOIN bs_supplier_signup bs ON bs.bidding=bb.uuid AND bs.supplier = be.UUID  AND bs.wfStatus >= 2 " +
                "WHERE (bp.purchase_method ='邀请招标'  AND bq.is_public = '1' AND   bs.supplier = be.uuid) or ((bq.is_public = '0' or bq.is_public is NULL)  and bq.sup_uuid =:supUUID ) or ( bp.purchase_method ='公开招标' AND  bq.is_public = '1' AND bs.supplier = be.uuid )";;
        Number count = (Number) bidfileQuestionDao.getCurrentSession().createSQLQuery(countSql).setParameter("supUUID",supUUID).uniqueResult();
        if(count == null) {
            page.setTotalCount(0);
            return ;
        }
        page.setTotalCount(count.intValue());
        if(count.intValue() == 0)
            return ;

        int from = (page.getpageIndex() ) * page.getPageSize();
       String  sql = "SELECT bq.uuid as uuid , bq.bid_code as bidCode , bq.bid_name as bidName , bq.file_version as version , bq.issue_type as issueType ," +
               " bq.sup_name as supName , bq.sup_uuid as supUUID , bq.contact as contact , bq.remark as remark , bq.feed_back as feedBack " +
               "from bs_bid_file_question bq " +
               "JOIN bs_bidding_file bf ON bf.uuid = bq.bid_file_uuid " +
               "JOIN bs_bidding_bulletin bb ON bb.uuid = bf.bulletin_uuid " +
               "JOIN bs_purchase_plan bp ON bp.uuid=bb.purchase_plan_uuid " +
               "JOIN bs_supplier_ext be ON be.account =:supUUID " +
               "left JOIN bs_purchase_plan_candiate  bc ON bc.plan_id=bp.uuid  and bc.supplier_id = be.uuid " +
               "JOIN bs_supplier_signup bs ON bs.bidding=bb.uuid AND bs.supplier = be.UUID  AND bs.wfStatus >= 2 " +
               "WHERE (bp.purchase_method ='邀请招标'  AND bq.is_public = '1' AND   bs.supplier = be.uuid) or ((bq.is_public = '0' or bq.is_public is NULL)  and bq.sup_uuid =:supUUID ) or ( bp.purchase_method ='公开招标' AND  bq.is_public = '1' AND bs.supplier = be.uuid )";

        Session session = bidfileQuestionDao.getCurrentSession();
        Query query = session.createSQLQuery(sql);
        query.setMaxResults(page.getPageSize());
        query.setParameter("supUUID",supUUID);
        query.setResultTransformer(Transformers.aliasToBean(BidFileQuestion.class));
        query .setFirstResult(from);
        List<BidFileQuestion> list =  query .list();
        page.setResult(list);
//        page.setTotalCount(list.size());
    }

    public CommonService<BidFileQuestion> getCommonService() {
        return commonService;
    }

    public void setCommonService(CommonService<BidFileQuestion> commonService) {
        this.commonService = commonService;
    }

    public BidFileQuestionDao getBidfileQuestionDao() {
        return bidfileQuestionDao;
    }

    public void setBidfileQuestionDao(BidFileQuestionDao bidfileQuestionDao) {
        this.bidfileQuestionDao = bidfileQuestionDao;
    }
}
