package htos.coresys.dao.impl;

import htos.common.entity.PageInfo;
import htos.common.util.StringUtil;
import htos.coresys.dao.CommonDao;
import htos.coresys.entity.User;
import htos.coresys.util.CommonUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;


public class CommonDaoImpl<T> extends BaseDaoImpl<T> implements CommonDao<T>{
	@Override
	public List<T> loadAllList(String entityClsName) {
		String hql = "from " + entityClsName;
		return super.find(hql);
	}

	@Override
	public int getAllDataCount(String entityClsName, String docIds) {
		StringBuffer hql = new StringBuffer("select count(*) from ");
		hql.append(entityClsName);
		if(CommonUtil.isNullOrEmpty(docIds)){
			return 0;
		}else{
			hql.append(" where uuid in ("+docIds+")");
		}
		return Integer.parseInt(super.findUnique(hql.toString()).toString());
	}


	@Override
	public List<T> getPageDataList(String entityClsName, PageInfo pageInfo, String docIds) {
		StringBuffer hql = new StringBuffer("from ");
		hql.append(entityClsName);
		if(CommonUtil.isNullOrEmpty(docIds)){
			return null;
		}else{
			hql.append(" where uuid in ("+docIds+")");
		}
		
		if(CommonUtil.isNullOrEmpty(pageInfo)){
			return super.find(hql.toString(), new String[] {},0, 0);
		}else{
			if (CommonUtil.isNullOrEmpty(pageInfo.getSortField())) {
			} else {
				hql.append(" order by ");
				hql.append(pageInfo.getSortField());
			}
			if (CommonUtil.isNullOrEmpty(pageInfo.getSortOrder())) {
			} else {
				hql.append(" ");
				hql.append(pageInfo.getSortOrder());
			}
		}
		return super.find(hql.toString(), new String[] {}, pageInfo.getpageIndex(), pageInfo.getPageSize());
		
	}

	@Override
	public T getEntityByID(String entityClsName, String uuid) {
		String hql = "from " + entityClsName+" where uuid='"+uuid+"'";
		return super.get(hql, new String[]{});
	}
	
	@Override
	public T getEntityByProperty(String entityClsName, String property,String value) {
		String hql = "from " + entityClsName+" where "+property+"='"+value+"'";
		return super.get(hql, new String[]{});
	}

	@Override
	public List<T> getPageDataMapHeadOrListSearch(String entityClsName, PageInfo pageInfo,String orgIds,List list) {
		//通过子查询关联数据查询表头或表体数据
		StringBuffer hql = new StringBuffer("from ");
		hql.append(entityClsName+" a");
		
		if(CommonUtil.isNullOrEmpty(orgIds)){
			return null;
		}else{
			String[] rightIds=orgIds.split("\\^HT\\^");
			String rid = "'"+rightIds[0].replace(",", "','")+"'";
			String subOrgIds="";
			if(orgIds.indexOf("^HT^")>-1){
				subOrgIds+=" or r.createDeptId in ('"+rightIds[1]+"')";
			}
			String linkOrgIds=" (r.orgId in ("+ rid +") "+subOrgIds+")  and ";
			
			
			
			hql.append(" where 1=1 ");
			
			//超级管理员查看所有
			HttpServletRequest request = ServletActionContext.getRequest();
			User user=(User)request.getSession().getAttribute("user");
			if((user.getUserType()!=null && user.getUserType()==0) || orgIds.equals("-")){
				linkOrgIds="";
			}else{
				hql.append(" and uuid in ('"+getIds(linkOrgIds,entityClsName)+"')");
			}
			hql.append(getSearch(list));
		}
		if(CommonUtil.isNullOrEmpty(pageInfo)){
			return super.find(hql.toString(), new String[] {},0, 0);
		}else{
			if (CommonUtil.isNullOrEmpty(pageInfo.getSortField())) {
			} else {
				hql.append(" order by ");
				hql.append(pageInfo.getSortField());
			}
			if (CommonUtil.isNullOrEmpty(pageInfo.getSortOrder())) {
			} else {
				hql.append(" ");
				hql.append(pageInfo.getSortOrder());
			}
		}
		return super.find(hql.toString(), new String[] {}, pageInfo.getpageIndex(), pageInfo.getPageSize());
	}
	
