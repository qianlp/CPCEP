package htos.business.dao.bid.impl;

import htos.business.dao.bid.SupplierBidDao;
import htos.business.dto.InviteSupplier;
import htos.business.dto.SupplierMaterialParamInfo;
import htos.business.entity.bid.SupplierBid;
import htos.business.entity.biddingFile.BidFileQuestion;
import htos.business.entity.supplier.view.ViewUserSupplier;
import htos.coresys.dao.impl.BaseDaoImpl;

import org.hibernate.transform.Transformers;

import java.util.List;

public class SupplierBidDaoImpl extends BaseDaoImpl<SupplierBid> implements SupplierBidDao {
	@Override
	public SupplierBid findBy(String userId, String biddingFileId) {
		String hql = " from SupplierBid where createUuid=? and biddingFileUuid=?";
		return this.get(hql, new Object[]{userId, biddingFileId});
	}

	@Override
	public List findByBidFile(String bidFileUuid) {
		String sql = " SELECT se.NAME as supplierName,sb.first_total_price as firstTotalPrice, sb.third_total_price as thirdTotalPrice ,sb.payment_method as paymentMethod, sb.delivery_time as deliveryTime " +
				" FROM bs_supplier_bid sb " +
				" JOIN bs_supplier_ext se ON sb.create_uuid = se.ACCOUNT "+
				" WHERE sb.bidding_file_uuid = :bidFileUuid "+
				" order by thirdTotalPrice asc ";

		return this.getCurrentSession().createSQLQuery(sql)
				.setParameter("bidFileUuid",bidFileUuid)
				.setResultTransformer(Transformers.aliasToBean(SupplierBid.class))
				.list();
	}
	public List<BidFileQuestion> findBidQuestionList(String uuid,String suppId){
		String sql="select remark as remark,issue_type as issueType, status as status,uuid as uuid, bid_file_uuid as bidFileUUID from bs_bid_file_question"+
	               " WHERE bid_file_uuid=:uuid and sup_uuid=:supplierUuid";
		return this.getCurrentSession().createSQLQuery(sql)
				   .setParameter("uuid", uuid)
				   .setParameter("supplierUuid", suppId)
				   .setResultTransformer(Transformers.aliasToBean(BidFileQuestion.class))
				   .list();
	}

	@Override
	public List<InviteSupplier> findInviteSupplier(String bidFileId, String type) {
		String sql = "SELECT sb.uuid as uuid, sb.first_total_price as firstTotalPrice,sb.second_total_price as secondTotalPrice,sb.third_total_price as thirdTotalPrice, "
				+ "ext.ACCOUNT as supplierUuid,ext.NAME AS supplierName,ext.CONTACT_ADDRESS AS contactAddress,ext.CORPORATIONS AS corporations, "
				+ "su.contact as contact,su.mobile as mobile "
				+ "from bs_supplier_bid sb "
				+ "JOIN bs_bidding_file bf ON bf.uuid = sb.bidding_file_uuid "
				+ "JOIN bs_supplier_ext ext ON ext.ACCOUNT = sb.create_uuid "
				+ "JOIN bs_supplier_signup su ON ext.UUID = su.supplier AND bf.bulletin_uuid = su.bidding ";
		if (type.equals("1")) {
			sql += " where bf.uuid=:bidFileId order by sb.first_total_price asc ";
		} else {
			sql += " JOIN bs_supplier_score sc ON  sc.supplier_bid_uuid = sb.uuid where bf.uuid=:bidFileId and sc.is_feasible ='可行' "
					+ "order by sb.third_total_price asc ";
		}
		return this.getCurrentSession().createSQLQuery(sql)
				.setParameter("bidFileId", bidFileId)
				.setResultTransformer(Transformers.aliasToBean(InviteSupplier.class)).list();
	}

	@Override
	public List<ViewUserSupplier> findSuppliers(String bidFileUuid) {
		String sql = " select a.account,a.name from bs_supplier_bid b join bs_supplier_ext a on b.create_uuid = a.account " +
				" where b.bidding_file_uuid = :bidFileUuid order by b.create_date asc";
		return this.getCurrentSession().createSQLQuery(sql)
				.setParameter("bidFileUuid",bidFileUuid)
				.setResultTransformer(Transformers.aliasToBean(ViewUserSupplier.class))
				.list();
	}

	@Override
	public List findSupplierMaterialParams(String bidFileUuid) {
		String sql = " SELECT " +
				"  sb.create_uuid as supplierUuid , " +
				"  mp.purchase_material_uuid as purchaseMaterialUuid , " +
				" mp.purchase_param_uuid as purchaseParamUuid ," +
				"  mp.response_value as  responseValue, " +
				"  mp.clarify_value as  clarifyValue" +
				" FROM bs_supplier_bid sb " +
				"  JOIN bs_supplier_material_param mp ON sb.uuid = mp.supplier_bid_uuid " +
				" where sb.bidding_file_uuid = :bidFileUuid " +
				" order by mp.purchase_material_uuid asc ";
		return this.getCurrentSession().createSQLQuery(sql)
				.setParameter("bidFileUuid",bidFileUuid)
				.setResultTransformer(Transformers.aliasToBean(SupplierMaterialParamInfo.class))
				.list();
	}
}
