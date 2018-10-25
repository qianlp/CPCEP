package htos.coresys.service.impl;

import htos.common.entity.PageInfo;
import htos.common.util.DateUtil;
import htos.coresys.dao.CommonDao;
import htos.coresys.service.CommonService;
import htos.coresys.util.CommonUtil;
import htos.coresys.util.ReflectObjUtil;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommonServiceImpl<T> implements CommonService<T> {
	// -----通用数据列表加载开始-----
	private CommonDao<T> commonDao;
	//private TaskBusinessService<T> taskBusinessService;

	@Override
	public List<T> loadAllList(String entityClsName) {
		List<T> listObj = commonDao.loadAllList(entityClsName);
		return listObj;
	}

	@Override
	public int getAllDataCount(String entityClsName, String docIds) {
		return commonDao.getAllDataCount(entityClsName, docIds);
	}

	@Override
	public List<T> getPageDataList(String entityClsName, PageInfo pageInfo,
			String docIds) {
		return commonDao.getPageDataList(entityClsName, pageInfo, docIds);
	}

	@Override
	public List<Map<String, String>> dataList(List<T> listObj,Set<String> fieldSet) {
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		Method m = null;
		String value = "";
		if (listObj == null) {
			return dataList;
		}
		for (Object obj : listObj) {
			map = new HashMap<String, String>();
			for (String field : fieldSet) {
				// 拿到该属性的gettet方法
				try {
					m = (Method) obj.getClass().getMethod("get" + CommonUtil.upperFirstChar(field));
					Type type= m.getGenericReturnType();
					// 如果类型为日期和时间类型则进行转换
					if (m.getName().endsWith("Date")) {
						value = DateUtil.DateConversionString((Date) m.invoke(obj));
					} else if (m.getName().endsWith("Time")) {
						value = DateUtil.DateConversion19String((Date) m.invoke(obj));
					}
					else if(int.class.equals(type) || Integer.class.equals(type)){
						value = String.valueOf((Integer)m.invoke(obj));
					}
					else if(Double.class.equals(type) || double.class.equals(type)){
						value = String.valueOf((Double)m.invoke(obj));
					}
					else if(int.class.equals(type) || Integer.class.equals(type)){
						value = String.valueOf((Integer)m.invoke(obj));
					}
					else if (Float.class.equals(type) || float.class.equals(type)) {
						value = String.valueOf((Float)m.invoke(obj));
					}
					else if (Boolean.class.equals(type) || boolean.class.equals(type)) {
						value = String.valueOf((Boolean)m.invoke(obj));
					}
					else if (BigDecimal.class.equals(type)) {
						BigDecimal bObj = new BigDecimal(m.invoke(obj).toString());
						bObj = bObj.setScale(2, BigDecimal.ROUND_HALF_UP);
						value =bObj.toString();
					}
					else {
						value = (String) m.invoke(obj);
					}
				} catch (Exception e) {
					value = "";
				} finally {
					if (value == null) {
						value = "";
					}
					map.put(field, value);
				}
			}
			dataList.add(map);
		}
		return dataList;
	}

	// -----通用数据列表加载结束-----
	public CommonDao<T> getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDao<T> commonDao) {
		this.commonDao = commonDao;
	}

	@Override
	public Serializable save(T o) {
		Serializable t = commonDao.save(o);
		//taskBusinessService.updateTaskBus(o, "1");
		return t;
	}

	@Override
	public void delete(T o) {
		commonDao.delete(o);
	}

	@Override
	public void update(T o) {
		commonDao.update(o);
		//taskBusinessService.updateTaskBus(o, "2");
	}

	@Override
	public void saveOrUpdate(T o) {
		commonDao.saveOrUpdate(o);
		//taskBusinessService.updateTaskBus(o, "2");
	}

	@Override
	public T get(Class<T> c, Serializable id) {
		return commonDao.get(c, id);
	}

	@Override
	public T getEntityByID(String entityClsName, String uuid) {
		return commonDao.getEntityByID(entityClsName, uuid);
	}

	@Override
	public List<T> getPageDataMapHeadOrList(String entityClsName,
			PageInfo pageInfo, String orgIds) {
		return commonDao.getPageDataMapHeadOrList(entityClsName, pageInfo,orgIds);
	}
	
	@Override
	public List<T> getPageDataMapHeadOrListSearch(String entityClsName,
			PageInfo pageInfo, String orgIds,List list) {
		return commonDao.getPageDataMapHeadOrListSearch(entityClsName, pageInfo, orgIds, list);
	}

	@Override
	public List<T> loadMapListForPageHeadList(String entityClsName,
			PageInfo pageInfo, String orgIds) {
		return commonDao.loadMapListForPageHeadList(entityClsName, pageInfo,orgIds);
	}

	@Override
	public int getAllCount(String entityClsName, String orgIds, boolean flag) {
		return commonDao.getAllCount(entityClsName, orgIds, flag);
	}
	
	@Override
	public List<T> loadMapListForPageHeadListSearch(String entityClsName,
			PageInfo pageInfo, String orgIds,List list) {
		return commonDao.loadMapListForPageHeadListSearch(entityClsName, pageInfo, orgIds, list);
	}

	@Override
	public int getAllCountSearch(String entityClsName, String orgIds, boolean flag,List list) {
		return commonDao.getAllCountSearch(entityClsName, orgIds, flag, list);
	}

	@Override
	public List<T> loadMapListForPageView(String viewName, PageInfo pageInfo,
			String userPerEname, boolean flag) {
		return commonDao.loadMapListForPageView(viewName, pageInfo,
				userPerEname, flag);
	}

	@Override
	public int getAllCountView(String viewName, String userPerEname,
			boolean flag) {
		return commonDao.getAllCountView(viewName, userPerEname, flag);
	}

	@Override
	public List<T> loadListView(String viewName, String userPerEname,
			boolean flag) {
		return commonDao.loadListView(viewName, userPerEname, flag);
	}

	@Override
	public void deleteId(String model, String attr, String param) {
		String hql = "delete from " + model + " where " + attr + " ='" + param+ "'";
		commonDao.delete(hql);
	}

	@Override
	public List<T> loadListForList(String entityClsName, String orgIds) {
		return commonDao.loadListForList(entityClsName, orgIds);
	}

	@Override
	public int getAllCountList(String entityClsName, String orgIds) {
		return commonDao.getAllCountList(entityClsName, orgIds);
	}

	@Override
	public List<T> loadMapListForPageList(String entityClsName,
			PageInfo pageInfo, String orgIds) {
		return commonDao.loadMapListForPageList(entityClsName, pageInfo, orgIds);
	}

	@Override
	public void deleteIdList(String model, String param) {
		String hql = "delete from " + model + " where uuid ='" + param + "'";
		commonDao.delete(hql);// 删除表头数据
		this.deleteId(model + "List", "pid", param);// 删除表体关联数据
		this.deleteId("MsgInfo", "docId", param);// 删除文档生成的对应待办通知
		this.deleteRight(model,param);//删除表头关联的right数据
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * htos.coresys.service.CommonService#getEntityByProperty(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public T getEntityByProperty(String entityClsName, String property,
			String value) {
		return commonDao.getEntityByProperty(entityClsName, property, value);
	}

	@Override
	public List<T> loadList(String entityClsName, String pid) {
		return commonDao.loadList(entityClsName, pid);
	}

	@Override
	public List<T> getListByProperty(String entityClsName, String property,
			String value) {
		return commonDao.find(" from " + entityClsName + " where " + property
				+ "='" + value + "'");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see htos.coresys.service.CommonService#getPageDataList(java.lang.String,
	 * htos.common.entity.PageInfo, java.lang.String, java.util.List)
	 */
	@Override
	public List<T> getPageDataList(String entityClsName, PageInfo pageInfo,
			String docIds, List list) {
		return commonDao.getPageDataList(entityClsName, pageInfo, docIds, list);
	}
	
	//根据参数删除同时删除权限数据
	@Override
	public void deleteRightId(String model, String attr, String param) {
		this.deleteId(model, attr, param);// 删除表数据
		this.deleteId("MsgInfo", "docId", param);// 删除文档生成的对应待办通知
		this.deleteRight(model, param);// 删除right数据

	}
	
	//根据对象删除同时删除权限数据
	@Override
	public void deleteObjectRight(T o) {
		this.delete(o);//删除级联对象
		String param = ReflectObjUtil.getAttrributeParentThreeValue(o, "uuid").toString();
		this.deleteRight(o.getClass().getSimpleName(), param);// 删除right数据
	}

	@Override
	public void deleteRight(String model, String param) {
		String hql = "delete from Right where docId ='" + param
				+ "' where modoName='" + model + "'";
		commonDao.delete(hql);
	}
	
	@Override
	public List<T> getListProperty(String entityClsName, String property, String value){
		return commonDao.getListProperty(entityClsName, property, value);
	}

	@Override
	public List<T> getPageDataMapHeadOrListSearch(String entityClsName,String queryEntityClsname, PageInfo pageInfo, String orgIds,List list) {
		return commonDao.getPageDataMapHeadOrListSearch(entityClsName,queryEntityClsname, pageInfo, orgIds, list);
	}

	@Override
	public int getAllCountSearch(String entityClsName,String queryEntityClsname, String orgIds, boolean flag, List list) {
		return commonDao.getAllCountSearch(entityClsName,queryEntityClsname, orgIds, flag, list);
	}

	@Override
	public List<T> getPageDataMapHeadOrListOrview(String entityClsName,String queryEntityClsname, PageInfo pageInfo, String orgIds) {
		return commonDao.getPageDataMapHeadOrList(entityClsName,queryEntityClsname, pageInfo,orgIds);
	}

	@Override
	public int getAllCount(String entityClsName, String queryEntityClsname,String orgIds, boolean flag) {
		return commonDao.getAllCount(entityClsName,queryEntityClsname, orgIds, flag);
	}

	@Override
	public T getEntityByNotProperty(String entityClsName, String property,String value) {
		return commonDao.getEntityByNotProperty(entityClsName, property, value);
	}

	@Override
	public T getEntityByLikeProperty(String entityClsName, String property,String value) {
		return commonDao.getEntityByLikeProperty(entityClsName, property, value);
	}

	@Override
	public List<T> getListByNotProperty(String entityClsName, String property,String value) {
		return commonDao.getListByNotProperty(entityClsName, property, value);
	}

	@Override
	public List<T> getListByLikeProperty(String entityClsName, String property,String value) {
		return commonDao.getListByLikeProperty(entityClsName, property, value);
	}

	@Override
	public List<T> loadListOrgIds(String enClsName, String orgIds) {
		return commonDao.loadListOrgIds(enClsName, orgIds);
	}

	@Override
	public List<T> getListByPropertyOrgIds(String entityClsName,String property, String value, String orgIds) {
		return commonDao.getListByPropertyOrgIds(entityClsName, property, value, orgIds);
	}

	@Override
	public List<T> getListPropertyOrgIds(String entityClsName, String property,String value, String orgIds) {
		return commonDao.getListPropertyOrgIds(entityClsName, property, value, orgIds);
	}

	@Override
	public List<T> getListByNotPropertyOrgIds(String entityClsName,String property, String value, String orgIds) {
		return commonDao.getListByNotPropertyOrgIds(entityClsName, property, value, orgIds);
	}

	@Override
	public List<T> getListByLikePropertyOrgIds(String entityClsName,String property, String value, String orgIds) {
		return commonDao.getListByLikePropertyOrgIds(entityClsName, property, value, orgIds);
	}

	@Override
	public T getEntityByIDOrgIds(String entityClsName, String uuid,String orgIds) {
		return commonDao.getEntityByIDOrgIds(entityClsName, uuid, orgIds);
	}

	@Override
	public T getEntityByPropertyOrgIds(String entityClsName, String property,String value, String orgIds) {
		return commonDao.getEntityByPropertyOrgIds(entityClsName, property, value, orgIds);
	}

	@Override
	public T getEntityByNotPropertyOrgIds(String entityClsName,String property, String value, String orgIds) {
		return commonDao.getEntityByNotPropertyOrgIds(entityClsName, property, value, orgIds);
	}

	@Override
	public T getEntityByLikePropertyOrgIds(String entityClsName,String property, String value, String orgIds) {
		return commonDao.getEntityByLikePropertyOrgIds(entityClsName, property, value, orgIds);
	}

	@Override
	public List<T> getHqlList(String hql) {
		return commonDao.getHqlList(hql);
	}

	@Override
	public T getHqlObject(String hql) {
		return commonDao.getHqlObject(hql);
	}

	@Override
	public List<T> getPageDataMapNotOrgSearch(String entityClsName,String queryEntityClsname, PageInfo pageInfo, List list) {
		return commonDao.getPageDataMapNotOrgSearch(entityClsName, queryEntityClsname, pageInfo, list);
	}

	@Override
	public int getAllCountNotOrgSearch(String entityClsName,String queryEntityClsname, boolean b, List list) {
		return commonDao.getAllCountNotOrgSearch(entityClsName, queryEntityClsname, b, list);
	}

	@Override
	public List<T> getPageDataMapNotOrg(String entityClsName,String queryEntityClsname, PageInfo pageInfo) {
		return commonDao.getPageDataMapNotOrg(entityClsName, queryEntityClsname, pageInfo);
	}

	@Override
	public int getAllCountNotOrg(String entityClsName,String queryEntityClsname, boolean b) {
		return commonDao.getAllCountNotOrg(entityClsName, queryEntityClsname, b);
	}

	

	@Override
	public void saveOrUpdate(String sql) {
		commonDao.saveOrUpdate(sql);
	}

	@Override
	public void merge(T o) {
		commonDao.merge(o);
	}

//	public TaskBusinessService<T> getTaskBusinessService() {
//		return taskBusinessService;
//	}
//
//	public void setTaskBusinessService(TaskBusinessService<T> taskBusinessService) {
//		this.taskBusinessService = taskBusinessService;
//	}
//	
	
}