	@Override
	public List<T> getPageDataMapHeadOrList(String entityClsName, PageInfo pageInfo,String orgIds) {
		//通过子查询关联数据查询表头或表体数据
		StringBuffer hql = new StringBuffer("from ");
		hql.append(entityClsName+" a");
		
		if(CommonUtil.isNullOrEmpty(orgIds)){
			return null;
		}else{
			String[] rightIds=orgIds.split("\\^HT\\^");
			String rid = "'"+rightIds[0].replace(",", "','")+"'";
			String subOrgIds="";
			if(orgIds.indexOf("^HT^")>-1){
				subOrgIds+=" or r.createDeptId in ('"+rightIds[1]+"')";
			}
			String linkOrgIds=" (r.orgId in ("+ rid +") "+subOrgIds+")  and ";
			hql.append(" where 1=1 ");
			
			//超级管理员查看所有
			HttpServletRequest request = ServletActionContext.getRequest();
			User user=(User)request.getSession().getAttribute("user");
			if((user.getUserType()!=null && user.getUserType()==0) || orgIds.equals("-")){
				linkOrgIds="";
			}else{
				hql.append(" and uuid in ('"+getIds(linkOrgIds,entityClsName)+"')");
			}
		}
		if(CommonUtil.isNullOrEmpty(pageInfo)){
			return super.find(hql.toString(), new String[] {},0, 0);
		}else{
			if (CommonUtil.isNullOrEmpty(pageInfo.getSortField())) {
			} else {
				hql.append(" order by ");
				hql.append(pageInfo.getSortField());
			}
			if (CommonUtil.isNullOrEmpty(pageInfo.getSortOrder())) {
			} else {
				hql.append(" ");
				hql.append(pageInfo.getSortOrder());
			}
		}
		return super.find(hql.toString(), new String[] {}, pageInfo.getpageIndex(), pageInfo.getPageSize());
	}

	@Override
	public List<T> loadMapListForPageHeadList(String entityClsName,PageInfo pageInfo, String orgIds) {
			//通过子查询关联数据查询表头和表体数据数据
			StringBuffer hql = new StringBuffer("from ");
			hql.append(entityClsName+" a");
			
			if(CommonUtil.isNullOrEmpty(orgIds)){
				return null;
			}else{
				String[] rightIds=orgIds.split("\\^HT\\^");
				String rid = "'"+rightIds[0].replace(",", "','")+"'";
				String subOrgIds="";
				if(orgIds.indexOf("^HT^")>-1){
					subOrgIds+=" or r.createDeptId in ('"+rightIds[1]+"')";
				}
				String linkOrgIds=" (r.orgId in ("+ rid +") "+subOrgIds+")  and ";
				hql.append(" where 1=1 ");
				
				//超级管理员查看所有
				HttpServletRequest request = ServletActionContext.getRequest();
				User user=(User)request.getSession().getAttribute("user");
				if((user.getUserType()!=null && user.getUserType()==0) || orgIds.equals("-")){
					linkOrgIds="";
				}else{
					hql.append(" and uuid in ('"+getIds(linkOrgIds,entityClsName)+"')");
				}
				hql.append("and  EXISTS (select 1 from "+entityClsName+"List p where a.uuid = p.pid)");
			}
			
			if(CommonUtil.isNullOrEmpty(pageInfo)){
				return super.find(hql.toString(), new String[] {},0, 0);
			}else{
				if (CommonUtil.isNullOrEmpty(pageInfo.getSortField())) {
				} else {
					hql.append(" order by ");
					hql.append(pageInfo.getSortField());
				}
				if (CommonUtil.isNullOrEmpty(pageInfo.getSortOrder())) {
				} else {
					hql.append(" ");
					hql.append(pageInfo.getSortOrder());
				}
			}
			return super.find(hql.toString(), new String[] {}, pageInfo.getpageIndex(), pageInfo.getPageSize());
		}
	
	@Override
	public List<T> loadMapListForPageHeadListSearch(String entityClsName,PageInfo pageInfo, String orgIds,List list) {
			//通过子查询关联数据查询表头和表体数据数据
			StringBuffer hql = new StringBuffer("from ");
			hql.append(entityClsName+" a");
			
			if(CommonUtil.isNullOrEmpty(orgIds)){
				return null;
			}else{
				String[] rightIds=orgIds.split("\\^HT\\^");
				String rid = "'"+rightIds[0].replace(",", "','")+"'";
				String subOrgIds="";
				if(orgIds.indexOf("^HT^")>-1){
					subOrgIds+=" or r.createDeptId in ('"+rightIds[1]+"')";
				}
				String linkOrgIds=" (r.orgId in ("+ rid +") "+subOrgIds+")  and ";
				hql.append(" where 1=1 ");
				
				//超级管理员查看所有
				HttpServletRequest request = ServletActionContext.getRequest();
				User user=(User)request.getSession().getAttribute("user");
				if((user.getUserType()!=null && user.getUserType()==0) || orgIds.equals("-")){
					linkOrgIds="";
				}else{
					hql.append(" and uuid in ('"+getIds(linkOrgIds,entityClsName)+"')");
				}
				hql.append("and  EXISTS (select 1 from "+entityClsName+"List p where a.uuid = p.pid)");
				hql.append(getSearch(list));
			}
			
			if(CommonUtil.isNullOrEmpty(pageInfo)){
				return super.find(hql.toString(), new String[] {},0, 0);
			}else{
				if (CommonUtil.isNullOrEmpty(pageInfo.getSortField())) {
				} else {
					hql.append(" order by ");
					hql.append(pageInfo.getSortField());
				}
				if (CommonUtil.isNullOrEmpty(pageInfo.getSortOrder())) {
				} else {
					hql.append(" ");
					hql.append(pageInfo.getSortOrder());
				}
			}
			return super.find(hql.toString(), new String[] {}, pageInfo.getpageIndex(), pageInfo.getPageSize());
		}
	
