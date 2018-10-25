package htos.business.service.sms;

public interface SmsVerifyCodeService {

	String send(String mobile);

	boolean checkVerifyCode(String mobile, String verifyCode);

}
