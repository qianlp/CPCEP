package htos.business.service.procurement.impl;

import htos.business.dao.procurement.PurchaseCandiateDao;
import htos.business.dao.supplier.SupplierExtDao;
import htos.business.entity.procurement.PurchaseCandiate;
import htos.business.entity.procurement.PurchaseMaterial;
import htos.business.entity.procurement.PurchasePlan;
import htos.business.entity.supplier.SupplierExt;
import htos.business.service.procurement.PurchaseCandidateService;
import htos.common.entity.PageInfo;
import htos.coresys.service.CommonService;
import htos.coresys.service.impl.CommonFacadeServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.transform.Transformers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseCandidateServiceImpl extends CommonFacadeServiceImpl<PurchaseCandiate> implements PurchaseCandidateService {

    private CommonService<PurchaseCandiate> commonService;
    private SupplierExtDao supplierExtDao;
    private PurchaseCandiateDao candiateDao;

    @Override
    public Map<String, Object> loadMaterialForPage(String planId, int type, PageInfo pageInfo) {
		Map<String, Object> data = new HashMap<String, Object>();
    	if(StringUtils.isBlank(planId)) {
			data.put("total", 0);
			data.put("data", null);
			return data;
		}

		String sql = "FROM bs_purchase_plan_candiate a " +
				"JOIN bs_supplier_ext b ON b.uuid=a.supplier_id " +
				"where plan_id=:planId and type=:type ";

		Number count = (Number) supplierExtDao.getCurrentSession().createSQLQuery("select count(*) " + sql)
				.setParameter("planId", planId)
				.setParameter("type", type)
				.uniqueResult();
		data.put("total", count.intValue());
		data.put("data", null);
		if(count.intValue() == 0)
			return data;

		sql = "select b.uuid as supplierId , b.name as name, b.contacts as contacts, b.contact_address as contactAddress, b.corporations as corporations, " +
				"b.phon as phon, a.remark as remark,a.tec_offer as tecOffer,a.bus_offer as busOffer,a.final_offer as finalOffer,a.is_win as isWin " + sql;
		List<PurchasePlan> list = supplierExtDao.getCurrentSession().createSQLQuery(sql)
				.setParameter("planId", planId)
				.setParameter("type", type)
				.setFirstResult(pageInfo.getFirstResult())
				.setMaxResults(pageInfo.getPageSize())
				.setResultTransformer(Transformers.aliasToBean(PurchaseCandiate.class))
				.list();
        data.put("data", list);
        return data;
    }

	@Override
	public void delPurchaseCandiate(String purchasePlanUUID,int type) {
	  candiateDao.delPurchaseCandiate(purchasePlanUUID,type);
	}

	@Override
    public CommonService<PurchaseCandiate> getCommonService() {
        return commonService;
    }

    public void setCommonService(CommonService<PurchaseCandiate> commonService) {
        this.commonService = commonService;
    }

	public void setSupplierExtDao(SupplierExtDao supplierExtDao) {
		this.supplierExtDao = supplierExtDao;
	}

	public PurchaseCandiateDao getCandiateDao() {
		return candiateDao;
	}

	public void setCandiateDao(PurchaseCandiateDao candiateDao) {
		this.candiateDao = candiateDao;
	}
}
