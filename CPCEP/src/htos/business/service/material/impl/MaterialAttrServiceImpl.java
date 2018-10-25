package htos.business.service.material.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import htos.business.dao.material.MaterialAttrDao;
import htos.business.entity.material.MaterialAttr;
import htos.business.entity.supplier.view.Page;
import htos.business.service.material.MaterialAttrService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author qinj
 * @date 2018-05-23 22:25
 **/
public class MaterialAttrServiceImpl implements MaterialAttrService {

	private MaterialAttrDao materialAttrDao;

	public void setMaterialAttrDao(MaterialAttrDao materialAttrDao) {
		this.materialAttrDao = materialAttrDao;
	}

	/**
	 * 保存设备分项信息
	 * @param planId
	 * @param materialId
	 * @param deviceAttr
	 * @param userId
	 */
	@Override
	public void saveDeviceAttr(String planId, String materialId, String deviceAttr, String userId, String supplier) {
		if(StringUtils.isBlank(deviceAttr))
			return ;
		if(StringUtils.isNotBlank(supplier)) {
			materialAttrDao.delete("delete MaterialAttr where " +
					"purchasePlan='" + planId + "' and purchaseMaterial='" + materialId + "' and userId='" + userId + "'");
		}
		JSONArray jsonArray = JSONArray.parseArray(deviceAttr);
		for (int index = 0; index < jsonArray.size(); index++) {
			JSONObject jsonObject = jsonArray.getJSONObject(index);
			String id = jsonObject.getString("id");
			MaterialAttr entity = null;
			boolean exist = false;
			if(StringUtils.isBlank(supplier)) {
				if(StringUtils.isNotBlank(id)) {
					entity = materialAttrDao.get(MaterialAttr.class, id);
					exist = true;
				}
			}

			if(entity == null) {
				entity = new MaterialAttr();
				exist = false;
			}
			entity.setBrand(jsonObject.getString("brand"));
			entity.setCount(jsonObject.getInteger("count"));
			entity.setName(jsonObject.getString("name"));
			entity.setNorms(jsonObject.getString("norms"));
			entity.setPurchaseMaterial(materialId);
			entity.setPurchasePlan(planId);
			if(StringUtils.isNotBlank(supplier))
				entity.setUserId(userId);
			entity.setPrice(jsonObject.getInteger("price"));
			if(jsonObject.containsKey("finalPrice")){
				entity.setFinalPrice(jsonObject.getInteger("finalPrice"));
			}
			if(!exist)
				materialAttrDao.save(entity);
			else
				materialAttrDao.update(entity);
		}
	}

	@Transactional
	public void deleteDeviceAttr(String[] ids){
		Query query = materialAttrDao.getCurrentSession().createQuery("delete MaterialAttr where id in (:ids)");
		query.setParameterList("ids",ids);
		query.executeUpdate();
	}

	/**
	 * 获取设备分项
	 * @param page
	 * @param planId
	 * @param materialId
	 */
	@Override
	public void findDeviceAttr(Page<MaterialAttr> page, String planId, String materialId, String userId) {
		String hql = "from MaterialAttr where purchasePlan=:planId and purchaseMaterial=:materialId and userId is null";

		Number count = (Number) materialAttrDao.getCurrentSession().createQuery("select count(*) " + hql)
				.setParameter("planId", planId)
				.setParameter("materialId", materialId)
				.uniqueResult();
		page.setTotalCount(count.intValue());
		if(count.intValue() == 0)
			return ;

		if(StringUtils.isNotBlank(userId)) {
			hql = "from MaterialAttr where purchasePlan=:planId and purchaseMaterial=:materialId and userId=:userId";
			List<MaterialAttr> list = materialAttrDao.getCurrentSession().createQuery(hql)
					.setParameter("planId", planId)
					.setParameter("materialId", materialId)
					.setParameter("userId", userId)
					.setFirstResult(page.getFirstResult())
					.setMaxResults(page.getPageSize())
					.list();
			if (list != null && !list.isEmpty() && list.size() == count.intValue()) {
				page.setResult(list);
				return;
			}
		}

		hql = "from MaterialAttr where purchasePlan=:planId and purchaseMaterial=:materialId";
		List<MaterialAttr> list = materialAttrDao.getCurrentSession().createQuery(hql)
				.setParameter("planId", planId)
				.setParameter("materialId", materialId)
				.setFirstResult(page.getFirstResult())
				.setMaxResults(page.getPageSize())
				.list();
		page.setResult(list);

	}

	@Override
	public List findDeviceAttrNoPage(String planId, String materialId, String userId) {
		if (StringUtils.isNotBlank(userId)) {
			String hql = "from MaterialAttr where purchasePlan=:planId and purchaseMaterial=:materialId and userId=:userId";
			List list = materialAttrDao.getCurrentSession().createQuery(hql)
					.setParameter("planId", planId)
					.setParameter("materialId", materialId)
					.setParameter("userId", userId)
					.list();
			if (list != null && !list.isEmpty()) {
				return list;
			}
		}
		String hql = "from MaterialAttr where purchasePlan=:planId and purchaseMaterial=:materialId and userId is null ";
		return materialAttrDao.getCurrentSession().createQuery(hql)
				.setParameter("planId", planId)
				.setParameter("materialId", materialId)
				.list();
	}

    @Override
    public void updateMaterial(String tempId, String uuid) {
	    String hql = "update MaterialAttr set purchasePlan=? where purchasePlan=? ";
        materialAttrDao.executeHql(hql, new Object[]{uuid, tempId});
    }
}
