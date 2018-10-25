package htos.sysfmt.message.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import htos.common.entity.PageInfo;
import htos.coresys.dao.impl.BaseDaoImpl;
import htos.coresys.util.CommonUtil;
import htos.sysfmt.message.dao.MsgInfoDao;
import htos.sysfmt.message.entity.MsgInfo;

public class MsgInfoDaoImpl extends BaseDaoImpl<MsgInfo> implements MsgInfoDao {

	@Override
	public List<MsgInfo> getMsgList(MsgInfo msgInfo) {
		StringBuffer hql = new StringBuffer("from MsgInfo where userId='"+msgInfo.getUserId()+"'");
		
		if(!CommonUtil.isNullOrEmpty(msgInfo.getMsgTitle())){
			hql.append(" and msgTitle like '%"+msgInfo.getMsgTitle()+"%'");
		}
		if(!CommonUtil.isNullOrEmpty(msgInfo.getCreateBy())){
			hql.append(" and createBy like '%"+msgInfo.getCreateBy()+"%'");
		}
		if(!CommonUtil.isNullOrEmpty(msgInfo.getMsgType())){
			hql.append(" and msgType="+msgInfo.getMsgType());
		}
		if(!CommonUtil.isNullOrEmpty(msgInfo.getStatus())){
			hql.append(" and status="+msgInfo.getStatus());
		}else{
			hql.append(" and ((status!=2 and status!=3) or status is null)");
		}
		if(!CommonUtil.isNullOrEmpty(msgInfo.getStartTime())){
			hql.append(" and createDate like '%"+msgInfo.getStartTime()+"%' ");
		}
		if(!CommonUtil.isNullOrEmpty(msgInfo.getMenuId())){
			hql.append(" and menuId='"+msgInfo.getMenuId()+"' ");
		}
		if(!CommonUtil.isNullOrEmpty(msgInfo.getRemark())){
			hql.append(" and remark='"+msgInfo.getRemark()+"' ");
		}
		if(!CommonUtil.isNullOrEmpty(msgInfo.getDocId())){
			hql.append(" and docId='"+msgInfo.getDocId()+"' ");
		}
		hql.append(" order by createDate desc");
		return super.find(hql.toString());
	}

	/* (non-Javadoc)
	 * @see htos.sysfmt.message.dao.MsgInfoDao#getMsgList(java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<MsgInfo> getMsgList(String docId, Integer msgType) {
		StringBuffer hql = new StringBuffer("from MsgInfo where docId='"+docId+"' and msgType="+msgType.toString()+"");	
		return super.find(hql.toString());
	}

	@Override
	public List<MsgInfo> getMsgList(PageInfo pageInfo, MsgInfo msgInfo) {
		StringBuffer hql = new StringBuffer("from MsgInfo where userId='"+msgInfo.getUserId()+"'");
		if(pageInfo==null){
			hql = new StringBuffer("select count(*) from MsgInfo where userId='"+msgInfo.getUserId()+"'");
		}
		if(!CommonUtil.isNullOrEmpty(msgInfo.getMsgTitle())){
			hql.append(" and msgTitle like '%"+msgInfo.getMsgTitle()+"%'");
		}
		if(!CommonUtil.isNullOrEmpty(msgInfo.getCreateBy())){
			hql.append(" and createBy like '%"+msgInfo.getCreateBy()+"%'");
		}
		if(!CommonUtil.isNullOrEmpty(msgInfo.getMsgType())){
			hql.append(" and msgType="+msgInfo.getMsgType());
		}
		if(!CommonUtil.isNullOrEmpty(msgInfo.getStatus())){
			hql.append(" and status="+msgInfo.getStatus());
		}else{
			hql.append(" and ((status!=2 and status!=3) or status is null)");
		}
		if(!CommonUtil.isNullOrEmpty(msgInfo.getStartTime())){
			hql.append(" and createDate like '%"+msgInfo.getStartTime()+"%' ");
		}
		if(!CommonUtil.isNullOrEmpty(msgInfo.getMenuId())){
			hql.append(" and menuId='"+msgInfo.getMenuId()+"' ");
		}
		if(!CommonUtil.isNullOrEmpty(msgInfo.getRemark())){
			hql.append(" and remark='"+msgInfo.getRemark()+"' ");
		}
		if(!CommonUtil.isNullOrEmpty(msgInfo.getDocId())){
			hql.append(" and docId='"+msgInfo.getDocId()+"' ");
		}
		hql.append(" order by createDate desc");
		if(pageInfo==null){
			List<MsgInfo> mlist=new ArrayList<MsgInfo>();
			MsgInfo m=new MsgInfo();
			m.setUuid(super.findUnique(hql.toString()).toString());
			mlist.add(m);
			return mlist;
		}
		return super.find(hql.toString(), new String[] {}, pageInfo.getpageIndex(), pageInfo.getPageSize());
	}

	@Override
	public void insertMsgBySql(MsgInfo m) {
		String sql = "insert into ht_message_msg_info (create_by,create_date,docId,menuId,msgTitle,msgType,uuid,userid,status)";
		sql = sql + " values('"+m.getCreateBy()+"','"+getDate()+"','"+m.getDocId()+"','"+m.getMenuId()+"','"+m.getMsgTitle()+"','"+m.getMsgType()+"','"+UUID.randomUUID().toString()+"','"+m.getUserId()+"','0')";
		this.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}
	
	public String getDate(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		return df.format(new Date());
	}

}
