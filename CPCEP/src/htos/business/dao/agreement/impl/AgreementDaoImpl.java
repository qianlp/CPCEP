package htos.business.dao.agreement.impl;

import htos.business.dao.agreement.AgreementDao;
import htos.business.dto.PurchaseWithProject;
import htos.business.dto.SupplierBidInfo;
import htos.business.entity.bid.TechBidEval;
import htos.business.entity.procurement.PurchaseMaterial;
import htos.common.entity.PageInfo;
import htos.coresys.dao.impl.BaseDaoImpl;
import org.hibernate.transform.Transformers;

import java.util.List;

public class AgreementDaoImpl extends BaseDaoImpl<TechBidEval> implements AgreementDao {
	@Override
	public List queryPurchaseWithProject(PageInfo pageInfo) {
		String sql = "select a.* from (SELECT " +
				"  pb.uuid as projectUuid," +
				"  pb.project_code as projectCode," +
				"  pb.project_name as projectName," +
				"  pb.owner as owner," +
				"  pp.uuid as purchasePlanUuid," +
				"  pp.purchase_plan_code as purchasePlanCode," +
				"  pp.purchase_plan_name as purchasePlanName," +
				"  pp.purchase_method as purchaseMethod," +
				"  pp.create_date as createDate," +
				"  pc.supplier_id as supplierId," +
				"  pc.supplier_name as supplierName," +
				"  '' as confirmUuid," +
				"  pc.final_offer as finalOffer " +
				"  FROM bs_project_base pb" +
				"  JOIN bs_purchase_apply pa ON pa.project_uuid = pb.uuid" +
				"  JOIN bs_purchase_plan pp ON pp.purchase_apply_uuid = pa.uuid" +
				"  JOIN bs_purchase_plan_candiate pc ON pc.plan_id = pp.uuid AND pc.is_win = 1" +
				"  UNION " +
				"  SELECT " +
				"  pb.uuid as projectUuid," +
				"  pb.project_code as projectCode," +
				"  pb.project_name as projectName," +
				"  pb.owner as owner," +
				"  pp.uuid as purchasePlanUuid," +
				"  pp.purchase_plan_code as purchasePlanCode," +
				"  pp.purchase_plan_name as purchasePlanName," +
				"  pp.purchase_method as purchaseMethod," +
				"  pp.create_date as createDate," +
				"  se.ACCOUNT as supplierId," +
				"  se.NAME as supplierName," +
				"  bc.uuid as confirmUuid," +
				"  sb.final_total_price as finalOffer " +
				"FROM bs_project_base pb" +
				"  JOIN bs_purchase_apply pa ON pa.project_uuid = pb.uuid" +
				"  JOIN bs_purchase_plan pp ON pp.purchase_apply_uuid = pa.uuid" +
				"  JOIN bs_bidding_bulletin bb ON bb.purchase_plan_uuid = pp.uuid" +
				"  JOIN bs_bidding_file bf ON bf.bulletin_uuid = bb.uuid" +
				"  JOIN bs_bidding_confirm bc ON bc.bidding_file = bf.uuid" +
				"  JOIN bs_supplier_bid sb ON sb.uuid = bc.bidding_id" +
				"  JOIN bs_supplier_ext se ON se.ACCOUNT = sb.create_uuid"
				+ ") a order by createDate desc ";
		return getCurrentSession().createSQLQuery(sql).setFirstResult(pageInfo.getFirstResult()).setMaxResults(pageInfo.getPageSize()).setResultTransformer(Transformers.aliasToBean(PurchaseWithProject.class)).list();
	}