		@Override
		public int getAllCountSearch(String entityClsName, String orgIds,boolean flag,List list) {//判断查询表头还是表体
			StringBuffer hql = new StringBuffer("select count(*) from ");
			hql.append(entityClsName+" a");
			if(CommonUtil.isNullOrEmpty(orgIds)){
				return 0;
			}else{
				String[] rightIds=orgIds.split("\\^HT\\^");
				String rid = "'"+rightIds[0].replace(",", "','")+"'";
				String subOrgIds="";
				if(orgIds.indexOf("^HT^")>-1){
					subOrgIds+=" or r.createDeptId in ('"+rightIds[1]+"')";
				}
				String linkOrgIds=" (r.orgId in ("+ rid +") "+subOrgIds+")  and ";
				hql.append(" where 1=1 ");
				//超级管理员查看所有
				HttpServletRequest request = ServletActionContext.getRequest();
				User user=(User)request.getSession().getAttribute("user");
				if((user.getUserType()!=null && user.getUserType()==0) || orgIds.equals("-")){
					linkOrgIds="";
				}else{
					hql.append(" and uuid in ('"+getIds(linkOrgIds,entityClsName)+"')");
				}
				
				if(flag){
					hql.append(getSearch(list));
				}else{
					hql.append("and  EXISTS (select 1 from "+entityClsName+"List p where a.uuid = p.pid)");
					hql.append(getSearch(list));
				}
			}
			return Integer.parseInt(super.findUnique(hql.toString()).toString());
		}
		
		@Override
		public int getAllCount(String entityClsName, String orgIds,boolean flag) {//判断查询表头还是表体
			StringBuffer hql = new StringBuffer("select count(*) from ");
			hql.append(entityClsName+" a");
			if(CommonUtil.isNullOrEmpty(orgIds)){
				return 0;
			}else{
				String[] rightIds=orgIds.split("\\^HT\\^");
				String rid = "'"+rightIds[0].replace(",", "','")+"'";
				String subOrgIds="";
				if(orgIds.indexOf("^HT^")>-1){
					subOrgIds+=" or r.createDeptId in ('"+rightIds[1]+"')";
				}
				String linkOrgIds=" (r.orgId in ("+ rid +") "+subOrgIds+")  and ";
				hql.append(" where 1=1 ");
				//超级管理员查看所有
				HttpServletRequest request = ServletActionContext.getRequest();
				User user=(User)request.getSession().getAttribute("user");
				if((user.getUserType()!=null && user.getUserType()==0) || orgIds.equals("-")){
					linkOrgIds="";
				}else{
					hql.append(" and uuid in ('"+getIds(linkOrgIds,entityClsName)+"')");
				}
				
				if(flag){
				}else{
					hql.append("and  EXISTS (select 1 from "+entityClsName+"List p where a.uuid = p.pid)");
				}
			}
			return Integer.parseInt(super.findUnique(hql.toString()).toString());
		}
		
		//查询视图总和
		@Override
		public int getAllCountView(String viewName,String userPerEname,boolean flag) {
			if(CommonUtil.isNullOrEmpty(userPerEname)){
				return 0;
			}
			String hql = "select count(*) from "+viewName+"";
			if(flag){
				hql+=" where createBy = '"+userPerEname+"' ";//用户名查询视图
			}
			return  Integer.parseInt(super.findUnique(hql.toString()).toString());
		}
		
		//表名查询分页
		@Override
		public List<T> loadMapListForPageView(String viewName, PageInfo pageInfo,String userPerEname,boolean flag) {
			if(CommonUtil.isNullOrEmpty(userPerEname)){
				return null;
			}
			StringBuffer sql = new StringBuffer("from ");
			sql.append(viewName);
			if(flag){
				sql.append(" where createBy='"+userPerEname+"'");//用户名查询视图
			}
			if(CommonUtil.isNullOrEmpty(pageInfo)){
				return super.find(sql.toString(),new String[] {}, 0, 0);
			}else{
				if (CommonUtil.isNullOrEmpty(pageInfo.getSortField())) {
				} else {
					sql.append(" order by ");
					sql.append(pageInfo.getSortField());
				}
				if (CommonUtil.isNullOrEmpty(pageInfo.getSortOrder())) {
				} else {
					sql.append(" ");
					sql.append(pageInfo.getSortOrder());
				}
			}
			
			return super.find(sql.toString(), new String[] {}, pageInfo.getpageIndex(), pageInfo.getPageSize());
		}
		
		//表名查询不分页
		@Override
		public List<T> loadListView(String viewName,String userPerEname,boolean flag) {
			if(CommonUtil.isNullOrEmpty(userPerEname)){
				return null;
			}
			String hql = " from "+viewName+"";
			if(flag){
				hql+=" where createBy = '"+userPerEname+"' ";//用户名查询视图
			}
			return super.find(hql);
		}
		
