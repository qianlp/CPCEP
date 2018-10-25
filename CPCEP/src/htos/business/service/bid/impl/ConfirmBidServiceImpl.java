package htos.business.service.bid.impl;

import htos.business.dao.bid.ConfirmBidDao;
import htos.business.entity.bid.view.ViewConfirmBid;
import htos.business.entity.bid.view.ViewMaterialDetail;
import htos.business.entity.supplier.view.Page;
import htos.business.service.bid.ConfirmBidService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qinj
 * @date 2018-05-27 15:47
 **/
public class ConfirmBidServiceImpl implements ConfirmBidService {

	/**
	 * 定标列表
	 * @param page
	 * @param name
	 */
	@Override
	public void findConfirmList(Page<ViewConfirmBid> page, String name) {
		String sql = "FROM bs_bidding_file a " +
				"JOIN bs_bidding_bulletin b ON b.uuid=a.bulletin_uuid " +
				"LEFT JOIN bs_bidding_confirm c ON c.bidding_file=a.uuid " +
				"where a.status=3 or a.status=4 ";
		if(StringUtils.isNotBlank(name)) {
			sql += "and a.name like :name";
		}


		Query query = confirmBidDao.getCurrentSession().createSQLQuery("select count(*) " + sql);
		if(StringUtils.isNotBlank(name))
			query.setParameter("name", "%" + name + "%");
		Number count = (Number) query.uniqueResult();
		page.setTotalCount(count.intValue());
		if(count.intValue() == 0)
			return ;

		sql = "SELECT a.version as version, a.uuid as bidId, b.name as name, b.bid_start_time as bidStartTime, b.bid_end_time as bidEndTime, " +
				"b.bid_open_time as bidOpenTime, b.code as code, c.uuid AS confirmId " + sql;
		query = confirmBidDao.getCurrentSession().createSQLQuery(sql);

		int from = page.getpageIndex() * page.getPageSize();
		if(StringUtils.isNotBlank(name))
			query.setParameter("name", "%" + name + "%");
		List<ViewConfirmBid> list = (List<ViewConfirmBid>) query
				.setFirstResult(from)
				.setMaxResults(page.getPageSize())
				.setResultTransformer(Transformers.aliasToBean(ViewConfirmBid.class)).list();

		page.setResult(list);
	}

	/**
	 * 招标信息
	 * @param bidFileId
	 * @return
	 */
	@Override
	public ViewConfirmBid findBidInfo(String bidFileId) {
		String sql = "SELECT a.uuid as bidId, b.name AS name, b.code AS code, a.version AS version,p.purchase_apply_uuid as purchaseApplyUuid " +
				" ,c.uuid as confirmId,c.confirm_time as confirmTime ,c.message as message ,c.bidding_id as biddingId,c.supplier_id as supplierId " +
				"FROM bs_bidding_file a " +
				"JOIN bs_bidding_bulletin b ON b.uuid=a.bulletin_uuid " +
				"JOIN bs_purchase_plan p ON p.uuid=b.purchase_plan_uuid " +
				" left join bs_bidding_confirm c on a.uuid = c.bidding_file "+
				"where a.uuid=:bidFileId";
		ViewConfirmBid view = (ViewConfirmBid) confirmBidDao.getCurrentSession().createSQLQuery(sql)
				.setParameter("bidFileId", bidFileId)
				.setResultTransformer(Transformers.aliasToBean(ViewConfirmBid.class))
				.uniqueResult();
		return view;
	}

	/**
	 * 获取投标的供应商
	 * @param page
	 * @param bidFileId
	 */
	@Override
	public void findBidSupplier(Page<ViewConfirmBid> page, String bidFileId) {
		String sql = "FROM bs_bidding_file a " +
				"JOIN bs_supplier_bid b ON b.bidding_file_uuid=a.uuid " +
				"JOIN ht_right_user c ON c.uuid=b.create_uuid " +
				"JOIN bs_supplier_ext d ON d.account=c.uuid " +
				"LEFT JOIN bs_supplier_score e ON e.supplier_bid_uuid=b.uuid " +
				"where a.uuid=:bidFileId ";

		sql += "ORDER BY b.third_total_price DESC ";

		Query query = confirmBidDao.getCurrentSession().createSQLQuery("select count(*) " + sql);
		query.setParameter("bidFileId", bidFileId);
		Number count = (Number) query.uniqueResult();
		page.setTotalCount(count.intValue());
		if(count.intValue() == 0)
			return ;

		sql = "SELECT d.name as name, e.is_feasible AS status, b.third_total_price as thirdTotalPrice " + sql;
		query = confirmBidDao.getCurrentSession().createSQLQuery(sql);
		query.setParameter("bidFileId", bidFileId);
		List<ViewConfirmBid> list = (List<ViewConfirmBid>) query
				.setFirstResult(page.getFirstResult())
				.setMaxResults(page.getPageSize())
				.setResultTransformer(Transformers.aliasToBean(ViewConfirmBid.class)).list();
		for (int index = 0; index < list.size(); index++) {
			list.get(index).setRank(index + 1);
			if(StringUtils.isBlank(list.get(index).getStatus()))
				list.get(index).setStatus("不可行");
		}
		page.setResult(list);
	}

