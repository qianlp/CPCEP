package htos.common.util;
import java.util.regex.Pattern;

import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.connection.IHttpClient;
import cn.jiguang.common.connection.NativeHttpClient;
import cn.jiguang.common.connection.NettyHttpClient;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jiguang.common.utils.Preconditions;
import cn.jiguang.common.utils.StringUtils;

import com.google.gson.JsonObject;

public class SMSClient {

	private static String SMS_CODE = "code";

	private String _baseUrl;
	private String _smsCodePath;
	private String _validPath;
	private String _voiceCodePath;
	private String _shortMsgPath;
	private String _schedulePath;
    private String _accountPath;
    private String _tempMsgPath;
	private IHttpClient _httpClient;

	public SMSClient(String masterSecret, String appkey) {
        this(masterSecret, appkey, null, JSMSConfig.getInstance());
	}

	public SMSClient(String masterSecret, String appkey, HttpProxy proxy, JSMSConfig conf) {
		ServiceHelper.checkBasic(appkey, masterSecret);

		_baseUrl = (String) conf.get(JSMSConfig.API_HOST_NAME);
		_smsCodePath = (String) conf.get(JSMSConfig.CODE_PATH);
		_validPath = (String) conf.get(JSMSConfig.VALID_PATH);
		_voiceCodePath = (String) conf.get(JSMSConfig.VOICE_CODE_PATH);
		_shortMsgPath = (String) conf.get(JSMSConfig.SHORT_MESSAGE_PATH);
        _tempMsgPath = (String) conf.get(JSMSConfig.TEMPlATE_MESSAGE_PATH);
		_schedulePath = (String) conf.get(JSMSConfig.SCHEDULE_PATH);
        _accountPath = (String) conf.get(JSMSConfig.ACCOUNT_PATH);
		String authCode = ServiceHelper.getBasicAuthorization(appkey, masterSecret);
        this._httpClient = new NativeHttpClient(authCode, proxy, conf.getClientConfig());
	}

	/**
	 * Send SMS verification code to mobile
	 * @param payload include two parameters: mobile number and templete id. The second parameter is optional.
	 * @return return SendSMSResult which includes msg_id
	 * @throws APIConnectionException connection exception
	 * @throws APIRequestException request exception
	 */
	public SendSMSResult sendSMSCode(SMSPayload payload)
		throws APIConnectionException, APIRequestException {
		Preconditions.checkArgument(null != payload, "SMS payload should not be null");

		ResponseWrapper response = _httpClient.sendPost(_baseUrl + _smsCodePath, payload.toString());
		return SendSMSResult.fromResponse(response, SendSMSResult.class);
	}
	
	/**
	 * Send template SMS to mobile
	 * @param payload payload includes mobile, temp_id and temp_para, the temp_para is a map,
	 *                which's key is what you had set in jiguang portal
	 * @return return SendSMSResult which includes msg_id 包括msg id的SendSMSResult
	 * @throws APIConnectionException  connection exception
	 * @throws APIRequestException request exception
	 */
	public SendSMSResult sendTemplateSMS(SMSPayload payload)
			throws APIConnectionException, APIRequestException {
		Preconditions.checkArgument(null != payload, "SMS payload should not be null");

		ResponseWrapper response = _httpClient.sendPost(_baseUrl + _shortMsgPath, payload.toString());
		return SendSMSResult.fromResponse(response, SendSMSResult.class);
	}

    /**
     * Send a batch of template SMS
     * @param payload BatchSMSPayload
     * @return BatchSMSResult
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
	
	public void setHttpClient(IHttpClient client) {
		this._httpClient = client;
	}
 
	// 如果使用 NettyHttpClient，在发送请求后需要手动调用 close 方法
	public void close() {
		if (_httpClient != null && _httpClient instanceof NettyHttpClient) {
			((NettyHttpClient) _httpClient).close();
		}
	}
   
	/**
	 * Send SMS verification code to server, to verify if the code valid                 发送短信到服务器，验证代码是否有效
	 * @param msgId The message id of the verification code                                验证码消息的ID
	 * @param code Verification code                                                       验证码
	 * @return return ValidSMSResult includes is_valid                                  返回一个ValidSMSResult是有效的
	 * @throws APIConnectionException connection exception
	 * @throws APIRequestException request exception
	 */
	public ValidSMSResult sendValidSMSCode(String msgId, String code)
		throws APIConnectionException, APIRequestException {
		//Preconditions.checkArgument(null != msgId, "Message id should not be null");
		//Pattern codePattern = Pattern.compile("^[0-9]{6}");
		//Preconditions.checkArgument(codePattern.matcher(code).matches(), "The verification code shoude be consist of six number");
		JsonObject json = new JsonObject();
		json.addProperty(SMS_CODE, code);

		ResponseWrapper response = _httpClient.sendPost(_baseUrl + _smsCodePath + "/" + msgId + _validPath, json.toString());
		return ValidSMSResult.fromResponse(response, ValidSMSResult.class);
	}


}
