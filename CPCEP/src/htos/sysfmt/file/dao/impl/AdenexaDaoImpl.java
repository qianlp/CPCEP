package htos.sysfmt.file.dao.impl;

import htos.common.entity.PageInfo;
import htos.common.util.StringUtil;
import htos.coresys.dao.impl.BaseDaoImpl;
import htos.coresys.entity.User;
import htos.coresys.util.CommonUtil;
import htos.sysfmt.file.dao.AdenexaDao;
import htos.sysfmt.file.entity.AdenexaModel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

public class AdenexaDaoImpl extends BaseDaoImpl<AdenexaModel> implements AdenexaDao {

	@Override
	public List<AdenexaModel> findAllAdenexaListJson(String pid) {
		String hql ="from AdenexaModel where pid='"+pid+"'";
		return super.find(hql);
	}

	@Override
	public AdenexaModel findViewAdenexaById(String uuid) {
		return super.get(AdenexaModel.class, uuid);
	}

	@Override
	public List<AdenexaModel> findAllAdenexaListJson(AdenexaModel adenexaModel) {
		String hql ="from AdenexaModel where area='"+adenexaModel.getArea()+"'";
		if(adenexaModel.getParentDocId()!=null && !adenexaModel.getParentDocId().equals("")){
			hql+=" and parentDocId like '%"+adenexaModel.getParentDocId()+"%'";
		}
		if(adenexaModel.getPrjID()!=null && !adenexaModel.getPrjID().equals("")){
			hql+=" and prjID like '%"+adenexaModel.getPrjID()+"%'";
		}
		return super.find(hql);
	}

	public List<AdenexaModel> getPageDataList(String model,PageInfo pageInfo, String orgIds,AdenexaModel adenexaModel) {
		StringBuffer hql = new StringBuffer("from ");
		hql.append(model);
		hql.append(" where 1=1 ");
		
		hql.append(getCondition(adenexaModel,orgIds));
		
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
		System.out.println("------------------------------");
		System.out.println(hql);
		return super.find(hql.toString(), new String[] {}, pageInfo.getpageIndex(), pageInfo.getPageSize());
	}

	@Override
	public int getAllDataCount(String model, String orgIds,AdenexaModel adenexaModel) {
		StringBuffer hql = new StringBuffer("select count(*) from ");
		hql.append(model);
		hql.append(" where 1=1 ");
		
		hql.append(getCondition(adenexaModel,orgIds));
		
		return Integer.parseInt(super.findUnique(hql.toString()).toString());
	}

	private String getCondition(AdenexaModel adenexaModel,String orgIds){
		String condition = "";
		String[] ids=orgIds.split(",");
		for(int k=0;k<ids.length;k++){
			if(k==0){
				condition+=" and (orgId like '%"+ids[k]+"%' ";
			}else{
				condition+=" or lookRolesId like '%"+ids[k]+"%' ";
			}
			condition+=" or defaultRoleId like '%"+ids[k]+"%' ";
			if((k+1)==ids.length){
				condition+=")";
			}
		}
		//超级管理员查看所有
		HttpServletRequest request = ServletActionContext.getRequest();
		User user=(User)request.getSession().getAttribute("user");
		if(user.getUserType()!=null && user.getUserType()==0){
			condition="";
		}
		if(adenexaModel!=null && "1".equals(adenexaModel.getType())){//如果是借阅文件目录则不需要文件全向
			condition="";
		}
		if(adenexaModel == null ) return condition;
		
		if(!StringUtils.isEmpty(adenexaModel.getPrjID())) condition+=" and prjID='"+adenexaModel.getPrjID()+"' ";
		if(adenexaModel.getVersion()!=null && !adenexaModel.getVersion().equals("")){
			if(adenexaModel.getVersion().equals("yes")){
				condition+=" and version!='' and version!=null";
			}else{
				condition+=" and (version='' or version=null)";
			}
		}
		
		//查询审核通过的数据
		if(!StringUtil.isEmpty(adenexaModel.getWfStatus())){
			condition+=" and wfStatus='"+adenexaModel.getWfStatus()+"' ";
		}
		
		if(!StringUtils.isEmpty(adenexaModel.getUuid())){
			String pid[] = adenexaModel.getUuid().split(",");
			if(!condition.equals("")){
				condition+=" and (";
			}
			String subCondition="";
			for (String strcid : pid) {
				if(!subCondition.equals("")){
					subCondition+=" or ";
				}
				subCondition+=" catalogId like '%"+strcid+"%'";
			}
			condition+=subCondition+")";
		}else{
			if(!condition.equals("")){
				condition+=" and 1=2";
			}
		}
		return condition;
	}
	@Override
	public void updateAdenexaVersion(String uuid, String version) {
		String hql = "update AdenexaModel set version='"+version+"' where uuid='"+uuid+"'";
		super.executeHql(hql);
	}
}