		//查询表体总记录
		@Override
		public int getAllCountList(String entityClsName, String orgIds) {
			StringBuffer hql = new StringBuffer("select count(*) from ");
			hql.append(entityClsName+"List a");
			if(CommonUtil.isNullOrEmpty(orgIds)){
				return 0;
			}else{
				String[] rightIds=orgIds.split("\\^HT\\^");
				String rid = "'"+rightIds[0].replace(",", "','")+"'";
				String subOrgIds="";
				if(orgIds.indexOf("^HT^")>-1){
					subOrgIds+=" or r.createDeptId in ('"+rightIds[1]+"')";
				}
				String linkOrgIds=" (r.orgId in ("+ rid +") "+subOrgIds+")  and ";
				hql.append(" where 1=1 ");
				//超级管理员查看所有
				HttpServletRequest request = ServletActionContext.getRequest();
				User user=(User)request.getSession().getAttribute("user");
				if((user.getUserType()!=null && user.getUserType()==0) || orgIds.equals("-")){
					linkOrgIds="";
				}else{
					hql.append(" and uuid in ('"+getIds(linkOrgIds,entityClsName)+"')");
				}
			}
			return Integer.parseInt(super.findUnique(hql.toString()).toString());
		}
		
		//查询表体分页
		@Override
		public List<T> loadMapListForPageList(String entityClsName,PageInfo pageInfo, String orgIds) {
				//通过子查询表体数据
				StringBuffer hql = new StringBuffer("from ");
				hql.append(entityClsName+"List a");
				if(CommonUtil.isNullOrEmpty(orgIds)){
					return null;
				}else{
					String[] rightIds=orgIds.split("\\^HT\\^");
					String rid = "'"+rightIds[0].replace(",", "','")+"'";
					String subOrgIds="";
					if(orgIds.indexOf("^HT^")>-1){
						subOrgIds+=" or r.createDeptId in ('"+rightIds[1]+"')";
					}
					String linkOrgIds=" (r.orgId in ("+ rid +") "+subOrgIds+")  and ";
					hql.append(" where 1=1 ");
					//超级管理员查看所有
					HttpServletRequest request = ServletActionContext.getRequest();
					User user=(User)request.getSession().getAttribute("user");
					if((user.getUserType()!=null && user.getUserType()==0) || orgIds.equals("-")){
						linkOrgIds="";
					}else{
						hql.append(" and uuid in ('"+getIds(linkOrgIds,entityClsName)+"')");
					}
				}
				
				if(CommonUtil.isNullOrEmpty(pageInfo)){
					return super.find(hql.toString(), new String[] {},0, 0);
				}else{
					if (CommonUtil.isNullOrEmpty(pageInfo.getSortField())) {
					} else {
						hql.append(" order by ");
						hql.append(pageInfo.getSortField());
					}
					if (CommonUtil.isNullOrEmpty(pageInfo.getSortOrder())) {
					} else {
						hql.append(" ");
						hql.append(pageInfo.getSortOrder());
					}
				}
				return super.find(hql.toString(), new String[] {}, pageInfo.getpageIndex(), pageInfo.getPageSize());
			}
		
		//查询表体不分页
		@Override
		public List<T> loadListForList(String entityClsName,String orgIds) {
				//通过子查询表体数据
				StringBuffer hql = new StringBuffer("from ");
				hql.append(entityClsName+"List a");
				if(CommonUtil.isNullOrEmpty(orgIds)){
					return null;
				}else{
					String[] rightIds=orgIds.split("\\^HT\\^");
					String rid = "'"+rightIds[0].replace(",", "','")+"'";
					String subOrgIds="";
					if(orgIds.indexOf("^HT^")>-1){
						subOrgIds+=" or r.createDeptId in ('"+rightIds[1]+"')";
					}
					String linkOrgIds=" (r.orgId in ("+ rid +") "+subOrgIds+")  and ";
					hql.append(" where 1=1 ");
					//超级管理员查看所有
					HttpServletRequest request = ServletActionContext.getRequest();
					User user=(User)request.getSession().getAttribute("user");
					if((user.getUserType()!=null && user.getUserType()==0) || orgIds.equals("-")){
						linkOrgIds="";
					}else{
						hql.append(" and uuid in ('"+getIds(linkOrgIds,entityClsName)+"')");
					}
					hql.append(" and  EXISTS (SELECT 1 FROM "+entityClsName+" b WHERE a.pid=b.uuid ");
				}
				return super.find(hql.toString());
			}
		
		//根据表头id查询表体不分页
		@Override
		public List<T> loadList(String entityClsName,String pid) {
				//通过子查询表体数据
				StringBuffer hql = new StringBuffer("from ");
				hql.append(entityClsName);
				if(CommonUtil.isNullOrEmpty(pid)){
					return null;
				}else{
					hql.append(" where 1=1 and  pid='"+pid+"' ");
				}
				return super.find(hql.toString());
			}

