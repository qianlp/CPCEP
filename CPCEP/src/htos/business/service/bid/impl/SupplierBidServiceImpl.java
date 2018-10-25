package htos.business.service.bid.impl;

import htos.business.dao.bid.SupplierBidDao;
import htos.business.dao.supplier.SupplierSignupDao;
import htos.business.dto.InviteSupplier;
import htos.business.dto.SupplierMaterialParamInfo;
import htos.business.dto.SupplierMaterialPriceInfo;
import htos.business.entity.bid.Invitation;
import htos.business.entity.bid.SupplierBid;
import htos.business.entity.bid.SupplierMaterialParam;
import htos.business.entity.bid.SupplierMaterialPrice;
import htos.business.entity.bid.SupplierScore;
import htos.business.entity.biddingFile.BidFileQuestion;
import htos.business.entity.material.MaterialCategoryParam;
import htos.business.entity.procurement.PurchaseMaterial;
import htos.business.entity.supplier.SupplierSignup;
import htos.business.entity.supplier.view.ViewUserSupplier;
import htos.business.service.bid.SupplierBidService;
import htos.coresys.dao.CommonDao;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.transaction.annotation.Transactional;

public class SupplierBidServiceImpl implements SupplierBidService {
	private SupplierBidDao supplierBidDao;
	private CommonDao<PurchaseMaterial> purchaseMaterialCommonDao;
	private CommonDao<SupplierMaterialPrice> materialPriceCommonDao;
	private CommonDao<SupplierMaterialPrice> supplierMaterialPriceCommonDao;
	private CommonDao<MaterialCategoryParam> materialCategoryParamCommonDao;
	private CommonDao<SupplierMaterialParam> supplierMaterialParamCommonDao;
	private SupplierSignupDao supplierSignupDao;

	public SupplierSignupDao getSupplierSignupDao() {
		return supplierSignupDao;
	}

	public void setSupplierSignupDao(SupplierSignupDao supplierSignupDao) {
		this.supplierSignupDao = supplierSignupDao;
	}

	@Override
	public SupplierBid findBy(String userId, String biddingFileId) {
		return supplierBidDao.findBy(userId, biddingFileId);
	}

	@Override
	public List<SupplierMaterialPriceInfo> findMaterialInfo(String deviceType, String purchaseId, String supplierBidUuid) {
		String hql = "from PurchaseMaterial where purchaseId=? and type=?";
		List<PurchaseMaterial> list = purchaseMaterialCommonDao.find(hql, new Object[]{purchaseId, deviceType});
		if (list != null) {
			List<SupplierMaterialPriceInfo> supplierMaterialPriceInfoList = new ArrayList<>(list.size());
			if (StringUtils.isNotBlank(supplierBidUuid)) {
				String hql2 = " from SupplierMaterialPrice where supplierBidUuid=?";
				List<SupplierMaterialPrice> supplierMaterialPrices = materialPriceCommonDao.find(hql2, new Object[]{supplierBidUuid});
				for (PurchaseMaterial purchaseMaterial : list) {
					if (supplierMaterialPrices != null && !supplierMaterialPrices.isEmpty()) {
						for (SupplierMaterialPrice supplierMaterialPrice : supplierMaterialPrices) {
							if (purchaseMaterial.getUuid().equals(supplierMaterialPrice.getPurchaseMaterialUuid())) {
								supplierMaterialPriceInfoList.add(SupplierMaterialPriceInfo.from(purchaseMaterial, supplierMaterialPrice));
							}
						}
					} else {
						supplierMaterialPriceInfoList.add(SupplierMaterialPriceInfo.from(purchaseMaterial));
					}
				}
			} else {
				for (PurchaseMaterial purchaseMaterial : list) {
					supplierMaterialPriceInfoList.add(SupplierMaterialPriceInfo.from(purchaseMaterial));
				}
			}
			return supplierMaterialPriceInfoList;
		}
		return null;
	}

