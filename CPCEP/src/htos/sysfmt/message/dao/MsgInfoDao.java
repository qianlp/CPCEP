package htos.sysfmt.message.dao;

import java.util.List;

import htos.common.entity.PageInfo;
import htos.coresys.dao.BaseDao;
import htos.sysfmt.message.entity.MsgInfo;

public interface MsgInfoDao extends BaseDao<MsgInfo> {
	List<MsgInfo> getMsgList(MsgInfo msgInfo);
	List<MsgInfo> getMsgList(PageInfo pageInfo,MsgInfo msgInfo);
	List<MsgInfo> getMsgList(String docId,Integer msgType);
	void insertMsgBySql(MsgInfo m);
}
