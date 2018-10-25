package htos.coresys.service;

import htos.common.entity.PageInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;


public interface CommonService<T> {

	public List<T> loadAllList(String entityClsName);
	//带权限所有数据列表
	public List<T> loadListOrgIds(String enClsName,String orgIds);

	// 获取所有条数
	public int getAllDataCount(String entityClsName, String docIds);

	// 加载带权限分页后的列表
	/**
	 * @describe : 得到权限过滤后的分页数据
	 * @author pangchengxiang
	 * @param entityClsName
	 *            : 实体类名称
	 * @param pageInfo
	 *            ： 分页对象
	 * @param orgIds
	 *            ： 的文档id字符串，如 :
	 *            '402881ed528ca3bd01528ca443fc0000','402881ed528ca3bd01528ca443fc0001
	 *            '
	 * @return
	 */
	public List<T> getPageDataList(String entityClsName, PageInfo pageInfo, String docIds);
	public List<T> getPageDataList(String entityClsName, PageInfo pageInfo, String docIds,List list);
	
	public Serializable save(T o);

	public void delete(T o);
	
	public void deleteId(String model, String attr, String param); 

	public void update(T o);

	public void saveOrUpdate(T o);
	
	public void merge(T o);
	
	public T get(Class<T> c, Serializable id);
	
	public T getEntityByID(String entityClsName, String uuid);
	//根据对象属性获取对象
	public T getEntityByProperty(String entityClsName, String property,String value);
	//根据不包含指定属性获取对象
	public T getEntityByNotProperty(String entityClsName, String property,String value);
	//根据包含指定属性获取对象
	public T getEntityByLikeProperty(String entityClsName, String property,String value);
	
	//根据不包含指定属性获取对象
	public List<T> getListByNotProperty(String entityClsName, String property,String value);
	//根据包含指定属性获取对象
	public List<T> getListByLikeProperty(String entityClsName, String property,String value);
	//根据对象属性获取列表
	public List<T> getListByProperty(String entityClsName, String property,String value);
	List<T> getListProperty(String entityClsName, String property, String value);
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
	
	
	public List<Map<String, String>> dataList(List<T> objList, Set<String> fieldSet);
	//子查询查询表头或表体数据
	public List<T> getPageDataMapHeadOrList(String entityClsName, PageInfo pageInfo,String orgIds);
	//子查询查询表头或表体数据(条件查询)
	List<T> getPageDataMapHeadOrListSearch(String entityClsName,PageInfo pageInfo, String orgIds, List list);
	//子查询查询表头关联表体数据
	public List<T> loadMapListForPageHeadList(String entityClsName, PageInfo pageInfo,String orgIds);
	//子查询查询表头关联表体数据(条件查询)
	List<T> loadMapListForPageHeadListSearch(String entityClsName,PageInfo pageInfo, String orgIds, List list);
	//查询总记(条件查询)
	int getAllCountSearch(String entityClsName, String orgIds, boolean flag,List list);
	//查询总记录
	public int getAllCount(String entityClsName, String orgIds,boolean flag);
	//查询视图及表名分页
	public List<T> loadMapListForPageView(String viewName,PageInfo pageInfo,String userPerEname,boolean flag);
	//查询视图及表名记录数
	public int getAllCountView(String viewName,String userPerEname,boolean flag);
	//查询视图及表名
	List<T> loadListView(String viewName,String userPerEname,boolean flag);
	//查询表体不分页
	List<T> loadListForList(String entityClsName, String orgIds);
	//查询表体总记录
	int getAllCountList(String entityClsName, String orgIds);
	//查询表体分页
	List<T> loadMapListForPageList(String entityClsName, PageInfo pageInfo,String orgIds);
	//根据id删除表头且关联的表体数据
	public void deleteIdList(String model, String param);
	//根据表头id查询表体不分页
	List<T> loadList(String entityClsName, String pid);
	//根据id删除数据后同时删除对应的right表数据
	public void deleteRightId(String model, String attr, String param);
	
	//删除关联right数据
	void deleteRight(String model, String param);

	//删除对象级联，同时删除权限数据
	void deleteObjectRight(T o);
	//带权限查询主表、字表或视图或查询条件分页
	public List<T> getPageDataMapHeadOrListSearch(String entityClsName,String queryEntityClsname, PageInfo pageInfo, String orgIds,List list);
	//带权限查询主表、字表或视图或查询条件总和
	public int getAllCountSearch(String entityClsName,String queryEntityClsname, String orgIds, boolean b, List list);
	//带权限查询主表、字表或视图分页
	public List<T> getPageDataMapHeadOrListOrview(String entityClsName,String queryEntityClsname, PageInfo pageInfo, String orgIds);
	//带权限查询主表、字表或视图总和
	public int getAllCount(String entityClsName, String queryEntityClsname,String orgIds, boolean b);
	//不带权限查询主表、字表或视图或查询条件分页
	public List<T> getPageDataMapNotOrgSearch(String entityClsName,String queryEntityClsname, PageInfo pageInfo, List list);
	//不带权限查询主表、字表或视图或查询条件总和
	public int getAllCountNotOrgSearch(String entityClsName,String queryEntityClsname, boolean b, List list);
	//不带权限查询主表、字表或视图分页
	public List<T> getPageDataMapNotOrg(String entityClsName,String queryEntityClsname, PageInfo pageInfo);
	//不带权限查询主表、字表或视图总和
	public int getAllCountNotOrg(String entityClsName, String queryEntityClsname,boolean b);
	
	//通过脚本参数查询返回集合
	public List<T> getHqlList(String hql);
	//通过脚本参数查询返回对象
	public T getHqlObject(String hql);
	//保存或修改sql
	public void saveOrUpdate(String sql);


}
