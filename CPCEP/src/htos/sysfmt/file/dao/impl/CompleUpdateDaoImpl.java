package htos.sysfmt.file.dao.impl;

import htos.common.entity.PageInfo;
import htos.coresys.dao.impl.BaseDaoImpl;
import htos.coresys.util.CommonUtil;
import htos.sysfmt.file.dao.CompleUpdateDao;
import htos.sysfmt.file.entity.CompleUpdateModel;

import java.util.List;

import org.apache.commons.lang.StringUtils;

public class CompleUpdateDaoImpl extends BaseDaoImpl<CompleUpdateModel> implements CompleUpdateDao {

	@Override
	public List<CompleUpdateModel> getPageDataList(String model,PageInfo pageInfo, String docIds,CompleUpdateModel compleUpdateModel) {
		StringBuffer hql = new StringBuffer("from ");
		hql.append(model);
		
		if(CommonUtil.isNullOrEmpty(docIds)){
			return null;
		}else{
			hql.append(" where uuid in ("+docIds+")");
		}
		
		hql.append(getCondition(compleUpdateModel));
		
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
	public int getAllDataCount(String model, String docIds,CompleUpdateModel compleUpdateModel) {
		StringBuffer hql = new StringBuffer("select count(*) from ");
		hql.append(model);
		if(CommonUtil.isNullOrEmpty(docIds)){
			return 0;
		}else{
			hql.append(" where uuid in ("+docIds+")");
		}
		
		hql.append(getCondition(compleUpdateModel));
		
		return Integer.parseInt(super.findUnique(hql.toString()).toString());
	}

	private String getCondition(CompleUpdateModel compleUpdateModel){
		String condition = "";
		if(compleUpdateModel == null ) return condition;
		if(!StringUtils.isEmpty(compleUpdateModel.getPid())) condition+= " and pid='"+compleUpdateModel.getPid()+"' ";
		if(!StringUtils.isEmpty(compleUpdateModel.getFid())) condition+= " and fid='"+compleUpdateModel.getFid()+"' ";
		return condition;
	}

	@Override
	public List<CompleUpdateModel> findAllParentTaskUIDJson(String parentTaskUID) {
		String hql ="from CompleUpdateModel where parentTaskUID='"+parentTaskUID+"'";
		return super.find(hql);
	}

}
