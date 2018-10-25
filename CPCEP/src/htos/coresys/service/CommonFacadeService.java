package htos.coresys.service;

import htos.common.entity.PageInfo;
import htos.coresys.entity.BaseWorkFlow;
import htos.coresys.entity.Menu;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;





public interface CommonFacadeService<T> {
	//所有数据json格式
	public List<Map<String, String>> loadAllList(String menuId);
	//所有数据json格式
	public List<T> loadList(String enClsName);
	//带权限所有数据json格式
	public List<T> loadListOrgIds(String enClsName,String orgIds);
	//分页带权限数据json格式
	public Map<String, Object> loadListForPage(String menuId, PageInfo pageInfo, String orgIds);
	public Map<String, Object> loadListForPage(String menuId, PageInfo pageInfo, String orgIds,List list);
	//分页不带权限查询
	public List<T> getPageDataList(String entityClsName, PageInfo pageInfo, String docIds);
	//带权限和流程、代办信息保存
	public String save(T o,String rigthData);
	//带权限、不带流程或代办保存
	public String save(T o);
	//不带权限删除
	public void delete(T o);
	//带权限、不带流程或代办修改
	public void update(T o, String rigthData) throws Exception;
	//不带权限、不带流程或代办修改
	public void update(T o);
	//不带权限、带流程和代办信息保存或修改
	public void saveOrUpdate(T o);
	//根据指定对象属性获取列表
	public List<T> getListByProperty(String entityClsName, String property,String value);
	public List<T> getListProperty(String entityClsName, String property, String value);
	//根据不包含指定属性获取对象
	public List<T> getListByNotProperty(String entityClsName, String property,String value);
	//根据包含指定属性获取对象
	public List<T> getListByLikeProperty(String entityClsName, String property,String value);
	//根据uuid查询数据
	public T getEntityByID(String entityClsName, String uuid);
	//根据指定属性获取对象
	public T getEntityByProperty(String entityClsName, String property,String value);
	//根据不包含指定属性获取对象
	public T getEntityByNotProperty(String entityClsName, String property,String value);
	//根据包含指定属性获取对象
	public T getEntityByLikeProperty(String entityClsName, String property,String value);
	
	//带权限根据指定对象属性获取列表
	public List<T> getListByPropertyOrgIds(String entityClsName, String property,String value,String orgIds);
	public List<T> getListPropertyOrgIds(String entityClsName, String property, String value,String orgIds);
	//带权限根据不包含指定属性获取对象
	public List<T> getListByNotPropertyOrgIds(String entityClsName, String property,String value,String orgIds);
	//带权限根据包含指定属性获取对象
	public List<T> getListByLikePropertyOrgIds(String entityClsName, String property,String value,String orgIds);
	//带权限根据uuid查询数据
	public T getEntityByIDOrgIds(String entityClsName, String uuid,String orgIds);
	//带权限根据指定属性获取对象
	public T getEntityByPropertyOrgIds(String entityClsName, String property,String value,String orgIds);
	//带权限根据不包含指定属性获取对象
	public T getEntityByNotPropertyOrgIds(String entityClsName, String property,String value,String orgIds);
	//带权限根据包含指定属性获取对象
	public T getEntityByLikePropertyOrgIds(String entityClsName, String property,String value,String orgIds);
	
	//通过脚本参数查询返回集合
	public List<T> getHqlList(String hql);
	//通过脚本参数查询返回对象
	public T getHqlObject(String hql);
	//根据主键查询对象
	public T get(Class<T> class1, Serializable id);
	
	
	//使用子查询，返回Map集合查询表头数据
	Map<String, Object> loadMapListForPageHeadOrList(String menuId,PageInfo pageInfo, String orgIds);
	//使用子查询，返回Map集合查询表头数据(条件查询)
	Map<String, Object> loadMapListForPageHeadOrListSearch(String menuId,PageInfo pageInfo, String orgIds, List list);
	//查询视图
	Map<String, Object> loadMapListForPageview(String menuId,PageInfo pageInfo, String viewName,String userPerEname,boolean flag);
	//使用子查询，返回Map集合表头表体关联数据
	Map<String, Object> loadMapListForPageHeadList(String menuId,PageInfo pageInfo, String orgIds);
	//使用子查询，返回Map集合表头表体关联数据(条件查询)
	Map<String, Object> loadMapListForPageHeadListSearch(String menuId,PageInfo pageInfo, String orgIds, List list);
	List<Map<String, String>> loadListView(String menuId, String viewName,String userPerEname,boolean flag);
	//更新表数据并向right表中添加数据
	public void updateOrSave(T o, Menu menu) throws Exception;
	//根据属性执行删除
	public void deleteId(String model, String attr, String param);
	//查询表体不分页
	List<Map<String, String>> loadListForList(String menuId, String orgIds);
	//查询表体分页
	Map<String, Object> loadMapListForPageList(String menuId,PageInfo pageInfo, String orgIds);
	//根据id执行删除表头且表体数据
	public void deleteIdList(String model,String param);
	//页面配置字段分页查询
	Map<String, Object> loadMapListForPageHead(String model, PageInfo pageInfo,String orgIds);
	//页面配置字段分页查询(条件查询)
	Map<String, Object> loadMapListForPageHeadSearch(String model,PageInfo pageInfo, String orgIds, List list);
	//根据表头id查询表体不分页
	List<T> loadList(String entityClsName, String pid);
	//根据id删除数据后同时删除对应的right表数据
	public void deleteRightId(String model, String attr, String param);
	//删除关联right表数据
	void deleteRight(String model, String param);
	//不走流程的消息提醒
	void toMsgUser(T o, String rightId, String docId);
	//走流程的消息提醒
	void toMsgUser(BaseWorkFlow baseWorkFlow, String rightId,String docId,String className);
	
	//删除对象级联同时删除权限数据right
	void deleteObjectRight(T o);
	//稳定添加阅读权限
	void addDocRight(String modeName, String docId, String orgId, int type);
	//流程阅读权限
	void wfFlowMsg(HttpServletRequest request, String docId, Menu menu,boolean isNewDoc,String className);
	//带权限查询主表、字表或视图分页
	Map<String, Object> loadMapListForPageHeadOrListOrview(String menuId,PageInfo pageInfo, String orgIds);
	//带权限查询主表、字表或视图或查询条件分页
	Map<String, Object> loadMapListForPageHeadOrListOrViewSearch(String menuId,PageInfo pageInfo, String orgIds, List list);
	//不带权限查询主表、字表或视图或查询条件分页
	Map<String, Object> loadMapListForNotOrgPageSearch(String menuId,PageInfo getpageInfo, List searchList);
	//不带权限查询主表、字表或视图或查询条件分页
	Map<String, Object> loadMapListForNotOrgPage(String menuId, PageInfo getpageInfo);
	
	//保存或修改sql
	void saveOrUpdate(String sql);
	/**
	 * @param modeName
	 * @param docId
	 * @param orgId
	 * @param type
	 * @param deptId
	 */
	void addDocRight(String modeName, String docId, String orgId, int type,
			String deptId);
	
}