	@Override
	public List<SupplierMaterialParamInfo> findSupplierMaterialParam(String purchaseMaterialId, String supplierBidUuid) {
		String sql = " SELECT " +
				"  pmp.pm_uuid AS purchaseMaterialUuid, " +
				"  pmp.uuid    AS purchaseParamUuid, " +
				"  pmp.param_name             AS paramName, " +
				"  pmp.param_unit             AS paramUnit, " +
				"  pmp.required_value         AS requiredValue, " +
				"  pmp.is_keyword             AS isKeyword, " +
				"  pmp.remark                 AS remark " +
				" FROM bs_purchase_material_param pmp " +
				" where pmp.pm_uuid=:pmUuid ";
		SQLQuery sqlQuery = supplierBidDao.getCurrentSession().createSQLQuery(sql);
		sqlQuery.setParameter("pmUuid", purchaseMaterialId);
		List<SupplierMaterialParamInfo> list = sqlQuery.setResultTransformer(Transformers.aliasToBean(SupplierMaterialParamInfo.class)).list();
		if (StringUtils.isNotBlank(supplierBidUuid)) {
			String hql = " from SupplierMaterialParam where purchaseParamUuid=? and supplierBidUuid=? ";
			for (SupplierMaterialParamInfo smp : list) {
				SupplierMaterialParam supplierMaterialParam = (SupplierMaterialParam) supplierMaterialParamCommonDao.findUnique(hql, new Object[]{smp.getPurchaseParamUuid(), supplierBidUuid});
				if (supplierMaterialParam != null) {
					smp.setUuid(supplierMaterialParam.getUuid());
					smp.setSupplierBidUuid(supplierMaterialParam.getSupplierBidUuid());
					smp.setResponseValue(supplierMaterialParam.getResponseValue());
					smp.setClarifyValue(supplierMaterialParam.getClarifyValue());
				}
			}
		}
		return list;
	}

	@Override
	public void saveOrUpdate(List<SupplierMaterialPrice> devices) {
		for (SupplierMaterialPrice supplierMaterialPrice : devices) {
			if (StringUtils.isNotBlank(supplierMaterialPrice.getUuid())) {
				supplierMaterialPriceCommonDao.update(supplierMaterialPrice);
			} else {
				supplierMaterialPriceCommonDao.save(supplierMaterialPrice);
			}
		}
	}

	@Override
	public void saveOrUpdateMaterialParam(List<SupplierMaterialParam> supplierMaterialParams) {
		for (SupplierMaterialParam supplierMaterialParam : supplierMaterialParams) {
			if (StringUtils.isNotBlank(supplierMaterialParam.getUuid())) {
				supplierMaterialParamCommonDao.update(supplierMaterialParam);
			} else {
				supplierMaterialParamCommonDao.save(supplierMaterialParam);
			}
		}
	}

	@Override
	public List<InviteSupplier> findInviteSupplier(String bidFileId,final String type){
		List<InviteSupplier> list = supplierBidDao.findInviteSupplier(bidFileId,type);
		if(list == null || list.isEmpty())
			return list;
		Collections.sort(list, new Comparator<InviteSupplier>() {
			@Override
			public int compare(InviteSupplier o1, InviteSupplier o2) {
				if (type.equals("1")) {
					return o1.getFirstTotalPrice().compareTo(o2.getFirstTotalPrice());
				} else {
					return o1.getThirdTotalPrice().compareTo(o2.getThirdTotalPrice());
				}
			}
		});
		float temp = 0f;
		int rank = 0;
		for (int index = 0; index < list.size(); index++) {
			InviteSupplier inviteSupplier = list.get(index);
			if (type.equals("1")) {
				float price = Float.parseFloat(inviteSupplier.getFirstTotalPrice());
				if(price > temp) {
					rank++;
				}
				inviteSupplier.setRank(rank);
				temp = price;

			} else {
				float price = Float.parseFloat(inviteSupplier.getThirdTotalPrice());
				if(price > temp) {
					rank++;
				}
				inviteSupplier.setRank(rank);
				temp = price;
			}
		}
		return list;
	}

	public List<ViewUserSupplier> findSuppliers(String bidFileUuid){
		return supplierBidDao.findSuppliers(bidFileUuid);
	}

	@Override
	public List findSupplierBidResults(String bidFileUuid) {
		return supplierBidDao.findByBidFile(bidFileUuid);
	}

	public CommonDao<PurchaseMaterial> getPurchaseMaterialCommonDao() {
		return purchaseMaterialCommonDao;
	}

	public void setPurchaseMaterialCommonDao(CommonDao<PurchaseMaterial> purchaseMaterialCommonDao) {
		this.purchaseMaterialCommonDao = purchaseMaterialCommonDao;
	}

	public CommonDao<SupplierMaterialPrice> getMaterialPriceCommonDao() {
		return materialPriceCommonDao;
	}

