package htos.sysfmt.message.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import htos.common.entity.PageInfo;
import htos.common.util.StringUtil;
import htos.coresys.entity.User;
import htos.coresys.service.UserService;
import htos.sysfmt.message.dao.MsgInfoDao;
import htos.sysfmt.message.entity.MsgInfo;
import htos.sysfmt.message.service.MsgInfoService;

public class MsgInfoServiceImpl implements MsgInfoService {
	private MsgInfoDao msgInfoDao;
	private UserService userService;
	
	@Override
	public List<MsgInfo> getMsgList(MsgInfo msgInfo) {
		
		return msgInfoDao.getMsgList(msgInfo);
	}

	@Override
	public void saveMsg(MsgInfo msgInfo) {
		msgInfoDao.save(msgInfo);
		String title = msgInfo.getMsgTitle();
		if(!StringUtil.isEmpty(msgInfo.getRemark())){
			title+=msgInfo.getRemark();
		}
		this.saveToMsgWX(msgInfo.getUserId(),title);
	}
	
	@Override
	public void insertMsgBySql(MsgInfo m) {
		msgInfoDao.insertMsgBySql(m);
	}

	@Override
	public void saveToMsgWX(String userId,String title){
		User user = userService.findUserById(userId);
		if(user!=null && !StringUtil.isEmpty(user.getUserPerEname())){
			//ComUtil com = new ComUtil();
			//com.Send_txtmsg(WechatProperties.getWXStr("AgentId"), user.getUserPerEname(), "", "", "text", title);
		}
	}

	@Override
	public void updateMsg(MsgInfo msgInfo) {
		msgInfoDao.update(msgInfo);
	}

	@Override
	public void delMsg(MsgInfo msgInfo) {
		msgInfoDao.delete(msgInfo);
	}

	/**
	 * @return the msgInfoDao
	 */
	public MsgInfoDao getMsgInfoDao() {
		return msgInfoDao;
	}

	/**
	 * @param msgInfoDao the msgInfoDao to set
	 */
	public void setMsgInfoDao(MsgInfoDao msgInfoDao) {
		this.msgInfoDao = msgInfoDao;
	}

	/* (non-Javadoc)
	 * @see htos.sysfmt.message.service.MsgInfoService#getMsgList(java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<MsgInfo> getMsgList(String docId, Integer msgType) {
		return msgInfoDao.getMsgList(docId, msgType);
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Map<String, Object> getMsgList(PageInfo pageInfo, MsgInfo msgInfo) {
		List<MsgInfo> mList=msgInfoDao.getMsgList(pageInfo, msgInfo);
		Map<String,Object> mo=new HashMap<String,Object>();
		if(mList!=null){
			mo.put("total", msgInfoDao.getMsgList(null, msgInfo).get(0).getUuid());
			mo.put("data", mList);
		}else{
			mo.put("total", 0);
			mo.put("data", new ArrayList<MsgInfo>());
		}
		return mo;
	}


	
	
}
