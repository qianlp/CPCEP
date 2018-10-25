package htos.business.utils.ajax;

/**
 * @author qinj
 * @date 2018-02-05 23:10
 **/
public enum AjaxCode {


	SUCCESS(200, "操作成功!"),
	SERVER_ERROR(500, "系统异常，请稍后重试!"),

	/////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// 注册相关
	//
	REGISTER_USERNAME_EXIST(1000, "用户名已存在!"),

	/////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// 文件上传相关
	//
	FILE_UPLOAD_FAILED(5000, "文件上传失败!");




	private int code;
	private String message;

	private AjaxCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}
}
