package htos.business.utils;

/**
 * @author qinj
 * @date 2018-02-07 22:01
 **/
public class BusinessConstants {

	// 账号类型
	public static final int USER_TYPE_ADMIN 	= 0;	// 超级管理员
	public static final int USER_TYPE_SUPPLIER	= 2;	// 供应商

	// 账号审核状况
	public static final String ACCOUNT_CHECK_WAITING = "9";	// 等待审核
	public static final String ACCOUNT_CHECK_FAILED  = "8";	// 审核失败
	public static final String ACCOUNT_CHECK_SUCCESS = "2";	// 审核成功

	// 供应商附件类型
	public static final short SUPPLIER_ATTACHEMENT_PERFORMANCE 			= 1;	// 业绩统计表
	public static final short SUPPLIER_ATTACHEMENT_PERFORMANCE_PROVE	= 2;	// 业绩证明文件
	public static final short SUPPLIER_ATTACHEMENT_OTHER				= 3;	// 其他附件文件
	public static final short SUPPLIER_ATTACHEMENT_LICENSE				= 4;	// 营业执照
	public static final short SUPPLIER_ATTACHEMENT_TAX_CERTIFICATE		= 5;	// 税务登记证
	public static final short SUPPLIER_ATTACHEMENT_ORG_CODE				= 6;	// 组织机构代码证
	public static final short SUPPLIER_ATTACHEMENT_CERTIFICATE			= 7;	// 专业能力资质证书

	// 供应商报名状态
	public static final short SUPPLIER_TRADER_DRAFT 		 = 0;	// 草稿
	public static final short SUPPLIER_TRADER_UNSIGNUP 		 = 1;	// 未报名
	public static final short SUPPLIER_TRADER_SIGNUP 		 = 9;	// 已报名
	public static final short SUPPLIER_TRADER_SIGNUP_FAILED  = 8;	// 审核失败
	public static final short SUPPLIER_TRADER_SIGNUP_SUCCESS = 2;	// 审核通过

	public static final short BID_STATUS_UNOPEN 	= 0;	// 未开标
	public static final short BID_STATUS_OPENING	= 1;	// 已开标
	public static final short BID_STATUS_CLOSE		= 2;	// 开标结束
}
