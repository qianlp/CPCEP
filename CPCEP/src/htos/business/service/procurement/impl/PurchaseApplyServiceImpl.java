package htos.business.service.procurement.impl;

import htos.business.dao.procurement.PurchaseApplyDao;
import htos.business.entity.procurement.PurchaseApply;
import htos.business.entity.procurement.PurchaseMaterialParam;
import htos.business.entity.procurement.view.PurchaseMaterialParamView;
import htos.business.service.procurement.PurchaseApplyService;
import htos.coresys.dao.CommonDao;
import htos.coresys.dao.impl.CommonDaoImpl;
import htos.coresys.service.impl.CommonFacadeServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PurchaseApplyServiceImpl extends CommonFacadeServiceImpl<PurchaseApply> implements PurchaseApplyService {

	private PurchaseApplyDao purchaseApplyDao;
	private CommonDao<PurchaseMaterialParam> purchaseMaterialParamCommonDao;

	public CommonDao<PurchaseMaterialParam> getPurchaseMaterialParamCommonDao() {
		return purchaseMaterialParamCommonDao;
	}

	public void setPurchaseMaterialParamCommonDao(CommonDao<PurchaseMaterialParam> purchaseMaterialParamCommonDao) {
		this.purchaseMaterialParamCommonDao = purchaseMaterialParamCommonDao;
	}

	public void setPurchaseApplyDao(PurchaseApplyDao purchaseApplyDao) {
		this.purchaseApplyDao = purchaseApplyDao;
	}

	@Override
	public void updatePurchaseApply(PurchaseApply purchaseApply) {
		// TODO Auto-generated method stub
	  super.update(purchaseApply);
	}

	@Override
	public void deletePurchaseApply(PurchaseApply purchaseApply) {
		// TODO Auto-generated method stub
		 String uuid = purchaseApply.getUuid();
			String[] uuidArr;
			if (uuid.indexOf("^") > 0) {
				uuidArr = uuid.split("\\^");
			} else {
				uuidArr = new String[] { uuid };
			}
			for (String str : uuidArr) {
		        this.deleteId("CheckTask", "planId", str);
		        this.deleteId("CheckReport", "planId", str);
		        this.deleteId("CheckPlan", "uuid", str);
		        this.deleteRight("CheckPlan", "uuid");
			}
	}

	@Override
	public void addPurchaseApply(PurchaseApply purchaseApply, String rightId) {
		// TODO Auto-generated method stub
		super.save(purchaseApply, rightId);
	}

	@Override
	public PurchaseApply loadPurchaseApply(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPurchaseApplyNo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PurchaseApply> findMyApply(String uuid, String wfStatus,List searchList) {
		String hql = "from PurchaseApply where executePeoId=? and wfStatus=? ";
		hql +=getSearch(searchList);
		return purchaseApplyDao.find(hql, new Object[]{uuid, wfStatus});
	}

	private String getSearch(List list){
		StringBuffer hql = new StringBuffer(" ");
		String subHql="";
		if(list!=null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Map map=(Map)list.get(i);
				if(!map.containsKey("dataType")){//如果不是表单头中的查询条件，则执行本段代码，否则执行下一段代码
					Set<Map.Entry> entrySet = map.entrySet();
					for (Map.Entry en:entrySet) {
						//过滤总体
						if(!"总体".equals(en.getValue())){
							hql.append(" and "+en.getKey()+"='"+en.getValue()+"' ");
						}
					}
				}else if(map.containsKey("group")){
					if(!subHql.equals("")){
						subHql+=" or ";
					}

					if(map.get("dataType").toString().equals("text")){
						if(map.get("operator").toString().equals("@")){
							subHql+=" "+map.get("name")+" like '%"+map.get("value")+"%'";
						}else if(map.get("operator").toString().equals("=")){
							subHql+=" "+map.get("name")+"='"+map.get("value")+"'";
						}else if(map.get("operator").toString().equals("!=")){
							subHql+=" "+map.get("name")+"!='"+map.get("value")+"'";
						}
					}
					if(map.get("dataType").toString().equals("number")){
						subHql+=" "+map.get("name")+" "+map.get("operator").toString()+" "+map.get("value");
					}
					if(map.get("dataType").toString().equals("date")){
						subHql+=" "+map.get("name")+" "+map.get("operator").toString()+" '"+map.get("value")+"'";
					}
					if(map.get("dataType").toString().equals("object")){
						subHql+=" "+map.get("name")+" "+map.get("operator").toString()+" "+map.get("value")+"";
					}

				}else{
					if(map.get("dataType").toString().equals("text")){
						if(map.get("operator").toString().equals("@")){
							hql.append(" and "+map.get("name")+" like '%"+map.get("value")+"%'");
						}else if(map.get("operator").toString().equals("=")){
							hql.append(" and "+map.get("name")+"='"+map.get("value")+"'");
						}else if(map.get("operator").toString().equals("!=")){
							hql.append(" and "+map.get("name")+"!='"+map.get("value")+"'");
						}
					}
					if(map.get("dataType").toString().equals("number")){
						hql.append(" and "+map.get("name")+" "+map.get("operator").toString()+" "+map.get("value"));
					}
					if(map.get("dataType").toString().equals("date")){
						hql.append(" and "+map.get("name")+" "+map.get("operator").toString()+" '"+map.get("value")+"'");
					}
					if(map.get("dataType").toString().equals("object")){
						hql.append(" and "+map.get("name")+" "+map.get("operator").toString()+" "+map.get("value")+"");
					}
				}
			}
			if(!subHql.equals("")){
				hql.append(" and "+subHql);
			}
		}
		return hql.toString();
	}

	@Override
	public List findMaterialParam(String pmUuid){
		String sql = " SELECT uuid as pmpUuid, pm_uuid as pmUuid, param_name as paramName, param_unit as paramUnit, required_value as requiredValue, is_keyword as isKeyword, remark as remark "
				+ " from bs_purchase_material_param "
				+" where pm_uuid = :pmUuid";
		return purchaseApplyDao.getCurrentSession().createSQLQuery(sql).setParameter("pmUuid",pmUuid)
				.setResultTransformer(Transformers.aliasToBean(PurchaseMaterialParamView.class)).list();
	}

	@Transactional
	public void saveUpdatePurchaseMaterialParam(List<PurchaseMaterialParamView> purchaseMaterialParamViews,String haveEditParam, String pmUuid) {
		if (StringUtils.isNotBlank(haveEditParam)&&haveEditParam.equals("1")){
			//编辑了参数
			List<String> pmpUuids = new ArrayList<>(purchaseMaterialParamViews.size());
			for (PurchaseMaterialParamView pv : purchaseMaterialParamViews){
				if(StringUtils.isNotBlank(pv.getPmpUuid())){
					pmpUuids.add(pv.getPmpUuid());
				}
			}
			String delHql = " delete from PurchaseMaterialParam where pmUuid=:pmUuid " ;
			if (!pmpUuids.isEmpty()){
				delHql += " and uuid not in (:pmpUuids)";
			}
			Query query = purchaseApplyDao.getCurrentSession().createQuery(delHql);
			query.setParameter("pmUuid",pmUuid);
			if (!pmpUuids.isEmpty()) {
				query.setParameterList("pmpUuids",pmpUuids).executeUpdate();
			}
			query.executeUpdate();
			for (PurchaseMaterialParamView pv : purchaseMaterialParamViews){
				purchaseMaterialParamCommonDao.saveOrUpdate(pv.toEntity(pmUuid));
			}
		}
	}

	@Transactional
	public void deletePurchaseMaterialParam(String pmUuid){
		String delHql = " delete from PurchaseMaterialParam where pmUuid=:pmUuid " ;
		Query query = purchaseApplyDao.getCurrentSession().createQuery(delHql);
		query.setParameter("pmUuid",pmUuid);
		query.executeUpdate();
	}
}
