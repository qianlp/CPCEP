package htos.coresys.dao;

import htos.common.entity.PageInfo;

import java.util.List;


public interface CommonDao<T> extends BaseDao<T>{
	//加载所有列表
	public List<T> loadAllList(String entityClsName);
	//带权限所有数据列表
	public List<T> loadListOrgIds(String enClsName,String orgIds);
	//获取所有条数
	public int getAllDataCount(String entityClsName, String docIds);
	//加载分页后的列表
	public List<T> getPageDataList(String entityClsName, PageInfo pageInfo, String docIds);
	//加载分页后的列表
	public List<T> getPageDataList(String entityClsName, PageInfo pageInfo, String docIds,List list);
	//根据对象id获取对象
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
	
	//通过子查询分页列表查询表头或表体数据
	public List<T> getPageDataMapHeadOrList(String entityClsName, PageInfo pageInfo,String orgIds);
	//通过子查询分页列表查询表头或表体数据(条件查询)
	List<T> getPageDataMapHeadOrListSearch(String entityClsName,PageInfo pageInfo, String orgIds, List list);
	//通过子查询分页列表查询表头关联表体数据
	public List<T> loadMapListForPageHeadList(String entityClsName, PageInfo pageInfo,String orgIds);
	//通过子查询分页列表查询表头关联表体数据(条件查询)
	List<T> loadMapListForPageHeadListSearch(String entityClsName,PageInfo pageInfo, String orgIds, List list);
	//取得数据count
	public int getAllCount(String entityClsName, String orgIds,boolean flag);
	//取得数据count(条件查询)
	int getAllCountSearch(String entityClsName, String orgIds, boolean flag,List list);
	//查询视图及表名总记录数
	public int getAllCountView(String viewName,String userPerEname,boolean flag);
	//分页查询视图及表名数据
	public List<T> loadMapListForPageView(String viewName, PageInfo pageInfo,String userPerEname,boolean flag);
	//查询视图及表名数据
	List<T> loadListView(String viewName,String userPerEname,boolean flag);
	//查询表体总记录
	int getAllCountList(String entityClsName, String orgIds);
	//查询表体总记录分页
	List<T> loadMapListForPageList(String entityClsName, PageInfo pageInfo,String orgIds);
	//查询表体不分页
	List<T> loadListForList(String entityClsName, String orgIds);
	//根据表头id查询表体不分页
	List<T> loadList(String entityClsName, String pid);
	//根据参数查询返回list
	List<T> getListProperty(String entityClsName, String property, String value);
	//带权限查询主表、字表或视图或查询条件分页
	public List<T> getPageDataMapHeadOrListSearch(String entityClsName,String queryEntityClsname, PageInfo pageInfo, String orgIds,List list);
	//带权限查询主表、字表或视图或查询条件总和
	public int getAllCountSearch(String entityClsName,String queryEntityClsname, String orgIds, boolean flag, List list);
	//带权限查询主表、字表或视图分页
	public List<T> getPageDataMapHeadOrList(String entityClsName,String queryEntityClsname, PageInfo pageInfo, String orgIds);
	//带权限查询主表、字表或视图总和
	public int getAllCount(String entityClsName, String queryEntityClsname,String orgIds, boolean flag);
	//不带权限查询主表、字表或视图或查询条件分页
	public List<T> getPageDataMapNotOrgSearch(String entityClsName,String queryEntityClsname, PageInfo pageInfo, List list);
	//不带权限查询主表、字表或视图或查询条件总和
	public int getAllCountNotOrgSearch(String entityClsName,String queryEntityClsname, boolean b, List list);
	//不带权限查询主表、字表或视图分页
	public List<T> getPageDataMapNotOrg(String entityClsName,String queryEntityClsname, PageInfo pageInfo);
	//不带权限查询主表、字表或视图总和
	public int getAllCountNotOrg(String entityClsName, String queryEntityClsname,boolean b);
	
	
}