	public void setMaterialPriceCommonDao(CommonDao<SupplierMaterialPrice> materialPriceCommonDao) {
		this.materialPriceCommonDao = materialPriceCommonDao;
	}

	public CommonDao<SupplierMaterialPrice> getSupplierMaterialPriceCommonDao() {
		return supplierMaterialPriceCommonDao;
	}

	public void setSupplierMaterialPriceCommonDao(CommonDao<SupplierMaterialPrice> supplierMaterialPriceCommonDao) {
		this.supplierMaterialPriceCommonDao = supplierMaterialPriceCommonDao;
	}

	public SupplierBidDao getSupplierBidDao() {
		return supplierBidDao;
	}

	public void setSupplierBidDao(SupplierBidDao supplierBidDao) {
		this.supplierBidDao = supplierBidDao;
	}

	public CommonDao<MaterialCategoryParam> getMaterialCategoryParamCommonDao() {
		return materialCategoryParamCommonDao;
	}

	public void setMaterialCategoryParamCommonDao(CommonDao<MaterialCategoryParam> materialCategoryParamCommonDao) {
		this.materialCategoryParamCommonDao = materialCategoryParamCommonDao;
	}

	public CommonDao<SupplierMaterialParam> getSupplierMaterialParamCommonDao() {
		return supplierMaterialParamCommonDao;
	}

	public void setSupplierMaterialParamCommonDao(CommonDao<SupplierMaterialParam> supplierMaterialParamCommonDao) {
		this.supplierMaterialParamCommonDao = supplierMaterialParamCommonDao;
	}

	@Override
	public String findPlanId(String biddingFileId) {
		String sql = "SELECT purchase_plan_uuid FROM bs_bidding_file a " +
				"JOIN bs_bidding_bulletin b ON b.uuid=a.bulletin_uuid " +
				"WHERE a.uuid=:fileId";
		String planId = (String) materialPriceCommonDao.getCurrentSession().createSQLQuery(sql).setParameter("fileId", biddingFileId).uniqueResult();
		return planId;
	}

	@Override
	@Transactional
	public void deleteSupplierBid(String uuid) {
		//删除供应商材料价格
		String sql = " delete from bs_supplier_material_param where supplier_bid_uuid=:uuid";
		supplierBidDao.getCurrentSession().createSQLQuery(sql).setParameter("uuid",uuid).executeUpdate();
		//删除供应商材料参数
		String sql2 = " delete from bs_supplier_material_price where supplier_bid_uuid=:uuid";
		supplierBidDao.getCurrentSession().createSQLQuery(sql2).setParameter("uuid",uuid).executeUpdate();
		//删除邀请和澄清
		String sql4 = " delete from bs_invitation where supplier_bid_uuid=:uuid";
		supplierBidDao.getCurrentSession().createSQLQuery(sql4).setParameter("uuid",uuid).executeUpdate();
		//删除投标
		String sql5 = " delete from bs_supplier_bid where uuid=:uuid";
		supplierBidDao.getCurrentSession().createSQLQuery(sql5).setParameter("uuid",uuid).executeUpdate();
	}
	
    public List<BidFileQuestion> findBidQuestionList(String uuid,String supplierUuid){
    	return  supplierBidDao.findBidQuestionList(uuid, supplierUuid);
    }
	@Override
	public SupplierSignup findSignupInfo(String biddingBulletinUuid, String supplierUuid){
		return supplierSignupDao.findSignupInfo(biddingBulletinUuid,supplierUuid);
	}

    @Override
    public Invitation getInvitation(String uuid) {
	    String sql = "select down_extent as downExtent, end_date as endDate, min_amount as minAmount " +
                "from bs_invitation where type=2 and supplier_bid_uuid=:id";
        return (Invitation) supplierSignupDao.getCurrentSession().createSQLQuery(sql).setParameter("id", uuid)
                .setResultTransformer(Transformers.aliasToBean(Invitation.class)).uniqueResult();
    }
    public SupplierScore getSupplierScore(String uuid,String teachId){
    	String hql="select is_feasible as isFeasible from bs_supplier_score where supplier_bid_uuid=:id and tech_bid_eval_uuid=:teachId ";
    	
    	return (SupplierScore)supplierSignupDao.getCurrentSession().createSQLQuery(hql).setParameter("id", uuid)
    			.setParameter("teachId", teachId)
    			.setResultTransformer(Transformers.aliasToBean(SupplierScore.class)).uniqueResult();
    }
}

