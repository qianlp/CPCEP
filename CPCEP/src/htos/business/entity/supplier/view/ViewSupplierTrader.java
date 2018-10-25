package htos.business.entity.supplier.view;

import htos.business.utils.BusinessConstants;

import java.util.Date;

public class ViewSupplierTrader {

	private String supplierName;
	private String supplier;
	private String signupId;
	private String tradeName;
	private String name;
	private String code;
	private String purchasePlanUuid;
	private String publishMedia;
	private Date registerStartTime;
	private Date registerEndTime;
	private Date bidStartTime;
	private Date bidEndTime;
	private Date bidOpenTime;
	private String status;
	private String id;
	private String wfStatus;
	private String contact;
	private String position;
	private String phone;
	private String mobile;
	private String email;

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWfStatus() {
		return wfStatus;
	}

	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPurchasePlanUuid() {
		return purchasePlanUuid;
	}

	public void setPurchasePlanUuid(String purchasePlanUuid) {
		this.purchasePlanUuid = purchasePlanUuid;
	}

	public String getPublishMedia() {
		return publishMedia;
	}

	public void setPublishMedia(String publishMedia) {
		this.publishMedia = publishMedia;
	}

	public Date getRegisterStartTime() {
		return registerStartTime;
	}

	public void setRegisterStartTime(Date registerStartTime) {
		this.registerStartTime = registerStartTime;
	}

	public Date getRegisterEndTime() {
		return registerEndTime;
	}

	public void setRegisterEndTime(Date registerEndTime) {
		this.registerEndTime = registerEndTime;
	}

	public Date getBidStartTime() {
		return bidStartTime;
	}

	public void setBidStartTime(Date bidStartTime) {
		this.bidStartTime = bidStartTime;
	}

	public Date getBidEndTime() {
		return bidEndTime;
	}

	public void setBidEndTime(Date bidEndTime) {
		this.bidEndTime = bidEndTime;
	}

	public Date getBidOpenTime() {
		return bidOpenTime;
	}

	public void setBidOpenTime(Date bidOpenTime) {
		this.bidOpenTime = bidOpenTime;
	}

	public String getStatus() {
		if(status == null)
			return BusinessConstants.SUPPLIER_TRADER_UNSIGNUP + "";
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getSignupId() {
		return signupId;
	}

	public void setSignupId(String signupId) {
		this.signupId = signupId;
	}
}
