package htos.business.dao.bid.impl;

import htos.business.dao.bid.BidEvaluationDao;
import htos.business.dto.SupplierScoreInfo;
import htos.business.entity.bid.CompBidEval;
import htos.business.entity.bid.TechBidEval;
import htos.coresys.dao.impl.BaseDaoImpl;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import java.util.List;

public class BidEvaluationDaoImpl extends BaseDaoImpl<TechBidEval> implements BidEvaluationDao {
	@Override
	public List<SupplierScoreInfo> findSupplierScoreByBidFile(String bidFileUuid,String isFeasible) {
		String sql = " select ss.uuid,ss.tech_bid_eval_uuid as techBidEvalUuid "
				+ ",se.account as supplierUuid,se.name as  supplierName, sb.uuid as supplierBidUuid ,sb.third_total_price as thirdTotalPrice "
				+ ", ss.is_feasible as isFeasible,ss.score as score ,ss.remark as remark "
				+ " from bs_supplier_bid sb "
				+ " join bs_supplier_ext se on sb.create_uuid = se.account "
				+ " left join bs_supplier_score ss on sb.uuid = ss.supplier_bid_uuid "
				+ " where sb.bidding_file_uuid =:bidFileUuid ";
		if(StringUtils.isNotBlank(isFeasible)){
			sql += " and ss.is_feasible =:isFeasible ";
		}
		sql += " order by sb.third_total_price asc ,sb.create_date asc ";
		Query query = this.getCurrentSession().createSQLQuery(sql)
				.setParameter("bidFileUuid", bidFileUuid);
		if(StringUtils.isNotBlank(isFeasible)){
			query.setParameter("isFeasible", isFeasible);
		}
		return query.setResultTransformer(Transformers.aliasToBean(SupplierScoreInfo.class))
				.list();
	}

	@Override
	public TechBidEval findByBidFile(String bidFileUuid) {
		String hql = " from TechBidEval where biddingFileUuid=? ";
		return (TechBidEval) this.findUnique(hql, new Object[]{bidFileUuid});
	}

	@Override
	public CompBidEval findCompByBidFile(String bidFileUuid) {
		String hql = " from CompBidEval where biddingFileUuid=? ";
		return (CompBidEval) this.findUnique(hql, new Object[]{bidFileUuid});
	}
}
