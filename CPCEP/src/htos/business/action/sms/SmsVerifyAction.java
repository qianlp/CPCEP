package htos.business.action.sms;

import htos.business.service.sms.SmsVerifyCodeService;
import htos.common.util.JPushSMSUtil;
import htos.common.util.SendSMSResult;
import htos.common.util.StringUtil;
import htos.common.util.ValidSMSResult;
import htos.coresys.action.BaseAction;
import htos.coresys.entity.User;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.util.CommonUtil;
import htos.coresys.util.MD5Util;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

/**
 * @author qinj
 * @date 2018-05-09 23:25
 **/
public class SmsVerifyAction extends BaseAction {
   private static String msgId;
   private CommonFacadeService<User> userCommonFacadeService;

	public void sendSms() {
		String mobile = request.getParameter("mobile");
		//String verifyCode = smsVerifyCodeService.send(mobile);
		SendSMSResult sendCode = JPushSMSUtil.sendCode(mobile);
		Map<String, Object> map = new HashMap<String, Object>();
		//AjaxUtils.renderData(verifyCode);
		map.put("msgId", sendCode.getMessageId());
		//map.put("code", sendCode.getResponseCode());
		//System.out.println("验证码"+sendCode.getResponseCode());
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), map);
	}
	/**
	 * 验证手机号
	 */
	public void checkPhon(){
		String mobile = request.getParameter("mobile");
		Map<String, String> map =new HashMap<>();
		User user = userCommonFacadeService.getEntityByProperty("User", "userTelephone", mobile);
		if(user!=null){
			map.put("msg", "手机号已注册");
		}
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), map);
	}
	//验证验证码
    public void checkCode(){
    	String verifyCode = request.getParameter("verifyCode");
    	String sendVerifyMsgId = request.getParameter("sendVerifyMsgId");
    	String mobile = request.getParameter("mobile");
    	Map<String, Object> map = new HashMap<String, Object>();
    	ValidSMSResult validResult =null;
    try{
    	validResult = JPushSMSUtil.sendValidResult(sendVerifyMsgId, verifyCode, mobile);
    	if(validResult == null || validResult.equals("")){
    		map.put("message", "fail");
    	}else if(validResult.getIsValid()){
      	  map.put("message", "success");  
        }else{
      	  map.put("message", "fail");  
        }
     }catch(Exception e){
    	 map.put("message", "error");
    	 //CommonUtil.toJsonStr(ServletActionContext.getResponse(), map);	 
     }
      
       
      CommonUtil.toJsonStr(ServletActionContext.getResponse(), map);
    }
	////////////////////////////////////////////////////////////////////////////////////////
	//
	//
	//
	   public void updatePWd(){
		   Map<String, Object> map = new HashMap<String, Object>();
		   String mobile = request.getParameter("mobile");
		   String password = request.getParameter("password");
		   User user = userCommonFacadeService.getEntityByProperty("User", "userTelephone", mobile);
		   if(user==null){
			   map.put("message", "用户未注册");
		   }else{
			   user.setUserPassword(MD5Util.EncoderByMd5(password));
			   userCommonFacadeService.update(user);
			   map.put("message", "修改成功");
		   }
		   CommonUtil.toJsonStr(ServletActionContext.getResponse(), map);
	   }
	private SmsVerifyCodeService smsVerifyCodeService;

	public void setSmsVerifyCodeService(SmsVerifyCodeService smsVerifyCodeService) {
		this.smsVerifyCodeService = smsVerifyCodeService;
	}
	public CommonFacadeService<User> getUserCommonFacadeService() {
		return userCommonFacadeService;
	}
	public void setUserCommonFacadeService(
			CommonFacadeService<User> userCommonFacadeService) {
		this.userCommonFacadeService = userCommonFacadeService;
	}
	public SmsVerifyCodeService getSmsVerifyCodeService() {
		return smsVerifyCodeService;
	}
	
}
