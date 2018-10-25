package htos.coresys.dao.impl;

import htos.common.util.StringUtil;
import htos.coresys.dao.RightDao;
import htos.coresys.entity.Right;
import htos.coresys.entity.User;
import htos.coresys.util.CommonUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

//import com.ht.right.entity.Menu;

public class RightDaoImpl extends BaseDaoImpl<Right> implements RightDao {
	@Override
	public List<Right> getRightByOrgIds(String orgIds,String modoName){
		String hql = "";
		//超级管理员可看所有
		HttpServletRequest request = ServletActionContext.getRequest();
		User user=(User)request.getSession().getAttribute("user");
		if(user.getUserType()!=null && user.getUserType()==0){
			return super.find("from Right r where r.modoName = '"+modoName+"'");
		}
		
		//bitwise修改，仅一个orgId时，语句有误，主要是少单引号
		if(orgIds.length()==32){
			hql = "from Right r where r.orgId in ('"+ orgIds +"')";
		}else{
			hql = "from Right r where r.orgId in ("+ orgIds +")";
		}
		if(!CommonUtil.isNullOrEmpty(modoName)){
			hql+=" and r.modoName = '"+modoName+"'";
		}
		return super.find(hql);
	}
	
	@Override
	public List<Right> getRightsByDocId(Right right) {
		StringBuffer hql = new StringBuffer("from Right r where r.docId = '");
		hql.append(right.getDocId());
		hql.append("' ");
		if(!CommonUtil.isNullOrEmpty(right.getModoName())){
			hql.append(" and r.modoName = '");
			hql.append(right.getModoName());
			hql.append("'");
		}
		return super.find(hql.toString());
	}
	@Override
	public List<Right> getRightsByDocId(String docId, String modoName) {
		StringBuffer hql = new StringBuffer("from Right r where r.docId = '");
		hql.append(docId);
		hql.append("' ");
		if(!CommonUtil.isNullOrEmpty(modoName)){
			hql.append(" and r.modoName = '");
			hql.append(modoName);
			hql.append("'");
		}
		return super.find(hql.toString());
	}
	/**
	* 获取菜单权限
	* @param 
	* 	orgId：组织或角色对应的唯一标识
	* 	docIds：需要分配地菜单的唯一标识

	* @return
	* 	符合条件的菜单或按钮列表
	* @exception
	* 	
	* @author bitwise
	* @Time 2016-06-11
	**/
	@Override
	public List<Right> getRightByOrgIdAndDocId(String orgId,String docIds){
		String hql = "from Right r where r.orgId in ('"+ orgId +"') and r.docId in (" + docIds +")";
		hql+=" and (r.modoName = 'Menu' or r.modoName = 'MenuToBtn')";
		return super.find(hql);
	}

	/* (non-Javadoc)
	 * @see htos.coresys.dao.RightDao#getRightsByProprty(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Right> getRightsByProprty(String modoName, String orgIds,
			String docId) {
		String hql = "from Right r where r.orgId='"+orgIds+"'";
		if(!CommonUtil.isNullOrEmpty(modoName)){
			hql+=" and r.modoName = '"+modoName+"'";
		}
		if(!CommonUtil.isNullOrEmpty(docId)){
			hql+=" and r.docId = '"+docId+"'";
		}
		
		return super.find(hql);
	}

	@Override
	public List<Right> getRightsList(Right right) {
		String hql = "from Right r where 1=1 "+getContent(right);
		return super.find(hql);
	}
	
	private String getContent(Right right){
		String content="";
		if(right==null) return content;
		if(!StringUtil.isEmpty(right.getDocId())){
			content+=" and r.docId = '"+right.getDocId()+"'";
		}
		if(!StringUtil.isEmpty(right.getOrgId())){
			content+=" and r.orgId = '"+right.getOrgId()+"'";
		}
		if(!StringUtil.isEmpty(right.getModoName())){
			content+=" and r.modoName = '"+right.getModoName()+"'";
		}
		if(!StringUtil.isEmpty(right.getRemark())){
			content+=" and r.remark = '"+right.getRemark()+"'";
		}
		
		return content;
	}
}