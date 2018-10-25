package htos.common.util;


import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;



public class JPushSMSUtil {
	
	protected static final Logger LOG = LoggerFactory.getLogger(JPushSMSUtil.class);
	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("mobile");
	private static final String appkey=getSessionattachmenttitle("appKey");
	private static final String masterSecret =getSessionattachmenttitle("masterSecret");
	private static final String devKey =getSessionattachmenttitle("devKey");
	private static final String devSecret=getSessionattachmenttitle("devSecret");
	private static final String tempId=getSessionattachmenttitle("tempId");
	private static SMSClient client = new SMSClient(masterSecret, appkey);
	//通过模板发送短信
	public static void SendTemplateSMS(Map<String,String> param,String mobile){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 SMSPayload payload = SMSPayload.newBuilder()
				              .setMobileNumber(mobile)
				              .setTempId(Integer.valueOf(tempId))
				             .addTempPara("companyName", param.get("company"))//公司名称
		                      .addTempPara("projectName",  param.get("projectName"))//项目名称
		                      .addTempPara("day", param.get("day"))//开标距离天数
		                      .build();
		 try {
	            SendSMSResult res = client.sendTemplateSMS(payload);
	            LOG.info(res.toString());
	        } catch (APIRequestException e) {
	        	System.out.println(e);
	            LOG.error("Error response from JPush server. Should review and fix it. ", e);
	            LOG.info("HTTP Status: " + e.getStatus());
	            LOG.info("Error Message: " + e.getMessage());
	        } catch (APIConnectionException e) {
	            LOG.error("Connection error. Should retry later. ", e);
	        }
	}
	//发送短信验证码
	public static SendSMSResult sendCode(String mobile){
			
			 SMSPayload payload = SMSPayload.newBuilder()
						           .setMobileNumber(mobile)
						           .build();
			 SendSMSResult sendSMSCode =null;
			try{
				 sendSMSCode = client.sendSMSCode(payload);
			} catch (APIConnectionException e) {
	            LOG.error("Connection error. Should retry later. ", e);
	        } catch (APIRequestException e) {
	            LOG.error("Error response from JPush server. Should review and fix it. ", e);
	            LOG.info("HTTP Status: " + e.getStatus());
	            LOG.info("Error Message: " + e.getMessage());
	        }

			return sendSMSCode;
	}
	//验证短信验证码
	public static ValidSMSResult sendValidResult(String msgId, String code,String mobile){
		SMSPayload spay=SMSPayload.newBuilder()
		        .setMobileNumber(mobile)
		        .build();
		 ValidSMSResult res =null;
		try{
		 res = client.sendValidSMSCode(msgId, code);
		} catch (APIConnectionException e) {
	        LOG.error("Connection error. Should retry later. ", e);
	    } catch (APIRequestException e) {
	        LOG.error("Error response from JPush server. Should review and fix it. ", e);
	        LOG.info("HTTP Status: " + e.getStatus());
	        LOG.info("Error Message: " + e.getMessage());    
	    }
		return res;
	}
		
	public static final String getSessionattachmenttitle(String sessionName) {
		return bundle.getString(sessionName);
	}
}