		/* (non-Javadoc)
		 * @see htos.coresys.dao.CommonDao#getPageDataList(java.lang.String, htos.common.entity.PageInfo, java.lang.String, java.util.Map)
		 */
		@Override
		public List<T> getPageDataList(String entityClsName, PageInfo pageInfo,
				String docIds, List list) {
			StringBuffer hql = new StringBuffer("from ");
			hql.append(entityClsName);
			if(CommonUtil.isNullOrEmpty(docIds)){
				return null;
			}else{
				hql.append(" where uuid in ("+docIds+")");
			}
			if(list!=null && list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					Map map=(Map)list.get(i);
					if(map.get("dataType").toString().equals("text")){
						if(map.get("operator").toString().equals("@")){
							hql.append(" and "+map.get("name")+" like '%"+map.get("value")+"%'");
						}else if(map.get("operator").toString().equals("=")){
							hql.append(" and "+map.get("name")+"='"+map.get("value")+"'");
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
			
			if(CommonUtil.isNullOrEmpty(pageInfo)){
				return super.find(hql.toString(), new String[] {},0, 0);
			}else{
				if (CommonUtil.isNullOrEmpty(pageInfo.getSortField())) {
				} else {
					hql.append(" order by ");
					hql.append(pageInfo.getSortField());
				}
				if (CommonUtil.isNullOrEmpty(pageInfo.getSortOrder())) {
				} else {
					hql.append(" ");
					hql.append(pageInfo.getSortOrder());
				}
			}
			return super.find(hql.toString(), new String[] {}, pageInfo.getpageIndex(), pageInfo.getPageSize());
		}
		
		//条件查询参数
		private String getSearch(List list){
			StringBuffer hql = new StringBuffer(" ");
			String subHql="";
			if(list!=null && list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					Map map=(Map)list.get(i);
					if(!map.containsKey("dataType")){//如果不是表单头中的查询条件，则执行本段代码，否则执行下一段代码
						Set<Entry> entrySet = map.entrySet();
						for (Entry en:entrySet) {
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
		
		//根据参数查询返回list
		@Override
		public List<T> getListProperty(String entityClsName, String property,String value) {
			String hql = "from " + entityClsName+" where "+property+"='"+value+"'";
			return super.find(hql);
		}

		@Override
		public List<T> getPageDataMapHeadOrListSearch(String entityClsName,String queryEntityClsname, PageInfo pageInfo, String orgIds,List list) {
			
			//通过子查询关联数据查询表头或表体数据或视图数据
			StringBuffer hql = new StringBuffer("from ");
			if(!StringUtil.isEmpty(queryEntityClsname)){
				hql.append(queryEntityClsname+" a");
			}else{
				hql.append(entityClsName+" a");
			}
			
			if(CommonUtil.isNullOrEmpty(orgIds)){
				return null;
			}else{
				String[] rightIds=orgIds.split("\\^HT\\^");
				String rid = "'"+rightIds[0].replace(",", "','")+"'";
				String subOrgIds="";
				if(orgIds.indexOf("^HT^")>-1){
					subOrgIds+=" or r.createDeptId in ('"+rightIds[1]+"')";
				}
				String linkOrgIds=" (r.orgId in ("+ rid +") "+subOrgIds+")  and ";
				hql.append(" where 1=1 ");
				//超级管理员查看所有
				HttpServletRequest request = ServletActionContext.getRequest();
				User user=(User)request.getSession().getAttribute("user");
				if((user.getUserType()!=null && user.getUserType()==0) || orgIds.equals("-")){
					linkOrgIds="";
				}else{
					hql.append(" and uuid in ('"+getIds(linkOrgIds,entityClsName)+"')");
				}
				//hql.append(" and  EXISTS (select 1 from Right r where "+linkOrgIds+" r.modoName = '"+entityClsName+"')");
				hql.append(getSearch(list));
			}
			if(CommonUtil.isNullOrEmpty(pageInfo)){
				return super.find(hql.toString(), new String[] {},0, 0);
			}else{
				if (CommonUtil.isNullOrEmpty(pageInfo.getSortField())) {
				} else {
					hql.append(" order by ");
					hql.append(pageInfo.getSortField());
				}
				if (CommonUtil.isNullOrEmpty(pageInfo.getSortOrder())) {
				} else {
					hql.append(" ");
					hql.append(pageInfo.getSortOrder());
				}
			}
			return super.find(hql.toString(), new String[] {}, pageInfo.getpageIndex(), pageInfo.getPageSize());
		}

		@Override
		public int getAllCountSearch(String entityClsName,String queryEntityClsname, String orgIds, boolean flag,List list) {
			StringBuffer hql = new StringBuffer("select count(*) from ");
			if(!StringUtil.isEmpty(queryEntityClsname)){
				hql.append(queryEntityClsname+" a");
			}else{
				hql.append(entityClsName+" a");
			}
			if(CommonUtil.isNullOrEmpty(orgIds)){
				return 0;
			}else{
				String[] rightIds=orgIds.split("\\^HT\\^");
				String rid = "'"+rightIds[0].replace(",", "','")+"'";
				String subOrgIds="";
				if(orgIds.indexOf("^HT^")>-1){
					subOrgIds+=" or r.createDeptId in ('"+rightIds[1]+"')";
				}
				String linkOrgIds=" (r.orgId in ("+ rid +") "+subOrgIds+")  and ";
				hql.append(" where 1=1 ");
				//超级管理员查看所有
				HttpServletRequest request = ServletActionContext.getRequest();
				User user=(User)request.getSession().getAttribute("user");
				if((user.getUserType()!=null && user.getUserType()==0) || orgIds.equals("-")){
					linkOrgIds="";
				}else{
					hql.append(" and uuid in ('"+getIds(linkOrgIds,entityClsName)+"')");
				}
				/*
				if(flag){
					hql.append(" where 1=1 and  EXISTS (select 1 from Right r where "+linkOrgIds+" r.modoName = '"+entityClsName+"')");
					hql.append(getSearch(list));
				}else{
					hql.append(" where 1=1 and  EXISTS (select 1 from Right r where "+linkOrgIds+" r.modoName = '"+entityClsName+"')");
					hql.append("and  EXISTS (select 1 from "+entityClsName+"List p where a.uuid = p.pid)");
					hql.append(getSearch(list));
				}
				*/
				
				if(flag){
					hql.append(getSearch(list));
				}else{
					hql.append("and  EXISTS (select 1 from "+entityClsName+"List p where a.uuid = p.pid)");
					hql.append(getSearch(list));
				}
			}
			return Integer.parseInt(super.findUnique(hql.toString()).toString());
		}
		
		private String getIds(String linkOrgIds,String entityClsName){
			String ids="";
			List<String> list=(List<String>) super.find("select docId from Right r where "+linkOrgIds+" r.modoName = '"+entityClsName+"'");
			Set<String> setList=new HashSet<String>();
			setList.addAll(list);
			list.clear();
			/*
			for(String s:list){
				if(!ids.equals("")){
					ids+="','";
				}
				ids+=s;
			}*/
			ids=StringUtils.join(setList,"','");
			list=null;
			setList=null;
			return ids;
		}

		@Override
		public List<T> getPageDataMapHeadOrList(String entityClsName,String queryEntityClsname, PageInfo pageInfo, String orgIds) {
			//通过子查询关联数据查询表头或表体数据
			StringBuffer hql = new StringBuffer("from ");
			if(!StringUtil.isEmpty(queryEntityClsname)){
				hql.append(queryEntityClsname+" a");
			}else{
				hql.append(entityClsName+" a");
			}
			if(CommonUtil.isNullOrEmpty(orgIds)){
				return null;
			}else{
				String[] rightIds=orgIds.split("\\^HT\\^");
				String rid = "'"+rightIds[0].replace(",", "','")+"'";
				String subOrgIds="";
				if(orgIds.indexOf("^HT^")>-1){
					subOrgIds+=" or r.createDeptId in ('"+rightIds[1]+"')";
				}
				String linkOrgIds=" (r.orgId in ("+ rid +") "+subOrgIds+")  and ";
				hql.append(" where 1=1 ");
				//超级管理员查看所有
				HttpServletRequest request = ServletActionContext.getRequest();
				User user=(User)request.getSession().getAttribute("user");
				if((user.getUserType()!=null && user.getUserType()==0) || orgIds.equals("-")){
					linkOrgIds="";
				}else{
					hql.append(" and uuid in ('"+getIds(linkOrgIds,entityClsName)+"')");
				}
			}
			if(CommonUtil.isNullOrEmpty(pageInfo)){
				return super.find(hql.toString(), new String[] {},0, 0);
			}else{
				if (CommonUtil.isNullOrEmpty(pageInfo.getSortField())) {
				} else {
					hql.append(" order by ");
					hql.append(pageInfo.getSortField());
				}
				if (CommonUtil.isNullOrEmpty(pageInfo.getSortOrder())) {
				} else {
					hql.append(" ");
					hql.append(pageInfo.getSortOrder());
				}
			}
			return super.find(hql.toString(), new String[] {}, pageInfo.getpageIndex(), pageInfo.getPageSize());
		
		}

		@Override
		public int getAllCount(String entityClsName, String queryEntityClsname,String orgIds, boolean flag) {
			StringBuffer hql = new StringBuffer("select count(*) from ");
			if(!StringUtil.isEmpty(queryEntityClsname)){
				hql.append(queryEntityClsname+" a");
			}else{
				hql.append(entityClsName+" a");
			}
			if(CommonUtil.isNullOrEmpty(orgIds)){
				return 0;
			}else{
				String[] rightIds=orgIds.split("\\^HT\\^");
				String rid = "'"+rightIds[0].replace(",", "','")+"'";
				String subOrgIds="";
				if(orgIds.indexOf("^HT^")>-1){
					subOrgIds+=" or r.createDeptId in ('"+rightIds[1]+"')";
				}
				String linkOrgIds=" (r.orgId in ("+ rid +") "+subOrgIds+")  and ";
				hql.append(" where 1=1 ");
				//超级管理员查看所有
				HttpServletRequest request = ServletActionContext.getRequest();
				User user=(User)request.getSession().getAttribute("user");
				if((user.getUserType()!=null && user.getUserType()==0) || orgIds.equals("-")){
					linkOrgIds="";
				}else{
					hql.append(" and uuid in ('"+getIds(linkOrgIds,entityClsName)+"')");
				}
				
				if(flag){
				}else{
					hql.append("and  EXISTS (select 1 from "+entityClsName+"List p where a.uuid = p.pid)");
				}
			}
			return Integer.parseInt(super.findUnique(hql.toString()).toString());
		}

		@Override
		public T getEntityByNotProperty(String entityClsName, String property,String value) {
			String hql = "from " + entityClsName+" where "+property+"!='"+value+"'";
			return super.get(hql, new String[]{});
		}

		@Override
		public T getEntityByLikeProperty(String entityClsName, String property,String value) {
			String hql = "from " + entityClsName+" where "+property+" like '%"+value+"%'";
			return super.get(hql, new String[]{});
		}

		@Override
		public List<T> getListByNotProperty(String entityClsName,String property, String value) {
			return super.find(" from " + entityClsName + " where " + property+" !='" + value + "'");
		}

		@Override
		public List<T> getListByLikeProperty(String entityClsName,String property, String value) {
			return super.find(" from " + entityClsName + " where " + property+" like '%" + value + "%'");
		}

		@Override
		public List<T> loadListOrgIds(String entityClsName, String orgIds) {
			if(!StringUtil.isEmpty(orgIds)){//走权限
				String hql = "from " + entityClsName+" a where 1=1 "+getOrgIdsSql(orgIds, entityClsName);
				return super.find(hql);
			}
			return this.loadAllList(entityClsName);//不走权限
		}

		@Override
		public List<T> getListByPropertyOrgIds(String entityClsName,String property, String value, String orgIds) {
			if(!StringUtil.isEmpty(orgIds)){//走权限
				String hql = "from " + entityClsName+" a where " + property+ "='" + value + "' "+getOrgIdsSql(orgIds, entityClsName);
				return super.find(hql);
			}
			return super.find(" from " + entityClsName + " where " + property+ "='" + value + "'");//不走权限
		}

		@Override
		public List<T> getListPropertyOrgIds(String entityClsName,String property, String value, String orgIds) {
			if(!StringUtil.isEmpty(orgIds)){//走权限
				String hql = "from " + entityClsName+" a where " + property+ "= '" + value + "' "+getOrgIdsSql(orgIds, entityClsName);
				return super.find(hql);
			}
			return this.getListProperty(entityClsName, property, value);//不走权限
		}

		@Override
		public List<T> getListByNotPropertyOrgIds(String entityClsName,String property, String value, String orgIds) {
			if(!StringUtil.isEmpty(orgIds)){//走权限
				String hql = "from " + entityClsName+" a where " + property+ " != '" + value + "' "+getOrgIdsSql(orgIds, entityClsName);
				return super.find(hql);
			}
			return this.getListByNotProperty(entityClsName, property, value);//不走权限
		}

		@Override
		public List<T> getListByLikePropertyOrgIds(String entityClsName,String property, String value, String orgIds) {
			if(!StringUtil.isEmpty(orgIds)){//走权限
				String hql = "from " + entityClsName+" a where " + property+ " like '%" + value + "%' "+getOrgIdsSql(orgIds, entityClsName);
				return super.find(hql);
			}
			return this.getListByLikeProperty(entityClsName, property, value);//不走权限
		}

		@Override
		public T getEntityByIDOrgIds(String entityClsName, String uuid,String orgIds) {
			if(!StringUtil.isEmpty(orgIds)){//走权限
				String hql = "from " + entityClsName+" a where uuid='" + uuid + "' "+getOrgIdsSql(orgIds, entityClsName);
				return super.get(hql, new String[]{});
			}
			return this.getEntityByID(entityClsName, uuid);//不走权限
		}

		@Override
		public T getEntityByPropertyOrgIds(String entityClsName,String property, String value, String orgIds) {
			if(!StringUtil.isEmpty(orgIds)){//走权限
				String hql = "from " + entityClsName+" a where " + property+ "= '" + value + "' "+getOrgIdsSql(orgIds, entityClsName);
				return super.get(hql, new String[]{});
			}
			return this.getEntityByProperty(entityClsName, property, value);//不走权限
		}

		@Override
		public T getEntityByNotPropertyOrgIds(String entityClsName,String property, String value, String orgIds) {
			if(!StringUtil.isEmpty(orgIds)){//走权限
				String hql = "from " + entityClsName+" a where " + property+ " != '" + value + "' "+getOrgIdsSql(orgIds, entityClsName);
				return super.get(hql, new String[]{});
			}
			return this.getEntityByNotProperty(entityClsName, property, value);//不走权限
		}

		@Override
		public T getEntityByLikePropertyOrgIds(String entityClsName,String property, String value, String orgIds) {
			if(!StringUtil.isEmpty(orgIds)){//走权限
				String hql = "from " + entityClsName+" a where " + property+ " like '%" + value + "%' "+getOrgIdsSql(orgIds, entityClsName);
				return super.get(hql, new String[]{});
			}
			return this.getEntityByLikeProperty(entityClsName, property, value);//不走权限
		}
		
		//权限关联查询
		private String getOrgIdsSql(String orgIds,String entityClsName){
			StringBuffer hql = new StringBuffer(" ");
			String[] rightIds=orgIds.split("\\^HT\\^");
			String rid = "'"+rightIds[0].replace(",", "','")+"'";
			String subOrgIds="";
			if(orgIds.indexOf("^HT^")>-1){
				subOrgIds+=" or r.createDeptId in ('"+rightIds[1]+"')";
			}
			String linkOrgIds=" (r.orgId in ("+ rid +") "+subOrgIds+")  and ";
			//超级管理员查看所有
			HttpServletRequest request = ServletActionContext.getRequest();
			User user=(User)request.getSession().getAttribute("user");
			if((user.getUserType()!=null && user.getUserType()==0) || orgIds.equals("-")){
				linkOrgIds="";
			}
			
			hql.append(" and uuid in ('"+getIds(linkOrgIds,entityClsName)+"')");
			return hql.toString();
		}

		@Override
		public List<T> getHqlList(String hql) {
			return super.find(hql);
		}

		@Override
		public T getHqlObject(String hql) {
			return super.get(hql, new String[]{});
		}

		@Override
		public List<T> getPageDataMapNotOrgSearch(String entityClsName,String queryEntityClsname, PageInfo pageInfo, List list) {
			//通过子查询关联数据查询表头或表体数据或视图数据
			StringBuffer hql = new StringBuffer("from ");
			if(!StringUtil.isEmpty(queryEntityClsname)){
				hql.append(queryEntityClsname+" a");
			}else{
				hql.append(entityClsName+" a");
			}
			
			hql.append(" where 1=1 "+getSearch(list));
			if(CommonUtil.isNullOrEmpty(pageInfo)){
				return super.find(hql.toString(), new String[] {},0, 0);
			}else{
				if (CommonUtil.isNullOrEmpty(pageInfo.getSortField())) {
				} else {
					hql.append(" order by ");
					hql.append(pageInfo.getSortField());
				}
				if (CommonUtil.isNullOrEmpty(pageInfo.getSortOrder())) {
				} else {
					hql.append(" ");
					hql.append(pageInfo.getSortOrder());
				}
			}
			return super.find(hql.toString(), new String[] {}, pageInfo.getpageIndex(), pageInfo.getPageSize());
		}

		@Override
		public int getAllCountNotOrgSearch(String entityClsName,String queryEntityClsname, boolean flag, List list) {
			StringBuffer hql = new StringBuffer("select count(*) from ");
			if(!StringUtil.isEmpty(queryEntityClsname)){
				hql.append(queryEntityClsname+" a");
			}else{
				hql.append(entityClsName+" a");
			}
				if(flag){
					hql.append(" where 1=1 "+getSearch(list));
				}else{
					hql.append(" where 1=1 and  EXISTS (select 1 from "+entityClsName+"List p where a.uuid = p.pid) "+getSearch(list));
				}
			return Integer.parseInt(super.findUnique(hql.toString()).toString());
		}

		@Override
		public List<T> getPageDataMapNotOrg(String entityClsName,String queryEntityClsname, PageInfo pageInfo) {
			//通过子查询关联数据查询表头或表体数据
			StringBuffer hql = new StringBuffer("from ");
			if(!StringUtil.isEmpty(queryEntityClsname)){
				hql.append(queryEntityClsname+" a");
			}else{
				hql.append(entityClsName+" a");
			}
				hql.append(" where 1=1 ");
			if(CommonUtil.isNullOrEmpty(pageInfo)){
				return super.find(hql.toString(), new String[] {},0, 0);
			}else{
				if (CommonUtil.isNullOrEmpty(pageInfo.getSortField())) {
				} else {
					hql.append(" order by ");
					hql.append(pageInfo.getSortField());
				}
				if (CommonUtil.isNullOrEmpty(pageInfo.getSortOrder())) {
				} else {
					hql.append(" ");
					hql.append(pageInfo.getSortOrder());
				}
			}
			return super.find(hql.toString(), new String[] {}, pageInfo.getpageIndex(), pageInfo.getPageSize());
		}

		@Override
		public int getAllCountNotOrg(String entityClsName,String queryEntityClsname, boolean flag) {
			StringBuffer hql = new StringBuffer("select count(*) from ");
			if(!StringUtil.isEmpty(queryEntityClsname)){
				hql.append(queryEntityClsname+" a");
			}else{
				hql.append(entityClsName+" a");
			}
				if(flag){
					hql.append(" where 1=1 ");
				}else{
					hql.append(" where 1=1 and  EXISTS (select 1 from "+entityClsName+"List p where a.uuid = p.pid)");
				}
			return Integer.parseInt(super.findUnique(hql.toString()).toString());
		}
	}
