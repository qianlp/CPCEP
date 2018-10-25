package htos.sysfmt.message.service;

import htos.common.entity.PageInfo;
import htos.sysfmt.message.entity.MsgInfo;

import java.util.List;
import java.util.Map;

public interface MsgInfoService {
	List<MsgInfo> getMsgList(MsgInfo msgInfo);
	Map<String,Object> getMsgList(PageInfo pageInfo,MsgInfo msgInfo);
	List<MsgInfo> getMsgList(String docId,Integer msgType);
	void saveMsg(MsgInfo msgInfo);
	void updateMsg(MsgInfo msgInfo);
	void delMsg(MsgInfo msgInfo);
	
	//微信消息推送
	void saveToMsgWX(String userId, String title);
	void insertMsgBySql(MsgInfo m);
	
}