	@Override
	public Integer queryPurchaseWithProjectCount() {
		String sql = "select count(*) from (SELECT " +
				"  pb.uuid as projectUuid," +
				"  pb.project_code as projectCode," +
				"  pb.project_name as projectName," +
				"  pb.owner as owner," +
				"  pp.uuid as purchasePlanUuid," +
				"  pp.purchase_plan_code as purchasePlanCode," +
				"  pp.purchase_plan_name as purchasePlanName," +
				"  pp.purchase_method as purchaseMethod," +
				"  pp.create_date as createDate," +
				"  pc.supplier_id as supplierId," +
				"  pc.supplier_name as supplierName," +
				"  pc.final_offer as finalOffer " +
				"  FROM bs_project_base pb" +
				"  JOIN bs_purchase_apply pa ON pa.project_uuid = pb.uuid" +
				"  JOIN bs_purchase_plan pp ON pp.purchase_apply_uuid = pa.uuid" +
				"  JOIN bs_purchase_plan_candiate pc ON pc.plan_id = pp.uuid AND pc.is_win = 1" +
				"  UNION " +
				"  SELECT" +
				"  pb.uuid as projectUuid," +
				"  pb.project_code as projectCode," +
				"  pb.project_name as projectName," +
				"  pb.owner as owner," +
				"  pp.uuid as purchasePlanUuid," +
				"  pp.purchase_plan_code as purchasePlanCode," +
				"  pp.purchase_plan_name as purchasePlanName," +
				"  pp.purchase_method as purchaseMethod," +
				"  pp.create_date as createDate," +
				"  se.ACCOUNT as supplierId," +
				"  se.NAME as supplierName," +
				"  sb.final_total_price as finalOffer " +
				"FROM bs_project_base pb" +
				"  JOIN bs_purchase_apply pa ON pa.project_uuid = pb.uuid" +
				"  JOIN bs_purchase_plan pp ON pp.purchase_apply_uuid = pa.uuid" +
				"  JOIN bs_bidding_bulletin bb ON bb.purchase_plan_uuid = pp.uuid" +
				"  JOIN bs_bidding_file bf ON bf.bulletin_uuid = bb.uuid" +
				"  JOIN bs_bidding_confirm bc ON bc.bidding_file = bf.uuid" +
				"  JOIN bs_supplier_bid sb ON sb.uuid = bc.bidding_id" +
				"  JOIN bs_supplier_ext se ON se.ACCOUNT = sb.create_uuid"
				+ ") a";
		return Integer.valueOf(getCurrentSession().createSQLQuery(sql).uniqueResult().toString());
	}

	@Override
	public SupplierBidInfo loadSupplierBidInfo(String confirmUuid) {
		String sql = "SELECT " +
				"  sb.uuid as supplierBidUuid ," +
				"  sb.delivery_time as deliveryTime," +
				"  sb.payment_method as paymentMethod," +
				"  se.ACCOUNT as supplierId," +
				"  se.NAME as supplierName," +
				"  sb.contacts as supplierContacts," +
				"  sb.phone as supplierMobile," +
				"  se.PHON as supplierPhone," +
				"  se.FAX as supplierFax," +
				"  se.EMAIL as supplierEmail " +
				"  FROM bs_bidding_confirm bc" +
				"  join bs_supplier_bid sb on bc.bidding_id = sb.uuid" +
				"  join bs_supplier_ext se on sb.create_uuid = se.ACCOUNT" +
				"  where bc.uuid =:confirmUuid";
		return (SupplierBidInfo) getCurrentSession()
				.createSQLQuery(sql)
				.setParameter("confirmUuid",confirmUuid)
				.setResultTransformer(Transformers.aliasToBean(SupplierBidInfo.class))
				.uniqueResult();
	}

	@Override
	public SupplierBidInfo loadSupplierInfo(String supplierId) {
		String sql = "SELECT" +
				"  se.ACCOUNT as supplierId ," +
				"  se.NAME as supplierName," +
				"  se.CONTACTS as supplierContacts," +
				"  se.PHON as supplierPhone," +
				"  se.FAX as supplierFax," +
				"  se.MOBILE as supplierMobile," +
				"  se.EMAIL as supplierEmail " +
				" from bs_supplier_ext se " +
				" where se.ACCOUNT = :supplierId ";
		return (SupplierBidInfo) getCurrentSession()
				.createSQLQuery(sql)
				.setParameter("supplierId",supplierId)
				.setResultTransformer(Transformers.aliasToBean(SupplierBidInfo.class))
				.uniqueResult();
	}

	@Override
	public List<PurchaseMaterial> findPurchaseMaterial(String purchasePlanUuid){
		String sql = " SELECT pm.num as num,m.material_name as materialName " +
				" FROM  bs_purchase_plan pp " +
				" join bs_purchase_material pm on pp.purchase_apply_uuid = pm.purchase_id " +
				" join bs_material m on pm.material_uuid = m.uuid " +
				" where pp.uuid = :purchasePlanUuid ";
		return  this.getCurrentSession().createSQLQuery(sql)
				.setParameter("purchasePlanUuid",purchasePlanUuid)
				.setResultTransformer(Transformers.aliasToBean(PurchaseMaterial.class))
				.list();
	}
}
