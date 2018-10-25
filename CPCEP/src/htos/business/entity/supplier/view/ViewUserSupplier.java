package htos.business.entity.supplier.view;

import java.util.Date;

/**
 * @author qinj
 * @date 2018-02-06 20:41
 **/
public class ViewUserSupplier {

	private String uuid;
	private String id;
	private String checkStatus;
	private String wfStatus;
	private String curUser;
	private Date updateDate;
	// 登录信息
	private String username;
	private String password;

	// 扩展信息
	private String account;
	private String name;
	private String registerAddress;
	private String taxpayerNo;
	private String corporations;
	private String nature;
	private String registerCapital;
	private Date registerTime;
	private String contacts;
	private String contactAddress;
	private String phon;
	private String mobile;
	private String fax;
	private String email;
	private String license;
	private String taxCertificate;
	private String orgCode;
	private String certificate;

	// 开票信息
	private String billingName;
	private String billingBankAccount;
	private String billingBankName;
	private String billingTaxpayerId;
	private String billingAddress;
	private String billingPhone;

	// 业绩统计表
	private String[] performance;
	// 业绩证明文件
	private String[] performanceProve;
	// 其他附件
	private String[] otherAttachment;

	private String contact;
	private String position;
	private String phone;
	private Boolean signup;

	private Boolean enableFlag;

	private String licenseFile;
	private String taxCertificateFile;
	private String orgCodeFile;
	private String certificateFile;

	private String scopes;
	private String materialName;
	private String materialId;

	public String getScopes() {
		return scopes;
	}

	public void setScopes(String scopes) {
		this.scopes = scopes;
	}

	public String getLicenseFile() {
		return licenseFile;
	}

	public void setLicenseFile(String licenseFile) {
		this.licenseFile = licenseFile;
	}

	public String getTaxCertificateFile() {
		return taxCertificateFile;
	}

	public void setTaxCertificateFile(String taxCertificateFile) {
		this.taxCertificateFile = taxCertificateFile;
	}

	public String getOrgCodeFile() {
		return orgCodeFile;
	}

	public void setOrgCodeFile(String orgCodeFile) {
		this.orgCodeFile = orgCodeFile;
	}

	public String getCertificateFile() {
		return certificateFile;
	}

	public void setCertificateFile(String certificateFile) {
		this.certificateFile = certificateFile;
	}

	public Boolean getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(Boolean enableFlag) {
		this.enableFlag = enableFlag;
	}

	public Boolean getSignup() {
		return signup;
	}

	public void setSignup(Boolean signup) {
		this.signup = signup;
	}

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegisterAddress() {
		return registerAddress;
	}

	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}

	public String getTaxpayerNo() {
		return taxpayerNo;
	}

	public void setTaxpayerNo(String taxpayerNo) {
		this.taxpayerNo = taxpayerNo;
	}

	public String getCorporations() {
		return corporations;
	}

	public void setCorporations(String corporations) {
		this.corporations = corporations;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getRegisterCapital() {
		return registerCapital;
	}

	public void setRegisterCapital(String registerCapital) {
		this.registerCapital = registerCapital;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getPhon() {
		return phon;
	}

	public void setPhon(String phon) {
		this.phon = phon;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getTaxCertificate() {
		return taxCertificate;
	}

	public void setTaxCertificate(String taxCertificate) {
		this.taxCertificate = taxCertificate;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getBillingName() {
		return billingName;
	}

	public void setBillingName(String billingName) {
		this.billingName = billingName;
	}

	public String getBillingBankAccount() {
		return billingBankAccount;
	}

	public void setBillingBankAccount(String billingBankAccount) {
		this.billingBankAccount = billingBankAccount;
	}

	public String getBillingBankName() {
		return billingBankName;
	}

	public void setBillingBankName(String billingBankName) {
		this.billingBankName = billingBankName;
	}

	public String getBillingTaxpayerId() {
		return billingTaxpayerId;
	}

	public void setBillingTaxpayerId(String billingTaxpayerId) {
		this.billingTaxpayerId = billingTaxpayerId;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getBillingPhone() {
		return billingPhone;
	}

	public void setBillingPhone(String billingPhone) {
		this.billingPhone = billingPhone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String[] getPerformance() {
		return performance;
	}

	public void setPerformance(String[] performance) {
		this.performance = performance;
	}

	public String[] getPerformanceProve() {
		return performanceProve;
	}

	public void setPerformanceProve(String[] performanceProve) {
		this.performanceProve = performanceProve;
	}

	public String[] getOtherAttachment() {
		return otherAttachment;
	}

	public void setOtherAttachment(String[] otherAttachment) {
		this.otherAttachment = otherAttachment;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getWfStatus() {
		return wfStatus;
	}

	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
	}

	public String getCurUser() {
		return curUser;
	}

	public void setCurUser(String curUser) {
		this.curUser = curUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	
	
	
}
