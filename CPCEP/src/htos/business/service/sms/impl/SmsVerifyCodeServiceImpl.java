package htos.business.service.sms.impl;

import htos.business.dao.sms.SmsVerifyCodeDao;
import htos.business.entity.sms.SmsVerifyCode;
import htos.business.service.sms.SmsVerifyCodeService;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author qinj
 * @date 2018-05-09 23:24
 **/
public class SmsVerifyCodeServiceImpl implements SmsVerifyCodeService {

	// 绑定短信前缀
	public static String MSG_PREFIX_BIND = "您的动态验证码为：";

	private String sendUrl = "http://sdk.zyer.cn/SmsService/SmsService.asmx/SendEx";
	private String sendLoginName = "meigaos";
	private String sendLoginPassword = "passw0rd.";
	private String smsKind = "807";

	/**
	 * @description 发送验证码
	 * @author qinj
	 * @date 2018/5/9 23:30
	 */
	@Override
	public String send(String mobile) {
		// TODO
		if(StringUtils.isBlank(mobile))
			throw new RuntimeException("请填写正确的手机号!");
		Date now = new Date();

		// 查找最近的验证码
		SmsVerifyCode existCode = this.findLastVerifyCode(mobile, now);
		if(existCode != null)
			return existCode.getCode();	// 存在有效的验证码

		String dynamicCode = this.getVerifyCode(6);
		String text = "您好," + MSG_PREFIX_BIND + dynamicCode
				+ ",请勿向任何人提供您收到的短信动态码  回TD退订【帝芬格恩】";
		boolean success = sendVerifyCode(mobile, text);
		if(!success)
			throw new RuntimeException("验证码发送失败!");
		save(mobile, dynamicCode);
		return dynamicCode;
	}

	/**
	 * @description 校验验证码
	 * @author qinj
	 * @date 2018/5/9 23:30
	 * @param mobile 	  手机号
	 * @param verifyCode 验证码
	 */
	@Override
	public boolean checkVerifyCode(String mobile, String verifyCode) {
		// TODO
		String hql = "select count(*) from SmsVerifyCode where mobile=? and useFlag=false and code=?";
		Number count = (Number) smsVerifyCodeDao.findUnique(hql, new Object[]{mobile, verifyCode});
		if(count.intValue() > 0)
			return true;
		return false;
	}


	///////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	//
	//

	private void save(String mobile, String dynamicCode) {
		Date now = new Date();
		SmsVerifyCode code = new SmsVerifyCode();
		code.setUseFlag(false);
		code.setCode(dynamicCode);
		code.setMobile(mobile);
		code.setSendTime(now);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.MINUTE, 10);
		code.setValidTime(calendar.getTime());
		smsVerifyCodeDao.save(code);
	}

	/**
	 * @description 查找最近发送的验证码
	 * @author qinj
	 * @date 2018/5/10 22:15
	 */
	private SmsVerifyCode findLastVerifyCode(String mobile, Date validTime) {
		String hql = "from SmsVerifyCode where mobile=? and validTime>? and useFlag=false order by sendTime";
		List<SmsVerifyCode> list = smsVerifyCodeDao.find(hql, new Object[]{mobile, validTime});
		if(list == null || list.isEmpty())
			return null;
		// 清楚多余的有效验证码
		SmsVerifyCode sendCode = list.remove(0);
		if(!list.isEmpty()) {
			for (SmsVerifyCode code : list) {
				code.setUseFlag(true);
				smsVerifyCodeDao.update(code);
			}
		}
		return sendCode;
	}

	private boolean sendVerifyCode(String mobile, String text) {
		// TODO
		try {
//			HttpClient client = new HttpClient();
//			PostMethod post = new PostMethod(sendUrl);
//			post.addRequestHeader("Content-Type",
//					"application/x-www-form-urlencoded;charset=UTF-8");
//
//			NameValuePair[] data = {
//					new NameValuePair("LoginName", sendLoginName),
////					new NameValuePair("Password", digest(sendLoginPassword)),
//					new NameValuePair("SmsKind", smsKind), // 默认为808
//					// 号码
//					new NameValuePair("SendSim", mobile),
//					new NameValuePair("ExpSmsId", ""), // 默认为空
//					// 内容
//					new NameValuePair("MsgContext", text) };
//
//			post.setRequestBody(data);
//			client.executeMethod(post);
//			String result = new String(post.getResponseBodyAsString().getBytes(
//					"UTF-8"));
//			// 释放资源
//			post.releaseConnection();
//			return getSmsError(result);
			return true;
		} catch (Exception e) {
//			log.error("发送短信失败！", e);
			return false;
		}

	}

	/**
	 * 获取length位的动态验证码
	 *
	 * @param length
	 * @return
	 */
	private String getVerifyCode(int length) {
		String[] beforeShuffle = new String[] { "0", "1", "2", "3", "4", "5",
				"6", "7", "8", "9" };
		int len = beforeShuffle.length;
		List<String> list = Arrays.asList(beforeShuffle); // 随机产生验证码
		Collections.shuffle(list);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
		String afterShuffle = sb.toString();

		if (length > len)
			length = len;
		return afterShuffle.substring(0, length); // 取出length位
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	//
	//

	private SmsVerifyCodeDao smsVerifyCodeDao;

	public void setSmsVerifyCodeDao(SmsVerifyCodeDao smsVerifyCodeDao) {
		this.smsVerifyCodeDao = smsVerifyCodeDao;
	}
}