	/**
	 * 获取投标的供应商
	 * @param bidFileId
	 */
	@Override
	public List<ViewConfirmBid> findBidSupplier(String bidFileId) {
		String sql = "FROM bs_bidding_file a " +
				"JOIN bs_supplier_bid b ON b.bidding_file_uuid=a.uuid " +
				"JOIN bs_supplier_ext d ON d.account=b.create_uuid " +
				"LEFT JOIN bs_supplier_score e ON e.supplier_bid_uuid=b.uuid " +
				"where a.uuid=:bidFileId ";
		sql += "ORDER BY b.third_total_price DESC ";
		sql = "SELECT d.name as name, e.is_feasible AS status, b.third_total_price as thirdTotalPrice,b.uuid as biddingId,b.final_total_price as finalPrice," +
				" d.account as supplierId " + sql;
		return  (List<ViewConfirmBid>) confirmBidDao.getCurrentSession()
				.createSQLQuery(sql)
				.setParameter("bidFileId", bidFileId)
				.setResultTransformer(Transformers.aliasToBean(ViewConfirmBid.class)).list();
	}

	@Override
	public List<ViewMaterialDetail> findMaterialDetail(String supplierBidUuid,String userId) {
		String sql = " select pm.uuid,m.material_name as materialName,pm.spec_model as norms, pm.brand as brand,cast(pm.num as char) as count,spm.third_unit_price as price,spm.final_unit_price as finalUnitPrice,pm.type as type "
				+ " FROM bs_supplier_bid sb JOIN bs_supplier_material_price spm ON sb.uuid = spm.supplier_bid_uuid "
				+ " JOIN bs_purchase_material pm ON pm.uuid = spm.purchase_material_uuid "
				+ " JOIN bs_material m ON pm.material_uuid = m.uuid "
				+" where sb.uuid = :supplierBidUuid order by pm.type asc , pm.create_date asc  ";
		List<ViewMaterialDetail> materials = confirmBidDao.getCurrentSession()
				.createSQLQuery(sql)
				.setParameter("supplierBidUuid", supplierBidUuid)
				.setResultTransformer(Transformers.aliasToBean(ViewMaterialDetail.class)).list();
		List<ViewMaterialDetail> result = new ArrayList<>();
		//查找设备分项
		for (int i = 0 ;i < materials.size();i++) {
			String sql2 = " SELECT ma.purchase_material as uuid,ma.id as materialAttrUuid,ma.name as materialAttrName,"
					+" ma.norms,ma.brand,cast(ma.count as char) as count,cast(ma.price as char) as price,cast(ma.final_price as char) as finalUnitPrice,"
					+" '"+ materials.get(i).getMaterialName()+"' as materialName,"
					+" '"+(i+1)+"' as rowId,"
					+" '"+materials.get(i).getType()+"' as type "
					+" from bs_material_attr ma "
					+" "
					+ " where ma.purchase_material = :pmUuid and ma.user_id =:userId"
					+" order by ma.id desc ";
			List<ViewMaterialDetail> viewMaterialDetails = confirmBidDao.getCurrentSession()
					.createSQLQuery(sql2)
					.setParameter("pmUuid", materials.get(i).getUuid())
					.setParameter("userId",userId)
					.setResultTransformer(Transformers.aliasToBean(ViewMaterialDetail.class)).list();
			if (viewMaterialDetails!=null&&!viewMaterialDetails.isEmpty()){
				result.addAll(viewMaterialDetails);
			}else{
				materials.get(i).setRowId(String.valueOf(i));
				result.add(materials.get(i));
			}
		}
		return result;
	}

	@Override
	public void updateMaterialAttrFinalPrice(List<ViewMaterialDetail> details) {
		String hql = " update MaterialAttr set finalPrice=:finalPrice where id=:id";
		for (ViewMaterialDetail detail:details) {
			confirmBidDao.getCurrentSession().createQuery(hql)
					.setParameter("finalPrice",Integer.valueOf(detail.getFinalUnitPrice()))
					.setParameter("id",detail.getMaterialAttrUuid())
					.executeUpdate();
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	//
	//

	private ConfirmBidDao confirmBidDao;

	public void setConfirmBidDao(ConfirmBidDao confirmBidDao) {
		this.confirmBidDao = confirmBidDao;
	}
}
