package htos.business.dao.bidFileRelease.impl;

import htos.business.dao.bidFileRelease.BidFileReleaseDao;
import htos.business.dto.BidFileWithProject;
import htos.business.dto.PurchaseMaterialParamInfo;
import htos.business.entity.biddingFile.BiddingFileRelease;
import htos.business.entity.procurement.PurchaseMaterial;
import htos.business.utils.CommonDaoUtils;
import htos.common.entity.PageInfo;
import htos.coresys.dao.impl.BaseDaoImpl;
import htos.coresys.util.CommonUtil;
import org.hibernate.transform.Transformers;

import java.util.ArrayList;
import java.util.List;

public class BidFileReleaseDaoImpl extends BaseDaoImpl<BiddingFileRelease> implements BidFileReleaseDao {


    @Override
    public List<BiddingFileRelease> findAll() {
        String hql = "from BiddingFileRelease mc where mc.delFlag = '1' order by mc.createDate asc";
        return super.find(hql);
    }


    @Override
    public List<BiddingFileRelease> loadDataForPage(String pid, List search, PageInfo pageInfo) {
        StringBuffer hql = new StringBuffer("from BiddingFileRelease where pid = ? and delFlag='1' ");
        List<Object> param = new ArrayList<>();
        param.add(pid);
        hql.append(CommonDaoUtils.createSearch(search,param));
        if(CommonUtil.isNullOrEmpty(pageInfo)){
            return super.find(hql.toString(), new String[] {},0, 0);
        }else{
            hql.append(CommonDaoUtils.createSort(pageInfo));
        }
        return super.find(hql.toString(), param, pageInfo.getpageIndex(), pageInfo.getPageSize());
    }

    @Override
    public Integer countBy(String pid, List search) {
        StringBuffer hql = new StringBuffer(" select count(*) from MaterialCategory where pid = ? and delFlag='1' ");
        List<Object> param = new ArrayList<>();
        param.add(pid);
        hql.append(CommonDaoUtils.createSearch(search,param));
        return Integer.valueOf(super.findUnique(hql.toString(),param).toString());
    }

    @Override
    public List<BiddingFileRelease> findByPid(String pid) {
        String hql = "from BiddingFileRelease where pid = ? and delFlag='1' ";
        return super.find(hql,new Object[]{pid});
    }

    @Override
    public void updateDelFlagById(String uuid) {
        String hql = " update BiddingFileRelease set delFlag='2' where uuid = ? ";
        super.executeHql(hql,new Object[]{uuid});
    }

    @Override
    public List findPurchaseMaterialParam(String bidFileUuid) {
        String sql = " SELECT" +
                "  pm.uuid as purchaseMaterialUuid, " +
                "  m.uuid as materialUuid, " +
                "  m.material_name as materialName, " +
				" pmp.uuid as paramUuid,"+
                "  pmp.param_name as paramName, " +
                "  pmp.required_value  as requiredValue " +
                " FROM bs_bidding_file bf " +
                "  JOIN bs_bidding_bulletin bb ON bf.bulletin_uuid = bb.uuid " +
                "    JOIN bs_purchase_plan pp ON pp.uuid = bb.purchase_plan_uuid " +
                "  JOIN bs_purchase_material pm ON pm.purchase_id = pp.purchase_apply_uuid " +
                "  JOIN bs_material m ON pm.material_uuid = m.uuid " +
                "  JOIN bs_purchase_material_param pmp ON pmp.pm_uuid = pm.uuid "+
                " where bf.uuid = :bidFileUuid " +
				" order by pm.uuid asc ";
		return this.getCurrentSession().createSQLQuery(sql)
				.setParameter("bidFileUuid",bidFileUuid)
				.setResultTransformer(Transformers.aliasToBean(PurchaseMaterialParamInfo.class)).list();
    }

    @Override
    public BidFileWithProject findBidFileWithProject(String bidFileUuid) {
        String sql = " SELECT pb.project_code as projectName,pb.project_name as projectCode,pa.purchase_name as purchaseName,pp.purchase_method as purchaseMethod FROM bs_bidding_file bf " +
                " join bs_bidding_bulletin bb on bf.bulletin_uuid = bb.uuid " +
                " join bs_purchase_plan pp on bb.purchase_plan_uuid = pp.uuid " +
                " join bs_purchase_apply pa on pp.purchase_apply_uuid = pa.uuid " +
                " join bs_project_base pb on pa.project_uuid = pb.uuid"+
                " where bf.uuid = :bidFileUuid ";
        return (BidFileWithProject) this.getCurrentSession().createSQLQuery(sql)
                .setParameter("bidFileUuid",bidFileUuid)
                .setResultTransformer(Transformers.aliasToBean(BidFileWithProject.class))
                .uniqueResult();
    }

    @Override
    public List<PurchaseMaterial> findPurchaseMaterial(String bidFileUuid){
        String sql = " SELECT pm.num as num,m.material_name as materialName " +
                " FROM bs_bidding_file bf " +
                " join bs_bidding_bulletin bb on bf.bulletin_uuid = bb.uuid " +
                " join bs_purchase_plan pp on bb.purchase_plan_uuid = pp.uuid " +
                " join bs_purchase_material pm on pp.purchase_apply_uuid = pm.purchase_id " +
                " join bs_material m on pm.material_uuid = m.uuid " +
                " where bf.uuid = :bidFileUuid ";
        return  this.getCurrentSession().createSQLQuery(sql)
                .setParameter("bidFileUuid",bidFileUuid)
                .setResultTransformer(Transformers.aliasToBean(PurchaseMaterial.class))
                .list();
    }
}
